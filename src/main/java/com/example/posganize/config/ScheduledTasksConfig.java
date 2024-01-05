package com.example.posganize.config;

import com.example.posganize.services.membership.MembershipService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.time.Instant;

@Configuration
@EnableScheduling
public class ScheduledTasksConfig implements SchedulingConfigurer {

    private final MembershipService membershipService;

    public ScheduledTasksConfig(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                membershipService::checkAndUpdateMembershipStatus,
                triggerContext -> {
//                  CronTrigger cronTrigger = new CronTrigger("0 0 0 * * ?");
                    CronTrigger cronTrigger = new CronTrigger("0 0/13 * * * ?");
                    Instant nextExecutionTime = cronTrigger.nextExecution(triggerContext);
                    assert nextExecutionTime != null;
                    return nextExecutionTime;
                }
        );
    }
}

