package shop.flower.framework.adapters.output;

import shop.flower.application.ports.output.ItemsOutputPort;
import shop.flower.domain.Bundle;
import shop.flower.domain.Item;
import shop.flower.domain.Money;

import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;

public enum ItemsOutputStaticAdapter implements ItemsOutputPort {
  INSTANCE;

  private final Map<String, Item> items;
  private final Map<String, Set<Bundle>> bundles;

  ItemsOutputStaticAdapter() {
    this.items =
        Map.ofEntries(
            entry("R12", new Item("R12", "Roses")),
            entry("L09", new Item("L09", "Lilies")),
            entry("T58", new Item("T58", "Tulips")));

    this.bundles =
        Map.ofEntries(
            entry(
                "R12",
                Set.of(new Bundle(5, Money.parse("$6.99")), new Bundle(10, Money.parse("$12.99")))),
            entry(
                "L09",
                Set.of(
                    new Bundle(3, Money.parse("$9.95")),
                    new Bundle(6, Money.parse("$16.95")),
                    new Bundle(9, Money.parse("$24.95")))),
            entry(
                "T58",
                Set.of(
                    new Bundle(3, Money.parse("$5.95")),
                    new Bundle(5, Money.parse("$9.95")),
                    new Bundle(9, Money.parse("$16.99")))));
  }

  public static ItemsOutputStaticAdapter getInstance() {
    return INSTANCE;
  }

  @Override
  public Item fetchItemByCode(String code) {
    return items.get(code);
  }

  @Override
  public Set<Bundle> listBundlesByItemCode(String code) {
    return bundles.get(code);
  }
}
