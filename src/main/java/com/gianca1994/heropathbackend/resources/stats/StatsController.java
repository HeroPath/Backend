package com.gianca1994.heropathbackend.resources.stats;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.resources.stats.models.StatsPvePvpPts;
import com.gianca1994.heropathbackend.resources.stats.models.StatsServer;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/server")
    public StatsServer getServerStats() {
        /**
         * @Author: Gianca1994
         * @Explanation: This method returns the stats of the server.
         * @return StatsModel
         */
        return new StatsServer(
                userRepository.count(),
                SvConfig.LEVEL_MAX,
                SvConfig.EXPERIENCE_MULTIPLIER,
                SvConfig.GOLD_MULTIPLIER
        );
    }

    @GetMapping("/pve-pvp/pts")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public StatsPvePvpPts getPvePvpPts() {
        /**
         * @Author: Gianca1994
         * @Explanation: This method returns the stats of the server.
         * @return StatsPvePvpPts
         */
        return new StatsPvePvpPts(
                SvConfig.PVE_PTS_MAX,
                SvConfig.PVP_PTS_MAX
        );
    }
}
