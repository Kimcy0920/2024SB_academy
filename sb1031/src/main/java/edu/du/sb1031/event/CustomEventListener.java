package edu.du.sb1031.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@Slf4j
public class CustomEventListener {

    @EventListener // Configuration - eventListener가 Bean처럼 등록된 것.
    public void handleCustomEvent(CustomEvent customEvent) {
        log.info("Handle context started event. message : {}", customEvent.getMessage());
    }
}
