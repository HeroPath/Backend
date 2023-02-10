package com.gianca1994.aowebbackend.resources.npc.utilities;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.npc.Npc;
import com.gianca1994.aowebbackend.resources.npc.dto.request.NpcDTO;

/**
 * @Author: Gianca1994
 * Explanation: This class contains all the methods used to validate the npc before saving it
 */

public class NpcServiceValidator {

    public void npcFound(boolean npcExist) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to validate the npc before saving it
         * @param boolean npcExist
         * @return void
         */
        if (!npcExist) throw new NotFound(NpcConst.NPC_NOT_FOUND);
    }

    public void npcNotFoundZone(int npcSize) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to validate the npc before saving it
         * @param int npcSize
         * @return void
         */
        if (npcSize <= 0) throw new NotFound(NpcConst.NOT_NPC_ZONE);
    }

    public void saveNpc(NpcDTO npc) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to validate the npc before saving it
         * @param NpcDTO npc
         * @return void
         */
        if (npc.getName().isEmpty()) throw new Conflict(NpcConst.NAME_EMPTY);
        if (npc.getLevel() < 1) throw new Conflict(NpcConst.LVL_LT_1);
        if (npc.getGiveMinExp() < 0) throw new Conflict(NpcConst.GIVE_MIN_EXP_LT_0);
        if (npc.getGiveMaxExp() < 0) throw new Conflict(NpcConst.GIVE_MAX_EXP_LT_0);
        if (npc.getGiveMinGold() < 0) throw new Conflict(NpcConst.GIVE_MIN_GOLD_LT_0);
        if (npc.getGiveMaxGold() < 0) throw new Conflict(NpcConst.GIVE_MAX_GOLD_LT_0);
        if (npc.getHp() < 0) throw new Conflict(NpcConst.HP_LT_0);
        if (npc.getMaxHp() < 0) throw new Conflict(NpcConst.MAX_HP_LT_0);
        if (npc.getMinDmg() < 0) throw new Conflict(NpcConst.MIN_DMG_LT_0);
        if (npc.getMaxDmg() < 0) throw new Conflict(NpcConst.MAX_DMG_LT_0);
        if (npc.getDefense() < 0) throw new Conflict(NpcConst.MIN_DEF_LT_0);
        if (npc.getZone().isEmpty()) throw new Conflict(NpcConst.ZONE_EMPTY);
    }
}
