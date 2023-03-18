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

    @Scheduled(cron = SvConfig.EVENT_EXP_X2_DATE_START)
    public void startEventExpX2() {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to start the x2 experience event.
         * @return void
         */
        if (SvConfig.EVENT_EXP_X2_ACTIVE) {
            SvConfig.EVENT_ACTIVE_MSG = SvConfig.EVENT_EXP_X2;
            SvConfig.EXPERIENCE_MULTIPLIER = SvConfig.EXPERIENCE_MULTIPLIER * 2;
        }

    }

    @Scheduled(cron = SvConfig.EVENT_EXP_X2_DATE_END)
    public void stopEventExpX2() {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to end the x2 experience event.
         * @return void
         */
        if (SvConfig.EVENT_EXP_X2_ACTIVE) {
            SvConfig.EVENT_ACTIVE_MSG = SvConfig.EVENT_NONE;
            SvConfig.EXPERIENCE_MULTIPLIER = SvConfig.EXPERIENCE_MULTIPLIER / 2;
        }
    }
}
