package edu.du.sb1010_2.chap07;

public class RecCalculator implements Calculator {

    @Override
    public long factorial(long num) {
//        long start = System.nanoTime();
        try {
            if(num == 0)
                return 1;
            else
                return num * factorial(num - 1);
        } finally {
//            long end = System.nanoTime();
//            System.out.printf("RecCalculator.factorial(%d) 실행시간 = %d\n", num, (end-start));
        }
    }
}
