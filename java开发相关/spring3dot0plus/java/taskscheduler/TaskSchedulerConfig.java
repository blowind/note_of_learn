package com.zxf.taskscheduler;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("com.zxf.taskscheduler")
@EnableScheduling
public class TaskSchedulerConfig {
}
