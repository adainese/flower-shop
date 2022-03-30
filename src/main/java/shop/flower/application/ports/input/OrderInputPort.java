package shop.flower.application.ports.input;

import org.jetbrains.annotations.NotNull;
import shop.flower.application.ports.output.ItemsOutputPort;
import shop.flower.application.ports.output.OrdersOutputPort;
import shop.flower.application.usecases.OrderArrangeBundlesUseCase;
import shop.flower.application.usecases.OrderCreateUseCase;
import shop.flower.domain.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class OrderInputPort implements OrderCreateUseCase, OrderArrangeBundlesUseCase {

  private final OrdersOutputPort ordersOutputPort;
  private final ItemsOutputPort itemsOutputPort;

  public OrderInputPort(OrdersOutputPort ordersOutputPort, ItemsOutputPort itemsOutputPort) {
    this.ordersOutputPort = ordersOutputPort;
    this.itemsOutputPort = itemsOutputPort;
  }

  @Override
  public OrderId createOrder(List<OrderLine> items) {
    var order = Order.create(OrderId.newId(), orderItems(items));
    ordersOutputPort.persistOrder(order);
    return order.getId();
  }

  private List<OrderItem> orderItems(List<OrderLine> items) {
    return items.stream().map(this::toOrderItem).toList();
  }

  private OrderItem toOrderItem(OrderLine line) {
    return new OrderItem(itemsOutputPort.fetchItemByCode(line.itemCode()), line.quantity());
  }

  @Override
  public List<BundlesArrangement> arrangeOrderBundles(OrderId orderId) {
    var order = ordersOutputPort.fetchOrderById(orderId);
    var bundles = getBundlesForAllItemCodes(order);
    return BundlesService.arrangeOrderBundles(order, bundles);
  }

  @NotNull
  private Map<Item, Set<Bundle>> getBundlesForAllItemCodes(Order order) {
    return order.getItems().stream()
        .map(OrderItem::item)
        .collect(toMap(identity(), item -> itemsOutputPort.listBundlesByItemCode(item.code())));
  }
}
