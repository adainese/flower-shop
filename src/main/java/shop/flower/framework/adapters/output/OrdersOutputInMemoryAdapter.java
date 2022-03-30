package shop.flower.framework.adapters.output;

import shop.flower.application.ports.output.OrdersOutputPort;
import shop.flower.domain.Order;
import shop.flower.domain.OrderId;

import java.util.HashMap;
import java.util.Map;

public enum OrdersOutputInMemoryAdapter implements OrdersOutputPort {
  INSTANCE;

  private final Map<OrderId, Order> orders;

  OrdersOutputInMemoryAdapter() {
    orders = new HashMap<>();
  }

  public static OrdersOutputInMemoryAdapter getInstance() {
    return INSTANCE;
  }

  @Override
  public Order fetchOrderById(OrderId orderId) {
    return orders.get(orderId);
  }

  @Override
  public void persistOrder(Order order) {
    orders.put(order.getId(), order);
  }
}
