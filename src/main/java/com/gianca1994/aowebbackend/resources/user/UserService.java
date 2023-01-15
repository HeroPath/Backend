package com.gianca1994.aowebbackend.resources.user;

import java.util.*;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.*;
import com.gianca1994.aowebbackend.combatSystem.pve.PveModel;
import com.gianca1994.aowebbackend.combatSystem.pve.PveSystem;
import com.gianca1994.aowebbackend.combatSystem.pvp.PvpSystem;
import com.gianca1994.aowebbackend.combatSystem.pvp.PvpModel;
import com.gianca1994.aowebbackend.exception.BadRequest;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.item.ItemRepository;
import com.gianca1994.aowebbackend.resources.jwt.JwtTokenUtil;
import com.gianca1994.aowebbackend.resources.item.Item;
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
    GenericFunctions genericFunctions = new GenericFunctions();

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public User getProfile(String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the profile of the user.
         * @param String token
         * @return User
         */
        return userRepository.findByUsername(jwtTokenUtil.getUsernameFromToken(token.substring(7)));
    }

    public void updateUser(User userToUpdate) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of updating the user.
         * @param String username
         * @return none
         */
        userRepository.save(userToUpdate);
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

    public User setFreeSkillPoint(String token, FreeSkillPointDTO freeSkillPointDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of adding skill points to the user.
         * @param String username
         * @param FreeSkillPointDTO freeSkillPointDTO
         * @return User
         */
        User user = userRepository.findByUsername(jwtTokenUtil.getUsernameFromToken(token.substring(7)));

        if (user == null) throw new NotFound(UserConst.USER_NOT_FOUND);
        if (freeSkillPointDTO.getAmount() <= 0) throw new BadRequest(UserConst.AMOUNT_MUST_GREATER_THAN_0);
        if (user.getFreeSkillPoints() <= 0) throw new Conflict(UserConst.DONT_HAVE_SKILL_POINTS);
        if (user.getFreeSkillPoints() < freeSkillPointDTO.getAmount())
            throw new Conflict(UserConst.DONT_HAVE_ENOUGH_SKILL_POINTS);

        if (!UserConst.SKILLS_ENABLED.contains(freeSkillPointDTO.getSkillPointName().toLowerCase()))
            throw new Conflict(UserConst.SKILL_POINT_NAME_MUST_ONE_FOLLOWING + UserConst.SKILLS_ENABLED);

        user.addFreeSkillPoints(freeSkillPointDTO);
        userRepository.save(user);
        return user;
    }

    public ArrayList<ObjectNode> userVsUserCombatSystem(String token,
                                                        UserAttackUserDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat system between users.
         * @param String token
         * @param UserAttackUserDTO nameRequestDTO
         * @return ArrayList<ObjectNode>
         */
        User attacker = userRepository.findByUsername(jwtTokenUtil.getUsernameFromToken(token.substring(7)));
        if (attacker == null) throw new NotFound(UserConst.USER_NOT_FOUND);
        if (genericFunctions.checkLifeStartCombat(attacker)) throw new BadRequest(UserConst.IMPOSSIBLE_ATTACK_LESS_HP);
        if (attacker.getLevel() < UserConst.MAX_LEVEL_DIFFERENCE) throw new Conflict(UserConst.CANT_ATTACK_LVL_LOWER_5);

        User defender = userRepository.findByUsername(nameRequestDTO.getName());
        if (attacker == defender) throw new Conflict(UserConst.CANT_ATTACK_YOURSELF);
        if (defender == null) throw new NotFound(UserConst.USER_NOT_FOUND);
        if (defender.getLevel() < UserConst.MAX_LEVEL_DIFFERENCE) throw new Conflict(UserConst.CANT_ATTACK_LVL_LOWER_5);
        if (defender.getRole().getRoleName().equals("ADMIN")) throw new Conflict(UserConst.CANT_ATTACK_ADMIN);
        if (genericFunctions.checkLifeStartCombat(defender)) throw new BadRequest(UserConst.IMPOSSIBLE_ATTACK_15_ENEMY);

        PvpModel pvpUserVsUserModel = PvpSystem.PvpUserVsUser(attacker, defender, titleRepository);

        userRepository.save(pvpUserVsUserModel.getAttacker());
        if (pvpUserVsUserModel.getDefender().getUsername().equals("test")) {
            pvpUserVsUserModel.getDefender().setHp(pvpUserVsUserModel.getDefender().getMaxHp());
        }
        userRepository.save(pvpUserVsUserModel.getDefender());
        return pvpUserVsUserModel.getHistoryCombat();
    }

    public ArrayList<ObjectNode> userVsNpcCombatSystem(String token,
                                                       UserAttackNpcDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat system between users and npcs.
         * @param String token
         * @param UserAttackNpcDTO nameRequestDTO
         * @return ArrayList<ObjectNode>
         */
        User user = userRepository.findByUsername(jwtTokenUtil.getUsernameFromToken(token.substring(7)));

        if (user == null) throw new NotFound(UserConst.USER_NOT_FOUND);
        if (genericFunctions.checkLifeStartCombat(user)) throw new BadRequest(UserConst.IMPOSSIBLE_ATTACK_LESS_HP);

        Npc npc = npcRepository.findByName(nameRequestDTO.getName().toLowerCase());
        if (npc == null) throw new NotFound(UserConst.NPC_NOT_FOUND);

        if (npc.getLevel() > user.getLevel() + UserConst.MAX_LEVEL_DIFFERENCE)
            throw new Conflict(UserConst.CANT_ATTACK_LVL_LOWER_5);

        boolean enabledSea = false, enabledHell = false;
        for (Item item : user.getEquipment().getItems()) {
            if (item.getType().equals("ship")) enabledSea = true;
            if (item.getType().equals("wings")) enabledHell = true;
        }
        if (npc.getZone().equals("sea") && !enabledSea) throw new Conflict(UserConst.CANT_ATTACK_NPC_SEA);
        if (npc.getZone().equals("hell") && !enabledHell) throw new Conflict(UserConst.CANT_ATTACK_NPC_HELL);

        PveModel pveSystem = PveSystem.PveUserVsNpc(user, npc, titleRepository);

        userRepository.save(pveSystem.getUser());
        return pveSystem.getHistoryCombat();
    }
}
