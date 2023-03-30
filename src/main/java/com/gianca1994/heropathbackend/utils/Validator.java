package com.gianca1994.heropathbackend.utils;

import com.gianca1994.heropathbackend.combatSystem.GenericFunctions;
import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.exception.BadReq;
import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.exception.NotFound;
import com.gianca1994.heropathbackend.resources.equipment.Equipment;
import com.gianca1994.heropathbackend.resources.npc.dto.request.NpcDTO;
import com.gianca1994.heropathbackend.resources.quest.dto.request.QuestDTO;
import com.gianca1994.heropathbackend.resources.user.User;
import com.gianca1994.heropathbackend.resources.user.dto.queyModel.UserAttributes;
import com.gianca1994.heropathbackend.resources.user.dto.response.UserGuildDTO;
import com.gianca1994.heropathbackend.resources.user.userRelations.userQuest.UserQuest;

import java.util.List;

public class Validator {

    GenericFunctions genericFunc = new GenericFunctions();

    public void userExist(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(Const.USER.NOT_FOUND.getMsg());
    }

    public void npcExist(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(Const.NPC.NOT_FOUND.getMsg());
    }

    public void getUserForGuild(UserGuildDTO userGuildDTO) throws NotFound {
        if (userGuildDTO == null) throw new NotFound(Const.USER.NOT_FOUND.getMsg());
    }

    public void setFreeSkillPoint(UserAttributes uAttr, String skillName) throws Conflict {
        if (uAttr == null) throw new NotFound(Const.USER.NOT_FOUND.getMsg());
        if (uAttr.getFreeSkillPoints() <= 0) throw new Conflict(Const.USER.DONT_HAVE_SKILLPTS.getMsg());
        if (!Const.USER.SKILLS_ENABLED.getMsg().contains(skillName.toLowerCase()))
            throw new Conflict(Const.USER.ALLOWED_SKILLPTS.getMsg() + Const.USER.SKILLS_ENABLED.getMsg());
    }

    public void autoAttack(User attacker, User defender) throws Conflict {
        if (attacker == defender) throw new Conflict(Const.USER.CANT_ATTACK_YOURSELF.getMsg());
    }

    public void differenceLevelPVP(short attackerLvl, short defenderLvl) throws Conflict {
        if (attackerLvl - defenderLvl > SvConfig.MAX_LEVEL_DIFFERENCE)
            throw new Conflict(Const.USER.CANT_ATTACK_LVL_LOWER_5.getMsg());
    }

    public void differenceLevelPVE(short userLvl, short npcLvl) throws Conflict {
        if (npcLvl > userLvl + SvConfig.MAX_LEVEL_DIFFERENCE)
            throw new Conflict(Const.USER.CANT_ATTACK_NPC_LVL_HIGHER_5.getMsg());
    }

    public void defenderNotAdmin(User defender) throws Conflict {
        if (defender.getRole().equals(SvConfig.ADMIN_ROLE)) throw new Conflict(Const.USER.CANT_ATTACK_ADMIN.getMsg());
    }

    public void lifeStartCombat(User user) {
        if (genericFunc.checkLifeStartCombat(user)) throw new BadReq(Const.USER.IMPOSSIBLE_ATTACK_LESS_HP.getMsg());

    }

    public void userItemReqZoneSea(Equipment userEquip, String npcZone) throws Conflict {
        if (userEquip.getItems().stream().noneMatch(item -> item.getType().equals("ship")) && npcZone.equals("sea"))
            throw new Conflict(Const.USER.CANT_ATTACK_NPC_SEA.getMsg());
    }

    public void userItemReqZoneHell(Equipment userEquip, String npcZone) throws Conflict {
        if (userEquip.getItems().stream().noneMatch(item -> item.getType().equals("wings")) && npcZone.equals("hell"))
            throw new Conflict(Const.USER.CANT_ATTACK_NPC_HELL.getMsg());
    }

    public void pvePtsEnough(User user) throws Conflict {
        if (user.getPvePts() <= 0) throw new Conflict(Const.USER.DONT_HAVE_PVE_PTS.getMsg());
    }

    public void pvpPtsEnough(User user) throws Conflict {
        if (user.getPvpPts() <= 0) throw new Conflict(Const.USER.DONT_HAVE_PVP_PTS.getMsg());
    }

    public void attackerAndDefenderInSameGuild(User attacker, User defender) throws Conflict {
        if (attacker.getGuildName().equals(defender.getGuildName()))
            throw new Conflict(Const.USER.CANT_ATTACK_GUILD_MEMBER.getMsg());
    }

