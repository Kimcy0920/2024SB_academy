package edu.du.sb1010_2.chap07;

public class ExeTimeCalculator implements Calculator {
// 실행시간을 구하는 프록시 ExeTimeCalculator
// ImpeCal, RecCal은 계산 기능을 구현한 프록시 대상 객체(두 클래스에 있던 시간계산 코드를 없애 중복X)

    private Calculator delegate;

    public ExeTimeCalculator(Calculator delegate) {
        this.delegate = delegate;
    }

    @Override
    public long factorial(long num) {
        long start = System.nanoTime();
        long result = delegate.factorial(num);
        long end = System.nanoTime();
        System.out.printf("%s.factorial(%d) 실행시간 = %d\n",
                delegate.getClass().getSimpleName(), num, end - start);
        return result;
    }
}
