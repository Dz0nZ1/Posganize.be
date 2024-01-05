package com.example.posganize.config;

import com.example.posganize.services.membership.MembershipService;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class KeepServerActive {
    private final MembershipService membershipService;

    public KeepServerActive(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    public void startKeepingServerActive() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(
                membershipService::checkAndUpdateMembershipStatus,
                0, 13, TimeUnit.MINUTES
        );
    }
}
