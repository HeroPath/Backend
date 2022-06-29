package com.gianca1994.aowebbackend.combatSystem.pve;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.model.User;
import lombok.*;

import java.util.ArrayList;

/**
 * @Author: Gianca1994
 * Explanation: PveSystem
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PveModel {
    private User user;
    private ArrayList<ObjectNode> historyCombat;
}
