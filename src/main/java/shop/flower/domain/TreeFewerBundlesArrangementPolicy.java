package shop.flower.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Comparator.comparingInt;
import static java.util.Objects.requireNonNull;

public class TreeFewerBundlesArrangementPolicy implements ArrangementPolicy {

  @Override
  public List<Bundle> arrange(int total, Set<Bundle> availableBundles) {
    requireNonNull(availableBundles, "availableBundles can't be null");

    var orderedBundles =
        availableBundles.stream().sorted(comparingInt(Bundle::size).reversed()).toList();

    var bundles = new DFSListWalker(orderedBundles).arrange(total);
    if (bundles == null) {
      throw new RuntimeException("Cannot fit order");
    }
    return bundles;
  }

  private record DFSListWalker(List<Bundle> availableBundles) {

    public List<Bundle> arrange(int total) {
      return availableBundles.stream()
          .map(bundle -> inner(bundle, total))
          .filter(Objects::nonNull)
          .findFirst()
          .orElse(null);
    }

    private List<Bundle> inner(Bundle bundle, int remain) {
      if (remain == 0) {
        return new ArrayList<>();
      } else if (remain >= bundle.size()) {
        for (Bundle children : availableBundles) {
          if (bundle.size() >= children.size()) {
            var result = inner(children, remain - bundle.size());
            if (result != null) {
              result.add(0, bundle);
              return result;
            }
          }
        }
      }

      return null;
    }
  }
}
