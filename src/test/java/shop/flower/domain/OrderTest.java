package shop.flower.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class OrderTest {

  private static final Item ITEM1 = new Item("I01", "Item 1");
  private static final Item ITEM2 = new Item("I02", "Item 2");
  private static final Item ITEM3 = new Item("I03", "Item 3");

  @Test
  void createOrderMergeItems() {

    List<OrderItem> items =
        List.of(new OrderItem(ITEM1, 5), new OrderItem(ITEM2, 4), new OrderItem(ITEM1, 3));

    var actual = Order.create(OrderId.newId(), items).getItems();

    assertThat(actual)
        .hasSize(2)
        .containsExactlyInAnyOrder(new OrderItem(ITEM1, 8), new OrderItem(ITEM2, 4));
  }

  @Test
  void addItemsMergeItems() {
    List<OrderItem> items =
        List.of(new OrderItem(ITEM1, 5), new OrderItem(ITEM2, 4), new OrderItem(ITEM1, 3));

    var actual =
        Order.create(OrderId.newId(), items)
            .updateItems(List.of(new OrderItem(ITEM1, 5), new OrderItem(ITEM3, 5)))
            .getItems();

    assertThat(actual)
        .hasSize(3)
        .containsExactlyInAnyOrder(
            new OrderItem(ITEM1, 13), new OrderItem(ITEM2, 4), new OrderItem(ITEM3, 5));
  }

  @Test
  void addItemsCanRemoveItems() {
    List<OrderItem> items =
        List.of(new OrderItem(ITEM1, 5), new OrderItem(ITEM2, 4), new OrderItem(ITEM1, 3));

    var actual =
        Order.create(OrderId.newId(), items)
            .updateItems(List.of(new OrderItem(ITEM1, -5), new OrderItem(ITEM3, 5)))
            .getItems();

    assertThat(actual)
        .hasSize(3)
        .containsExactlyInAnyOrder(
            new OrderItem(ITEM1, 3), new OrderItem(ITEM2, 4), new OrderItem(ITEM3, 5));
  }

  @Test
  void addItemsCannotResultInNegativeItems() {
    List<OrderItem> items =
        List.of(new OrderItem(ITEM1, 5), new OrderItem(ITEM2, 4), new OrderItem(ITEM1, 3));

    var order = Order.create(OrderId.newId(), items);

    assertThatIllegalArgumentException()
        .isThrownBy(
            () -> order.updateItems(List.of(new OrderItem(ITEM1, -15), new OrderItem(ITEM3, 5))))
        .withMessageContaining(ITEM1.code());
  }

  @Test
  void itemsWithZeroQuantityAreRemoved() {
    List<OrderItem> items =
        List.of(new OrderItem(ITEM1, 5), new OrderItem(ITEM2, 4), new OrderItem(ITEM1, 3));

    var actual =
        Order.create(OrderId.newId(), items)
            .updateItems(List.of(new OrderItem(ITEM1, -8), new OrderItem(ITEM3, 5)))
            .getItems();

    assertThat(actual).hasSize(2).noneMatch(item -> item.equals(ITEM1));
  }
}
