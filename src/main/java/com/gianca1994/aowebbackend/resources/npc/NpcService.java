package com.gianca1994.aowebbackend.resources.npc;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Comparator;


/**
 * @Author: Gianca1994
 * Explanation: NpcService
 */

@Service
public class NpcService {

    @Autowired
    private NpcRepository npcRepository;

    public ArrayList<Npc> getAllNpcs() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting all npcs.
         * @param none
         * @return ArrayList<Npc>
         */
        return (ArrayList<Npc>) npcRepository.findAll();
    }

    public Npc getNpcByName(String name) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the npc by name.
         * @param String name
         * @return Npc
         */
        if (npcRepository.findByName(name.toLowerCase()) == null) throw new NotFound(NpcConst.NPC_NOT_FOUND);
        return npcRepository.findByName(name.toLowerCase());
    }

    public ArrayList<Npc> filterNpcByZone(String zone) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of filtering the npcs by zone.
         * @param String zone
         * @return ArrayList<Npc>
         */
        if (npcRepository.findByZone(zone.toLowerCase()).isEmpty())
            throw new NotFound(NpcConst.NPC_NOT_FOUND_ZONE);
        return npcRepository.findByZone(zone);
    }

    public Npc saveNpc(NpcDTO npc) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving the npc.
         * @param NpcDTO npc
         * @return Npc
         */
        if (npc.getName().isEmpty()) throw new Conflict(NpcConst.NAME_EMPTY);
        if (npc.getLevel() < 1) throw new Conflict(NpcConst.LEVEL_LESS_THAN_ONE);
        if (npc.getGiveMinExp() < 0) throw new Conflict(NpcConst.GIVE_MIN_EXP_LESS_THAN_ZERO);
        if (npc.getGiveMaxExp() < 0) throw new Conflict(NpcConst.GIVE_MAX_EXP_LESS_THAN_ZERO);
        if (npc.getGiveMinGold() < 0) throw new Conflict(NpcConst.GIVE_MIN_GOLD_LESS_THAN_ZERO);
        if (npc.getGiveMaxGold() < 0) throw new Conflict(NpcConst.GIVE_MAX_GOLD_LESS_THAN_ZERO);
        if (npc.getHp() < 0) throw new Conflict(NpcConst.HP_LESS_THAN_ZERO);
        if (npc.getMaxHp() < 0) throw new Conflict(NpcConst.MAX_HP_LESS_THAN_ZERO);
        if (npc.getMinDmg() < 0) throw new Conflict(NpcConst.MIN_DMG_LESS_THAN_ZERO);
        if (npc.getMaxDmg() < 0) throw new Conflict(NpcConst.MAX_DMG_LESS_THAN_ZERO);
        if (npc.getDefense() < 0) throw new Conflict(NpcConst.DEFENSE_LESS_THAN_ZERO);
        if (npc.getZone().isEmpty()) throw new Conflict(NpcConst.ZONE_EMPTY);

        Npc checkNpcSave = npcRepository.findByName(npc.getName().toLowerCase());

        if (checkNpcSave == null) {
            checkNpcSave = new Npc();
            checkNpcSave.setName(npc.getName().toLowerCase());
        }

        checkNpcSave.setLevel(npc.getLevel());
        checkNpcSave.setGiveMinExp(npc.getGiveMinExp());
        checkNpcSave.setGiveMaxExp(npc.getGiveMaxExp());
        checkNpcSave.setGiveMinGold(npc.getGiveMinGold());
        checkNpcSave.setGiveMaxGold(npc.getGiveMaxGold());
        checkNpcSave.setHp(npc.getHp());
        checkNpcSave.setMaxHp(npc.getMaxHp());
        checkNpcSave.setMinDmg(npc.getMinDmg());
        checkNpcSave.setMaxDmg(npc.getMaxDmg());
        checkNpcSave.setDefense(npc.getDefense());
        checkNpcSave.setZone(npc.getZone());
        return npcRepository.save(checkNpcSave);
    }
}
