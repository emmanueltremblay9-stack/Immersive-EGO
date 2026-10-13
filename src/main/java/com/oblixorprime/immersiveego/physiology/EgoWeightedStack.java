package com.oblixorprime.immersiveego.physiology;

import java.util.regex.Pattern;

public record EgoWeightedStack(
        String itemId,
        int count,
        int maxStackSize,
        boolean damageable) {
    private static final Pattern ITEM_ID = Pattern.compile("[a-z0-9_.-]+:[a-z0-9_./-]+");

    public EgoWeightedStack {
        if (itemId == null) {
            throw new NullPointerException("itemId");
        }
        if (!ITEM_ID.matcher(itemId).matches()) {
            throw new IllegalArgumentException("itemId must be a namespaced item id");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("count must be positive");
        }
        if (maxStackSize <= 0) {
            throw new IllegalArgumentException("maxStackSize must be positive");
        }
    }

    public String namespace() {
        return itemId.substring(0, itemId.indexOf(':'));
    }

    public String path() {
        return itemId.substring(itemId.indexOf(':') + 1);
    }
}
