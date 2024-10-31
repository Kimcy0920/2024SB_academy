package edu.du.sb1031.event2;

import edu.du.sb1031.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderShipmentEventPublisher {

    @Autowired
    private ApplicationEventPublisher aePublisher;

    public void doStuffAndPublishAnEvent(final Order order) {
        System.out.println(" ------orderShipmentEvent------ ");
        OrderShipmentEvent osEvent = new OrderShipmentEvent(this, order);
        aePublisher.publishEvent(osEvent);
    }

}

