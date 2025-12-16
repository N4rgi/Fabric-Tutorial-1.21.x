package net.nargi.friulcraft.entity.custom;

import java.util.Arrays;
import java.util.Comparator;

public enum GolemVariant {
    DEFAULT(0),
    GRAPES(1);

    private static final GolemVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(GolemVariant::getId)).toArray(GolemVariant[]::new);
    private final int id;

    GolemVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static GolemVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
