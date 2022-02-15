package com.pdomingo.mma.sim.domain.fighter;

import com.pdomingo.mma.sim.domain.constants.Stance;
import com.pdomingo.mma.sim.domain.fighter.limbs.Arm;
import com.pdomingo.mma.sim.domain.fighter.limbs.Head;
import com.pdomingo.mma.sim.domain.fighter.limbs.Leg;
import com.pdomingo.mma.sim.domain.fighter.limbs.Torso;
import com.pdomingo.mma.sim.domain.fighter.modifiers.Perk;

import java.math.BigDecimal;

public class Fighter {
    private Name name;
    private Head head;
    private Torso torso;
    private Arm leftArm;
    private Arm rightArm;
    private Leg leftLeg;
    private Leg rightLeg;

    //Physical
    private BigDecimal naturalSpeed; // 1.0 - 2.0
    private BigDecimal naturalPower;
    private BigDecimal headMovement;
    private BigDecimal bodyMovement;
    private BigDecimal footwork;
    private BigDecimal maxStamina;
    private BigDecimal stamina; // 1000 - 100000
    private BigDecimal staminaMalus = BigDecimal.ZERO; // 1000-100000
    private BigDecimal recovery; // 10 - 10000
    private BigDecimal heart;

    //Mental
    private BigDecimal positioning;
    private BigDecimal reflexes;
    private BigDecimal timing;
    private BigDecimal aggression;
    private BigDecimal fightIq;
    private BigDecimal discipline;

    private Perk perk;

    private Strategy strategy;

    private Stance dominantStance;
    private Stance stance;

    private Fighter() {
        this.name = new Name();
        this.head = new Head();
        this.torso = new Torso();
        this.leftArm = new Arm();
        this.rightArm = new Arm();
        this.leftLeg = new Leg();
        this.rightLeg = new Leg();
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Torso getTorso() {
        return torso;
    }

    public void setTorso(Torso torso) {
        this.torso = torso;
    }

    public Arm getLeftArm() {
        return leftArm;
    }

    public void setLeftArm(Arm leftArm) {
        this.leftArm = leftArm;
    }

    public Arm getRightArm() {
        return rightArm;
    }

    public void setRightArm(Arm rightArm) {
        this.rightArm = rightArm;
    }

    public Leg getLeftLeg() {
        return leftLeg;
    }

    public void setLeftLeg(Leg leftLeg) {
        this.leftLeg = leftLeg;
    }

    public Leg getRightLeg() {
        return rightLeg;
    }

    public void setRightLeg(Leg rightLeg) {
        this.rightLeg = rightLeg;
    }

    public BigDecimal getNaturalSpeed() {
        return naturalSpeed;
    }

    public void setNaturalSpeed(BigDecimal naturalSpeed) {
        this.naturalSpeed = naturalSpeed;
    }

    public BigDecimal getNaturalPower() {
        return naturalPower;
    }

    public void setNaturalPower(BigDecimal naturalPower) {
        this.naturalPower = naturalPower;
    }

    public BigDecimal getHeadMovement() {
        return headMovement;
    }

    public void setHeadMovement(BigDecimal headMovement) {
        this.headMovement = headMovement;
    }

    public BigDecimal getBodyMovement() {
        return bodyMovement;
    }

    public void setBodyMovement(BigDecimal bodyMovement) {
        this.bodyMovement = bodyMovement;
    }

    public BigDecimal getFootwork() {
        return footwork;
    }

    public void setFootwork(BigDecimal footwork) {
        this.footwork = footwork;
    }

    public BigDecimal getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(BigDecimal maxStamina) {
        this.maxStamina = maxStamina;
    }

    public BigDecimal getStamina() {
        return stamina;
    }

    public void setStamina(BigDecimal stamina) {
        this.stamina = stamina;
    }

    public BigDecimal getStaminaMalus() {
        return staminaMalus;
    }

    public void setStaminaMalus(BigDecimal staminaMalus) {
        this.staminaMalus = staminaMalus;
    }

    public BigDecimal getStaminaPercentage() {
        return stamina.multiply(staminaMalus).divide(maxStamina, 2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getRecovery() {
        return recovery;
    }

    public void setRecovery(BigDecimal recovery) {
        this.recovery = recovery;
    }

    public BigDecimal getHeart() {
        return heart;
    }

    public void setHeart(BigDecimal heart) {
        this.heart = heart;
    }

    public BigDecimal getPositioning() {
        return positioning;
    }

    public void setPositioning(BigDecimal positioning) {
        this.positioning = positioning;
    }

    public BigDecimal getReflexes() {
        return reflexes;
    }

    public void setReflexes(BigDecimal reflexes) {
        this.reflexes = reflexes;
    }

    public BigDecimal getTiming() {
        return timing;
    }

    public void setTiming(BigDecimal timing) {
        this.timing = timing;
    }

    public BigDecimal getAggression() {
        return aggression;
    }

    public void setAggression(BigDecimal aggression) {
        this.aggression = aggression;
    }

    public BigDecimal getFightIq() {
        return fightIq;
    }

    public void setFightIq(BigDecimal fightIq) {
        this.fightIq = fightIq;
    }

    public BigDecimal getDiscipline() {
        return discipline;
    }

    public void setDiscipline(BigDecimal discipline) {
        this.discipline = discipline;
    }

    public Perk getPerk() {
        return perk;
    }

    public void setPerk(Perk perk) {
        this.perk = perk;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Stance getDominantStance() {
        return dominantStance;
    }


    public void setDominantStance(Stance dominantStance) {
        this.dominantStance = dominantStance;
    }

    public Stance getStance() {
        return stance;
    }

    public void setStance(Stance stance) {
        this.stance = stance;
    }
}
