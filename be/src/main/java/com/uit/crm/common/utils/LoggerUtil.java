package com.uit.crm.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerUtil {
    public <T>Logger logger(Class<T> tClass){
        return LoggerFactory.getLogger(tClass);
    }
}
