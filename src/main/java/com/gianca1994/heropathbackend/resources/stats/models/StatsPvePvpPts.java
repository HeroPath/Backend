package com.gianca1994.heropathbackend.resources.stats.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * @Explanation: This class contains all the statics of the server.
 */


@AllArgsConstructor
@Getter
@Setter
public class StatsPvePvpPts {
    private int maxPvePts;
    private int maxPvpPts;
}
