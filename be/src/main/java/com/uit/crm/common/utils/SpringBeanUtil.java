package com.uit.crm.common.utils;


import org.springframework.context.ApplicationContext;

public class SpringBeanUtil {

    public static <T> T getBean(Class<T> className){
        return getApplicationContext().getBean(className);
    }

    public static ApplicationContext getApplicationContext(){
        return ApplicationContextFactory.getApplicationContext();
    }
}
