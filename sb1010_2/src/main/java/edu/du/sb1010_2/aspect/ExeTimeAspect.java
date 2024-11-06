package edu.du.sb1010_2.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

@Aspect // Advice 정리함, 클래스 앞에 붙임.
@Order(1)
public class ExeTimeAspect {

    // 메소드 앞에 붙임.
//    @Pointcut("execution(public * *..chap07..*(..))") // 조건을 정의함, 괄호 안 해당하는 곳에 있으면 조건
    private void publicTarget() {
    }

    /*
    "execution(public * *..chap07..*(..))" 경로가 제대로 되었을 때 실행시간 계산과 프록시 정보가 나옴
    ImpeCalculator.factorial([5]) 실행 시간 : 15200 ns
    cal.factorial(5) = 120
    jdk.proxy2.$Proxy18

    "execution(public * chap07..*(..))"    경로가 잘못된 경우 코드는 실행되지만 프록시가 실행되지 않아 실행시간 계산과 프록시 정보가 나오지 않음.
    cal.factorial(5) = 120
    edu.du.sb1010_2.chap07.ImpeCalculator
     */

    // 메소드 앞에 붙임.
//    @Around("publicTarget()") // Advice 종류 중 하나, Pointcut 조건대상
    @Around("execution(public * *..chap07..*(..))") // Pointcut 거치지 않고, Around 바로 조건입력
    public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
                       // ProceedingJoinPoint joinPoint
        long start = System.nanoTime();
        try {
            // 2. 코드 실행 순서
            Object result = joinPoint.proceed(); // Object 타입을 사용해 어떤 타입이 와도 상관이 없음
                         // joinPoint.proceed()
            return result;
        } finally {
            // 3. 코드 실행 순서
            long finish = System.nanoTime();
            Signature sig = joinPoint.getSignature();
            System.out.printf("%s.%s(%s) 실행 시간 : %d ns\n",
                    joinPoint.getTarget().getClass().getSimpleName(),
                    sig.getName(), Arrays.toString(joinPoint.getArgs()),
                    (finish - start));
        }
    }
}
