package edu.du.sb1010.spring2;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Client implements InitializingBean, DisposableBean {
// implements를 사용해 메서드명을 그대로 사용.

    private String host;

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Client.destroy() 실행");
    }

    public void send() {
        System.out.println("Client.send() to " + host);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Client.afterPropertiesSet() 실행");
    }
}
