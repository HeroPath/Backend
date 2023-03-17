package com.gianca1994.heropathbackend.timers;

import com.gianca1994.heropathbackend.config.SvConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to manage the event EXP X2
 */

@Component
public class EventExpX2 {

    @Scheduled(cron = "0 17 2 * * ?")
    public void startEventExpX2() {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is called every day
         * @return void
         */
        SvConfig.EVENT_ACTIVE = "EXP X2";
        SvConfig.EXPERIENCE_MULTIPLIER = SvConfig.EXPERIENCE_MULTIPLIER * 2;
    }

    @Scheduled(cron = "0 20 2 * * ?")
    public void stopEventExpX2() {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is called every day
         * @return void
         */
        SvConfig.EVENT_ACTIVE = "NONE";
        SvConfig.EXPERIENCE_MULTIPLIER = SvConfig.EXPERIENCE_MULTIPLIER / 2;
    }
}
