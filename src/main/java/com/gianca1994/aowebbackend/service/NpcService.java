package com.gianca1994.aowebbackend.service;

import com.gianca1994.aowebbackend.dto.NpcDTO;
import com.gianca1994.aowebbackend.exception.ConflictException;
import com.gianca1994.aowebbackend.exception.NotFoundException;
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
        if (npcRepository.findByName(name.toLowerCase()) == null) throw new NotFoundException("Npc not found");
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
            throw new NotFoundException("No npcs found in that area");
        return npcRepository.findByZone(zone);
    }

    public Npc saveNpc(NpcDTO npc) throws ConflictException {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving the npc.
         * @param NpcDTO npc
         * @return Npc
         */
        if (npc.getName().isEmpty()) throw new ConflictException("Name cannot be empty");
        if (npc.getLevel() < 1) throw new ConflictException("Level cannot be less than 1");
        if (npc.getGiveMinExp() < 0) throw new ConflictException("GiveMinExp cannot be less than 0");
        if (npc.getGiveMaxExp() < 0) throw new ConflictException("GiveMaxExp cannot be less than 0");
        if (npc.getGiveMinGold() < 0) throw new ConflictException("GiveMinGold cannot be less than 0");
        if (npc.getGiveMaxGold() < 0) throw new ConflictException("GiveMaxGold cannot be less than 0");
        if (npc.getHp() < 0) throw new ConflictException("Hp cannot be less than 0");
        if (npc.getMaxHp() < 0) throw new ConflictException("MaxHp cannot be less than 0");
        if (npc.getMinDmg() < 0) throw new ConflictException("MinDmg cannot be less than 0");
        if (npc.getMaxDmg() < 0) throw new ConflictException("MaxDmg cannot be less than 0");
        if (npc.getDefense() < 0) throw new ConflictException("MinDefense cannot be less than 0");
        if (npc.getZone().isEmpty()) throw new ConflictException("Zone cannot be empty");

        Npc checkNpcSave = npcRepository.findByName(npc.getName().toLowerCase());
        Npc newNpc = new Npc();

        if (checkNpcSave == null) {
            newNpc.setName(npc.getName().toLowerCase());
            newNpc.setLevel(npc.getLevel());
            newNpc.setGiveMinExp(npc.getGiveMinExp());
            newNpc.setGiveMaxExp(npc.getGiveMaxExp());
            newNpc.setGiveMinGold(npc.getGiveMinGold());
            newNpc.setGiveMaxGold(npc.getGiveMaxGold());
            newNpc.setHp(npc.getHp());
            newNpc.setMaxHp(npc.getMaxHp());
            newNpc.setMinDmg(npc.getMinDmg());
            newNpc.setMaxDmg(npc.getMaxDmg());
            newNpc.setDefense(npc.getDefense());
            newNpc.setZone(npc.getZone());
            return npcRepository.save(newNpc);
        }else {
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
}
