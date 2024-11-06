package edu.du.sb1010_21;

import edu.du.sb1010_21.chap07.Calculator;
import edu.du.sb1010_21.chap07.ExeTimeCalculator;
import edu.du.sb1010_21.chap07.ImpCalculator;
import edu.du.sb1010_21.chap07.RecCalculator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Sb101021Application {

    public static void main(String[] args) {
        SpringApplication.run(Sb101021Application.class, args);

//        Calculator impCal = new ImpCalculator();
//        System.out.println(impCal.factorial(5));
//
//        Calculator recCal = new RecCalculator();
//        System.out.println(recCal.factorial(5));
        ImpCalculator impCal = new ImpCalculator();
        ExeTimeCalculator exeCal = new ExeTimeCalculator(impCal);
        System.out.println(exeCal.factorial(12));
    }

}
