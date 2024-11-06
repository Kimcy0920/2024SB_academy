package edu.du.sb1031.event2;

import edu.du.sb1031.order.Order;
import edu.du.sb1031.shipment.Shipment;
import edu.du.sb1031.shipment.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class OrderShipmentEventListener {

    private final ShipmentRepository shipmentRepository;

    @EventListener
    public void handleOrderShipmentEvent(OrderShipmentEvent osEvent) {
        Order order = osEvent.getOrder();
        log.info("Handling shipment event for Order: id={}, productName={}, quantity={}, price={}",
                order.getId(), order.getProductName(), order.getQuantity(), order.getPrice());
        Shipment shipment = Shipment.builder()
                .orderId(order.getId())
                .price(order.getPrice())
                .productName(order.getProductName())
                .quantity(order.getQuantity())
                .build();
        shipmentRepository.save(shipment);
    }
}

