package com.gianca1994.heropathbackend.utils;

import com.gianca1994.heropathbackend.combatSystem.GenericFunctions;
import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.exception.BadReq;
import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.exception.NotFound;
import com.gianca1994.heropathbackend.resources.equipment.Equipment;
import com.gianca1994.heropathbackend.resources.guild.Guild;
import com.gianca1994.heropathbackend.resources.guild.dto.request.GuildDTO;
import com.gianca1994.heropathbackend.resources.item.Item;
import com.gianca1994.heropathbackend.resources.item.dto.request.ItemDTO;
import com.gianca1994.heropathbackend.resources.item.utilities.ItemConst;
import com.gianca1994.heropathbackend.resources.mail.utilities.MailConst;
import com.gianca1994.heropathbackend.resources.market.utilities.MarketConst;
import com.gianca1994.heropathbackend.resources.npc.dto.request.NpcDTO;
import com.gianca1994.heropathbackend.resources.quest.dto.request.QuestDTO;
import com.gianca1994.heropathbackend.resources.quest.utilities.QuestConst;
import com.gianca1994.heropathbackend.resources.user.User;
import com.gianca1994.heropathbackend.resources.user.dto.queyModel.UserAttributes;
import com.gianca1994.heropathbackend.resources.user.dto.response.UserGuildDTO;
import com.gianca1994.heropathbackend.resources.user.userRelations.userQuest.UserQuest;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Validator {
    GenericFunctions genericFunctions = new GenericFunctions();

    public void userExist(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(Const.USER.NOT_FOUND.getMsg());
    }

    public void questExist(boolean exist) throws Conflict {
        if (exist) throw new Conflict(QuestConst.QUEST_ALREADY_EXISTS);
    }

    public void npcExist(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(Const.NPC.NOT_FOUND.getMsg());
    }

    public void mailExist(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(MailConst.MAIL_NOT_FOUND);
    }

    public void guildExist(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(Const.GUILD.NOT_FOUND.getMsg());
    }

    public void itemExist(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(ItemConst.ITEM_NOT_FOUND);
    }


    /* USER */
    public void getUserForGuild(UserGuildDTO userGuildDTO) throws NotFound {
        if (userGuildDTO == null) throw new NotFound(Const.USER.NOT_FOUND.getMsg());
    }

    public void setFreeSkillPoint(UserAttributes uAttr, String skillName) throws Conflict {
        if (uAttr == null) throw new NotFound(Const.USER.NOT_FOUND.getMsg());
        if (uAttr.getFreeSkillPoints() <= 0) throw new Conflict(Const.USER.DONT_HAVE_SKILLPTS.getMsg());
        if (!Const.USER.SKILLS_ENABLED.getSkills().contains(skillName.toLowerCase()))
            throw new Conflict(Const.USER.ALLOWED_SKILLPTS.getMsg() + Const.USER.SKILLS_ENABLED.getSkills());
    }

    public void checkAutoAttack(User attacker, User defender) throws Conflict {
        if (attacker == defender) throw new Conflict(Const.USER.CANT_ATTACK_YOURSELF.getMsg());
    }

    public void checkDifferenceLevelPVP(short attackerLvl, short defenderLvl) throws Conflict {
        if (attackerLvl - defenderLvl > SvConfig.MAX_LEVEL_DIFFERENCE)
            throw new Conflict(Const.USER.CANT_ATTACK_LVL_LOWER_5.getMsg());
    }

    public void checkDifferenceLevelPVE(short userLvl, short npcLvl) throws Conflict {
        if (npcLvl > userLvl + SvConfig.MAX_LEVEL_DIFFERENCE)
            throw new Conflict(Const.USER.CANT_ATTACK_NPC_LVL_HIGHER_5.getMsg());
    }

    public void checkDefenderNotAdmin(User defender) throws Conflict {
        if (defender.getRole().equals(SvConfig.ADMIN_ROLE)) throw new Conflict(Const.USER.CANT_ATTACK_ADMIN.getMsg());
    }

    public void checkLifeStartCombat(User user) {
        if (genericFunctions.checkLifeStartCombat(user))
            throw new BadReq(Const.USER.IMPOSSIBLE_ATTACK_LESS_HP.getMsg());

    }

    public void checkUserItemReqZoneSea(Equipment userEquip, String npcZone) throws Conflict {
        if (userEquip.getItems().stream().noneMatch(item -> item.getType().equals("ship")) && npcZone.equals("sea"))
            throw new Conflict(Const.USER.CANT_ATTACK_NPC_SEA.getMsg());
    }

    public void checkUserItemReqZoneHell(Equipment userEquip, String npcZone) throws Conflict {
        if (userEquip.getItems().stream().noneMatch(item -> item.getType().equals("wings")) && npcZone.equals("hell"))
            throw new Conflict(Const.USER.CANT_ATTACK_NPC_HELL.getMsg());
    }

    public void checkPvePtsEnough(User user) throws Conflict {
        if (user.getPvePts() <= 0) throw new Conflict(Const.USER.DONT_HAVE_PVE_PTS.getMsg());
    }

    public void checkPvpPtsEnough(User user) throws Conflict {
        if (user.getPvpPts() <= 0) throw new Conflict(Const.USER.DONT_HAVE_PVP_PTS.getMsg());
    }

    public void checkAttackerAndDefenderInSameGuild(User attacker, User defender) throws Conflict {
        if (attacker.getGuildName().equals(defender.getGuildName()))
            throw new Conflict(Const.USER.CANT_ATTACK_GUILD_MEMBER.getMsg());
    }

    /* QUESTS */
    public void questFound(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(QuestConst.QUEST_NOT_FOUND);
    }



    public void userQuestFound(UserQuest userQuest) {
        if (userQuest == null) throw new NotFound(QuestConst.USER_QUEST_NOT_FOUND);
    }

    public void userFound(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(QuestConst.USER_NOT_FOUND);
    }

    public void checkDtoSaveQuest(QuestDTO quest) throws Conflict {
        if (quest.getName().isEmpty()) throw new Conflict(QuestConst.NAME_EMPTY);
        if (quest.getNameNpcKill().isEmpty()) throw new Conflict(QuestConst.NAME_NPC_KILL_EMPTY);
        if (quest.getNpcAmountNeed() < 0) throw new Conflict(QuestConst.NPC_KILL_AMOUNT_LT0);
        if (quest.getUserAmountNeed() < 0) throw new Conflict(QuestConst.USER_KILL_AMOUNT_LT0);
        if (quest.getGiveExp() < 0) throw new Conflict(QuestConst.GIVE_EXP_LT0);
        if (quest.getGiveGold() < 0) throw new Conflict(QuestConst.GIVE_GOLD_LT0);
        if (quest.getGiveDiamonds() < 0) throw new Conflict(QuestConst.GIVE_DIAMONDS_LT0);
    }

    public void checkUserMaxQuests(int amountQuests) throws Conflict {
        if (amountQuests >= SvConfig.MAX_ACTIVE_QUESTS) throw new Conflict(QuestConst.MAX_ACTIVE_QUESTS);
    }

    public void checkQuestAccepted(List<UserQuest> userQuests, String questName) throws Conflict {
        if (userQuests.stream().anyMatch(userQuest -> userQuest.getQuest().getName().equals(questName))) {
            throw new Conflict(QuestConst.QUEST_ALREADY_ACCEPTED);
        }
    }

    public void checkQuestCompleted(int amountKill, int amountNeeded) throws Conflict {
        if (amountKill < amountNeeded) throw new Conflict(QuestConst.QUEST_AMOUNT_CHECK);
    }

    public void questAlreadyCompleted(long userQuestId) throws Conflict {
        if (userQuestId == 0) throw new Conflict(QuestConst.QUEST_ALREADY_COMPLETED);
    }

    public void checkUserHaveLvlRequired(int userLvl, int questLvl) throws Conflict {
        if (userLvl < questLvl) throw new Conflict(QuestConst.USER_LVL_NOT_ENOUGH);
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

    /* MARKET */
    public void checkItemExists(boolean exists) {
        if (!exists) throw new BadReq(MarketConst.ITEM_NOT_FOUND);
    }

    public void checkSellerExists(boolean exists) {
        if (!exists) throw new BadReq(MarketConst.SELLER_NOT_FOUND);
    }

    public void checkMaxItemsPublished(int itemsPublished) {
        if (itemsPublished >= SvConfig.MAXIMUM_ITEMS_PUBLISHED) throw new BadReq(MarketConst.MAX_ITEMS_PUBLISHED);
    }

    public void checkMaxGoldPrice(long goldPrice) {
        if (goldPrice >= SvConfig.MAXIMUM_GOLD_PRICE) throw new BadReq(MarketConst.MAX_GOLD_PRICE);
    }

    public void checkMaxDiamondPrice(int diamondPrice) {
        if (diamondPrice >= SvConfig.MAXIMUM_DIAMOND_PRICE) throw new BadReq(MarketConst.MAX_DIAMOND_PRICE);
    }

    public void checkItemAlreadyInMarket(boolean inMarket) {
        if (inMarket) throw new BadReq(MarketConst.ITEM_ALREADY_IN_MARKET);
    }

    public void checkItemOwned(long itemUserId, long userId) {
        if (itemUserId != userId) throw new BadReq(MarketConst.ITEM_NOT_OWNED);
    }

    public void checkEnoughGold(long userGold, long goldPrice) {
        if (userGold < goldPrice) throw new BadReq(MarketConst.NOT_ENOUGH_GOLD);
    }

    public void checkEnoughDiamond(long userDiamond, int diamondPrice) {
        if (userDiamond < diamondPrice) throw new BadReq(MarketConst.NOT_ENOUGH_DIAMOND);
    }

    /* MAIL */
    public void receiverNotEmpty(String receiver) throws Conflict {
        if (receiver.isEmpty()) throw new Conflict(MailConst.RECEIVER_EMPTY);
    }

    public void subjectNotEmpty(String subject) throws Conflict {
        if (subject.isEmpty()) throw new Conflict(MailConst.SUBJECT_EMPTY);
    }

    public void messageNotEmpty(String message) throws Conflict {
        if (message.isEmpty()) throw new Conflict(MailConst.MESSAGE_EMPTY);
    }



    public void userNotEqual(String username, String receiver) throws Conflict {
        if (username.equals(receiver)) throw new Conflict(MailConst.USER_NOT_EQUAL);
    }

    public void userHaveMails(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(MailConst.USER_NOT_HAVE_MAILS);
    }

    /* ITEM */
    public void itemFound(boolean itemExist) throws NotFound {
        if (!itemExist) throw new NotFound(ItemConst.ITEM_NOT_FOUND);
    }

    public void itemExists(boolean itemExist) throws NotFound {
        if (itemExist) throw new NotFound(ItemConst.ALREADY_EXISTS);
    }

    public void checkDtoToSaveItem(ItemDTO newItem) throws BadReq {
        if (newItem.getName().isEmpty()) throw new BadReq(ItemConst.NAME_NOT_EMPTY);
        if (newItem.getType().isEmpty()) throw new BadReq(ItemConst.TYPE_NOT_EMPTY);
        if (newItem.getLvlMin() < 0) throw new BadReq(ItemConst.LVL_NOT_LESS_0);
        if (newItem.getPrice() < 0) throw new BadReq(ItemConst.PRICE_NOT_LESS_0);
        if (newItem.getStrength() < 0 || newItem.getDexterity() < 0 || newItem.getIntelligence() < 0 || newItem.getVitality() < 0 || newItem.getLuck() < 0)
            throw new BadReq(ItemConst.STATS_NOT_LESS_0);
        if (!ItemConst.ENABLED_ITEM_TYPE_SAVE.contains(newItem.getType()))
            throw new BadReq(ItemConst.CANT_EQUIP_MORE_ITEM + newItem.getType());
    }

    public void checkGoldEnough(long goldUser, int itemPrice) throws Conflict {
        if (goldUser < itemPrice) throw new Conflict(ItemConst.NOT_ENOUGH_GOLD);
    }

    public void checkInventoryFull(int inventorySize) throws Conflict {
        if (inventorySize >= SvConfig.MAX_ITEMS_INVENTORY) throw new Conflict(ItemConst.INVENTORY_FULL);
    }

    public void inventoryContainsItem(Set<Item> userInventory, Item item) throws Conflict {
        if (!userInventory.contains(item)) throw new Conflict(ItemConst.ITEM_NOT_INVENTORY);
    }

    public void checkItemClassEquip(String userClass, String itemClass) throws Conflict {
        if (!userClass.equals(itemClass) && !"none".equals(itemClass)) throw new Conflict(ItemConst.ITEM_NOT_FOR_CLASS);
    }

    public void checkItemLevelEquip(int userLevel, int itemLevel) throws Conflict {
        if (userLevel < itemLevel) throw new Conflict(ItemConst.ITEM_LEVEL_REQ + itemLevel);
    }

    public void checkItemEquipIfPermitted(String itemType) throws Conflict {
        if (!ItemConst.ENABLED_EQUIP.contains(itemType)) throw new Conflict(ItemConst.ITEM_EQUIP_NOT_PERMITTED);
    }

    public void checkEquipOnlyOneType(Set<Item> equipment, String itemType) throws Conflict {
        for (Item item : equipment) {
            if (item.getType().equals(itemType)) throw new Conflict(ItemConst.CANT_EQUIP_MORE_ITEM);
        }
    }

    public void checkItemInEquipment(Set<Item> equipment, Item item) {
        if (!equipment.contains(item)) throw new NotFound(ItemConst.ITEM_NOT_EQUIPMENT);
    }

    public void checkItemFromTrader(boolean itemFromTrader) throws Conflict {
        if (!itemFromTrader) throw new Conflict(ItemConst.ITEM_NOT_FROM_TRADER);
    }

    public void checkItemNotInPossession(boolean itemNotPossession) throws Conflict {
        if (itemNotPossession) throw new Conflict(ItemConst.ITEM_NOT_IN_POSSESSION);
    }

    public void checkItemUpgradeAmount(int upgradeAmount, int requirementAmount) throws BadReq {
        if (upgradeAmount < requirementAmount) throw new BadReq(ItemConst.NOT_ENOUGH_ITEMS_TO_UPGRADE);
    }

    public void checkItemLevelMax(int itemLevel) throws BadReq {
        if (itemLevel >= SvConfig.MAX_ITEM_LEVEL) throw new BadReq(ItemConst.ITEM_ALREADY_MAX_LVL);
    }

    public void checkItemUpgradeInPossession(boolean userHaveItem) throws Conflict {
        if (!userHaveItem) throw new Conflict(ItemConst.USER_NOT_HAVE_ITEM);
    }

    public void checkUserHaveAmountGem(int userGems, int gemsNeeded) throws Conflict {
        if (userGems < gemsNeeded) throw new Conflict(String.format(ItemConst.NOT_ENOUGH_GEMS, gemsNeeded));
    }

    public void checkItemIsUpgradeable(boolean isUpgradeable) throws Conflict {
        if (!isUpgradeable) throw new Conflict(ItemConst.ITEM_NOT_UPGRADEABLE);
    }

    /* GUILD */

    public void userFoundByObject(User user) throws NotFound {
        if (user == null) throw new NotFound(Const.USER.NOT_FOUND.getMsg());
    }

    public void guildFound(Guild guild) throws NotFound {
        if (guild == null) throw new NotFound(Const.GUILD.NOT_FOUND.getMsg());
    }

    public void guildFoundByName(boolean guildExist) throws NotFound {
        if (!guildExist) throw new NotFound(Const.GUILD.NOT_FOUND.getMsg());
    }

    public void checkUserInGuild(String guildName) throws Conflict {
        if (!Objects.equals(guildName, "")) throw new Conflict(Const.GUILD.ALREADY_IN_GUILD.getMsg());
    }

    public void checkUserNotInGuild(String guildName) throws Conflict {
        if (Objects.equals(guildName, "")) throw new Conflict(Const.GUILD.NOT_INSIDE.getMsg());
    }

    public void guildNameExist(boolean guildExist) throws Conflict {
        if (guildExist) throw new Conflict(Const.GUILD.NAME_ALREADY_EXIST.getMsg());
    }

    public void guildTagExist(boolean tagExist) throws Conflict {
        if (tagExist) throw new Conflict(Const.GUILD.TAG_ALREADY_EXIST.getMsg());
    }

    public void guildDtoReqToSaveGuild(GuildDTO guildDTO) throws Conflict {
        if (guildDTO.getName() == null) throw new Conflict(Const.GUILD.NAME_REQUIRED.getMsg());
        if (guildDTO.getDescription() == null) throw new Conflict(Const.GUILD.DESCRIPTION_REQUIRED.getMsg());
        if (guildDTO.getTag() == null) throw new Conflict(Const.GUILD.TAG_REQUIRED.getMsg());
    }

    public void guildReqToCreate(int level, long gold, int diamond) throws Conflict {
        if (level < SvConfig.LEVEL_TO_CREATE_GUILD) throw new Conflict(Const.GUILD.LVL_REQ.getMsg());
        if (gold < SvConfig.GOLD_TO_CREATE_GUILD) throw new Conflict(Const.GUILD.GOLD_REQ.getMsg());
        if (diamond < SvConfig.DIAMOND_TO_CREATE_GUILD) throw new Conflict(Const.GUILD.DIAMOND_REQ.getMsg());
    }

    public void reqLvlToReqGuild(int level) throws Conflict {
        if (level < SvConfig.LEVEL_TO_JOIN_GUILD) throw new Conflict(Const.GUILD.LVL_REQ.getMsg());
    }

    public void checkGuildIsFull(int membersSize, int maxMembers) throws Conflict {
        if (membersSize >= maxMembers) throw new Conflict(Const.GUILD.FULL.getMsg());
    }

    public void checkGuildLeaderOrSubLeader(boolean isLeaderOrSubLeader) throws Conflict {
        if (!isLeaderOrSubLeader) throw new Conflict(Const.GUILD.NOT_LEADER_OR_SUBLEADER.getMsg());
    }

    public void checkOtherUserInGuild(String guildName) throws Conflict {
        if (!Objects.equals(guildName, "")) throw new Conflict(Const.GUILD.USER_IN_GUILD.getMsg());
    }

    public void checkUserInReqGuild(boolean userInRequest) throws Conflict {
        if (!userInRequest) throw new Conflict(Const.GUILD.USER_NOT_REQUEST.getMsg());
    }

    public void checkUserIsLeader(String username, String leader) throws Conflict {
        if (Objects.equals(username, leader)) throw new Conflict(Const.GUILD.USER_ALREADY_LEADER.getMsg());
    }

    public void checkUserRemoveLeader(String nameRemove, String leader) throws Conflict {
        if (Objects.equals(nameRemove, leader)) throw new Conflict(Const.GUILD.CANNOT_REMOVE_LEADER.getMsg());
    }

    public void checkRemoveLeaderNotSubLeader(String nameRemove, String leader, String subLeader,
                                              int memberSize) throws Conflict {
        if (nameRemove.equals(leader) && subLeader.equals("") && memberSize > 1)
            throw new Conflict(Const.GUILD.NO_SUBLEADER.getMsg());
    }

    public void checkUserDiamondsForDonate(int userDiamonds, int diamonds) throws Conflict {
        if (userDiamonds < diamonds) throw new Conflict(Const.GUILD.YOU_NOT_ENOUGH_DIAMONDS.getMsg());
    }

    public void checkGuildLvlMax(int guildLevel) throws Conflict {
        if (guildLevel >= SvConfig.GUILD_LVL_MAX) throw new Conflict(Const.GUILD.LVL_MAX.getMsg());
    }

    public void checkGuildDiamondsForUpgrade(int guildDiamonds, int guildLevel) throws Conflict {
        if (guildDiamonds < GuildUpgrade.getDiamondCost(guildLevel))
            throw new Conflict(Const.GUILD.NOT_ENOUGH_DIAMONDS.getMsg());
    }

}
