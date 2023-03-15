package com.gianca1994.heropathbackend.resources.stats;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatsModel {
    private int userRegistered;
    private int lvlMax;
    private int expMultiplier;
    private int goldMultiplier;

    public void addUserStat() {
        this.userRegistered++;
    }
}
