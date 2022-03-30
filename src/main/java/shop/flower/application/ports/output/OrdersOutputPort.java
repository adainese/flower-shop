package shop.flower.application.ports.output;

import shop.flower.domain.Order;
import shop.flower.domain.OrderId;

public interface OrdersOutputPort {

  Order fetchOrderById(OrderId orderId);

  void persistOrder(Order order);

}
