package com.oblixorprime.immersiveego.gametest;

import com.mojang.authlib.GameProfile;
import com.oblixorprime.immersiveego.ImmersiveEgo;
import com.oblixorprime.immersiveego.config.*;
import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.environment.*;
import com.oblixorprime.immersiveego.registry.*;
import com.oblixorprime.immersiveego.wetness.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.UUID;
import net.minecraft.gametest.framework.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.gametest.*;

@GameTestHolder(ImmersiveEgo.MOD_ID)
@PrefixGameTestTemplate(false)
public final class EgoWetnessGameTests {
    private EgoWetnessGameTests() {}
    private static void check(boolean condition, String message) {
        if (!condition) throw new IllegalStateException(message);
    }
    private static ServerPlayer player(GameTestHelper helper) {
        return new ServerPlayer(helper.getLevel().getServer(),helper.getLevel(),
                new GameProfile(UUID.randomUUID(),"ego-wetness"),ClientInformation.createDefault());
    }
    private static void mode(ServerPlayer player, GameType type) {
        CompoundTag tag=new CompoundTag(); player.saveWithoutId(tag);
        tag.putInt("playerGameType",type.getId()); player.loadGameTypes(tag);
        check(player.gameMode.getGameModeForPlayer()==type,"Native mode fixture must match requested mode");
    }
    private static EgoWetnessState wet(double w) { return EgoWetnessState.dry().withWetness(w); }

    // Only temporary test configuration: restore the actual server configuration in finally.
    private static void active(GameTestHelper helper, Runnable scenario) throws IOException {
        Path temporary=Files.createTempDirectory("ego-wetness-gametest-");
        Path settings=temporary.resolve("immersive_ego/simulation.toml");
        Files.createDirectories(settings.getParent());
        Files.writeString(settings,"[scheduler]\nenabled=true\nintervalTicks=20\n");
        boolean core=EgoCoreConfig.ENABLE_SIMULATION.get();
        boolean notices=EgoCoreConfig.SHOW_WETNESS_NOTICES.get();
        var environment=EgoServerModuleConfigs.byKey("environment").orElseThrow().enabled();
        boolean module=environment.get();
        try {
            EgoCoreConfig.ENABLE_SIMULATION.set(true); environment.set(true);
            EgoCoreConfig.SHOW_WETNESS_NOTICES.set(false);
            EgoDynamicServerConfigLoader.reload(temporary);
            scenario.run();
        } finally {
            EgoCoreConfig.ENABLE_SIMULATION.set(core); environment.set(module);
            EgoCoreConfig.SHOW_WETNESS_NOTICES.set(notices);
            EgoDynamicServerConfigLoader.reload(helper.getLevel().getServer()
                    .getWorldPath(LevelResource.ROOT).resolve("serverconfig"));
            Files.deleteIfExists(settings); Files.deleteIfExists(settings.getParent()); Files.deleteIfExists(temporary);
        }
    }

    @GameTest(template="empty",timeoutTicks=20)
    public static void wetness_persistence_preserves_other_state_and_legacy_default(GameTestHelper h) {
        Player original=h.makeMockPlayer(GameType.SURVIVAL);
        EgoState ego=EgoState.defaultState().withDisplayValue(EgoAttributeCatalog.HYDRATION,.31);
        original.setData(EgoAttachments.EGO_STATE.get(),ego);
        original.setData(EgoAttachments.EGO_WETNESS.get(),wet(.73));
        CompoundTag tag=new CompoundTag(); original.saveWithoutId(tag);
        Player loaded=h.makeMockPlayer(GameType.SURVIVAL); loaded.load(tag);
        check(loaded.getData(EgoAttachments.EGO_WETNESS.get()).equals(wet(.73)),"W round trip");
        check(loaded.getData(EgoAttachments.EGO_STATE.get()).equals(ego),"Other EGO state preserved");
        tag.getCompound("neoforge:attachments").remove("immersive_ego:wetness");
        Player legacy=h.makeMockPlayer(GameType.SURVIVAL); legacy.load(tag);
        check(legacy.getData(EgoAttachments.EGO_WETNESS.get()).equals(EgoWetnessState.dry()),"Legacy dry default");
        check(legacy.getData(EgoAttachments.EGO_STATE.get()).equals(ego),"Legacy EGO not reset");
        h.succeed();
    }

