package com.gianca1994.heropathbackend.timers;

import com.gianca1994.heropathbackend.config.SvConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventGoldX2 {

    @Scheduled(cron = "0 2 2 * * ?") // reset daily at 00:00:00
    public void startEventGoldX2() {
        SvConfig.GOLD_MULTIPLIER = SvConfig.GOLD_MULTIPLIER * 2;
    }

    @Scheduled(cron = "0 5 2 * * ?") // reset daily at 00:00:00
    public void stopEventGoldX2() {
        SvConfig.GOLD_MULTIPLIER = SvConfig.GOLD_MULTIPLIER / 2;
    }
}
