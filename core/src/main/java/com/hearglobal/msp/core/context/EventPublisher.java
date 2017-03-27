package com.hearglobal.msp.core.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * spring事件发布器
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
@Component
public class EventPublisher {
    @Resource
    private ApplicationContext applicationContext;

    public void publish(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }
}
