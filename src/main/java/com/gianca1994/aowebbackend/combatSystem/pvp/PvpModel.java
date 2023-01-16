package com.gianca1994.aowebbackend.combatSystem.pvp;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.resources.user.User;
import lombok.*;

import java.util.ArrayList;


/**
 * @Author: Gianca1994
 * Explanation: This class is used to represent a PvP combat.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PvpModel {
    private User attacker;
    private User defender;
    private ArrayList<ObjectNode> historyCombat;
}
