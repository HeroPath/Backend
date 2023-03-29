package com.gianca1994.heropathbackend.resources.user.utilities;

import com.gianca1994.heropathbackend.combatSystem.GenericFunctions;
import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.exception.BadReq;
import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.exception.NotFound;
import com.gianca1994.heropathbackend.resources.equipment.Equipment;
import com.gianca1994.heropathbackend.resources.user.User;
import com.gianca1994.heropathbackend.resources.user.dto.queyModel.UserAttributes;
import com.gianca1994.heropathbackend.resources.user.dto.response.UserGuildDTO;
import com.gianca1994.heropathbackend.utils.Const;

/**
 * @Author: Gianca1994
 * @Explanation: This class is in charge of validating the user service.
 */

public class UserServiceValidator {

    GenericFunctions genericFunctions = new GenericFunctions();

    public void userExist(boolean exist) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of validating the user.
         * @param boolean exist
         * @return void
         */
        if (!exist) throw new Conflict(Const.USER.NOT_FOUND.getMsg());
    }

    public void npcExist(boolean exist) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of validating the npc.
         * @param boolean exist
         * @return void
         */
        if (!exist) throw new Conflict(Const.NPC.NOT_FOUND.getMsg());
    }

    public void getUserForGuild(UserGuildDTO userGuildDTO) throws NotFound {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of validating the user for guild.
         * @param UserGuildDTO userGuildDTO
         * @return void
         */
        if (userGuildDTO == null) throw new NotFound(Const.USER.NOT_FOUND.getMsg());
    }

    public void setFreeSkillPoint(UserAttributes uAttr, String skillName) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of validating the free skill point.
         * @param UserAttributes uAttr
         * @param String skillName
         * @return void
         */
        if (uAttr == null) throw new NotFound(Const.USER.NOT_FOUND.getMsg());
        if (uAttr.getFreeSkillPoints() <= 0) throw new Conflict(Const.USER.DONT_HAVE_SKILLPTS.getMsg());
        if (!Const.USER.SKILLS_ENABLED.getMsg().contains(skillName.toLowerCase()))
            throw new Conflict(Const.USER.ALLOWED_SKILLPTS.getMsg() + Const.USER.SKILLS_ENABLED.getMsg());
    }

    public void autoAttack(User attacker, User defender) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of validating the auto attack.
         * @param User attacker
         * @param User defender
         * @return void
         */
        if (attacker == defender) throw new Conflict(Const.USER.CANT_ATTACK_YOURSELF.getMsg());
    }

    public void differenceLevelPVP(short attackerLvl, short defenderLvl) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of validating the difference level pvp.
         * @param short attackerLvl
         * @param short defenderLvl
         * @return void
         */
        if (attackerLvl - defenderLvl > SvConfig.MAX_LEVEL_DIFFERENCE)
            throw new Conflict(Const.USER.CANT_ATTACK_LVL_LOWER_5.getMsg());
    }

    public void differenceLevelPVE(short userLvl, short npcLvl) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of validating the difference level pve.
         * @param short userLvl
         * @param short npcLvl
         * @return void
         */
        if (npcLvl > userLvl + SvConfig.MAX_LEVEL_DIFFERENCE)
            throw new Conflict(Const.USER.CANT_ATTACK_NPC_LVL_HIGHER_5.getMsg());
    }

    public void defenderNotAdmin(User defender) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of validating the defender not admin.
         * @param User defender
         * @return void
         */
        if (defender.getRole().equals(SvConfig.ADMIN_ROLE)) throw new Conflict(Const.USER.CANT_ATTACK_ADMIN.getMsg());
    }

    public void lifeStartCombat(User user) {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of validating the life start combat.
         * @param User user
         * @return void
         */
        if (genericFunctions.checkLifeStartCombat(user))
            throw new BadReq(Const.USER.IMPOSSIBLE_ATTACK_LESS_HP.getMsg());

    }

    public void userItemReqZoneSea(Equipment userEquip, String npcZone) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of validating the user item req zone sea.
         * @param Equipment userEquip
         * @return void
         */
        if (userEquip.getItems().stream().noneMatch(item -> item.getType().equals("ship")) && npcZone.equals("sea"))
            throw new Conflict(Const.USER.CANT_ATTACK_NPC_SEA.getMsg());
    }

    public void userItemReqZoneHell(Equipment userEquip, String npcZone) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of validating the user item req zone hell.
         * @param Equipment userEquip
         * @return void
         */
        if (userEquip.getItems().stream().noneMatch(item -> item.getType().equals("wings")) && npcZone.equals("hell"))
            throw new Conflict(Const.USER.CANT_ATTACK_NPC_HELL.getMsg());
    }

    public void pvePtsEnough(User user) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of validating the pve pts enough.
         * @param User user
         * @return void
         */
        if (user.getPvePts() <= 0) throw new Conflict(Const.USER.DONT_HAVE_PVE_PTS.getMsg());
    }

    public void pvpPtsEnough(User user) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of validating the pvp pts enough.
         * @param User user
         * @return void
         */
        if (user.getPvpPts() <= 0) throw new Conflict(Const.USER.DONT_HAVE_PVP_PTS.getMsg());
    }

    public void attackerAndDefenderInSameGuild(User attacker, User defender) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of validating the attacker and defender in same guild.
         * @param User attacker
         * @param User defender
         * @return void
         */
        if (attacker.getGuildName().equals(defender.getGuildName()))
            throw new Conflict(Const.USER.CANT_ATTACK_GUILD_MEMBER.getMsg());
    }
}
