package com.gianca1994.aowebbackend.combatSystem;

import com.gianca1994.aowebbackend.model.User;
import com.gianca1994.aowebbackend.repository.UserRepository;
import com.gianca1994.aowebbackend.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PvPSystem {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    public void pvpUserVsUser(String usernameAttacker, String usernameDefender) {

        User attacker = userRepository.findByUsername(usernameAttacker);
        User defender = userRepository.findByUsername(usernameDefender);

        ArrayList<Object> historyCombat = new ArrayList<>();
        int roundCounter = 0;
        boolean stopPvP = false;

        float goldTheftPercentage = 0.25f;
        float goldLossPercentage = 0.1f;

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
                }
            }

            if (!stopPvP) {
                attacker.setHp(attacker.getHp() - defenderDmg);
                if (attacker.getHp() <= 0) {
                    attacker.setHp(0);
                    attacker.setGold((long) (attacker.getGold() - (attacker.getGold() * goldLossPercentage)));
                    stopPvP = true;
                }
            }

            JSONObject round = new JSONObject();
            round.put("round", roundCounter);
            round.put("attackerLife", attacker.getHp());
            round.put("defenderLife", defender.getHp());
            round.put("attackerDmg", attackerDmg);
            round.put("defenderDmg", defenderDmg);

            historyCombat.add(round);

        } while (attacker.getHp() > 0 && defender.getHp() > 0);

        userRepository.save(attacker);
        userRepository.save(defender);

        System.out.println(historyCombat);
    }
}
