package com.gianca1994.heropathbackend.timers;

import com.gianca1994.heropathbackend.config.SvConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to start and stop the event
 */

@Component
public class EventGoldX2 {

    @Scheduled(cron = "0 0 11 * * ?")
    @Scheduled(cron = "0 0 22 * * ?")
    public void startEventGoldX2() {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to start the x2 gold event.
         * @return void
         */
        SvConfig.EVENT_ACTIVE_MSG = SvConfig.EVENT_GOLD_X2;
        SvConfig.GOLD_MULTIPLIER = SvConfig.GOLD_MULTIPLIER * 2;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @Scheduled(cron = "0 0 23 * * ?")
    public void stopEventGoldX2() {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to end the x2 gold event.
         * @return void
         */
        SvConfig.EVENT_ACTIVE_MSG = SvConfig.EVENT_NONE;
        SvConfig.GOLD_MULTIPLIER = SvConfig.GOLD_MULTIPLIER / 2;
    }
}
