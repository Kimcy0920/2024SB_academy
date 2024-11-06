package edu.du.sb1010_2.main;

import edu.du.sb1010_2.chap07.Calculator;
import edu.du.sb1010_2.config.AppCtxWithCache;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspectWithCache {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtxWithCache.class);

        Calculator cal = ctx.getBean("calculator", Calculator.class);
        cal.factorial(7); // 프록시 대상객체로 추가
        cal.factorial(7); // 캐시에 존재해 키값으로 찾아 호출
        cal.factorial(5);
        cal.factorial(5);
        ctx.close();

        /*

        코드 실행 순서: CacheAspect -> ExeTimeAspect -> 실제 대상객체
        RecCalculator.factorial([7]) 실행 시간 : 9100 ns <- ExeTimeAspect 시간출력
        CacheAspect: Cache 추가[7]
        CacheAspect: Cache 구함[7]
        RecCalculator.factorial([5]) 실행 시간 : 8500 ns
        CacheAspect: Cache 추가[5]
        CacheAspect: Cache 구함[5]

        @Order 코드 실행 순서: ExeTimeAspect -> CacheAspect -> 실제 대상객체
        CacheAspect: Cache 추가[7]
        RecCalculator.factorial([7]) 실행 시간 : 4235499 ns
        CacheAspect: Cache 구함[7]
        RecCalculator.factorial([7]) 실행 시간 : 35100 ns
        CacheAspect: Cache 추가[5]
        RecCalculator.factorial([5]) 실행 시간 : 43400 ns
        CacheAspect: Cache 구함[5]
        RecCalculator.factorial([5]) 실행 시간 : 29000 ns
         */
    }
}
