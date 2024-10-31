package edu.du.sb1031.event;

import org.springframework.context.ApplicationEvent;

// 클래스에서 클래스로 전송해주는 이벤트
public class CustomEvent extends ApplicationEvent {
    private String message;

    // 생성자
    public CustomEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
