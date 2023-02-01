package com.gianca1994.aowebbackend.resources.user;

import com.gianca1994.aowebbackend.combatSystem.GenericFunctions;
import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.BadRequest;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.item.Item;
import com.gianca1994.aowebbackend.resources.npc.Npc;
import com.gianca1994.aowebbackend.resources.user.dto.request.FreeSkillPointDTO;


public class UserServiceValidator {

    GenericFunctions genericFunctions = new GenericFunctions();

    public void getUserForGuild(User user) throws NotFound {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating the user for guild.
         * @param User user
         * @return void
         */
        if (user == null) throw new NotFound(UserConst.USER_NOT_FOUND);
    }

    public void setFreeSkillPoint(User user, FreeSkillPointDTO freeSkillPointDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating the free skill point.
         * @param User user
         * @param FreeSkillPointDTO freeSkillPointDTO
         * @return void
         */
        if (user == null) throw new NotFound(UserConst.USER_NOT_FOUND);
        if (freeSkillPointDTO.getAmount() <= 0) throw new BadRequest(UserConst.AMOUNT_MUST_GREATER_THAN_0);
        if (user.getFreeSkillPoints() <= 0) throw new Conflict(UserConst.DONT_HAVE_SKILL_POINTS);
        if (user.getFreeSkillPoints() < freeSkillPointDTO.getAmount())
            throw new Conflict(UserConst.DONT_HAVE_ENOUGH_SKILL_POINTS);

        if (!UserConst.SKILLS_ENABLED.contains(freeSkillPointDTO.getSkillPointName().toLowerCase()))
            throw new Conflict(UserConst.SKILL_POINT_NAME_MUST_ONE_FOLLOWING + UserConst.SKILLS_ENABLED);
    }

    public void userVsUserCombatSystem(User attacker, User defender) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating the user vs user combat system.
         * @param User attacker
         * @param User defender
         * @return void
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

    public void userVsNpcCombatSystem(User user, Npc npc) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating the user vs npc combat system.
         * @param User user
         * @param Npc npc
         * @return void
         */
        if (user == null) throw new NotFound(UserConst.USER_NOT_FOUND);
        if (genericFunctions.checkLifeStartCombat(user)) throw new BadRequest(UserConst.IMPOSSIBLE_ATTACK_LESS_HP);
        if (npc == null) throw new NotFound(UserConst.NPC_NOT_FOUND);
        if (npc.getLevel() > user.getLevel() + SvConfig.MAX_LEVEL_DIFFERENCE)
            throw new Conflict(UserConst.CANT_ATTACK_NPC_LVL_HIGHER_5);

        String userEquipmentType = "none";
        for (Item item : user.getEquipment().getItems()) {
            if (item.getType().equals("ship")) userEquipmentType = "ship";
            if (item.getType().equals("wings")) userEquipmentType = "wings";
        }
        if (npc.getZone().equals("sea") && !userEquipmentType.equals("ship")) throw new Conflict(UserConst.CANT_ATTACK_NPC_SEA);
        if (npc.getZone().equals("hell") && !userEquipmentType.equals("wings")) throw new Conflict(UserConst.CANT_ATTACK_NPC_HELL);

    }
}
