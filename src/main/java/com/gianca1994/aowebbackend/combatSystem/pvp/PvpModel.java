package com.gianca1994.aowebbackend.combatSystem.pvp;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.model.User;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PvpModel {
    private User attacker;
    private User defender;
    private ArrayList<ObjectNode> historyCombat;
}
