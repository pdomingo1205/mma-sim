package com.pdomingo.mma.sim.domain.combat;

import com.pdomingo.mma.sim.domain.constants.ActionValue;
import com.pdomingo.mma.sim.domain.constants.AttackTarget;
import com.pdomingo.mma.sim.domain.fighter.techniques.Technique;

public class Action {
    private ActionValue actionValue;
    private Technique technique;
    private AttackTarget attackTarget;

    public Action() {
        this.actionValue = ActionValue.DO_NOTHING;
    }

    public ActionValue getActionValue() {
        return actionValue;
    }

    public void setActionValue(ActionValue actionValue) {
        this.actionValue = actionValue;
    }

    public Technique getTechnique() {
        return technique;
    }

    public void setTechnique(Technique technique) {
        this.technique = technique;
    }

    public AttackTarget getAttackTarget() {
        return attackTarget;
    }

    public void setAttackTarget(AttackTarget attackTarget) {
        this.attackTarget = attackTarget;
    }
}
