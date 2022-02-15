package com.pdomingo.mma.sim.domain.fighter.limbs;

import com.pdomingo.mma.sim.domain.constants.LimbSide;
import com.pdomingo.mma.sim.domain.fighter.techniques.Technique;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class BaseLimb {
    private BigDecimal health;

    private BigDecimal maxHealth;

    private BigDecimal lastingDamage = BigDecimal.ZERO;

    private LimbSide limbSide;

    private List<Technique> techniques;

    public BigDecimal getHealth() {
        return health;
    }

    public void setHealth(BigDecimal health) {
        this.health = health;
    }

    public BigDecimal getLastingDamage() {
        return lastingDamage;
    }

    public void setLastingDamage(BigDecimal lastingDamage) {
        this.lastingDamage = lastingDamage;
    }

    public BigDecimal getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(BigDecimal maxHealth) {
        this.maxHealth = maxHealth;
    }

    public LimbSide getLimbSide() {
        return limbSide;
    }

    public void setLimbSide(LimbSide limbSide) {
        this.limbSide = limbSide;
    }

    public List<Technique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public BigDecimal getHealthPercentage() {
        return health.divide(maxHealth, 2, RoundingMode.HALF_UP);
    }
}
