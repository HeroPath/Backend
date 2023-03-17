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
    public void startEventGoldX2() {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is called every day
         * @return void
         */
        SvConfig.EVENT_ACTIVE = "GOLD X2";
        SvConfig.GOLD_MULTIPLIER = SvConfig.GOLD_MULTIPLIER * 2;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void stopEventGoldX2() {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is called every day
         * @return void
         */
        SvConfig.EVENT_ACTIVE = "NONE";
        SvConfig.GOLD_MULTIPLIER = SvConfig.GOLD_MULTIPLIER / 2;
    }
}
