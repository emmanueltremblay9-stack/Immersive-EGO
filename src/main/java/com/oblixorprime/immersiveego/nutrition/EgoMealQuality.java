package com.oblixorprime.immersiveego.nutrition;

import com.oblixorprime.immersiveego.simulation.EgoMath;

public record EgoMealQuality(
        double mealQuality,
        double gutHealth) {
    public EgoMealQuality {
        mealQuality = EgoMath.clamp01(mealQuality);
        gutHealth = EgoMath.clamp01(gutHealth);
    }
}
