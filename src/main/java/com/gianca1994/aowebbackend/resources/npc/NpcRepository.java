package com.gianca1994.aowebbackend.resources.npc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @Author: Gianca1994
 * Explanation: NpcRepository
 */

@Repository
public interface NpcRepository extends JpaRepository<Npc, Long> {
    Npc findByName(String name);
    ArrayList<Npc> findByZone(String zone);
}
