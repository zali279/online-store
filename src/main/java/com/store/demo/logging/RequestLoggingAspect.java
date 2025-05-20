package com.store.demo.logging;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class RequestLoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {}

    @Before("restControllerMethods()")
    public void logRequest(JoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes attrs) {
            HttpServletRequest request = attrs.getRequest();

            String method = request.getMethod();
            String uri = request.getRequestURI();
            String controller = joinPoint.getSignature().getDeclaringTypeName();
            String action = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();

            log.info("➡️ [{}] {} - {}.{} called with args: {}", method, uri, controller, action, Arrays.toString(args));
        }
    }

    @AfterReturning(pointcut = "restControllerMethods()", returning = "result")
    public void logResponse(JoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        log.info("✅ {}.{} responded with: {}", signature.getDeclaringTypeName(), signature.getName(), result);
    }

    @AfterThrowing(pointcut = "restControllerMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.error("❌ Exception in {}.{}: {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), ex.getMessage(), ex);
    }
}
