package shop.flower.application.usecases;

import shop.flower.domain.OrderId;

import java.util.List;

public interface OrderCreateUseCase {

  OrderId createOrder(List<OrderLine> items);

  record OrderLine(String itemCode, int quantity) {
    static OrderLine of(String itemCode, int quantity) {
      return new OrderLine(itemCode, quantity);
    }
  }
}
