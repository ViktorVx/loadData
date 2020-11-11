package org.pva.loadData.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Configuration
public class LogServicesAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* org.pva.loadData.service.*.*(..)) && !execution(* org.pva.loadData.service.*.set*(..))")
    public void beforeServices(JoinPoint joinPoint) {
        logger.info(String.format("Service starts. Signature: %s, Args: %s", joinPoint.getSignature(),
                Arrays.stream(joinPoint.getArgs())
                        .map(Object::toString)
                        .collect(Collectors.joining( "," ))
                )
        );
    }

    @AfterReturning(value = "execution(* org.pva.loadData.service.*.*(..)) && !execution(* org.pva.loadData.service.*.set*(..))",
        returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        logger.info(String.format("Service success. Signature: %s, Args: %s, Result: %s", joinPoint.getSignature(),
                Arrays.stream(joinPoint.getArgs())
                        .map(Object::toString)
                        .collect(Collectors.joining( "," )),
                result == null ? "Upload success" : "Download success"
                )
        );
    }

    @AfterThrowing(value = "execution(* org.pva.loadData.service.*.*(..)) && !execution(* org.pva.loadData.service.*.set*(..))",
            throwing = "throwing")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwing) {
        logger.info(String.format("Service fail. Signature: %s, Args: %s, Throwing: %s", joinPoint.getSignature(),
                Arrays.stream(joinPoint.getArgs())
                        .map(Object::toString)
                        .collect(Collectors.joining( "," )),
                throwing
                )
        );
    }
}