    @GameTest(template="empty",timeoutTicks=20)
    public static void wetness_invalid_and_future_data_are_localized_and_diagnosed(GameTestHelper h) {
        var serializer=new EgoWetnessSerializer(); Player p=h.makeMockPlayer(GameType.SURVIVAL);
        for(double value:new double[]{Double.NaN,Double.POSITIVE_INFINITY,-.2,1.2}) {
            CompoundTag bad=new CompoundTag();bad.putInt("schema_version",1);bad.putDouble("wetness",value);
            var result=serializer.read(p,bad,h.getLevel().registryAccess());
            check(result.quarantined()&&!result.supported(),"Invalid W must remain visibly quarantined");
            check(serializer.read(p,serializer.write(result,h.getLevel().registryAccess()),h.getLevel().registryAccess()).equals(result),"Quarantine survives saving");
        }
        CompoundTag bad=new CompoundTag();bad.putInt("schema_version",1);bad.putString("wetness","bad");
        check(serializer.read(p,bad,h.getLevel().registryAccess()).quarantined(),"Wrong type");
        bad.remove("wetness");check(serializer.read(p,bad,h.getLevel().registryAccess()).quarantined(),"Missing current W");
        CompoundTag future=new CompoundTag();future.putInt("schema_version",99);future.putDouble("wetness",.4);
        var result=serializer.read(p,future,h.getLevel().registryAccess());
        check(result.schemaVersion()==99 && result.wetness()==.4 && !result.supported(),"Future schema paused");
        check(serializer.read(p,new CompoundTag(),h.getLevel().registryAccess()).equals(EgoWetnessState.dry()),"Schema zero migration");
        var other=EgoState.defaultState().withDisplayValue(EgoAttributeCatalog.STAMINA,.43);
        p.setData(EgoAttachments.EGO_STATE.get(),other);p.setData(EgoAttachments.EGO_WETNESS.get(),wet(.5));
        CompoundTag saved=new CompoundTag();p.saveWithoutId(saved);
        saved.getCompound("neoforge:attachments").put("immersive_ego:wetness",future);
        Player loaded=h.makeMockPlayer(GameType.SURVIVAL);loaded.load(saved);
        check(loaded.getData(EgoAttachments.EGO_STATE.get()).equals(other),"Future W must not damage another attachment");
        check(!loaded.getData(EgoAttachments.EGO_WETNESS.get()).supported(),"Future W actually read through NeoForge");
        h.succeed();
    }

    @GameTest(template="empty",timeoutTicks=20)
    public static void g10_death_resets_without_restore_and_g10b_reacquires(GameTestHelper h) throws IOException {
        active(h,()->{
            ServerPlayer original=player(h),respawn=player(h);
            original.setData(EgoAttachments.EGO_WETNESS.get(),wet(.9));
            NeoForge.EVENT_BUS.post(new PlayerEvent.Clone(respawn,original,true));
            check(respawn.getData(EgoAttachments.EGO_WETNESS.get()).wetness()==0,"Death W=0");
            EgoWetnessRuntime.advancePlayerTick(respawn);
            check(respawn.getData(EgoAttachments.EGO_WETNESS.get()).wetness()==0,"No old exposure restoration");
            check(EgoWetnessRuntime.lastApplied(respawn).step().seconds()==.05,"Only current logical tick consumed");
            // A newly wet respawn uses the normal observation/model path, no special restoration value.
            var acquired=EgoWetnessModel.advance(respawn.getData(EgoAttachments.EGO_WETNESS.get()),
                    EgoWetnessExposure.observed(true,false),EgoEnvironmentSampler.sample(respawn).climateProfile(),.05);
            check(acquired.state().wetness()>0 && acquired.state().wetness()<.1,"Normal acquisition after death");
            ServerPlayer dimensionClone=player(h);
            NeoForge.EVENT_BUS.post(new PlayerEvent.Clone(dimensionClone,original,false));
            check(dimensionClone.getData(EgoAttachments.EGO_WETNESS.get()).wetness()==.9,"Nondeath clone preserves W");
            EgoWetnessRuntime.remove(original.getUUID());EgoWetnessRuntime.remove(respawn.getUUID());
        });h.succeed();
    }

