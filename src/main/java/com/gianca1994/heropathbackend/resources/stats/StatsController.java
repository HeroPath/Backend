package com.gianca1994.heropathbackend.resources.stats;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gianca1994.heropathbackend.resources.stats.StatsConfig.statsModel;

/**
 * @Author: Gianca1994
 * @Explanation:
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/stats")
public class StatsController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public StatsModel getStats() {
        if (statsModel.getLvlMax() == SvConfig.LEVEL_MAX) return statsModel;

        statsModel.setLvlMax(SvConfig.LEVEL_MAX);
        statsModel.setExpMultiplier(SvConfig.EXPERIENCE_MULTIPLIER);
        statsModel.setGoldMultiplier(SvConfig.GOLD_MULTIPLIER);
        statsModel.setUserRegistered((int) userRepository.count());
        return statsModel;
    }
}
