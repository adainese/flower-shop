package shop.flower.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static shop.flower.domain.BundlesArrangement.fromBundles;

public class BundlesService {

  public static List<BundlesArrangement> arrangeOrderBundles(
      Order order, Map<Item, Set<Bundle>> bundles) {

    ArrangementPolicy policy = new FewerBundlesArrangementPolicy();
    return order.getItems().stream()
        .map(orderItem -> fromBundles(orderItem.item(), getBundles(policy, orderItem, bundles)))
        .toList();
  }

  private static List<Bundle> getBundles(
      ArrangementPolicy policy, OrderItem item, Map<Item, Set<Bundle>> bundles) {
    return policy.arrange(item.quantity(), bundles.getOrDefault(item.item(), Set.of()));
  }
}
