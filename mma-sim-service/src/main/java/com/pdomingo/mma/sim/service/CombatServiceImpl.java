package com.pdomingo.mma.sim.service;

import com.pdomingo.mma.sim.api.CombatService;
import com.pdomingo.mma.sim.domain.combat.Action;
import com.pdomingo.mma.sim.domain.constants.*;
import com.pdomingo.mma.sim.domain.fighter.Fighter;
import com.pdomingo.mma.sim.domain.fighter.Strategy;
import com.pdomingo.mma.sim.domain.fighter.limbs.BaseLimb;
import com.pdomingo.mma.sim.domain.fighter.techniques.Technique;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CombatServiceImpl implements CombatService {

    private static final Random r = new Random();

    public void startCombat(Integer rounds, Integer timeBetweenRounds, Fighter fighter1, Fighter fighter2) {
        Integer totFightTime = rounds * timeBetweenRounds;
        Integer secondsPerTurn = 1;

        for (int turn = 0; turn < totFightTime; turn += secondsPerTurn) {
            //Roll for initiative
            Double f1Initiative = (r.nextInt(100) + 1) * fighter1.getNaturalSpeed().doubleValue();
            Double f2Initiative = (r.nextInt(100) + 1) * fighter2.getNaturalSpeed().doubleValue();

            Double fighter1MovePoints = 100d * fighter1.getNaturalSpeed().doubleValue();
            Double fighter2MovePoints = 100d * fighter2.getNaturalSpeed().doubleValue();
            Action fighter1PrevAction = null;
            Action fighter2PrevAction = null;

            do {
                Action action1 = new Action();
                if (fighter1MovePoints > 0) {
                    action1 = evaluateFighterAction(fighter1, fighter1PrevAction);
                }
                Action action2 = new Action();
                if (fighter2MovePoints > 0) {
                    action2 = evaluateFighterAction(fighter2, fighter2PrevAction);
                }

                if (f1Initiative > f2Initiative) {
                    determineActions(action1, action2, fighter1, fighter2);
                } else {
                    determineActions(action2, action1, fighter2, fighter1);
                }

                //EVAL MOVE POINT DEDUCTION
                if (action1.getTechnique() != null) {
                    fighter1MovePoints -= action1.getTechnique().getMoveCost().doubleValue();
                } else {
                    fighter1MovePoints = 0d;
                }

                if (action2.getTechnique() != null) {
                    fighter2MovePoints -= action2.getTechnique().getMoveCost().doubleValue();
                } else {
                    fighter2MovePoints = 0d;
                }

                fighter1PrevAction = action1;
                fighter2PrevAction = action2;

            } while (fighter1MovePoints > 1 || fighter2MovePoints > 1);
            calculateRecovery(fighter1);
            calculateRecovery(fighter2);

        }
    }

    private void calculateRecovery(Fighter fighter) {
        calculateLimbRecovery(fighter.getHead(), fighter.getRecovery());
        calculateLimbRecovery(fighter.getTorso(), fighter.getRecovery());
        calculateLimbRecovery(fighter.getLeftArm(), fighter.getRecovery());
        calculateLimbRecovery(fighter.getRightArm(), fighter.getRecovery());
        calculateLimbRecovery(fighter.getLeftLeg(), fighter.getRecovery());
        calculateLimbRecovery(fighter.getRightLeg(), fighter.getRecovery());

        fighter.setStamina(fighter.getStamina().add(fighter.getStaminaMalus()).add(fighter.getRecovery()));
        fighter.setStaminaMalus(fighter.getStamina().multiply(BigDecimal.ONE.subtract(fighter.getTorso().getHealthPercentage())));
        fighter.setStamina(fighter.getStamina().subtract(fighter.getStaminaMalus()));
    }

    private void calculateLimbRecovery (BaseLimb baseLimb, BigDecimal recoveryRate) {
        BigDecimal supposedNewHealth = baseLimb.getHealth().add(recoveryRate);
        BigDecimal actualMaxHealth = baseLimb.getMaxHealth().subtract(baseLimb.getLastingDamage());
        BigDecimal actualNewHealth = supposedNewHealth.compareTo(actualMaxHealth) > 0 ? actualMaxHealth : supposedNewHealth;
        baseLimb.setHealth(actualNewHealth);
    }

    private void determineActions(Action firstAction, Action secondAction, Fighter firstFighter, Fighter secondFighter) {
        //Evaluate event if both fighters attack
        if (firstAction.getActionValue().name().equals(ActionValue.ATTACK)
                && secondAction.getActionValue().name().equals(ActionValue.ATTACK)) {
            exchangeAttacks(firstAction, secondAction, firstFighter, secondFighter);
        } else if ( (firstAction.getActionValue().name().equals(ActionValue.ATTACK) && secondAction.getActionValue().name().equals(ActionValue.DEFEND)) ||
                (secondAction.getActionValue().name().equals(ActionValue.ATTACK) && firstAction.getActionValue().name().equals(ActionValue.DEFEND))
        ) {
            //Evaluate event if one fighter attacks and the other defends
            if (firstAction.getActionValue().name().equals(ActionValue.ATTACK)) {
                calculateAttDefExchange(firstAction.getTechnique(), firstFighter, secondFighter, firstAction, secondAction);
            } else {
                calculateAttDefExchange(secondAction.getTechnique(), secondFighter, firstFighter, secondAction, firstAction);
            }

        } else if ( (firstAction.getActionValue().name().equals(ActionValue.ATTACK) && secondAction.getActionValue().name().equals(ActionValue.DO_NOTHING)) ||
                (secondAction.getActionValue().name().equals(ActionValue.ATTACK) && firstAction.getActionValue().name().equals(ActionValue.DO_NOTHING))
        ) {
            //Evaluate event if one fighter attacks and the other fails to act
            if (firstAction.getActionValue().name().equals(ActionValue.ATTACK)) {
                calculateAttackExchange(firstAction.getTechnique(), firstFighter, secondFighter, firstAction, BigDecimal.ZERO);
            } else {
                calculateAttackExchange(secondAction.getTechnique(), secondFighter, firstFighter, secondAction, BigDecimal.ZERO);
            }

        }
    }

    private void exchangeAttacks (Action action1, Action action2, Fighter fighter1, Fighter fighter2) {
        Technique tech1 = action1.getTechnique();
        Technique tech2 = action2.getTechnique();

        BigDecimal tech1MoveCost = tech1.getMoveCost();
        Double actualTech1MoveCost = tech1MoveCost.subtract(tech1MoveCost.multiply(tech1.getSpeedMod().subtract(BigDecimal.ONE))).doubleValue();
        BigDecimal tech2MoveCost = tech2.getMoveCost();
        Double actualTech2MoveCost = tech2MoveCost.subtract(tech2MoveCost.multiply(tech2.getSpeedMod().subtract(BigDecimal.ONE))).doubleValue();

        if (actualTech1MoveCost > actualTech2MoveCost) {
            BigDecimal counterStrikeModifier = BigDecimal.ZERO;
            counterStrikeModifier = calculateAttackExchange(tech1, fighter1, fighter2, action1, counterStrikeModifier);
            calculateAttackExchange(tech2, fighter2, fighter1, action2, counterStrikeModifier);
        } else {
            BigDecimal counterStrikeModifier = BigDecimal.ZERO;
            counterStrikeModifier = calculateAttackExchange(tech2, fighter2, fighter1, action2, counterStrikeModifier);
            calculateAttackExchange(tech1, fighter1, fighter2, action1, counterStrikeModifier);
        }
    }

    private BigDecimal calculateAttackExchange(Technique techUsed,
                                               Fighter attacker,
                                               Fighter defender,
                                               Action attackerAction,
                                               BigDecimal counterStrikeMod) {

        Double attackRoll = ((r.nextInt(techUsed.getAccuracyMod().intValue()) + 1) * attacker.getNaturalSpeed().multiply(techUsed.getSpeedMod()).add(counterStrikeMod).doubleValue());
        Double defenceRoll = 0d;
        if (attackerAction.getAttackTarget().name().equals(AttackTarget.HEAD)) {
            defenceRoll = ((r.nextInt(defender.getHeadMovement().intValue()) + 1) * 0.3 + (r.nextInt(defender.getReflexes().intValue()) + 1) * 0.5);
        } else if (attackerAction.getAttackTarget().name().equals(AttackTarget.BODY)) {
            defenceRoll = ((r.nextInt(defender.getBodyMovement().intValue()) + 1) * 0.3 + (r.nextInt(defender.getReflexes().intValue()) + 1) * 0.5);
        } else {
            defenceRoll = (r.nextInt(defender.getReflexes().intValue()) + 1) * 0.5;
        }

        if (defenceRoll > 0) {
            defender.setStamina(defender.getStamina().subtract(BigDecimal.valueOf(10d)));
        }

        BigDecimal totStrikePower = techUsed.getPowerMod().multiply(attacker.getNaturalPower());

        if (attackRoll > defenceRoll) {
            //CALC HIT
            if (attackerAction.getAttackTarget().name().equals(AttackTarget.HEAD)) {
                defender.getHead().setHealth(defender.getHead().getHealth().subtract(totStrikePower));
            } else if (attackerAction.getAttackTarget().name().equals(AttackTarget.BODY)) {
                defender.getTorso().setHealth(defender.getTorso().getHealth().subtract(totStrikePower));
//                defender.setStamina(defender.getStamina().multiply(defender.getTorso().getHealthPercentage()));
            } else {
                if (defender.getStance().name().equals(Stance.ORTHODOX)) {
                    defender.getLeftLeg().setHealth(defender.getLeftLeg().getHealth().subtract(totStrikePower));
                } else {
                    defender.getRightLeg().setHealth(defender.getRightLeg().getHealth().subtract(totStrikePower));
                }
            }
            counterStrikeMod = totStrikePower.multiply(BigDecimal.valueOf(-1.5));
        } else {
            counterStrikeMod = totStrikePower.multiply(BigDecimal.valueOf(0.5));
        }

        attacker.setStamina(attacker.getStamina().subtract(techUsed.getMoveCost()));

        return counterStrikeMod;
    }

    private BigDecimal calculateAttackBlocked( Technique attTech,
                                               Technique defTech,
                                               Fighter attacker,
                                               Fighter defender,
                                               Action action1) {

        Double attackRoll = ((r.nextInt(attTech.getAccuracyMod().intValue()) + 1) * attacker.getNaturalSpeed().multiply(attTech.getSpeedMod()).doubleValue());
        Double defenceRoll = defTech == null ? 0 :  ((r.nextInt(defTech.getAccuracyMod().intValue() + 1) * defender.getNaturalSpeed().multiply(defTech.getSpeedMod()).doubleValue()));


        BigDecimal totStrikePower = attTech.getPowerMod().multiply(attacker.getNaturalPower());
        BigDecimal totBlockPower = defTech.getPowerMod().multiply(defender.getNaturalPower());
        if (attackRoll <= defenceRoll) {
            //CALC HIT
            totStrikePower = totStrikePower.compareTo(totBlockPower) > 0 ? totStrikePower.subtract(totBlockPower): BigDecimal.ZERO;
        }

        if (action1.getAttackTarget().name().equals(AttackTarget.HEAD)) {
            defender.getHead().setHealth(defender.getHead().getHealth().subtract(totStrikePower));
        } else if (action1.getAttackTarget().name().equals(AttackTarget.BODY)) {
            defender.getTorso().setHealth(defender.getTorso().getHealth().subtract(totStrikePower));
        } else {
            if (defender.getStance().name().equals(Stance.ORTHODOX)) {
                defender.getLeftLeg().setHealth(defender.getLeftLeg().getHealth().subtract(totStrikePower));
            } else {
                defender.getRightLeg().setHealth(defender.getRightLeg().getHealth().subtract(totStrikePower));
            }
        }

        attacker.setStamina(attacker.getStamina().subtract(attTech.getMoveCost()));
        defender.setStamina(defender.getStamina().subtract(defTech.getMoveCost()));

        return totStrikePower;
    }

    private BigDecimal calculateAttDefExchange(Technique attackerTech,
                                               Fighter attacker,
                                               Fighter defender,
                                               Action attackAction,
                                               Action defendAction) {

        Boolean willDefend = (r.nextInt(100) + 1) < (r.nextInt(defender.getReflexes().intValue()) + 1) * 0.5 + (defendAction.getActionValue().name().equals(ActionValue.DEFEND) ? 50 : 0);

        Double attackRoll = ((r.nextInt(attackerTech.getAccuracyMod().intValue()) + 1) * attacker.getNaturalSpeed().multiply(attackerTech.getSpeedMod()).doubleValue());
        Double defenceRoll = 0d;

        Boolean willBlock = (r.nextInt(100) + 1) <= (defender.getStrategy().getBlockRate().multiply(defender.getDiscipline())).intValue();
        Boolean willEvade = (r.nextInt(100) + 1) <= (defender.getStrategy().getEvadeRate().multiply(defender.getDiscipline())).intValue();
        Boolean willCounter = (r.nextInt(100) + 1) <= (defender.getStrategy().getCounterRate().multiply(defender.getDiscipline())).intValue();
//        Boolean willTrade = (r.nextInt(100) + 1) <= (defender.getStrategy().getTradeBlowsRate().multiply(defender.getDiscipline())).intValue();

        if (willDefend) {
            if (willBlock) {
                Technique defenderTech = evaluateDefence(defender, defender.getStrategy(), attackAction);
                defendAction.setTechnique(defenderTech);
                calculateAttackBlocked(attackerTech, defenderTech, attacker, defender, attackAction);
            } else if (willEvade) {
                if (attackAction.getAttackTarget().name().equals(AttackTarget.HEAD)) {
                    defenceRoll = ((r.nextInt(defender.getHeadMovement().intValue()) + 1) * defender.getNaturalSpeed().doubleValue());
                } else if (attackAction.getAttackTarget().name().equals(AttackTarget.BODY)) {
                    defenceRoll = ((r.nextInt(defender.getBodyMovement().intValue()) + 1) * defender.getNaturalSpeed().doubleValue());
                } else {
                    defenceRoll = (r.nextInt(defender.getReflexes().intValue()) + 1) * 0.5 + (r.nextInt(defender.getFootwork().intValue()) + 1) * defender.getNaturalSpeed().doubleValue();
                }
                if (attackRoll > defenceRoll) {
                    calculateAttackBlocked(attackerTech, null, attacker, defender, attackAction);
                }

            } else if (willCounter) {
                Action action = new Action();
                Technique technique = evalAttackTarget(defender, action);
                if (technique != null) {
                    action.setTechnique(technique);
                    action.setActionValue(ActionValue.ATTACK);
                }
                defendAction.setTechnique(technique);
                exchangeAttacks(action, attackAction, defender, attacker);
            }
        }

        return BigDecimal.ONE;
    }

    private Action evaluateFighterAction(Fighter fighter, Action prevAction) {
        Action action = new Action();
        Strategy strategy = fighter.getStrategy();
        BigDecimal discipline = fighter.getDiscipline();

        int randomInt = r.nextInt(100) + 1;
        Boolean willTryToAttack;
        Boolean willTryToCombo = false;
        if (prevAction != null && prevAction.getActionValue().name().equals(ActionValue.ATTACK)) {
            willTryToAttack = randomInt <= (strategy.getAttackRate().multiply(strategy.getComboRate()).multiply(discipline).multiply(fighter.getStaminaPercentage())).doubleValue();
            willTryToCombo = true;
        } else {
            willTryToAttack = randomInt <= (strategy.getAttackRate().multiply(discipline).multiply(fighter.getStaminaPercentage())).doubleValue();
        }

        if (willTryToAttack) {
            Boolean willAttack = (r.nextInt(100) + 1) <= fighter.getAggression().multiply(fighter.getStaminaPercentage()).doubleValue();
            if (willAttack) {

                Technique technique = evalAttackTarget(fighter, action);
                if (willTryToCombo) {
                    technique = evaluateComboToUse(prevAction.getTechnique());
                }

                if (technique != null) {
                    action.setTechnique(technique);
                    action.setActionValue(ActionValue.ATTACK);
                }
            }
        } else {
            Boolean willDefend = (r.nextInt(100) + 1) <= strategy.getDefendRate().multiply(discipline).multiply(fighter.getStaminaPercentage()).doubleValue();
            action.setActionValue(willDefend ? ActionValue.DEFEND: ActionValue.DO_NOTHING);
        }
        return action;
    }

    private Technique evalAttackTarget(Fighter fighter, Action action) {
        Strategy strategy = fighter.getStrategy();
        BigDecimal discipline = fighter.getDiscipline();

        Integer headHuntRate = strategy.getHeadHuntRate().intValue();
        Integer bodyHuntRate = strategy.getBodyHuntRate().intValue();
        Boolean willAttackHead = (r.nextInt(100) + 1) <= (strategy.getHeadHuntRate().multiply(discipline)).intValue();
        Boolean willAttackBody = (r.nextInt(100 - headHuntRate) + 1) <= (strategy.getBodyHuntRate().multiply(discipline)).intValue();
        Boolean willAttackLegs = (r.nextInt(100 - headHuntRate - bodyHuntRate) + 1) <= (strategy.getLegHuntRate().multiply(discipline)).intValue();
        Technique technique = null;
        if (willAttackHead) {
            technique = evaluateAttackTech(fighter, strategy, false);
            action.setAttackTarget(AttackTarget.HEAD);
        } else if (willAttackBody) {
            technique = evaluateAttackTech(fighter, strategy, false);
            action.setAttackTarget(AttackTarget.BODY);
        } else if (willAttackLegs) {
            technique = evaluateAttackTech(fighter, strategy, true);
            action.setAttackTarget(AttackTarget.LEAD_LEG);
        }
        return technique;
    }

    private Technique evaluateAttackTech(Fighter fighter, Strategy strategy, Boolean isLegAttack) {
        Technique techToUse = null;
        Boolean willPunch = (r.nextInt(100) + 1) <= (strategy.getPunchRate().multiply(fighter.getDiscipline())).intValue();
        Boolean willKick = (r.nextInt(100 - strategy.getPunchRate().intValue()) + 1) <= (strategy.getPunchRate().multiply(fighter.getDiscipline())).intValue();
        Integer dominantSideModifier = fighter.getDominantStance().compareTo(fighter.getStance()) == 0 ? 15 : -15;
        Boolean useDominantSide = (r.nextInt(100) + 1) < 50 + dominantSideModifier;
        if (willPunch && !isLegAttack) {
            if (useDominantSide) {
                if (fighter.getDominantStance().name().equals(Stance.SOUTHPAW)) {
                    techToUse = evaluateTechToUse(fighter.getLeftArm(), TechniqueType.ATTACK, false);
                } else {
                    techToUse = evaluateTechToUse(fighter.getRightArm(), TechniqueType.ATTACK, false);
                }
            } else {
                if (fighter.getStance().name().equals(Stance.SOUTHPAW) && (r.nextInt(100) + 1) < 50) {
                    techToUse = evaluateTechToUse(fighter.getLeftArm(), TechniqueType.ATTACK, false);
                } else {
                    techToUse = evaluateTechToUse(fighter.getRightArm(), TechniqueType.ATTACK, false);
                }
            }
        } else if (willKick) {
            if (useDominantSide) {
                if (fighter.getDominantStance().name().equals(Stance.SOUTHPAW)) {
                    techToUse = evaluateTechToUse(fighter.getLeftLeg(), TechniqueType.ATTACK, false);
                } else {
                    techToUse = evaluateTechToUse(fighter.getRightLeg(), TechniqueType.ATTACK, false);
                }
            } else {
                if (fighter.getStance().name().equals(Stance.SOUTHPAW)) {
                    techToUse = evaluateTechToUse(fighter.getLeftLeg(), TechniqueType.ATTACK, false);
                } else {
                    techToUse = evaluateTechToUse(fighter.getRightLeg(), TechniqueType.ATTACK, false);
                }
            }
        }
        return techToUse;
    }

    private Technique evaluateDefence(Fighter defender, Strategy strategy, Action attackAction) {
        Technique techToUse = null;
        if (attackAction.getAttackTarget().name().equals(AttackTarget.HEAD)) {

            techToUse = evaluateTechToUse(defender.getLeftArm(), TechniqueType.BLOCK, true);
            if (techToUse == null) {
                evaluateTechToUse(defender.getRightArm(), TechniqueType.BLOCK, true);
            } else {
                evaluateTechToUse(defender.getHead(), TechniqueType.BLOCK, true);
            }


        } else if (attackAction.getAttackTarget().name().equals(AttackTarget.BODY)) {
//            defenceRoll = ((r.nextInt(defender.getBodyMovement().intValue()) + 1) * 0.3 + (r.nextInt(defender.getReflexes().intValue()) + 1) * 0.5);
            techToUse = evaluateTechToUse(defender.getLeftArm(), TechniqueType.BLOCK, true);
            if (techToUse == null) {
                evaluateTechToUse(defender.getRightArm(), TechniqueType.BLOCK, true);
            } else {
                evaluateTechToUse(defender.getTorso(), TechniqueType.BLOCK, true);
            }
        } else {
//            defenceRoll = (r.nextInt(defender.getReflexes().intValue()) + 1) * 0.5;
            if (defender.getStance().name().equals(Stance.ORTHODOX)) {
                techToUse = evaluateTechToUse(defender.getLeftLeg(), TechniqueType.BLOCK, true);
            } else {
                techToUse = evaluateTechToUse(defender.getRightLeg(), TechniqueType.BLOCK, true);
            }
        }

        return techToUse;
    }

    private Technique evaluateTechToUse(BaseLimb baseLimb, TechniqueType techniqueType, Boolean isNullable) {
        List<Technique> techList = baseLimb.getTechniques().stream()
                .filter(technique -> technique.getRequiredSide().compareTo(baseLimb.getLimbSide()) == 0
                        && (r.nextInt(100) + 1) <= 30
                        && techniqueType.name().equals(techniqueType.name())
                ).collect(Collectors.toList());
        if (techList != null) {
            return techList.get(0);
        } else {
            return baseLimb.getTechniques().get(0);
        }
    }

    private Technique evaluateComboToUse(Technique technique) {
        List<Technique> techList = technique.getCombo().stream()
                .filter(combo -> (r.nextInt(100) + 1) <= 30).collect(Collectors.toList());
        if (techList != null) {
            return techList.get(0);
        } else {
            return technique.getCombo().get(0);
        }
    }
}
