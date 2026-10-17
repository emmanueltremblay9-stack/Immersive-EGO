from pathlib import Path
import json,math,re
p=Path(__file__).parent
rows=json.loads((p/'client-samples.json').read_text())
rows=[r for r in rows if (r.get('label') or '').startswith('THERMAL_') and r['label'].endswith(('_T0','_T1'))]
by={r['label']:r for r in rows};checks=[];results=[]
def check(name,expected,actual,tolerance=1e-12):
 ok=(abs(expected-actual)<=tolerance) if isinstance(expected,(int,float)) else expected==actual
 checks.append({'name':name,'expected':expected,'observed':actual,'tolerance':tolerance if isinstance(expected,(int,float)) else None,'pass':ok})
for climate,raw_heat,raw_cold in [('HEAT',.87,.195),('COLD',.074,1.0)]:
 for label,t in [('T1',1),('T2',2),('T05',.5),('T025',.25),('T4',4)]:
  name=f'THERMAL_{climate}_{label}';a=by[name+'_T0'];b=by[name+'_T1'];s0=a['ego_state'];s1=b['ego_state'];o0=a['ego_origin_state'];o1=b['ego_origin_state']
  heat=min(1,raw_heat/t);cold=min(1,raw_cold/t);strain=max(heat,cold)
  check(name+' duration ticks',100,b['tick']-a['tick']);check(name+' phase',1,a['tick']%20)
  for suffix,r in [('T0',a),('T1',b)]:
   check(name+' '+suffix+' effective tolerance',t*1000,r['thermal_tolerance_milli'])
   check(name+' '+suffix+' dry W',0,r['wetness']['wetness'])
   check(name+' '+suffix+' arid origin','arid',r['ego_origin_state']['origin_id'])
   o=r['ego_origin_state'];expected=max(0,strain-.08*max(o['heat_acclimation'],o['cold_acclimation']))
   check(name+' '+suffix+' final thermal strain',expected,r['ego_state']['thermal_strain'])
  check(name+' fatigue stable',s0['fatigue'],s1['fatigue'])
  # Physical fixture y=200, Overworld sea level=63, dry, respiration efficiency=1.
  raw_resp=(41/128)*(.8/3.5*.65+.35)
  check(name+' raw respiration reconstruction',raw_resp,s0['respiratory_strain']+.06*o0['altitude_acclimation'])
  water_loss=5*(.00025+strain*.00075+raw_resp*.0005+s0['fatigue']*.00025)
  check(name+' hydration loss over five frames',water_loss,s0['hydration']-s1['hydration'])
  targets={'heat_acclimation':.7*heat+.3*.75,'cold_acclimation':.7*cold+.3*.15,'altitude_acclimation':.3*.2,'humidity_acclimation':.3*.1}
  for axis,target in targets.items():
   expected=target+(o0[axis]-target)*(.988**5)
   check(name+' '+axis+' five-frame recurrence',expected,o1[axis])
  results.append({'scenario':name,'tolerance':t,'ticks':100,'raw_heat':raw_heat,'raw_cold':raw_cold,'adjusted_heat':heat,'adjusted_cold':cold,'adjusted_strain':strain,'hydration_loss':s0['hydration']-s1['hydration'],'heat_acclimation_t0':o0['heat_acclimation'],'heat_acclimation_t1':o1['heat_acclimation'],'cold_acclimation_t0':o0['cold_acclimation'],'cold_acclimation_t1':o1['cold_acclimation'],'state_strain_t0':s0['thermal_strain'],'state_strain_t1':s1['thermal_strain']})
failed=[c for c in checks if not c['pass']]
report={'candidate':json.loads((p/'candidate-identity.json').read_text()),'samples':len(rows),'scenarios':results,'checks':checks,'failed':failed,'pass':not failed}
(p/'client-numeric-checks-reproduced.json').write_text(json.dumps(report,indent=2))
print(json.dumps({'samples':len(rows),'scenarios':len(results),'checks':len(checks),'failed':failed},indent=2))
for r in results:print(r['scenario'],'adjusted',r['adjusted_strain'],'H loss',r['hydration_loss'])
assert not failed
