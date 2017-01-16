package com.hearglobal.msp.core.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lvzhouyang on 17/1/16.
 */
@Component
public class EventPublisher {
    @Resource
    private ApplicationContext applicationContext;

    public void publish(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }
}
