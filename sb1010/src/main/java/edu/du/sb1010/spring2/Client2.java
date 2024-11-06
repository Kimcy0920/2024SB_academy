package edu.du.sb1010.spring2;

public class Client2 { // implements없이 AppCtx2에 Bean등록, 메서드명을 지정해 사용가능함.
    private String host;

    public void setHost(String host) {
        this.host = host;
    }

    public void send() {
        System.out.println("Client2.send() to " + host);
    }

    public void connect() {
        System.out.println("Client2.connect() 실행");
    }

    public void close() {
        System.out.println("Client2.close() 실행");
    }
}