    @GameTest(template="empty",timeoutTicks=20)
    public static void g12_mode_and_disabled_pause_preserve_without_catchup(GameTestHelper h) throws IOException {
        active(h,()->{
            ServerPlayer p=player(h);p.setData(EgoAttachments.EGO_WETNESS.get(),wet(.7));
            // Restore the native saved game mode without requiring a connected network listener.
            for(GameType mode:new GameType[]{GameType.CREATIVE,GameType.SPECTATOR}) {
                mode(p,mode);p.tickCount++;
                EgoWetnessRuntime.advancePlayerTick(p);
                check(p.getData(EgoAttachments.EGO_WETNESS.get()).wetness()==.7,"Paused W preserved");
                check(EgoWetnessRuntime.thermalWetness(p)==0,"Paused W has no thermal effect");
            }
            mode(p,GameType.ADVENTURE);p.tickCount+=10000;
            EgoWetnessRuntime.advancePlayerTick(p);
            check(EgoWetnessRuntime.lastApplied(p).step().seconds()==.05,"No pause catchup");
            check(p.getData(EgoAttachments.EGO_WETNESS.get()).wetness()<.7,"Adventure active");
            var saved=p.getData(EgoAttachments.EGO_WETNESS.get());
            EgoCoreConfig.ENABLE_SIMULATION.set(false);p.tickCount++;
            EgoWetnessRuntime.advancePlayerTick(p);
            check(p.getData(EgoAttachments.EGO_WETNESS.get()).equals(saved),"Disabled W preserved");
            check(EgoWetnessRuntime.thermalWetness(p)==0,"Disabled W effect absent");
            EgoCoreConfig.ENABLE_SIMULATION.set(true);p.tickCount+=100000;
            EgoWetnessRuntime.advancePlayerTick(p);
            check(EgoWetnessRuntime.lastApplied(p).step().seconds()==.05,"No disabled catchup");
            EgoWetnessRuntime.remove(p.getUUID());
        });h.succeed();
    }

