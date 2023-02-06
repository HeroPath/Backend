package com.gianca1994.aowebbackend.resources.user;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.CombatModel;
import com.gianca1994.aowebbackend.combatSystem.pve.PveSystem;
import com.gianca1994.aowebbackend.combatSystem.pvp.PvpSystem;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.guild.GuildRepository;
import com.gianca1994.aowebbackend.resources.item.ItemRepository;
import com.gianca1994.aowebbackend.resources.npc.Npc;
import com.gianca1994.aowebbackend.resources.npc.NpcRepository;
import com.gianca1994.aowebbackend.resources.quest.QuestRepository;
import com.gianca1994.aowebbackend.resources.user.dto.queyModel.UserAttributes;
import com.gianca1994.aowebbackend.resources.user.dto.request.NameRequestDTO;
import com.gianca1994.aowebbackend.resources.user.dto.response.RankingResponseDTO;
import com.gianca1994.aowebbackend.resources.user.dto.response.UserGuildDTO;
import com.gianca1994.aowebbackend.resources.user.dto.response.UserRankingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;


/**
 * @Author: Gianca1994
 * Explanation: UserService
 */

@Service
public class UserService {

    UserServiceValidator validator = new UserServiceValidator();

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    NpcRepository npcRepository;

    @Autowired
    QuestRepository questRepository;

    @Autowired
    GuildRepository guildRepository;

    public User getProfile(String username) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the profile of the user.
         * @param String username
         * @return User
         */
        return userRepository.findByUsername(username);
    }

    public UserGuildDTO getUserForGuild(long userId) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the profile of the user.
         * @param long userId
         * @return UserGuildDTO
         */
        UserGuildDTO userGuildDTO = userRepository.getUserForGuild(userId);
        validator.getUserForGuild(userGuildDTO);
        return userGuildDTO;
    }

    public RankingResponseDTO getRankingAll(int page) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the ranking of all users.
         * @param int page
         * @return RankingResponseDTO
         */
        int userPerPage = 10;
        int totalPages = (int) Math.ceil((double) userRepository.count() / userPerPage);
        Page<User> usersPage = userRepository.findAllByOrderByLevelDescTitlePointsDescExperienceDesc(PageRequest.of(page, userPerPage));
        List<User> users = usersPage.getContent();
        AtomicInteger pos = new AtomicInteger((page * userPerPage) + 1);

        List<UserRankingDTO> ranking = users.stream().map(user -> new UserRankingDTO(
                pos.getAndIncrement(),
                user.getUsername(),
                !Objects.equals(user.getGuildName(), "") ? user.getGuildName() : "---",
                user.getAClass(),
                user.getLevel(),
                user.getTitleName(), user.getTitlePoints(),
                user.getStrength(), user.getDexterity(), user.getVitality(), user.getIntelligence(), user.getLuck(),
                user.getPvpWins(), user.getPvpLosses()
        )).collect(Collectors.toList());

        return new RankingResponseDTO(ranking, totalPages);
    }


    @Transactional
    public UserAttributes setFreeSkillPoint(long userId, String skillName) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of setting the free skill point of the user.
         * @param long userId
         * @param String skillName
         * @return UserAttributes
         */
        UserAttributes uAttr = userRepository.findAttributesByUserId(userId);
        validator.setFreeSkillPoint(uAttr, skillName);

        uAttr.addStat(skillName);
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
         * Explanation: This function is in charge of the combat between two users.
         * @param String username
         * @param NameRequestDTO nameRequestDTO
         * @return ArrayList<ObjectNode>
         */
        User attacker = userRepository.findByUsername(username);
        User defender = userRepository.findByUsername(nameRequestDTO.getName());
        validator.userVsUserCombatSystem(attacker, defender);

        CombatModel pvpSystem = PvpSystem.PvpUserVsUser(attacker, defender, guildRepository);
        userRepository.save(pvpSystem.getAttacker());

        if (defender.getUsername().equals("test")) pvpSystem.getDefender().setHp(defender.getMaxHp());
        userRepository.save(pvpSystem.getDefender());
        return pvpSystem.getHistoryCombat();
    }

    public ArrayList<ObjectNode> userVsNpcCombatSystem(String username,
                                                       NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat between a user and a npc.
         * @param String username
         * @param NameRequestDTO nameRequestDTO
         * @return ArrayList<ObjectNode>
         */
        User user = userRepository.findByUsername(username);
        Npc npc = npcRepository.findByName(nameRequestDTO.getName().toLowerCase());
        validator.userVsNpcCombatSystem(user, npc);

        CombatModel pveSystem = PveSystem.PveUserVsNpc(user, npc);
        userRepository.save(pveSystem.getAttacker());
        return pveSystem.getHistoryCombat();
    }
}
