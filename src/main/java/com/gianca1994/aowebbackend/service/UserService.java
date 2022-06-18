package com.gianca1994.aowebbackend.service;

import java.util.ArrayList;
import java.util.Comparator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.model.Npc;
import com.gianca1994.aowebbackend.model.User;
import com.gianca1994.aowebbackend.repository.NpcRepository;
import com.gianca1994.aowebbackend.repository.RoleRepository;
import com.gianca1994.aowebbackend.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    NpcRepository npcRepository;

    @Value("${SERVER_EXPERIENCE_RATE}")
    private int EXPERIENCE_MULTIPLIER_PATTERN;

    @Value("${SERVER_GOLD_RATE}")
    private int GOLD_MULTIPLIER_PATTERN;

    public User getProfile(String username) {
        return userRepository.findByUsername(username);
    }

    public ArrayList<User> getRankingAll() {
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        users.sort(Comparator.comparing(User::getLevel).reversed());
        return users;
    }

    public ArrayList<ObjectNode> pvpUserVsUser(String usernameAttacker, String usernameDefender) {
        final float goldTheftPercentage = 0.25f;
        final float goldLossPercentage = 0.1f;

        User attacker = userRepository.findByUsername(usernameAttacker);
        User defender = userRepository.findByUsername(usernameDefender);

        if (attacker.getHp() < attacker.getMaxHp() * 0.25 ||
                defender.getHp() < defender.getMaxHp() * 0.25) {
            return null;
        }

        ArrayList<ObjectNode> historyCombat = new ArrayList<>();

        int roundCounter = 0;
        boolean stopPvP = false;
        do {
            roundCounter++;
            int attackerDmg = (int) ((int) (Math.random() * (attacker.getMaxDmg() - attacker.getMinDmg())) + attacker.getMinDmg());
            int defenderDmg = (int) ((int) (Math.random() * (defender.getMaxDmg() - defender.getMinDmg())) + defender.getMinDmg());

            if (!stopPvP) {
                defender.setHp(defender.getHp() - attackerDmg);
                if (defender.getHp() <= 0) {
                    defender.setHp(0);
                    long goldTheft = (long) (defender.getGold() * goldTheftPercentage);
                    attacker.setGold(attacker.getGold() + goldTheft);
                    defender.setGold(defender.getGold() - goldTheft);
                    stopPvP = true;
                } else {
                    attacker.setHp(attacker.getHp() - defenderDmg);
                    if (attacker.getHp() <= 0) {
                        attacker.setHp(0);
                        attacker.setGold((long) (attacker.getGold() - (attacker.getGold() * goldLossPercentage)));
                        stopPvP = true;
                    }
                }
            }

            ObjectNode round = new ObjectMapper().createObjectNode();
            round.put("round", roundCounter);
            round.put("attackerLife", attacker.getHp());
            round.put("defenderLife", defender.getHp());
            round.put("attackerDmg", attackerDmg);
            round.put("defenderDmg", defenderDmg);
            historyCombat.add(round);


        } while (attacker.getHp() > 0 && defender.getHp() > 0);

        userRepository.save(attacker);
        userRepository.save(defender);

        return historyCombat;
    }

    public ArrayList<ObjectNode> pvpUserVsNPC(String usernameAttacker, long npcId) {
        User attacker = userRepository.findByUsername(usernameAttacker);
        Npc npc = npcRepository.findById(npcId).get();

        if (attacker.getHp() < attacker.getMaxHp() * 0.25) {
            return null;
        }

        ArrayList<ObjectNode> historyCombat = new ArrayList<>();

        int roundCounter = 0;
        boolean stopPvP = false;
        do {
            roundCounter++;
            int attackerDmg = (int) ((int) (Math.random() * (attacker.getMaxDmg() - attacker.getMinDmg())) + attacker.getMinDmg());
            int npcDmg = (int) (Math.random() * (npc.getMaxDmg() - npc.getMinDmg())) + npc.getMinDmg();

            if (!stopPvP) {
                npc.setHp(npc.getHp() - attackerDmg);
                if (npc.getHp() <= 0) {
                    npc.setHp(0);
                    attacker.setExperience((long) (attacker.getExperience() + ((Math.random() * (npc.getGiveMaxExp() - npc.getGiveMinExp())) + npc.getGiveMinExp()) * EXPERIENCE_MULTIPLIER_PATTERN));
                    attacker.setGold((long) (attacker.getGold() + ((Math.random() * (npc.getGiveMaxGold() - npc.getGiveMinGold())) + npc.getGiveMinGold()) * GOLD_MULTIPLIER_PATTERN));

                    if (attacker.getExperienceToNextLevel() <= attacker.getExperience()) {
                        attacker.setLevel((short) (attacker.getLevel() + 1));

                        if (attacker.getLevel() <= 10) {
                            attacker.setExperienceToNextLevel((attacker.getExperienceToNextLevel() * 2));
                        } else if (attacker.getLevel() <= 20) {
                            attacker.setExperienceToNextLevel((long) (attacker.getExperienceToNextLevel() * 1.5));
                        } else if (attacker.getLevel() <= 30) {
                            attacker.setExperienceToNextLevel((long) (attacker.getExperienceToNextLevel() * 1.25));
                        } else if (attacker.getLevel() <= 40) {
                            attacker.setExperienceToNextLevel((long) (attacker.getExperienceToNextLevel() * 1.15));
                        } else if (attacker.getLevel() <= 50) {
                            attacker.setExperienceToNextLevel((long) (attacker.getExperienceToNextLevel() * 1.1));
                        } else if (attacker.getLevel() <= 60) {
                            attacker.setExperienceToNextLevel((long) (attacker.getExperienceToNextLevel() * 1.05));
                        } else if (attacker.getLevel() <= 70) {
                            attacker.setExperienceToNextLevel((long) (attacker.getExperienceToNextLevel() * 1.025));
                        } else if (attacker.getLevel() <= 80) {
                            attacker.setExperienceToNextLevel((long) (attacker.getExperienceToNextLevel() * 1.025));
                        } else if (attacker.getLevel() <= 90) {
                            attacker.setExperienceToNextLevel((long) (attacker.getExperienceToNextLevel() * 1.01));
                        } else if (attacker.getLevel() <= 100) {
                            attacker.setExperienceToNextLevel((long) (attacker.getExperienceToNextLevel() * 1.01));
                        }
                    }
                    stopPvP = true;
                } else {
                    attacker.setHp(attacker.getHp() - npcDmg);
                    if (attacker.getHp() <= 0) {
                        attacker.setHp(0);
                        stopPvP = true;
                    }
                }
            }


            ObjectNode round = new ObjectMapper().createObjectNode();
            round.put("round", roundCounter);
            round.put("attackerLife", attacker.getHp());
            round.put("NpcLife", npc.getHp());
            round.put("attackerDmg", attackerDmg);
            round.put("NpcDmg", npcDmg);
            historyCombat.add(round);


        } while (attacker.getHp() > 0 && npc.getHp() > 0);

        npc.setHp(npc.getMaxHp());

        userRepository.save(attacker);
        return historyCombat;
    }
}
