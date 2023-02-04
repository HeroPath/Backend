package com.gianca1994.aowebbackend.config;

import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.title.Title;

import java.util.Arrays;
import java.util.List;

public class ModifConfig {
    /**
     * @Author: Gianca1994
     * Explanation: This class is used to modify the configuration of the application.
     */

    /////////////////////////// USER ///////////////////////////
    public static final short START_LVL = 1;
    public static final long START_EXP = 0;
    public static final long START_GOLD = 1000;
    public static final int START_DIAMOND = 100;
    public static final int START_FREE_SKILL_POINTS = 3;
    public static final int FREE_SKILL_POINTS_PER_LEVEL = 1;
    public static final int MAX_CRITICAL_PERCENTAGE = 50;
    /////////////////////////// USER ///////////////////////////

    /////////////////////////// TITLE ///////////////////////////
    public static final List<Title> TITLES = Arrays.asList(
            new Title("iron", 1, 0, 0, 0, 0, 0, 0),
            new Title("bronze", 25, 50, 5, 5, 5, 5, 5),
            new Title("silver", 50, 100, 10, 10, 10, 10, 10),
            new Title("gold", 100, 200, 15, 15, 15, 15, 15),
            new Title("platinum", 150, 400, 20, 20, 20, 20, 20),
            new Title("diamond", 200, 800, 25, 25, 25, 25, 25),
            new Title("challenger", 250, 1600, 30, 30, 30, 30, 30)
    );
    /////////////////////////// TITLE ///////////////////////////

    /////////////////////////// CLASS ////////////////////////////
    public static final List<Class> CLASSES = Arrays.asList(
            new Class("mage", 1, 1, 3, 2, 2, 5, 7, 10, 2, 2, 0.15f),
            new Class("warrior", 3, 1, 1, 3, 1, 3, 5, 20, 5, 2, 0.1f),
            new Class("archer", 1, 3, 1, 2, 2, 4, 6, 15, 3, 4, 0.125f)
    );
    /////////////////////////// CLASS ////////////////////////////
}
