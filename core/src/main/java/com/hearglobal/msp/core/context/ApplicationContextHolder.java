package com.hearglobal.msp.core.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * The type Application context holder.
 * 容器holder
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
public class ApplicationContextHolder implements ApplicationContextAware {

    public static ApplicationContext context;

    public static ApplicationConstant constant;

    public static final ApplicationContextHolder INSTANCE = new ApplicationContextHolder();

    private ApplicationContextHolder(){}

    public static ApplicationContextHolder getInstance() {
        return INSTANCE;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        constant = applicationContext.getBean(ApplicationConstant.class);
    }


}
