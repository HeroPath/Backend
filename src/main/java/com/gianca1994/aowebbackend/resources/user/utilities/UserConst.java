package com.gianca1994.aowebbackend.resources.user.utilities;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: This class contains all the constants used in the user package
 */

public class UserConst {
    public static final String USER_NOT_FOUND = "User not found";
    public static final String DONT_HAVE_SKILL_POINTS = "You don't have any free skill points";
    public static final List<String> SKILLS_ENABLED = Arrays.asList(
            "strength",
            "dexterity",
            "intelligence",
            "vitality",
            "luck"
    );
    public static final String SKILL_POINT_NAME_MUST_ONE_FOLLOWING = "Skill point name must be one of the following: ";
    public static final String IMPOSSIBLE_ATTACK_LESS_HP = "Impossible to attack with less than 15% of life";
    public static final String IMPOSSIBLE_ATTACK_15_ENEMY = "Unable to attack, enemy has less than 15% health";
    public static final String CANT_ATTACK_LVL_LOWER_5 = "You can't attack with a level lower than 5";
    public static final String CANT_ATTACK_YOURSELF = "You can't fight yourself";
    public static final String CANT_ATTACK_ADMIN = "You can't attack an admin";
    public static final String NPC_NOT_FOUND = "Npc not found";
    public static final String CANT_ATTACK_NPC_LVL_HIGHER_5 = "You can't attack an npc with level higher than 5 levels higher than you";
    public static final String CANT_ATTACK_NPC_SEA = "You can't attack an npc in the sea without a ship";
    public static final String CANT_ATTACK_NPC_HELL = "You can't attack an npc in hell without wings";
}
