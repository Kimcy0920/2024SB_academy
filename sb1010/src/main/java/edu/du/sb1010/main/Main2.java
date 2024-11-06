package edu.du.sb1010.main;

import edu.du.sb1010.config.AppCtx2;
import edu.du.sb1010.spring2.Client2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Main2 {

    public static void main(String[] args) {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx2.class);

        Client2 client = ctx.getBean(Client2.class);
        client.send();
        ctx.close();
    }

/* 실행결과
Client.afterPropertiesSet() 실행
Client2.connect() 실행
Client2.send() to host
Client2.close() 실행
Client.destroy() 실행
사용되지 않아도 Client도 실행, 종료됨
*/
}
