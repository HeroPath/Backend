package com.gianca1994.aowebbackend.resources.user;

import com.gianca1994.aowebbackend.combatSystem.GenericFunctions;
import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.BadRequest;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;


public class UserServiceValidator {

    GenericFunctions genericFunctions = new GenericFunctions();


    public void userVsUserCombatSystem(User attacker, User defender) throws Conflict {
        /**
         *
         */
        if (attacker == null) throw new NotFound(UserConst.USER_NOT_FOUND);
        if (genericFunctions.checkLifeStartCombat(attacker)) throw new BadRequest(UserConst.IMPOSSIBLE_ATTACK_LESS_HP);
        if (attacker.getLevel() < SvConfig.MAX_LEVEL_DIFFERENCE) throw new Conflict(UserConst.CANT_ATTACK_LVL_LOWER_5);
        if (attacker == defender) throw new Conflict(UserConst.CANT_ATTACK_YOURSELF);
        if (defender == null) throw new NotFound(UserConst.USER_NOT_FOUND);
        if (defender.getLevel() < SvConfig.MAX_LEVEL_DIFFERENCE) throw new Conflict(UserConst.CANT_ATTACK_LVL_LOWER_5);
        if (defender.getRole().getRoleName().equals("ADMIN")) throw new Conflict(UserConst.CANT_ATTACK_ADMIN);
        if (genericFunctions.checkLifeStartCombat(defender)) throw new BadRequest(UserConst.IMPOSSIBLE_ATTACK_15_ENEMY);
    }
}
