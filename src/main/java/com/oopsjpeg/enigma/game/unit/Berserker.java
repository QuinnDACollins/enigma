package com.oopsjpeg.enigma.game.unit;

import com.oopsjpeg.enigma.game.DamageEvent;
import com.oopsjpeg.enigma.game.Game;
import com.oopsjpeg.enigma.game.Stats;
import com.oopsjpeg.enigma.game.obj.Unit;
import com.oopsjpeg.enigma.util.Emote;
import com.oopsjpeg.enigma.util.Stacker;
import com.oopsjpeg.enigma.util.Util;

import java.awt.*;

public class Berserker extends Unit {
    public static final int RAGE_MAX = 5;
    public static final float BONUS_DAMAGE = 0.04f;
    public static final int BONUS_AP = 10;
    public static final int BONUS_ENERGY = 100;

    public static final String NAME = "Berserker";
    public static final String DESC = "Basic attacking or being basic attacked builds up to **" + RAGE_MAX + "** stacks of **Rage**."
            + "\nUsing `>rage` consumes stacks to increase damage dealt for a single turn (**" + Util.percent(BONUS_DAMAGE) + "** (+1% per " + BONUS_AP + " AP) per stack)."
            + "\nAt maximum stacks, Rage grants **" + BONUS_ENERGY + "** bonus energy.";
    public static final Color COLOR = Color.RED;
    public static final Stats STATS = new Stats()
            .put(Stats.ENERGY, 100)
            .put(Stats.MAX_HP, 780)
            .put(Stats.DAMAGE, 19);
    public static final Stats PER_TURN = new Stats()
            .put(Stats.HP, 12);

    private final Stacker rage = new Stacker(RAGE_MAX);
    private float bonus = 0;

    public Stacker getRage() {
        return rage;
    }

    public String rage(Game.Member member) {
        if (rage.stack() && rage.notif())
            return Emote.RAGE + "**" + member.getName() + "'s Rage** is at max capacity.";
        return "";
    }

    public float getBonus() {
        return bonus;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
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
        bonus = 0;
        return "";
    }

    @Override
    public DamageEvent onBasicAttack(DamageEvent event) {
        if (bonus > 0)
            event.damage *= 1 + bonus;
        else
            event.output.add(rage(event.actor));
        return event;
    }

    @Override
    public DamageEvent wasBasicAttacked(DamageEvent event) {
        event.output.add(rage(event.target));
        return event;
    }
}
