package com.gianca1994.aowebbackend.resources.user;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.pve.PveModel;
import com.gianca1994.aowebbackend.combatSystem.pve.PveSystem;
import com.gianca1994.aowebbackend.combatSystem.pvp.PvpSystem;
import com.gianca1994.aowebbackend.combatSystem.pvp.PvpModel;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.guild.GuildRepository;
import com.gianca1994.aowebbackend.resources.guild.UserGuildDTO;
import com.gianca1994.aowebbackend.resources.item.ItemRepository;
import com.gianca1994.aowebbackend.resources.npc.Npc;
import com.gianca1994.aowebbackend.resources.npc.NpcRepository;
import com.gianca1994.aowebbackend.resources.quest.QuestRepository;
import com.gianca1994.aowebbackend.resources.role.RoleRepository;
import com.gianca1994.aowebbackend.resources.title.TitleRepository;
import com.gianca1994.aowebbackend.resources.user.dto.queyModel.UserAttributes;
import com.gianca1994.aowebbackend.resources.user.dto.request.NameRequestDTO;
import com.gianca1994.aowebbackend.resources.user.dto.response.UserRankingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;


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
        userGuildDTO.setClassName(user.getAClass());
        userGuildDTO.setTitleName(user.getTitle().getName());
        return userGuildDTO;
    }

    public List<UserRankingDTO> getRankingAll(int page) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the ranking of all users.
         * @param none
         * @return ArrayList<UserData>
         */

        Page<User> usersPage = userRepository.findAllByOrderByLevelDescTitlePointsDescExperienceDesc(PageRequest.of(page, 10));
        List<User> users = usersPage.getContent();
        AtomicInteger pos = new AtomicInteger(1);

        return users.stream().map(user -> new UserRankingDTO(
                pos.getAndIncrement(),
                user.getUsername(),
                !Objects.equals(user.getGuildName(), "") ? user.getGuildName() : "---",
                user.getAClass(),
                user.getLevel(),
                user.getTitle().getName(), user.getTitlePoints(),
                user.getStrength(), user.getDexterity(), user.getVitality(), user.getIntelligence(), user.getLuck(),
                user.getPvpWins(), user.getPvpLosses()
        )).collect(Collectors.toList());
    }

    @Transactional
    public UserAttributes setFreeSkillPoint(long userId, String skillName) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of adding skill points to the user.
         * @param String username
         * @param FreeSkillPointDTO freeSkillPointDTO
         * @return User
         */
        UserAttributes uAttr = userRepository.findAttributesByUserId(userId);
        uAttr.addStat(skillName);
        uAttr.updateStats();
        userRepository.updateUserStats(
                uAttr.getStrength(), uAttr.getDexterity(), uAttr.getVitality(),
                uAttr.getIntelligence(), uAttr.getLuck(),
                uAttr.getFreeSkillPoints(),
                uAttr.getMaxDmg(), uAttr.getMinDmg(),
                uAttr.getMaxHp(), uAttr.getHp(),
                uAttr.getDefense(), uAttr.getEvasion(), uAttr.getCriticalChance(),
                userId
        );
        return uAttr;
    }

    public ArrayList<ObjectNode> userVsUserCombatSystem(String username,
                                                        NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat system between users.
         * @param String username
         * @param NameRequestDTO nameRequestDTO
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
                                                       NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat system between users and npcs.
         * @param String username
         * @param NameRequestDTO nameRequestDTO
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
