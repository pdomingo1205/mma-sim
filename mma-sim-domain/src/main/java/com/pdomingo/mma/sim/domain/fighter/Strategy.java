package com.pdomingo.mma.sim.domain.fighter;

import java.math.BigDecimal;

public class Strategy {

    private BigDecimal attackRate; // Chance of trying to attack
    private BigDecimal defendRate; // Chance of waiting to defend attack

    private BigDecimal blockRate; // Chance of using a block technique when defending
    private BigDecimal evadeRate; // Chance of evading when defending
    private BigDecimal counterRate; // Chance of using a counter when defending
    private BigDecimal tradeBlowsRate; // Chance of trading blows when defending

    private BigDecimal pickShotsRate; // Chance of trying only single shots
    private BigDecimal comboRate; // Chance of trying to string together attacks

    private BigDecimal punchRate; // Chance of using punches
    private BigDecimal kickRate; // Chance of using kicks

    private BigDecimal headHuntRate; // Chance of attacking head
    private BigDecimal bodyHuntRate; // Chance of attacking body
    private BigDecimal legHuntRate; // Chance of attacking leg

    private BigDecimal pressureRate; //Chance of getting in pocket
    private BigDecimal stayCageRate; //Chance of staying back
    private BigDecimal stayCenterRate; //Chance of staying in center

    private Boolean conserveStamina;
    private Boolean goWild; // Try to expend stamina for more powerful but less accurate blows


    public BigDecimal getAttackRate() {
        return attackRate;
    }

    public void setAttackRate(BigDecimal attackRate) {
        this.attackRate = attackRate;
    }

    public BigDecimal getDefendRate() {
        return defendRate;
    }

    public void setDefendRate(BigDecimal defendRate) {
        this.defendRate = defendRate;
    }

    public BigDecimal getBlockRate() {
        return blockRate;
    }

    public void setBlockRate(BigDecimal blockRate) {
        this.blockRate = blockRate;
    }

    public BigDecimal getEvadeRate() {
        return evadeRate;
    }

    public void setEvadeRate(BigDecimal evadeRate) {
        this.evadeRate = evadeRate;
    }

    public BigDecimal getCounterRate() {
        return counterRate;
    }

    public void setCounterRate(BigDecimal counterRate) {
        this.counterRate = counterRate;
    }

    public BigDecimal getTradeBlowsRate() {
        return tradeBlowsRate;
    }

    public void setTradeBlowsRate(BigDecimal tradeBlowsRate) {
        this.tradeBlowsRate = tradeBlowsRate;
    }

    public BigDecimal getPickShotsRate() {
        return pickShotsRate;
    }

    public void setPickShotsRate(BigDecimal pickShotsRate) {
        this.pickShotsRate = pickShotsRate;
    }

    public BigDecimal getComboRate() {
        return comboRate;
    }

    public void setComboRate(BigDecimal comboRate) {
        this.comboRate = comboRate;
    }

    public BigDecimal getPunchRate() {
        return punchRate;
    }

    public void setPunchRate(BigDecimal punchRate) {
        this.punchRate = punchRate;
    }

    public BigDecimal getKickRate() {
        return kickRate;
    }

    public void setKickRate(BigDecimal kickRate) {
        this.kickRate = kickRate;
    }

    public BigDecimal getHeadHuntRate() {
        return headHuntRate;
    }

    public void setHeadHuntRate(BigDecimal headHuntRate) {
        this.headHuntRate = headHuntRate;
    }

    public BigDecimal getBodyHuntRate() {
        return bodyHuntRate;
    }

    public void setBodyHuntRate(BigDecimal bodyHuntRate) {
        this.bodyHuntRate = bodyHuntRate;
    }

    public BigDecimal getLegHuntRate() {
        return legHuntRate;
    }

    public void setLegHuntRate(BigDecimal legHuntRate) {
        this.legHuntRate = legHuntRate;
    }

    public BigDecimal getPressureRate() {
        return pressureRate;
    }

    public void setPressureRate(BigDecimal pressureRate) {
        this.pressureRate = pressureRate;
    }

    public BigDecimal getStayCageRate() {
        return stayCageRate;
    }

    public void setStayCageRate(BigDecimal stayCageRate) {
        this.stayCageRate = stayCageRate;
    }

    public BigDecimal getStayCenterRate() {
        return stayCenterRate;
    }

    public void setStayCenterRate(BigDecimal stayCenterRate) {
        this.stayCenterRate = stayCenterRate;
    }

    public Boolean getConserveStamina() {
        return conserveStamina;
    }

    public void setConserveStamina(Boolean conserveStamina) {
        this.conserveStamina = conserveStamina;
    }

    public Boolean getGoWild() {
        return goWild;
    }

    public void setGoWild(Boolean goWild) {
        this.goWild = goWild;
    }
}
