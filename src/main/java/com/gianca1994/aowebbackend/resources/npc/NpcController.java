package com.gianca1994.aowebbackend.resources.npc;

import com.gianca1994.aowebbackend.exception.Conflict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Set;

/**
 * @Author: Gianca1994
 * Explanation: NpcController
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/npcs")
public class NpcController {

    @Autowired
    private NpcService npcService;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public ArrayList<Npc> getAllNpcs() throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting all npcs.
         * @param String token
         * @return ArrayList<Npc>
         */
        try {
            return npcService.getAllNpcs();
        } catch (Exception e) {
            throw new Conflict("Error in getting all npcs");
        }
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public Npc getNpcByName(@PathVariable String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the npc by name.
         * @param String token
         * @param String name
         * @return Npc
         */
        try {
            return npcService.getNpcByName(name);
        } catch (Exception e) {
            throw new Conflict("Error in getting npc by name");
        }
    }

    @GetMapping("/zone/{zone}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public Set<Npc> filterNpcByZone(@PathVariable String zone) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of filtering the npcs by zone.
         * @param String zone
         * @return ArrayList<Npc>
         */
        try {
            return npcService.filterNpcByZone(zone);
        } catch (Exception e) {
            throw new Conflict("Error in getting npcs by zone");
        }
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public Npc saveNpc(@RequestBody NpcDTO npc) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving the npc.
         * @param String token
         * @param Npc npc
         * @return Npc
         */
        try {
            return npcService.saveNpc(npc);
        } catch (Exception e) {
            throw new Conflict("Error in saving npc");
        }
    }
}
