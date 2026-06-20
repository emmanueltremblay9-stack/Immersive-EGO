package com.oblixorprime.immersiveego.config;

import com.oblixorprime.immersiveego.simulation.EgoSynergyEdge;
import java.util.List;

public record EgoDynamicSynergyConfig(List<EgoSynergyEdge> edges) {
    public EgoDynamicSynergyConfig {
        edges = List.copyOf(edges);
    }

    public static EgoDynamicSynergyConfig defaults() {
        return new EgoDynamicSynergyConfig(List.of());
    }
}
