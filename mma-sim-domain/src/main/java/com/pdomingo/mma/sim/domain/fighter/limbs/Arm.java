package com.pdomingo.mma.sim.domain.fighter.limbs;

import java.math.BigDecimal;

public class Arm extends BaseLimb {
    private BigDecimal armPower;
    private BigDecimal armSpeed;

    public BigDecimal getArmPower() {
        return armPower;
    }

    public void setArmPower(BigDecimal armPower) {
        this.armPower = armPower;
    }

    public BigDecimal getArmSpeed() {
        return armSpeed;
    }

    public void setArmSpeed(BigDecimal armSpeed) {
        this.armSpeed = armSpeed;
    }
}
