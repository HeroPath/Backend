package com.gianca1994.aowebbackend.service;

import java.io.Serializable;
import java.util.*;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.*;
import com.gianca1994.aowebbackend.combatSystem.pve.PveModel;
import com.gianca1994.aowebbackend.combatSystem.pve.PveSystem;
import com.gianca1994.aowebbackend.combatSystem.pvp.PvpSystem;
import com.gianca1994.aowebbackend.combatSystem.pvp.PvpModel;
import com.gianca1994.aowebbackend.dto.EquipUnequipItemDTO;
import com.gianca1994.aowebbackend.dto.FreeSkillPointDTO;
import com.gianca1994.aowebbackend.dto.UserAttackNpcDTO;
import com.gianca1994.aowebbackend.dto.UserAttackUserDTO;
import com.gianca1994.aowebbackend.exception.BadRequestException;
import com.gianca1994.aowebbackend.exception.ConflictException;
import com.gianca1994.aowebbackend.exception.NotFoundException;
import com.gianca1994.aowebbackend.jwt.JwtTokenUtil;
import com.gianca1994.aowebbackend.model.Item;
import com.gianca1994.aowebbackend.model.Npc;
import com.gianca1994.aowebbackend.model.User;
import com.gianca1994.aowebbackend.repository.ItemRepository;
import com.gianca1994.aowebbackend.repository.NpcRepository;
import com.gianca1994.aowebbackend.repository.RoleRepository;
import com.gianca1994.aowebbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;


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
    NpcRepository npcRepository;

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

    public User setFreeSkillPoint(String token, FreeSkillPointDTO freeSkillPointDTO) throws ConflictException {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of adding skill points to the user.
         * @param String username
         * @param FreeSkillPointDTO freeSkillPointDTO
         * @return User
         */
        User user = userRepository.findByUsername(getTokenUser(token));

        if (user == null) throw new NotFoundException("User not found");
        if (freeSkillPointDTO.getAmount() <= 0) throw new BadRequestException("Amount must be greater than 0");
        if (user.getFreeSkillPoints() <= 0) throw new ConflictException("You don't have any free skill points");
        if (user.getFreeSkillPoints() < freeSkillPointDTO.getAmount())
            throw new ConflictException("You don't have enough free skill points");

        user.addFreeSkillPoints(freeSkillPointDTO);

        userRepository.save(user);
        return user;
    }

    public User equipItem(String token, EquipUnequipItemDTO equipUnequipItemDTO) throws ConflictException {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of equipping or unequipping an item to the user.
         * @param String username
         * @param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User
         */
        User user = userRepository.findByUsername(getTokenUser(token));
        if (user == null) throw new NotFoundException("User not found");

        Item item = itemRepository.findById(equipUnequipItemDTO.getId()).orElseThrow(() -> new NotFoundException("Item not found"));
        if (!user.getInventory().getItems().contains(item)) throw new NotFoundException("Item not found in inventory");
        if (!Objects.equals(user.getAClass().getName(), item.getClassRequired()) && !Objects.equals(item.getClassRequired(), ""))
            throw new ConflictException("The item does not correspond to your class");

        List<String> itemsEnabledToEquip = Arrays.asList("weapon", "shield", "helmet", "armor", "pants", "gloves", "boots", "ship", "wings");
        for (Item itemEquipedOld : user.getEquipment().getItems()) {
            if (!itemsEnabledToEquip.contains(itemEquipedOld.getType()))
                throw new ConflictException("You can't equip more than one " + itemEquipedOld.getType() + " item");
            if (Objects.equals(itemEquipedOld.getType(), item.getType()))
                throw new ConflictException("You can't equip two items of the same type");
        }

        if (user.getLevel() < item.getLvlMin())
            throw new ConflictException("You can't equip an item that requires level " + item.getLvlMin());

        if (item.getAmount() > 1) item.setAmount(item.getAmount() - 1);
        else user.getInventory().getItems().remove(item);

        user.getEquipment().getItems().add(item);

        user.swapItemToEquipmentOrInventory(item, true);
        userRepository.save(user);
        return user;
    }

    public User unequipItem(String token, EquipUnequipItemDTO equipUnequipItemDTO) throws ConflictException {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of equipping or unequipping an item to the user.
         * @param String username
         * @param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User
         */
        User user = userRepository.findByUsername(getTokenUser(token));
        if (user == null) throw new NotFoundException("User not found");

        Item item = itemRepository.findById(equipUnequipItemDTO.getId()).orElseThrow(() -> new NotFoundException("Item not found"));
        if (!user.getEquipment().getItems().contains(item))
            throw new NotFoundException("Item not found in equipment");
        if (user.getInventory().getItems().size() >= MAX_ITEMS_INVENTORY)
            throw new ConflictException("Inventory is full");

        user.getEquipment().getItems().remove(item);
        if (user.getInventory().getItems().contains(item))
            item.setAmount(item.getAmount() + 1);

        user.getInventory().getItems().add(item);

        user.swapItemToEquipmentOrInventory(item, false);
        userRepository.save(user);
        return user;
    }

    public void buyItem(String token, String name) throws ConflictException {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of buying an item.
         * @param String token
         * @param String name
         * @return none
         */
        User user = userRepository.findByUsername(getTokenUser(token));
        if (user == null) throw new NotFoundException("User not found");
        Item itemBuy = itemRepository.findByName(name);
        if (Objects.isNull(itemBuy)) throw new BadRequestException("Item not found");

        if (user.getGold() < itemBuy.getPrice()) throw new ConflictException("You don't have enough gold");
        if (user.getInventory().getItems().size() >= MAX_ITEMS_INVENTORY) throw new ConflictException("Inventory is full");

        user.setGold(user.getGold() - itemBuy.getPrice());

        if (user.getInventory().getItems().contains(itemBuy))
            itemBuy.setAmount(itemBuy.getAmount() + 1);
        else
            user.getInventory().getItems().add(itemBuy);

        userRepository.save(user);
    }

    //////////////////////////////////////////////////////////////////////
    ////////////////// INFO: PVP AND PVE SYSTEMS /////////////////////////
    //////////////////////////////////////////////////////////////////////
    public ArrayList<ObjectNode> userVsUserCombatSystem(String token, UserAttackUserDTO userAttackUserDTO) throws
            ConflictException {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat system between two users.
         * @param String usernameAttacker
         * @param String usernameDefender
         * @return ArrayList<ObjectNode>
         */
        User attacker = userRepository.findByUsername(getTokenUser(token));
        if (attacker == null) throw new NotFoundException("User not found");
        if (genericFunctions.checkLifeStartCombat(attacker))
            throw new BadRequestException("Impossible to attack with less than 25% of life");

        User defender = userRepository.findByUsername(userAttackUserDTO.getName());
        if (defender == null) throw new NotFoundException("Enemy not found");
        if (defender.getRole().getRoleName().equals("ADMIN"))
            throw new ConflictException("You can't attack an admin");
        if (genericFunctions.checkLifeStartCombat(defender))
            throw new BadRequestException("Impossible to attack an enemy with less than 25% of its health");

        if (attacker == defender) throw new ConflictException("You can't fight yourself");

        PvpModel pvpUserVsUserModel = PvpSystem.PvpUserVsUser(attacker, defender);

        userRepository.save(pvpUserVsUserModel.getAttacker());
        userRepository.save(pvpUserVsUserModel.getDefender());
        return pvpUserVsUserModel.getHistoryCombat();
    }


    public ArrayList<ObjectNode> userVsNpcCombatSystem(String token, UserAttackNpcDTO userAttackNpcDTO) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat between the user and the npc.
         * @param String usernameAttacker
         * @param long npcId
         * @return ArrayList<ObjectNode>
         */
        User user = userRepository.findByUsername(getTokenUser(token));

        if (user == null) throw new NotFoundException("User not found");
        if (genericFunctions.checkLifeStartCombat(user))
            throw new BadRequestException("Impossible to attack with less than 25% of life");

        Npc npc = npcRepository.findByName(userAttackNpcDTO.getName().toLowerCase());
        if (npc == null) throw new NotFoundException("Npc not found");

        PveModel pveSystem = PveSystem.PveUserVsNpc(user, npc);

        userRepository.save(pveSystem.getUser());
        return pveSystem.getHistoryCombat();
    }
}
