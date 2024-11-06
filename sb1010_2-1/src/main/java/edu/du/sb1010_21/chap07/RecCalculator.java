package edu.du.sb1010_21.chap07;

public class RecCalculator implements Calculator {


    @Override
    public long factorial(long num) {
        if(num == 1)
            return 1;
        else
            return num * factorial(num - 1);
    }
}
