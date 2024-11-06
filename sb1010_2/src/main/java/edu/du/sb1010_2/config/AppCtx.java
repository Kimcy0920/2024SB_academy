package edu.du.sb1010_2.config;

import edu.du.sb1010_2.aspect.ExeTimeAspect;
import edu.du.sb1010_2.chap07.Calculator;
import edu.du.sb1010_2.chap07.ImpeCalculator;
import edu.du.sb1010_2.chap07.RecCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true) // (proxyTargetClass = true) 인터페이스가 아닌 클래스를 상속받아 프록시를 생성하게 함
public class AppCtx {

    @Bean
    public ExeTimeAspect exeTimeAspect() {
        return new ExeTimeAspect();
    }

    @Bean
    public Calculator calculator() {
        return new RecCalculator();
//        return new ImpeCalculator();
    }
}
