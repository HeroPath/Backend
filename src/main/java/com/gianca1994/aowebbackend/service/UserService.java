package com.gianca1994.aowebbackend.service;

import java.util.*;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.*;
import com.gianca1994.aowebbackend.combatSystem.pve.PveModel;
import com.gianca1994.aowebbackend.combatSystem.pve.PveSystem;
import com.gianca1994.aowebbackend.combatSystem.pvp.PvpSystem;
import com.gianca1994.aowebbackend.combatSystem.pvp.PvpModel;
import com.gianca1994.aowebbackend.dto.*;
import com.gianca1994.aowebbackend.exception.BadRequest;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.jwt.JwtTokenUtil;
import com.gianca1994.aowebbackend.model.Item;
import com.gianca1994.aowebbackend.model.Npc;
import com.gianca1994.aowebbackend.model.Quest;
import com.gianca1994.aowebbackend.model.User;
import com.gianca1994.aowebbackend.repository.*;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @Author: Gianca1994
 * Explanation: NpcService
 */

@Service
public class UserService {

    private final int MAX_ITEMS_INVENTORY = 24;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    TitleRepository titleRepository;
    @Autowired
    NpcRepository npcRepository;
    @Autowired
    QuestRepository questRepository;
    GenericFunctions genericFunctions = new GenericFunctions();

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    private String getTokenUser(String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get the username from the token.
         * @param String token
         * @return String username
         */
        String jwtToken = token.substring(7);
        return jwtTokenUtil.getUsernameFromToken(jwtToken);
    }

    public User getProfile(String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the profile of the user.
         * @param String username
         * @return User
         */
        return userRepository.findByUsername(getTokenUser(token));
    }

