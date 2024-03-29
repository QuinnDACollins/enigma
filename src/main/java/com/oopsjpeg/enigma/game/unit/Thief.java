package com.oopsjpeg.enigma.game.unit;

import com.oopsjpeg.enigma.game.DamageEvent;
import com.oopsjpeg.enigma.game.Game;
import com.oopsjpeg.enigma.game.Stats;
import com.oopsjpeg.enigma.game.obj.Unit;
import com.oopsjpeg.enigma.util.Emote;
import com.oopsjpeg.enigma.util.Util;

import java.awt.*;

public class Thief extends Unit {
    public static final float STEAL_AMOUNT = 0.3f;
    public static final float STEAL_AP = 0.6f;
    public static final float CRIT_REDUCE = 0.2f;
    public static final float CRIT_INCREASE = 0.2f;

    public static final String NAME = "Thief";
    public static final String DESC = "The first crit per turn steals gold equal to **" + Util.percent(STEAL_AMOUNT) + "** (+" + Util.percent(STEAL_AP) + " AP) of base damage."
            + "\nCrit damage is reduced by **" + Util.percent(CRIT_REDUCE) + "**, however, subsequent crits in a turn deal increasing damage.";
    public static final Color COLOR = Color.YELLOW;
    public static final Stats STATS = new Stats()
            .put(Stats.ENERGY, 150)
            .put(Stats.MAX_HP, 735)
            .put(Stats.DAMAGE, 20)
            .put(Stats.CRIT_CHANCE, 0.2f)
            .put(Stats.CRIT_DAMAGE, -1 * CRIT_REDUCE);
    public static final Stats PER_TURN = new Stats()
            .put(Stats.HP, 8);

    private int critAmount = 0;

    public int getCritAmount() {
        return critAmount;
    }

    public void setCritAmount(int critAmount) {
        this.critAmount = Math.max(0, critAmount);
    }

    public int crit() {
        setCritAmount(critAmount + 1);
        return critAmount;
    }

    @Override
    public DamageEvent onCrit(DamageEvent event) {
        event.critMul += getCritAmount() * CRIT_INCREASE;
        if (crit() == 1) {
            int steal = (int) Math.min((event.actor.getStats().get(Stats.DAMAGE) * STEAL_AMOUNT) + (event.actor.getStats().get(Stats.ABILITY_POWER) * STEAL_AP), event.target.getStats().getInt(Stats.GOLD));
            event.actor.getStats().add(Stats.GOLD, steal);
            event.target.getStats().sub(Stats.GOLD, steal);
            event.output.add(Emote.BUY + "**" + getName() + "** stole **" + steal + "** gold!");
        }
        return event;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDesc() {
        return DESC;
    }

    @Override
    public Color getColor() {
        return COLOR;
    }

    @Override
    public Stats getStats() {
        return STATS;
    }

    @Override
    public Stats getPerTurn() {
        return PER_TURN;
    }

    @Override
    public String onTurnEnd(Game.Member member) {
        critAmount = 0;
        return "";
    }
}
