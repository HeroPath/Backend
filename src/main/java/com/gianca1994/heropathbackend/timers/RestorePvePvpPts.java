package com.gianca1994.heropathbackend.timers;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.resources.user.User;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestorePvePvpPts {

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 37 1 * * ?")
    public void resetDailyAttacks() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setPvePts(SvConfig.PVE_PTS_MAX);
            user.setPvpPts(SvConfig.PVP_PTS_MAX);
        }
        userRepository.saveAll(users);
    }
}
