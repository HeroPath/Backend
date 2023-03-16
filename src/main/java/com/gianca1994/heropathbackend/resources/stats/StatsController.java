package com.gianca1994.heropathbackend.resources.stats;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gianca1994
 * @Explanation: This Class is used to manage the stats of the server.
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/stats")
public class StatsController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public StatsModel getStats() {
        /**
         * @Author: Gianca1994
         * @Explanation: This method returns the stats of the server.
         * @return StatsModel
         */
        return new StatsModel(
                userRepository.count(),
                SvConfig.LEVEL_MAX,
                SvConfig.EXPERIENCE_MULTIPLIER,
                SvConfig.GOLD_MULTIPLIER
        );
    }
}
