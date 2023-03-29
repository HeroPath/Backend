package com.gianca1994.heropathbackend.resources.npc.utilities;

import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.exception.NotFound;
import com.gianca1994.heropathbackend.resources.npc.dto.request.NpcDTO;
import com.gianca1994.heropathbackend.utils.Const;

/**
 * @Author: Gianca1994
 * @Explanation: This class contains all the methods used to validate the npc before saving it
 */

public class NpcServiceValidator {

    public void npcFound(boolean npcExist) {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to validate the npc before saving it
         * @param boolean npcExist
         * @return void
         */
        if (!npcExist) throw new NotFound(Const.NPC.NOT_FOUND.getMsg());
    }

    public void npcNotFoundZone(int npcSize) {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to validate the npc before saving it
         * @param int npcSize
         * @return void
         */
        if (npcSize <= 0) throw new NotFound(Const.NPC.NOT_IN_ZONE.getMsg());
    }

    public void saveNpc(NpcDTO npc) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to validate the npc before saving it
         * @param NpcDTO npc
         * @return void
         */
        if (npc.getName().isEmpty()) throw new Conflict(Const.NPC.NAME_EMPTY.getMsg());
        if (npc.getLevel() < 1) throw new Conflict(Const.NPC.LVL_LT_1.getMsg());
        if (npc.getGiveMinExp() < 0) throw new Conflict(Const.NPC.GIVE_MIN_EXP_LT_0.getMsg());
        if (npc.getGiveMaxExp() < 0) throw new Conflict(Const.NPC.GIVE_MAX_EXP_LT_0.getMsg());
        if (npc.getGiveMinGold() < 0) throw new Conflict(Const.NPC.GIVE_MIN_GOLD_LT_0.getMsg());
        if (npc.getGiveMaxGold() < 0) throw new Conflict(Const.NPC.GIVE_MAX_GOLD_LT_0.getMsg());
        if (npc.getHp() < 0) throw new Conflict(Const.NPC.HP_LT_0.getMsg());
        if (npc.getMaxHp() < 0) throw new Conflict(Const.NPC.MAX_HP_LT_0.getMsg());
        if (npc.getMinDmg() < 0) throw new Conflict(Const.NPC.MIN_DMG_LT_0.getMsg());
        if (npc.getMaxDmg() < 0) throw new Conflict(Const.NPC.MAX_DMG_LT_0.getMsg());
        if (npc.getDefense() < 0) throw new Conflict(Const.NPC.MIN_DEF_LT_0.getMsg());
        if (npc.getZone().isEmpty()) throw new Conflict(Const.NPC.ZONE_EMPTY.getMsg());
    }
}
