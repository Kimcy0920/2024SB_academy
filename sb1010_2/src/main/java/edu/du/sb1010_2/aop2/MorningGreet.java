package edu.du.sb1010_2.aop2;

import org.springframework.stereotype.Component;

// Evening과 Morning 둘 다 Greet 인터페이스를 사용해 둘 중 하나만 @Component를 붙여야됨
// 둘 다 @Component 어노테이션을 사용하면 값을 두 개 찾았다는 에러가 발생함

//@Component
public class MorningGreet implements Greet {
    @Override
    public void greeting() {
        System.out.println("------------------");
        System.out.println("좋은 아침입니다");
        System.out.println("------------------");
    }
}
