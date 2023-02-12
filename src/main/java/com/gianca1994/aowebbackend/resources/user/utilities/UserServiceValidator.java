package com.gianca1994.aowebbackend.resources.user.utilities;

import com.gianca1994.aowebbackend.combatSystem.GenericFunctions;
import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.BadRequest;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.dto.queyModel.UserAttributes;
import com.gianca1994.aowebbackend.resources.user.dto.response.UserGuildDTO;

/**
 * @Author: Gianca1994
 * Explanation: This class is in charge of validating the user service.
 */

public class UserServiceValidator {

    GenericFunctions genericFunctions = new GenericFunctions();

    public void userExist(boolean exist) throws Conflict {
        /**
         *
         */
        if (!exist) throw new Conflict(UserConst.USER_NOT_FOUND);
    }

    public void npcExist(boolean exist) throws Conflict {
        /**
         *
         */
        if (!exist) throw new Conflict(UserConst.NPC_NOT_FOUND);
    }

    public void getUserForGuild(UserGuildDTO userGuildDTO) throws NotFound {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating the user for guild.
         * @param UserGuildDTO userGuildDTO
         * @return void
         */
        if (userGuildDTO == null) throw new NotFound(UserConst.USER_NOT_FOUND);
    }

    public void setFreeSkillPoint(UserAttributes uAttr, String skillName) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating the free skill point.
         * @param UserAttributes uAttr
         * @param String skillName
         * @return void
         */
        if (uAttr == null) throw new NotFound(UserConst.USER_NOT_FOUND);
        if (uAttr.getFreeSkillPoints() <= 0) throw new Conflict(UserConst.DONT_HAVE_SKILL_POINTS);
        if (!UserConst.SKILLS_ENABLED.contains(skillName.toLowerCase()))
            throw new Conflict(UserConst.SKILL_POINT_NAME_MUST_ONE_FOLLOWING + UserConst.SKILLS_ENABLED);
    }

    public void checkAutoAttack(User attacker, User defender) throws Conflict {
        /**
         *
         */
        if (attacker == defender) throw new Conflict(UserConst.CANT_ATTACK_YOURSELF);
    }

    public void checkDifferenceLevelPVP(short attackerLvl, short defenderLvl) throws Conflict {
        /**
         *
         */
        if (attackerLvl - defenderLvl > SvConfig.MAX_LEVEL_DIFFERENCE)
            throw new Conflict(UserConst.CANT_ATTACK_LVL_LOWER_5);
    }

    public void checkDifferenceLevelPVE(short userLvl, short npcLvl) throws Conflict {
        /**
         *
         */
        if (npcLvl > userLvl + SvConfig.MAX_LEVEL_DIFFERENCE)
            throw new Conflict(UserConst.CANT_ATTACK_NPC_LVL_HIGHER_5);
    }


    public void checkDefenderNotAdmin(User defender) throws Conflict {
        /**
         *
         */
        if (defender.getRole().equals("ADMIN")) throw new Conflict(UserConst.CANT_ATTACK_ADMIN);
    }

    public void checkLifeStartCombat(User user) throws Conflict {
        /**
         *
         */
        if (genericFunctions.checkLifeStartCombat(user)) throw new BadRequest(UserConst.IMPOSSIBLE_ATTACK_LESS_HP);

    }

    public void checkUserItemReqZoneSea(Equipment userEquip) throws Conflict {
        /**
         *
         */
        if (userEquip.getItems().stream().noneMatch(item -> item.getType().equals("ship")))
            throw new Conflict(UserConst.CANT_ATTACK_NPC_SEA);
    }

    public void checkUserItemReqZoneHell(Equipment userEquip) throws Conflict {
        /**
         *
         */
        if (userEquip.getItems().stream().noneMatch(item -> item.getType().equals("wings")))
            throw new Conflict(UserConst.CANT_ATTACK_NPC_HELL);
    }
}
