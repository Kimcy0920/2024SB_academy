package edu.du.sb1010_2.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Order(2)
public class CacheAspect {

    private final Map<Long, Object> cache = new HashMap<>();

    @Pointcut("execution(public * *..chap07..*(long))")
    public void cacheTarget() {
    }

    @Around("cacheTarget()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        Long num = (Long) joinPoint.getArgs()[0]; // 첫 번째 인자를 long 타입으로 구함

        if(cache.containsKey(num)) { // 캐시에 있는지 확인, 존재하면 키에 해당하는 값을 구해 리턴함
            System.out.printf("CacheAspect: Cache 구함[%d]\n", num);
            return cache.get(num);
        }

        // 1. 코드 실행 순서
        Object result = joinPoint.proceed(); // 캐시에 없으면 프록시 대상객체를 실행해 추가함

        // 4. 코드 실행 순서
        cache.put(num, result); // 대상객체를 실행한 결과를 캐시에 추가함
        System.out.printf("CacheAspect: Cache 추가[%d]\n", num);
        return result; // 결과값 리턴
    }
}
