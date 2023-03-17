package com.gianca1994.heropathbackend.timers;

import com.gianca1994.heropathbackend.config.SvConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventExpX2 {

    @Scheduled(cron = "0 2 2 * * ?")
    public void startEventExpX2() {
        SvConfig.EXPERIENCE_MULTIPLIER = SvConfig.EXPERIENCE_MULTIPLIER * 2;
    }

    @Scheduled(cron = "0 5 2 * * ?")
    public void stopEventExpX2() {
        SvConfig.EXPERIENCE_MULTIPLIER = SvConfig.EXPERIENCE_MULTIPLIER / 2;
    }
}
