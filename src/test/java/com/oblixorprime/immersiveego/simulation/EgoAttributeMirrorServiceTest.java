package com.oblixorprime.immersiveego.simulation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class EgoAttributeMirrorServiceTest {
    @Test
    void mirrorUpdatesRespectConfiguredDeltaThreshold() {
        assertFalse(EgoAttributeMirrorService.shouldApplyMirror(0.0D, 0.004D, 0.005D));
        assertTrue(EgoAttributeMirrorService.shouldApplyMirror(0.0D, 0.006D, 0.005D));
        assertTrue(EgoAttributeMirrorService.shouldApplyMirror(0.20D, 0.0D, 0.005D));
    }
}
