package edu.du.sb1010_2.main;

import edu.du.sb1010_2.chap07.Calculator;
import edu.du.sb1010_2.chap07.RecCalculator;
import edu.du.sb1010_2.config.AppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspect {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);

//        Calculator cal= ctx.getBean("calculator", Calculator.class);
        RecCalculator cal= ctx.getBean("calculator", RecCalculator.class);
        System.out.println("cal.factorial(5) = " + cal.factorial(5));
        System.out.println(cal.getClass().getName());
        ctx.close();
    }
}
