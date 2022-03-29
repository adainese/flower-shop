package shop.flower.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public record BundlesArrangement(Map<Bundle, Integer> arrangement) {

  public static BundlesArrangement fromBundles(@NotNull Collection<Bundle> bundles) {
    requireNonNull(bundles, "bundles can not be null");
    HashMap<Bundle, Integer> arrangement = new HashMap<>();
    bundles.forEach(bundle -> arrangement.merge(bundle, 1, Integer::sum));
    return new BundlesArrangement(arrangement);
  }

  public int getTotalQuantity() {
    return arrangement.entrySet()
        .stream()
        .mapToInt(entry -> entry.getValue() * entry.getKey().size())
        .sum();
  }

  public Money getTotalCost() {
    return arrangement.entrySet()
        .stream()
        .map(entry -> entry.getKey().cost().multiply(entry.getValue().longValue()))
        .reduce(Money.ZERO, Money::add);
  }

}
