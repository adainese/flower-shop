package shop.flower.domain;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Order {

  private final OrderId id;
  private List<OrderItem> items;

  private Order(OrderId id) {
    this.id = id;
  }

  public static Order create(OrderId id, List<OrderItem> items) {
    var order = new Order(id);
    order.items = mergeQuantities(items.stream());
    return order;
  }

  private static List<OrderItem> mergeQuantities(Stream<OrderItem> stream) {
    return stream
        .collect(toMap(OrderItem::item, OrderItem::quantity, Integer::sum))
        .entrySet()
        .stream()
        .map(entry -> new OrderItem(entry.getKey(), entry.getValue()))
        .toList();
  }

  public OrderId getId() {
    return id;
  }

  public List<OrderItem> getItems() {
    return items;
  }

  public Order updateItems(List<OrderItem> items) {
    this.items = mergeQuantities(Stream.concat(this.items.stream(), items.stream()));
    return this;
  }
}
