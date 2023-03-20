package com.gianca1994.heropathbackend.timers;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.resources.user.User;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to restore the PVE and PVP points of all users
 */

@Component
public class RestorePvePvpPts {

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * ?") // reset daily at 00:00:00
    public void resetDailyAttacks() {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to restore the PVE and PVP points of all users
         * @return void
         */
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setPvePts(SvConfig.PVE_PTS_MAX);
            user.setPvpPts(SvConfig.PVP_PTS_MAX);
            user.setHp(user.getMaxHp());
        }
        userRepository.saveAll(users);
    }
}
