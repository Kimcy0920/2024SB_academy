package edu.du.sb1010_21.chap07;

public class ImpCalculator implements Calculator {

    @Override
    public long factorial(long num) {
        long start = System.nanoTime();
        long result = 1;
        for (long i = 1; i <= num; i++) {
            result *= i;
        }
        long end = System.nanoTime();
        System.out.printf("ImpCal.factorial(%d) 실행 시간: %d", num, (end-start));
        return result;
    }
}
