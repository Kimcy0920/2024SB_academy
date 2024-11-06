package edu.du.sb1031.event2;

import edu.du.sb1031.order.Order;
import org.springframework.context.ApplicationEvent;

// 클래스에서 클래스로 전송해주는 이벤트
public class OrderShipmentEvent extends ApplicationEvent {

    private Order order;

    // 생성자
    public OrderShipmentEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}

