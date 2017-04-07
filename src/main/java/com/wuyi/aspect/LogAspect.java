package com.wuyi.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/**
 * Created by wy on 2017/3/23.
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger LOGGER= LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.wuyi.controller.IndexController.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        LOGGER.info("之前打印");
    }

    @After("execution(* com.wuyi.controller.IndexController.*(..))")
    public void afterMethod(JoinPoint joinPoint){
        LOGGER.info("之后打印");

    }

}
