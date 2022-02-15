package com.pdomingo.mma.sim.domain.fighter.techniques;

import com.pdomingo.mma.sim.domain.fighter.RefData;
import com.pdomingo.mma.sim.domain.constants.LimbSide;
import com.pdomingo.mma.sim.domain.constants.TechniqueType;

import java.math.BigDecimal;
import java.util.List;

public class Technique extends RefData {

    private TechniqueType techniqueType;
    private BigDecimal powerMod;
    private BigDecimal accuracyMod; // 0.01 - 1 value; modifies accuracy
    private BigDecimal speedMod; // 0.01 - 1 value; modifies punch speed
    private BigDecimal moveCost;
    private LimbSide requiredSide;

    private List<Technique> combo;
    private BigDecimal comboPowerMod;
    private BigDecimal comboSpeedMod;
    private BigDecimal comboAccuracyMod;

    private Boolean enabled;

    public TechniqueType getTechniqueType() {
        return techniqueType;
    }

    public void setTechniqueType(TechniqueType techniqueType) {
        this.techniqueType = techniqueType;
    }

    public BigDecimal getPowerMod() {
        return powerMod;
    }

    public void setPowerMod(BigDecimal powerMod) {
        this.powerMod = powerMod;
    }

    public BigDecimal getAccuracyMod() {
        return accuracyMod;
    }

    public void setAccuracyMod(BigDecimal accuracyMod) {
        this.accuracyMod = accuracyMod;
    }

    public BigDecimal getSpeedMod() {
        return speedMod;
    }

    public void setSpeedMod(BigDecimal speedMod) {
        this.speedMod = speedMod;
    }

    public LimbSide getRequiredSide() {
        return requiredSide;
    }

    public void setRequiredSide(LimbSide requiredSide) {
        this.requiredSide = requiredSide;
    }

    public List<Technique> getCombo() {
        return combo;
    }

    public void setCombo(List<Technique> combo) {
        this.combo = combo;
    }

    public BigDecimal getComboPowerMod() {
        return comboPowerMod;
    }

    public void setComboPowerMod(BigDecimal comboPowerMod) {
        this.comboPowerMod = comboPowerMod;
    }

    public BigDecimal getComboSpeedMod() {
        return comboSpeedMod;
    }

    public void setComboSpeedMod(BigDecimal comboSpeedMod) {
        this.comboSpeedMod = comboSpeedMod;
    }

    public BigDecimal getComboAccuracyMod() {
        return comboAccuracyMod;
    }

    public void setComboAccuracyMod(BigDecimal comboAccuracyMod) {
        this.comboAccuracyMod = comboAccuracyMod;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public BigDecimal getMoveCost() {
        return moveCost;
    }

    public void setMoveCost(BigDecimal moveCost) {
        this.moveCost = moveCost;
    }
}