    public ArrayList<User> getRankingAll() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the ranking of all users.
         * @param none
         * @return ArrayList<User>
         */
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        users.sort(Comparator.comparing(User::getLevel).thenComparing(User::getPvpWins).reversed());
        return users;
    }

    public User setFreeSkillPoint(String token, FreeSkillPointDTO freeSkillPointDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of adding skill points to the user.
         * @param String username
         * @param FreeSkillPointDTO freeSkillPointDTO
         * @return User
         */
        User user = userRepository.findByUsername(getTokenUser(token));

        if (user == null) throw new NotFound("User not found");
        if (freeSkillPointDTO.getAmount() <= 0) throw new BadRequest("Amount must be greater than 0");
        if (user.getFreeSkillPoints() <= 0) throw new Conflict("You don't have any free skill points");
        if (user.getFreeSkillPoints() < freeSkillPointDTO.getAmount())
            throw new Conflict("You don't have enough free skill points");

        List<String> skillsEnabled = Arrays.asList("strength", "dexterity", "intelligence", "vitality", "luck");
        if (!skillsEnabled.contains(freeSkillPointDTO.getSkillPointName().toLowerCase()))
            throw new Conflict("Skill point name must be one of the following: " + skillsEnabled);

        user.addFreeSkillPoints(freeSkillPointDTO);

        userRepository.save(user);
        return user;
    }

    ///////////////////// OPEN: ITEMS SYSTEMS ///////////////////////////
    public User equipItem(String token, EquipUnequipItemDTO equipUnequipItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of equipping or unequipping an item to the user.
         * @param String username
         * @param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User
         */
        User user = userRepository.findByUsername(getTokenUser(token));
        if (user == null) throw new NotFound("User not found");

        Item item = itemRepository.findById(equipUnequipItemDTO.getId()).orElseThrow(() -> new NotFound("Item not found"));
        if (!user.getInventory().getItems().contains(item)) throw new NotFound("Item not found in inventory");
        if (!Objects.equals(user.getAClass().getName(), item.getClassRequired()) && !Objects.equals(item.getClassRequired(), "none"))
            throw new Conflict("The item does not correspond to your class");

        List<String> itemsEnabledToEquip = Arrays.asList("weapon", "shield", "helmet", "armor", "pants", "gloves", "boots", "ship", "wings");
        for (Item itemEquipedOld : user.getEquipment().getItems()) {
            if (!itemsEnabledToEquip.contains(itemEquipedOld.getType()))
                throw new Conflict("You can't equip more than one " + itemEquipedOld.getType() + " item");
            if (Objects.equals(itemEquipedOld.getType(), item.getType()))
                throw new Conflict("You can't equip two items of the same type");
        }

        if (user.getLevel() < item.getLvlMin())
            throw new Conflict("You can't equip an item that requires level " + item.getLvlMin());

        if (item.getAmount() > 1) item.setAmount(item.getAmount() - 1);
        else user.getInventory().getItems().remove(item);

        user.getEquipment().getItems().add(item);

        user.swapItemToEquipmentOrInventory(item, true);
        userRepository.save(user);
        return user;
    }

    public User unequipItem(String token, EquipUnequipItemDTO equipUnequipItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of equipping or unequipping an item to the user.
         * @param String username
         * @param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User
         */
        User user = userRepository.findByUsername(getTokenUser(token));
        if (user == null) throw new NotFound("User not found");

        Item item = itemRepository.findById(equipUnequipItemDTO.getId()).orElseThrow(() -> new NotFound("Item not found"));
        if (!user.getEquipment().getItems().contains(item)) throw new NotFound("Item not found in equipment");
        if (user.getInventory().getItems().size() >= MAX_ITEMS_INVENTORY) throw new Conflict("Inventory is full");

        user.getEquipment().getItems().remove(item);
        if (user.getInventory().getItems().contains(item)) item.setAmount(item.getAmount() + 1);

        user.getInventory().getItems().add(item);

        user.swapItemToEquipmentOrInventory(item, false);
        if (user.getHp() > user.getMaxHp()) user.setHp(user.getMaxHp());
        userRepository.save(user);
        return user;
    }

    public void buyItem(String token, BuyItemDTO buyItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of buying an item.
         * @param String token
         * @param String name
         * @return none
         */
        User user = userRepository.findByUsername(getTokenUser(token));
        if (user == null) throw new NotFound("User not found");

        Item itemBuy = itemRepository.findByName(buyItemDTO.getName().toLowerCase());
        if (Objects.isNull(itemBuy)) throw new NotFound("Item not found");

        if (user.getGold() < itemBuy.getPrice()) throw new Conflict("You don't have enough gold");
        if (user.getInventory().getItems().size() >= MAX_ITEMS_INVENTORY && !user.getInventory().getItems().contains(itemBuy))
            throw new Conflict("Inventory is full");

        user.setGold(user.getGold() - itemBuy.getPrice());

        if (user.getInventory().getItems().contains(itemBuy)) itemBuy.setAmount(itemBuy.getAmount() + 1);
        else user.getInventory().getItems().add(itemBuy);
        userRepository.save(user);
    }

    public void sellItem(String token, SellItemDTO sellItemDTO) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of selling an item.
         * @param String token
         * @param SellItemDTO sellItemDTO
         * @return none
         */
        User user = userRepository.findByUsername(getTokenUser(token));
        if (user == null) throw new NotFound("User not found");

        Item itemBuy = itemRepository.findByName(sellItemDTO.getName().toLowerCase());
        if (Objects.isNull(itemBuy)) throw new NotFound("Item not found");

        if (!user.getInventory().getItems().contains(itemBuy)) throw new NotFound("Item not found in inventory");
        user.setGold(user.getGold() + (itemBuy.getPrice() / 2));

        if (itemBuy.getAmount() > 1) itemBuy.setAmount(itemBuy.getAmount() - 1);
        else user.getInventory().getItems().remove(itemBuy);
        userRepository.save(user);
    }
    ///////////////////// CLOSE: ITEMS SYSTEMS ///////////////////////////

    ///////////////////// OPEN: QUESTS SYSTEMS ///////////////////////////
    public void acceptQuest(String token, AcceptedQuestDTO acceptedQuestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of accepting a quest.
         * @param String token
         * @param AcceptedQuestDTO acceptedQuestDTO
         * @return none
         */
        Quest quest = questRepository.findByName(acceptedQuestDTO.getName());
        if (Objects.isNull(quest)) throw new NotFound("Quest not found");

        User user = userRepository.findByUsername(getTokenUser(token));

        if (user == null) throw new NotFound("User not found");
        if (user.getQuests().contains(quest)) throw new Conflict("You already accepted this quest");
        if (user.getQuests().size() >= 3) throw new Conflict("You can't accept more quests");

        quest.setNpcKillAmount(0);
        quest.setUserKillAmount(0);

        user.getQuests().add(quest);
        userRepository.save(user);
    }

    public void cancelQuest(String token, AcceptedQuestDTO acceptedQuestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of canceling a quest.
         * @param String token
         * @param AcceptedQuestDTO acceptedQuestDTO
         * @return none
         */
        Quest quest = questRepository.findByName(acceptedQuestDTO.getName());
        if (Objects.isNull(quest)) throw new NotFound("Quest not found");

        User user = userRepository.findByUsername(getTokenUser(token));
        if (user == null) throw new NotFound("User not found");
        if (!user.getQuests().contains(quest)) throw new Conflict("You didn't accept this quest");

        user.getQuests().remove(quest);
        userRepository.save(user);
    }
    //////////////////// CLOSE: QUESTS SYSTEMS ///////////////////////////

    ////////////////// OPEN: PVP AND PVE SYSTEMS /////////////////////////
    public ArrayList<ObjectNode> userVsUserCombatSystem(String token, UserAttackUserDTO userAttackUserDTO) throws
            Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat system between two users.
         * @param String usernameAttacker
         * @param String usernameDefender
         * @return ArrayList<ObjectNode>
         */
        User attacker = userRepository.findByUsername(getTokenUser(token));
        if (attacker == null) throw new NotFound("User not found");
        if (genericFunctions.checkLifeStartCombat(attacker))
            throw new BadRequest("Impossible to attack with less than 15% of life");
        if (attacker.getLevel() < 5) throw new Conflict("You can't attack with a level lower than 5");

        User defender = userRepository.findByUsername(userAttackUserDTO.getName());
        if (attacker == defender) throw new Conflict("You can't fight yourself");
        if (defender == null) throw new NotFound("Enemy not found");
        if (defender.getLevel() < 5) throw new Conflict("You can't attack with a level lower than 5");
        if (defender.getRole().getRoleName().equals("ADMIN")) throw new Conflict("You can't attack an admin");
        if (genericFunctions.checkLifeStartCombat(defender))
            throw new BadRequest("Unable to attack, enemy has less than 15% health");

        PvpModel pvpUserVsUserModel = PvpSystem.PvpUserVsUser(attacker, defender, titleRepository);

        userRepository.save(pvpUserVsUserModel.getAttacker());
        if (pvpUserVsUserModel.getDefender().getUsername().equals("test")) {
            pvpUserVsUserModel.getDefender().setHp(pvpUserVsUserModel.getDefender().getMaxHp());
        }
        userRepository.save(pvpUserVsUserModel.getDefender());
        return pvpUserVsUserModel.getHistoryCombat();
    }

    public ArrayList<ObjectNode> userVsNpcCombatSystem(String token, UserAttackNpcDTO userAttackNpcDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat between the user and the npc.
         * @param String usernameAttacker
         * @param long npcId
         * @return ArrayList<ObjectNode>
         */
        User user = userRepository.findByUsername(getTokenUser(token));

        if (user == null) throw new NotFound("User not found");
        if (genericFunctions.checkLifeStartCombat(user))
            throw new BadRequest("Impossible to attack with less than 25% of life");

        Npc npc = npcRepository.findByName(userAttackNpcDTO.getName().toLowerCase());
        if (npc == null) throw new NotFound("Npc not found");

        if (npc.getLevel() > user.getLevel() + 5)
            throw new Conflict("You can't attack an npc with level higher than 5 levels higher than you");

        boolean enabledSea = false, enabledHell = false;
        for (Item item : user.getEquipment().getItems()) {
            if (item.getType().equals("ship")) enabledSea = true;
            if (item.getType().equals("wings")) enabledHell = true;
        }
        if (npc.getZone().equals("sea") && !enabledSea)
            throw new Conflict("You can't attack an npc in the sea without a ship");
        if (npc.getZone().equals("hell") && !enabledHell)
            throw new Conflict("You can't attack an npc in hell without wings");

        PveModel pveSystem = PveSystem.PveUserVsNpc(user, npc, titleRepository);

        userRepository.save(pveSystem.getUser());
        return pveSystem.getHistoryCombat();
    }
    ////////////////// CLOSE: PVP AND PVE SYSTEMS ////////////////////////
}
