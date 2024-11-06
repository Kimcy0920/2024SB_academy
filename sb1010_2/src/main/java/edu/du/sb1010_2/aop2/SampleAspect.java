package edu.du.sb1010_2.aop2;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Aspect
@Component
public class SampleAspect {
    @Around("execution(* *..aop2.*Greet.*(..))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        // 시작 부분 표시
        System.out.println("===== Around Advice =====");
        System.out.println("▼▼▼ 처리전 ▼▼▼");
        Object result = joinPoint.proceed();
        System.out.println("▲▲▲ 처리후 ▲▲▲");

        return result;
    }

    // Before: Greet 실행 전에 아래 메서드 호출

    @Before("execution(* *..aop2.*Greet.*(..))")
    public void beforeAdvice(JoinPoint joinPoint){
        // 시작 부분 표시
        System.out.println("===== Before Advice =====");

        // 날짜를 출력
        System.out.println(new SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date()));

        // 메서드명 출력
        System.out.println(String.format("메서드:%s", joinPoint.getSignature().getName()));
    }

    // After: Greet 실행 후에 아래 메서드 호출
    @After("execution(* *..aop2.*Greet.*(..))")
    public void afterAdvice(JoinPoint joinPoint){
        // 시작 부분 표시
        System.out.println("===== After Advice =====");

        // 날짜를 출력
        System.out.println(new SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date()));

        // 메서드명 출력
        System.out.println(String.format("메서드:%s", joinPoint.getSignature().getName()));
    }
}
