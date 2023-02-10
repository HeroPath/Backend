package com.gianca1994.aowebbackend.resources.npc;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.npc.dto.request.NpcDTO;
import com.gianca1994.aowebbackend.resources.npc.utilities.NpcConst;
import com.gianca1994.aowebbackend.resources.npc.utilities.NpcServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Gianca1994
 * Explanation: NpcService
 */

@Service
public class NpcService {

    NpcServiceValidator validator = new NpcServiceValidator();

    @Autowired
    private NpcRepository npcR;

    public ArrayList<Npc> getAllNpcs() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting all npcs.
         * @param none
         * @return ArrayList<Npc>
         */
        return (ArrayList<Npc>) npcR.findAll();
    }

    public Npc getNpcByName(String name) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the npc by name.
         * @param String name
         * @return Npc
         */
        validator.npcFound(npcR.existsByName(name.toLowerCase()));
        return npcR.findByName(name.toLowerCase());
    }

    public Set<Npc> filterNpcByZone(String zone) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of filtering the npcs by zone.
         * @param String zone
         * @return Set<Npc>
         */
        Set<Npc> npcs = new TreeSet<>(new NpcLevelComparator());
        npcs.addAll(npcR.findByZone(zone.toLowerCase()));
        validator.filterNpcByZone(npcs);
        return npcs;
    }

    static class NpcLevelComparator implements Comparator<Npc> {
        /**
         * @return int
         * @Author: Gianca1994
         * Explanation: This function is in charge of comparing the npcs by level.
         */
        @Override
        public int compare(Npc npc1, Npc npc2) {
            return Integer.compare(npc1.getLevel(), npc2.getLevel());
        }
    }

    public Npc saveNpc(NpcDTO npc) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving the npc.
         * @param NpcDTO npc
         * @return Npc
         */
        validator.saveNpc(npc);
        Npc checkNpcSave = npcR.findByName(npc.getName().toLowerCase());

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
        return npcR.save(checkNpcSave);
    }
}
