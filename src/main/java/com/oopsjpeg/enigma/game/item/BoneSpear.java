package com.oopsjpeg.enigma.game.item;

import com.oopsjpeg.enigma.game.Stats;
import com.oopsjpeg.enigma.game.effect.Wounder;
import com.oopsjpeg.enigma.game.obj.Effect;
import com.oopsjpeg.enigma.game.obj.Item;

public class BoneSpear extends Item {
    public static final String NAME = "Bone Spear";
    public static final int COST = 375;
    public static final Item[] BUILD = new Item[]{new Knife()};
    public static final Effect[] EFFECTS = new Effect[]{new Wounder(0.3f)};
    public static final Stats STATS = new Stats()
            .put(Stats.DAMAGE, 6);

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getCost() {
        return COST;
    }

    @Override
    public Item[] getBuild() {
        return BUILD;
    }

    @Override
    public Effect[] getEffects() {
        return EFFECTS;
    }

    @Override
    public Stats getStats() {
        return STATS;
    }
}
