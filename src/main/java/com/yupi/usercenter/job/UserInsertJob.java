package com.yupi.usercenter.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 执行定时任务的类  使用spring自带的 spring task
 */
//@Component
public class UserInsertJob {

    @Scheduled(fixedRate = 3000)
    void testSchedule(){
        System.out.println("xinhui dashazi!");
    }

}
