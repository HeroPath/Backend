package com.gianca1994.aowebbackend.resources.user;

import java.util.*;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.pve.PveModel;
import com.gianca1994.aowebbackend.combatSystem.pve.PveSystem;
import com.gianca1994.aowebbackend.combatSystem.pvp.PvpSystem;
import com.gianca1994.aowebbackend.combatSystem.pvp.PvpModel;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.guild.GuildRepository;
import com.gianca1994.aowebbackend.resources.guild.UserGuildDTO;
import com.gianca1994.aowebbackend.resources.item.ItemRepository;
import com.gianca1994.aowebbackend.resources.npc.Npc;
import com.gianca1994.aowebbackend.resources.npc.NpcRepository;
import com.gianca1994.aowebbackend.resources.quest.QuestRepository;
import com.gianca1994.aowebbackend.resources.role.RoleRepository;
import com.gianca1994.aowebbackend.resources.title.TitleRepository;
import com.gianca1994.aowebbackend.resources.user.dto.*;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Author: Gianca1994
 * Explanation: NpcService
 */

@Service
public class UserService {

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

    @Autowired
    GuildRepository guildRepository;

    UserServiceValidator validator = new UserServiceValidator();

    public User getProfile(String username) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the profile of the user.
         * @param String username
         * @return User
         */
        return userRepository.findByUsername(username);
    }

    public UserGuildDTO getUserForGuild(String username) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the profile of the user.
         * @param String username
         * @return UserGuildDTO
         */
        User user = userRepository.findByUsername(username);
        validator.getUserForGuild(user);

        UserGuildDTO userGuildDTO = new UserGuildDTO();
        userGuildDTO.setUsername(user.getUsername());
        userGuildDTO.setLevel(user.getLevel());
        userGuildDTO.setTitlePoints(user.getTitlePoints());
        userGuildDTO.setClassName(user.getAClass().getName());
        userGuildDTO.setTitleName(user.getTitle().getName());
        return userGuildDTO;
    }

    public ArrayList<User> getRankingAll() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the ranking of all users.
         * @param none
         * @return ArrayList<User>
         */
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        users.sort(Comparator.comparing(User::getLevel).thenComparing(User::getTitlePoints).thenComparing(User::getExperience).reversed());
        return users;
    }

    public User setFreeSkillPoint(String username, FreeSkillPointDTO freeSkillPointDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of adding skill points to the user.
         * @param String username
         * @param FreeSkillPointDTO freeSkillPointDTO
         * @return User
         */
        User user = userRepository.findByUsername(username);
        validator.setFreeSkillPoint(user, freeSkillPointDTO);

        user.addFreeSkillPoints(freeSkillPointDTO);
        userRepository.save(user);
        return user;
    }

    public ArrayList<ObjectNode> userVsUserCombatSystem(String username,
                                                        UserAttackUserDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat system between users.
         * @param String username
         * @param UserAttackUserDTO nameRequestDTO
         * @return ArrayList<ObjectNode>
         */
        User attacker = userRepository.findByUsername(username);
        User defender = userRepository.findByUsername(nameRequestDTO.getName());

        validator.userVsUserCombatSystem(attacker, defender);

        PvpModel pvpUserVsUserModel = PvpSystem.PvpUserVsUser(
                attacker, defender, titleRepository, guildRepository);

        userRepository.save(pvpUserVsUserModel.getUser());
        if (defender.getUsername().equals("test")) pvpUserVsUserModel.getDefender().setHp(defender.getMaxHp());
        userRepository.save(pvpUserVsUserModel.getDefender());

        return pvpUserVsUserModel.getHistoryCombat();
    }

    public ArrayList<ObjectNode> userVsNpcCombatSystem(String username,
                                                       UserAttackNpcDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat system between users and npcs.
         * @param String username
         * @param UserAttackNpcDTO nameRequestDTO
         * @return ArrayList<ObjectNode>
         */
        User user = userRepository.findByUsername(username);
        Npc npc = npcRepository.findByName(nameRequestDTO.getName().toLowerCase());

        validator.userVsNpcCombatSystem(user, npc);

        PveModel pveSystem = PveSystem.PveUserVsNpc(user, npc, titleRepository);

        userRepository.save(pveSystem.getUser());
        return pveSystem.getHistoryCombat();
    }
}
