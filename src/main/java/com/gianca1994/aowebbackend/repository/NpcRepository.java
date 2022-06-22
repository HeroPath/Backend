package com.gianca1994.aowebbackend.repository;

import com.gianca1994.aowebbackend.model.Npc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NpcRepository extends JpaRepository<Npc, Long> {
    Npc findByName(String name);
}
