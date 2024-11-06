package edu.du.sb1010_2.main;

import edu.du.sb1010_2.chap07.Calculator;
import edu.du.sb1010_2.chap07.ImpeCalculator;
import edu.du.sb1010_2.chap07.RecCalculator;

public class TestMain {

    public static void main(String[] args) {
        Calculator cal1 = new ImpeCalculator();
        long start1 = System.nanoTime();
        long fac1 = cal1.factorial(5);
        long end1 = System.nanoTime();
        System.out.printf("ImpeCalculator.factorial(5) 실행시간 = %d\n", (end1 - start1));

        Calculator cal2 = new RecCalculator();
        long start2 = System.nanoTime();
        long fac2 = cal2.factorial(5);
        long end2 = System.nanoTime();
        System.out.printf("RecCalculator.factorial(5) 실행시간 = %d\n", (end2 - start2));
    }
}