    @GameTest(template="empty",timeoutTicks=20)
    public static void wetness_reads_cache_dimension_and_multiplayer_do_not_replay(GameTestHelper h) throws IOException {
        active(h,()->{
            ServerPlayer a=player(h),b=player(h);
            a.setData(EgoAttachments.EGO_WETNESS.get(),wet(.8));b.setData(EgoAttachments.EGO_WETNESS.get(),wet(.2));
            EgoWetnessRuntime.advancePlayerTick(a);
            var state=a.getData(EgoAttachments.EGO_WETNESS.get());
            for(int i=0;i<30;i++) {
                EgoEnvironmentService.debugSnapshot(a);EgoEnvironmentService.latest(a);EgoWetnessRuntime.lastApplied(a);
                CompoundTag tag=new CompoundTag();a.saveWithoutId(tag);
            }
            check(a.getData(EgoAttachments.EGO_WETNESS.get()).equals(state),"Reads/save must not integrate");
            EgoWetnessRuntime.advancePlayerTick(a);
            check(a.getData(EgoAttachments.EGO_WETNESS.get()).equals(state),"Duplicate tick must not integrate");
            NeoForge.EVENT_BUS.post(new PlayerEvent.PlayerChangedDimensionEvent(a,Level.OVERWORLD,Level.NETHER));
            check(EgoEnvironmentService.latest(a).isEmpty(),"Dimension invalidates context");
            EgoWetnessRuntime.advancePlayerTick(a);
            check(a.getData(EgoAttachments.EGO_WETNESS.get()).equals(state),"Dimension retains duplicate guard");
            a.tickCount++;EgoWetnessRuntime.advancePlayerTick(a);
            check(a.getData(EgoAttachments.EGO_WETNESS.get()).wetness()<state.wetness(),"New observation after dimension");
            check(b.getData(EgoAttachments.EGO_WETNESS.get()).wetness()==.2,"Multiplayer isolated");
            var thermal=EgoEnvironmentService.debugSnapshot(a);
            check(thermal.thermalSnapshot().equals(EgoThermalSnapshotCalculator.calculate(thermal.exposureSample(),EgoWetnessRuntime.thermalWetness(a))),"Cache thermal uses current W");
            EgoWetnessRuntime.remove(a.getUUID());EgoWetnessRuntime.remove(b.getUUID());
        });h.succeed();
    }
    @GameTest(template="empty",timeoutTicks=20)
    public static void actual_water_rain_roof_and_boat_observations(GameTestHelper h) throws IOException {
        active(h,()->{
            ServerPlayer p=player(h);
            var level=h.getLevel();
            var pos=h.absolutePos(new net.minecraft.core.BlockPos(1,40,1));
            var roof=pos.above(3);
            var oldWater=level.getBlockState(pos);var oldRoof=level.getBlockState(roof);
            float rain=level.getRainLevel(1);
            try {
                p.setPos(pos.getX()+.5,pos.getY(),pos.getZ()+.5);
                level.setBlock(pos,net.minecraft.world.level.block.Blocks.WATER.defaultBlockState(),3);
                p.baseTick();
                ServerPlayer dead=player(h);dead.setData(EgoAttachments.EGO_WETNESS.get(),wet(.9));
                NeoForge.EVENT_BUS.post(new PlayerEvent.Clone(p,dead,true));
                check(p.getData(EgoAttachments.EGO_WETNESS.get()).wetness()==0,"Wet spawn first resets W at death");
                check(p.isInWater(),"Native body bounding box must touch water");
                check(EgoWetnessRuntime.exposure(p)==EgoWetnessExposure.IMMERSION,"Actual water observed");
                EgoWetnessRuntime.advancePlayerTick(p);
                double first=p.getData(EgoAttachments.EGO_WETNESS.get()).wetness();
                check(first>.06 && first<.1,"One water tick retained");
                level.setBlock(pos,net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(),3);
                p.baseTick();p.tickCount++;
                check(!p.isInWater(),"Native liquid state refreshed");
                level.setRainLevel(1);
                check(level.isRainingAt(pos),"Rain fixture: raining="+level.isRaining()+" sky="+level.canSeeSky(pos)+" biome="+level.getBiome(pos).unwrapKey()+" precipitation="+level.getBiome(pos).value().getPrecipitationAt(pos)+" height="+level.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING,pos.getX(),pos.getZ()));
                check(EgoWetnessRuntime.exposure(p)==EgoWetnessExposure.RAIN,"Real rain observed");
                EgoWetnessRuntime.advancePlayerTick(p);
                double rainy=p.getData(EgoAttachments.EGO_WETNESS.get()).wetness();
                check(rainy>first,"Received rain increases W");
                level.setBlock(roof,net.minecraft.world.level.block.Blocks.STONE.defaultBlockState(),3);
                check(!level.isRainingAt(pos),"Roof fixture really blocks rain");
                check(EgoWetnessRuntime.exposure(p)==EgoWetnessExposure.DRY,"Effective roof stops input");
                p.tickCount++;EgoWetnessRuntime.advancePlayerTick(p);
                double sheltered=p.getData(EgoAttachments.EGO_WETNESS.get()).wetness();
                check(sheltered>0 && sheltered<rainy,"Roof does not instantly dry W");
                // A boat entity alone never supplies immersion; native body state remains authoritative.
                var boat=new net.minecraft.world.entity.vehicle.Boat(level,pos.getX()+.5,pos.getY(),pos.getZ()+.5);
                var connection=new net.minecraft.network.Connection(net.minecraft.network.protocol.PacketFlow.SERVERBOUND);
                var channel=new io.netty.channel.embedded.EmbeddedChannel(connection);
                p.connection=new net.minecraft.server.network.ServerGamePacketListenerImpl(level.getServer(),connection,p,
                        net.minecraft.server.network.CommonListenerCookie.createInitial(p.getGameProfile(),false));
                try {
                    check(p.startRiding(boat,true),"Boat fixture mounted");p.baseTick();
                    check(EgoWetnessRuntime.exposure(p)==EgoWetnessExposure.DRY,"Passenger status alone not immersion");
                    p.stopRiding();
                } finally { channel.finishAndReleaseAll(); }
            } finally {
                level.setBlock(pos,oldWater,3);level.setBlock(roof,oldRoof,3);level.setRainLevel(rain);
                EgoWetnessRuntime.remove(p.getUUID());
            }
        });h.succeed();
    }

    @GameTest(template="empty",timeoutTicks=20)
    public static void wetness_save_after_real_step_when_ego_unchanged_and_logout_no_catchup(GameTestHelper h) throws IOException {
        active(h,()->{
            ServerPlayer p=player(h);p.setData(EgoAttachments.EGO_WETNESS.get(),wet(.8));
            var ego=p.getData(EgoAttachments.EGO_STATE.get());
            EgoWetnessRuntime.advancePlayerTick(p);
            var expected=p.getData(EgoAttachments.EGO_WETNESS.get());
            check(expected.wetness()<.8,"W changed independently of scheduler frame");
            check(p.getData(EgoAttachments.EGO_STATE.get()).equals(ego),"No direct penalties or other state writes");
            CompoundTag tag=new CompoundTag();p.saveWithoutId(tag);
            ServerPlayer reconnect=player(h);reconnect.load(tag);
            check(reconnect.getData(EgoAttachments.EGO_WETNESS.get()).equals(expected),"W persisted without EGO change");
            EgoWetnessRuntime.remove(p.getUUID());reconnect.tickCount+=500000;
            EgoWetnessRuntime.advancePlayerTick(reconnect);
            check(EgoWetnessRuntime.lastApplied(reconnect).step().seconds()==.05,"Reconnect gap not consumed");
            check(reconnect.getData(EgoAttachments.EGO_STATE.get()).equals(ego),"Other state still preserved");
            EgoWetnessRuntime.remove(reconnect.getUUID());
        });h.succeed();
    }

    @GameTest(template="empty",timeoutTicks=20)
    public static void module_pause_world_time_jump_and_equipment_are_neutral(GameTestHelper h) throws IOException {
        active(h,()->{
            ServerPlayer p=player(h);p.setData(EgoAttachments.EGO_WETNESS.get(),wet(.6));
            var module=EgoServerModuleConfigs.byKey("environment").orElseThrow().enabled();
            long day=h.getLevel().getDayTime();
            try {
                module.set(false);EgoWetnessRuntime.advancePlayerTick(p);
                check(p.getData(EgoAttachments.EGO_WETNESS.get()).wetness()==.6,"Environment-disabled pause");
                check(EgoWetnessRuntime.thermalWetness(p)==0,"Environment-disabled effect absent");
                module.set(true);p.tickCount++;
                h.getLevel().setDayTime(day+1000000);EgoWetnessRuntime.advancePlayerTick(p);
                check(EgoWetnessRuntime.lastApplied(p).step().seconds()==.05,"Day jump never consumed as elapsed time");
                var before=p.getData(EgoAttachments.EGO_WETNESS.get());
                var raw=EgoEnvironmentService.debugSnapshot(p).exposureSample();
                p.setItemSlot(net.minecraft.world.entity.EquipmentSlot.CHEST,
                        new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.LEATHER_CHESTPLATE));
                check(p.getData(EgoAttachments.EGO_WETNESS.get()).equals(before),"Equipping never resets W");
                p.tickCount++;EgoWetnessRuntime.advancePlayerTick(p);
                check(p.getData(EgoAttachments.EGO_WETNESS.get()).equals(EgoWetnessModel.advance(before,
                        EgoWetnessExposure.DRY,raw.climateProfile(),.05).state()),"Equipment does not change drying rate");
                p.setItemSlot(net.minecraft.world.entity.EquipmentSlot.CHEST,net.minecraft.world.item.ItemStack.EMPTY);
                p.setData(EgoAttachments.EGO_WETNESS.get(),new EgoWetnessState(99,.6,false));p.tickCount++;
                EgoWetnessRuntime.advancePlayerTick(p);
                check(EgoWetnessRuntime.activity(p).equals("data_quarantined"),"Future schema diagnostic");
                check(p.getData(EgoAttachments.EGO_WETNESS.get()).wetness()==.6 && EgoWetnessRuntime.thermalWetness(p)==0,"Unsupported W preserved without effect");
            } finally { module.set(true);h.getLevel().setDayTime(day);EgoWetnessRuntime.remove(p.getUUID()); }
        });h.succeed();
    }

    @GameTest(template="empty",timeoutTicks=20)
    public static void wetness_debug_command_executes_without_mutation_or_invalid_translation_args(GameTestHelper h) throws IOException {
        active(h,()->{
            ServerPlayer p=player(h);p.setData(EgoAttachments.EGO_WETNESS.get(),wet(.4));
            EgoWetnessRuntime.advancePlayerTick(p);
            var state=p.getData(EgoAttachments.EGO_WETNESS.get());
            var messages=new java.util.ArrayList<net.minecraft.network.chat.Component>();
            net.minecraft.commands.CommandSource collector=new net.minecraft.commands.CommandSource() {
                public void sendSystemMessage(net.minecraft.network.chat.Component message) { messages.add(message); }
                public boolean acceptsSuccess() { return true; }
                public boolean acceptsFailure() { return true; }
                public boolean shouldInformAdmins() { return false; }
            };
            var source=new net.minecraft.commands.CommandSourceStack(collector,net.minecraft.world.phys.Vec3.ZERO,
                    net.minecraft.world.phys.Vec2.ZERO,h.getLevel(),2,"Wetness test",
                    net.minecraft.network.chat.Component.literal("Wetness test"),h.getLevel().getServer(),p);
            try {
                int result=h.getLevel().getServer().getCommands().getDispatcher().execute("ego debug wetness",source);
                check(result==1 && messages.size()==3,"Debug command must actually complete all output lines");
                for(var message:messages) net.minecraft.network.chat.Component.Serializer.toJson(message,h.getLevel().registryAccess());
                check(p.getData(EgoAttachments.EGO_WETNESS.get()).equals(state),"Command must not advance W");
                check(h.getLevel().getServer().getCommands().getDispatcher().execute("ego wetness notices false",source.withPermission(0))==1,"Self opt-out without operator permission");
                check(h.getLevel().getServer().getCommands().getDispatcher().execute("ego wetness notices true",source.withPermission(0))==1,"Self opt-in without operator permission");
            } catch(com.mojang.brigadier.exceptions.CommandSyntaxException error) { throw new IllegalStateException(error); }
            finally { EgoWetnessRuntime.remove(p.getUUID()); }
        });h.succeed();
    }

    @GameTest(template="empty",timeoutTicks=20)
    public static void scheduler_disable_preserves_w_and_resume_consumes_only_present_tick(GameTestHelper h) throws IOException {
        Path root=Files.createTempDirectory("ego-wetness-gates-");
        Path off=root.resolve("off/immersive_ego/simulation.toml"),on=root.resolve("on/immersive_ego/simulation.toml");
        Files.createDirectories(off.getParent());Files.createDirectories(on.getParent());
        Files.writeString(off,"[scheduler]\nenabled=false\n");Files.writeString(on,"[scheduler]\nenabled=true\n");
        try {
            active(h,()->{
                ServerPlayer p=player(h);p.setData(EgoAttachments.EGO_WETNESS.get(),wet(.8));
                EgoDynamicServerConfigLoader.reload(root.resolve("off"));
                EgoWetnessRuntime.advancePlayerTick(p);
                check(EgoWetnessRuntime.activity(p).equals("disabled"),"Scheduler disabled diagnostic");
                check(p.getData(EgoAttachments.EGO_WETNESS.get()).wetness()==.8 && EgoWetnessRuntime.thermalWetness(p)==0,"Scheduler pause preserves W without effect");
                EgoDynamicServerConfigLoader.reload(root.resolve("on"));p.tickCount+=100000;
                EgoWetnessRuntime.advancePlayerTick(p);
                check(EgoWetnessRuntime.lastApplied(p).step().seconds()==.05,"No suspended interval replay");
                EgoWetnessRuntime.remove(p.getUUID());
            });
        } finally {
            try(var paths=Files.walk(root)) { for(Path path:paths.sorted(java.util.Comparator.reverseOrder()).toList()) Files.deleteIfExists(path); }
        }
        h.succeed();
    }

    @GameTest(template="empty",timeoutTicks=40)
    public static void ordered_brief_water_ticks_are_independent_of_actual_outer_scheduler(GameTestHelper h) throws IOException {
        Path root=Files.createTempDirectory("ego-wetness-frequency-");
        for(int interval:new int[]{1,20,1200}) {
            Path settings=root.resolve(interval+"/immersive_ego/simulation.toml");Files.createDirectories(settings.getParent());
            Files.writeString(settings,"[scheduler]\nenabled=true\nintervalTicks="+interval+"\n");
        }
        var level=h.getLevel();var worldData=level.getServer().getWorldData().overworldData();
        long gameTime=level.getGameTime();float rain=level.getRainLevel(1);
        var pos=h.absolutePos(new net.minecraft.core.BlockPos(1,40,1));var previous=level.getBlockState(pos);
        try {
            active(h,()->{
                level.setRainLevel(0);level.setBlock(pos,net.minecraft.world.level.block.Blocks.WATER.defaultBlockState(),3);
                Double expected=null;
                for(int interval:new int[]{1,20,1200}) {
                    EgoDynamicServerConfigLoader.reload(root.resolve(Integer.toString(interval)));
                    ServerPlayer p=player(h);
                    var connection=new net.minecraft.network.Connection(net.minecraft.network.protocol.PacketFlow.SERVERBOUND);
                    var channel=new io.netty.channel.embedded.EmbeddedChannel(connection);
                    p.connection=new net.minecraft.server.network.ServerGamePacketListenerImpl(level.getServer(),connection,p,
                            net.minecraft.server.network.CommonListenerCookie.createInitial(p.getGameProfile(),false));
                    try {
                        for(int tick=1;tick<=200;tick++) {
                            boolean water=tick%10==1;
                            p.setPos(pos.getX()+.5+(water?0:2),pos.getY(),pos.getZ()+.5);
                            p.baseTick();p.tickCount=tick;worldData.setGameTime(tick);
                            check(p.isInWater()==water,"Native one-tick water/dry fixture");
                            NeoForge.EVENT_BUS.post(new net.neoforged.neoforge.event.tick.PlayerTickEvent.Post(p));
                            if(tick==1) check(p.getData(EgoAttachments.EGO_WETNESS.get()).wetness()>.06,
                                    "Brief water before first outer frame must not be lost");
                        }
                        double observed=p.getData(EgoAttachments.EGO_WETNESS.get()).wetness();
                        if(expected==null) expected=observed;
                        else check(Math.abs(expected-observed)<1e-13,"Actual outer interval changed ordered wetness result");
                        check(EgoWetnessRuntime.lastApplied(p).consumedTicks()==200,"Exactly 200 ticks consumed");
                    } finally { channel.finishAndReleaseAll();NeoForge.EVENT_BUS.post(new PlayerEvent.PlayerLoggedOutEvent(p)); }
                }
            });
        } finally {
            worldData.setGameTime(gameTime);level.setRainLevel(rain);level.setBlock(pos,previous,3);
            try(var paths=Files.walk(root)) { for(Path path:paths.sorted(java.util.Comparator.reverseOrder()).toList()) Files.deleteIfExists(path); }
        }
        h.succeed();
    }

}
