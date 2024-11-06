package edu.du.sb1010_2;

import edu.du.sb1010_2.aop2.Greet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 컨테이너 역할, 메인을 컨테이너로. 아래 코드 생략됨
// AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
public class Sb10102Application {

    @Autowired
    Greet greet;

    private void execute() {
        greet.greeting();
    }

    public static void main(String[] args) {
        SpringApplication.run(Sb10102Application.class, args).getBean(Sb10102Application.class).execute();
    }

}
