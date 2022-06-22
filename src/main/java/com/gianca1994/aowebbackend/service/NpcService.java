package com.gianca1994.aowebbackend.service;

import com.gianca1994.aowebbackend.dto.NpcDTO;
import com.gianca1994.aowebbackend.model.Npc;
import com.gianca1994.aowebbackend.repository.NpcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
        if(npcRepository.findByName(name) == null) return null;
        return npcRepository.findByName(name);

    }

    public Npc saveNpc(NpcDTO npc) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving the npc.
         * @param Npc npc
         * @return Npc
         */
        if(!(npcRepository.findByName(npc.getName().toLowerCase()) == null)) return null;

        Npc newNpc = new Npc(
                npc.getName().toLowerCase(),
                npc.getGiveMinExp(),
                npc.getGiveMaxExp(),
                npc.getGiveMinGold(),
                npc.getGiveMaxGold(),
                npc.getHp(),
                npc.getMaxHp(),
                npc.getMinDmg(),
                npc.getMaxDmg(),
                npc.getDefense()
        );

        return npcRepository.save(newNpc);
    }
}
