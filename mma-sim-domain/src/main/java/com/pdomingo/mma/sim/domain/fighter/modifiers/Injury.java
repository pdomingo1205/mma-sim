package com.pdomingo.mma.sim.domain.fighter.modifiers;

import com.pdomingo.mma.sim.domain.fighter.RefData;

import java.math.BigDecimal;

public class Injury  extends RefData {
    private ModifierEffect effect;
    private BigDecimal effectStrength;

    public ModifierEffect getEffect() {
        return effect;
    }

    public void setEffect(ModifierEffect effect) {
        this.effect = effect;
    }

    public BigDecimal getEffectStrength() {
        return effectStrength;
    }

    public void setEffectStrength(BigDecimal effectStrength) {
        this.effectStrength = effectStrength;
    }
}