    /* QUEST */
    public void questExist(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(Const.QUEST.NOT_FOUND.getMsg());
    }

    public void questAlreadyExist(boolean alreadyExist) throws Conflict {
        if (alreadyExist) throw new Conflict(Const.QUEST.ALREADY_EXIST.getMsg());
    }

    public void userQuestExist(UserQuest userQuest) {
        if (userQuest == null) throw new NotFound(Const.QUEST.USER_QUEST_NOT_FOUND.getMsg());
    }

    public void dtoSaveQuest(QuestDTO quest) throws Conflict {
        if (quest.getName().isEmpty()) throw new Conflict(Const.QUEST.NAME_EMPTY.getMsg());
        if (quest.getNameNpcKill().isEmpty()) throw new Conflict(Const.QUEST.NAME_NPC_EMPTY.getMsg());
        if (quest.getNpcAmountNeed() < 0) throw new Conflict(Const.QUEST.NPC_AMOUNT_LT0.getMsg());
        if (quest.getUserAmountNeed() < 0) throw new Conflict(Const.QUEST.USER_AMOUNT_LT0.getMsg());
        if (quest.getGiveExp() < 0) throw new Conflict(Const.QUEST.GIVE_EXP_LT0.getMsg());
        if (quest.getGiveGold() < 0) throw new Conflict(Const.QUEST.GIVE_GOLD_LT0.getMsg());
        if (quest.getGiveDiamonds() < 0) throw new Conflict(Const.QUEST.GIVE_DIAMOND_LT0.getMsg());
    }

    public void maxActiveQuest(int amountQuests) throws Conflict {
        int MAX_QUESTS = SvConfig.MAX_ACTIVE_QUESTS;
        if (amountQuests >= MAX_QUESTS) throw new Conflict(String.format(Const.QUEST.MAX_ACTIVE.getMsg(), MAX_QUESTS));
    }

    public void questAccepted(List<UserQuest> userQuests, String questName) throws Conflict {
        if (userQuests.stream().anyMatch(userQuest -> userQuest.getQuest().getName().equals(questName))) {
            throw new Conflict(Const.QUEST.ALREADY_ACCEPTED.getMsg());
        }
    }

    public void questCompleted(int amountKill, int amountNeeded) throws Conflict {
        if (amountKill < amountNeeded) throw new Conflict(Const.QUEST.AMOUNT_CHECK.getMsg());
    }

    public void alreadyCompleted(long userQuestId) throws Conflict {
        if (userQuestId == 0) throw new Conflict(Const.QUEST.ALREADY_COMPLETED.getMsg());
    }

    public void questLvlMin(int userLvl, int questLvl) throws Conflict {
        if (userLvl < questLvl) throw new Conflict(Const.QUEST.LVL_NOT_ENOUGH.getMsg());
    }

    /* NPC */
    public void npcNotFoundZone(int npcSize) {
        if (npcSize <= 0) throw new NotFound(Const.NPC.NOT_IN_ZONE.getMsg());
    }

    public void saveNpc(NpcDTO npc) throws Conflict {
        if (npc.getName().isEmpty()) throw new Conflict(Const.NPC.NAME_EMPTY.getMsg());
        if (npc.getLevel() < 1) throw new Conflict(Const.NPC.LVL_LT_1.getMsg());
        if (npc.getGiveMinExp() < 0) throw new Conflict(Const.NPC.GIVE_MIN_EXP_LT_0.getMsg());
        if (npc.getGiveMaxExp() < 0) throw new Conflict(Const.NPC.GIVE_MAX_EXP_LT_0.getMsg());
        if (npc.getGiveMinGold() < 0) throw new Conflict(Const.NPC.GIVE_MIN_GOLD_LT_0.getMsg());
        if (npc.getGiveMaxGold() < 0) throw new Conflict(Const.NPC.GIVE_MAX_GOLD_LT_0.getMsg());
        if (npc.getHp() < 0) throw new Conflict(Const.NPC.HP_LT_0.getMsg());
        if (npc.getMaxHp() < 0) throw new Conflict(Const.NPC.MAX_HP_LT_0.getMsg());
        if (npc.getMinDmg() < 0) throw new Conflict(Const.NPC.MIN_DMG_LT_0.getMsg());
        if (npc.getMaxDmg() < 0) throw new Conflict(Const.NPC.MAX_DMG_LT_0.getMsg());
        if (npc.getDefense() < 0) throw new Conflict(Const.NPC.MIN_DEF_LT_0.getMsg());
        if (npc.getZone().isEmpty()) throw new Conflict(Const.NPC.ZONE_EMPTY.getMsg());
    }
}
