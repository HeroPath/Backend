package com.gianca1994.heropathbackend.resources.npc;

import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.resources.npc.dto.request.NpcDTO;
import com.gianca1994.heropathbackend.resources.npc.utilities.NpcServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Gianca1994
 * @Explanation: This class is in charge of the business logic of the npc.
 */

@Service
public class NpcService {

    NpcServiceValidator validator = new NpcServiceValidator();

    @Autowired
    private NpcRepository npcR;

    public ArrayList<Npc> getAllNpcs() {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of getting all npcs.
         * @param none
         * @return ArrayList<Npc>
         */
        return (ArrayList<Npc>) npcR.findAll();
    }

    public Npc getNpcByName(String name) {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of getting the npc by name.
         * @param String name
         * @return Npc
         */
        validator.npcFound(npcR.existsByName(name.toLowerCase()));
        return npcR.findByName(name.toLowerCase());
    }

    public ArrayList<Npc> filterNpcByZone(String zone) {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of filtering the npcs by zone.
         * @param String zone
         * @return Set<Npc>
         */
        ArrayList<Npc> npcs = npcR.findByZoneAndOrderByLevel(zone.toLowerCase());
        validator.npcNotFoundZone(npcs.size());
        return npcs;
    }

    public Npc saveNpc(NpcDTO npc) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of saving the npc.
         * @param NpcDTO npc
         * @return Npc
         */
        validator.saveNpc(npc);
        String nameNpc = npc.getName().toLowerCase();
        Npc checkNpcSave = npcR.findByName(nameNpc);

        if (checkNpcSave == null) {
            checkNpcSave = new Npc();
            checkNpcSave.setName(nameNpc);
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
