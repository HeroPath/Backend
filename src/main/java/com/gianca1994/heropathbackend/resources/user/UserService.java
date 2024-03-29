package com.gianca1994.heropathbackend.resources.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.heropathbackend.combatSystem.CombatModel;
import com.gianca1994.heropathbackend.combatSystem.pve.PveSystem;
import com.gianca1994.heropathbackend.combatSystem.pvp.PvpSystem;
import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.resources.guild.GuildRepository;
import com.gianca1994.heropathbackend.resources.item.ItemRepository;
import com.gianca1994.heropathbackend.resources.npc.Npc;
import com.gianca1994.heropathbackend.resources.npc.NpcRepository;
import com.gianca1994.heropathbackend.resources.quest.QuestRepository;
import com.gianca1994.heropathbackend.resources.user.dto.queyModel.UserAttributes;
import com.gianca1994.heropathbackend.resources.user.dto.response.RankingResponseDTO;
import com.gianca1994.heropathbackend.resources.user.dto.response.UserGuildDTO;
import com.gianca1994.heropathbackend.resources.user.dto.response.UserRankingDTO;
import com.gianca1994.heropathbackend.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
 * @Author: Gianca1994
 * @Explanation: This class is in charge of the user service.
 */

@Service
public class UserService {

    Validator validate = new Validator();

    @Autowired
    UserRepository userR;

    @Autowired
    ItemRepository itemR;

    @Autowired
    NpcRepository npcR;

    @Autowired
    QuestRepository questR;

    @Autowired
    GuildRepository guildR;

    public User getProfile(String username) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of getting the profile of the user.
         * @param String username
         * @return User
         */
        validate.userExist(userR.existsByUsername(username));
        return userR.findByUsername(username);
    }

    public UserGuildDTO getUserForGuild(long userId) {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of getting the user for the guild.
         * @param long userId
         * @return UserGuildDTO
         */
        UserGuildDTO userGuildDTO = userR.getUserForGuild(userId);
        validate.getUserForGuild(userGuildDTO);
        return userGuildDTO;
    }

    public RankingResponseDTO getRankingAll(int page) {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of getting the ranking of all the users.
         * @param int page
         * @return RankingResponseDTO
         */
        int totalPages = (int) Math.ceil((double) userR.count() / SvConfig.USER_PER_PAGE);
        Page<User> usersPage = userR.findAllByOrderByLevelDescTitlePointsDescExperienceDesc(PageRequest.of(page, SvConfig.USER_PER_PAGE));
        List<User> users = usersPage.getContent();
        AtomicInteger pos = new AtomicInteger((page * SvConfig.USER_PER_PAGE) + 1);

        List<UserRankingDTO> ranking = users.stream().map(user -> new UserRankingDTO(
                pos.getAndIncrement(), user.getUsername(),
                !Objects.equals(user.getGuildName(), "") ? user.getGuildName() : "---",
                user.getAClass(), user.getLevel(), user.getTitleName(), user.getTitlePoints(), user.getPvpWins()
        )).collect(Collectors.toList());

        return new RankingResponseDTO(ranking, totalPages);
    }

    @Transactional
    public UserAttributes setFreeSkillPoint(long userId, String skillName) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of setting the free skill point.
         * @param long userId
         * @param String skillName
         * @return UserAttributes
         */
        validate.userExist(userR.existsById(userId));
        UserAttributes uAttr = userR.findAttributesByUserId(userId);
        validate.setFreeSkillPoint(uAttr, skillName);

        uAttr.addStat(skillName);
        userR.updateUserStats(
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

    public ArrayList<ObjectNode> userVsUserCombatSystem(String username, String nameDefender) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of the user vs user combat system.
         * @param String username
         * @param String nameDefender
         * @return ArrayList<ObjectNode>
         */
        validate.userExist(userR.existsByUsername(username));
        validate.userExist(userR.existsByUsername(nameDefender));

        User attacker = userR.findByUsername(username);
        validate.lifeStartCombat(attacker);
        validate.pvpPtsEnough(attacker);

        User defender = userR.findByUsername(nameDefender);
        validate.defenderNotAdmin(defender);
        validate.lifeStartCombat(defender);
        validate.autoAttack(attacker, defender);
        validate.differenceLevelPVP(attacker.getLevel(), defender.getLevel());
        validate.attackerAndDefenderInSameGuild(attacker, defender);

        CombatModel pvpSystem = PvpSystem.PvpUserVsUser(attacker, defender, guildR);
        pvpSystem.getAttacker().setPvpPts(pvpSystem.getAttacker().getPvpPts() - 1);
        userR.save(pvpSystem.getAttacker());

        if (defender.getUsername().equals("test")) pvpSystem.getDefender().setHp(defender.getMaxHp());
        userR.save(pvpSystem.getDefender());
        return pvpSystem.getHistoryCombat();
    }

    public ArrayList<ObjectNode> userVsNpcCombatSystem(String username, String npcName) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of the user vs npc combat system.
         * @param String username
         * @param String npcName
         * @return ArrayList<ObjectNode>
         */
        validate.userExist(userR.existsByUsername(username));
        validate.npcExist(npcR.existsByName(npcName));

        User user = userR.findByUsername(username);
        validate.lifeStartCombat(user);
        validate.pvePtsEnough(user);

        Npc npc = npcR.findByName(npcName);
        validate.userItemReqZoneSea(user.getEquipment(), npc.getZone());
        validate.userItemReqZoneHell(user.getEquipment(), npc.getZone());
        validate.differenceLevelPVE(user.getLevel(), npc.getLevel());

        CombatModel pveSystem = PveSystem.PveUserVsNpc(user, npc, calculateBonusGuild(user.getGuildName()));
        pveSystem.getAttacker().setPvePts(pveSystem.getAttacker().getPvePts() - 1);
        userR.save(pveSystem.getAttacker());
        return pveSystem.getHistoryCombat();
    }

    private float calculateBonusGuild(String guildName) {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of calculating the bonus of the guild.
         * @param String guildName
         * @return float
         */
        if (!guildR.existsGuildByName(guildName)) return 1;
        int guildLevel = guildR.findLevelByName(guildName);
        return guildLevel > 1 ? 1 + guildLevel * guildLevel / 100f : 1;
    }
}
