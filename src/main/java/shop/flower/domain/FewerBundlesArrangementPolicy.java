package shop.flower.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.Objects.requireNonNull;

public class FewerBundlesArrangementPolicy implements ArrangementPolicy {

  @Override
  public List<Bundle> arrange(int total, Set<Bundle> availableBundles) {
    requireNonNull(availableBundles, "availableBundles can't be null");

    var orderedBundles =
        availableBundles.stream()
            .sorted(comparingInt(Bundle::size).reversed())
            .collect(Collectors.toCollection(ArrayList::new));

    List<Bundle> result;

    // TODO: this seems a good candidate for using a recursive strategy.
    // Try to refactor.
    // Heap size/recursion depth should not be an issue but consider profiling or use tail
    // recursion.

    do {
      result = inner(total, orderedBundles);
      if (result != null) {
        return result;
      } else {
        orderedBundles.remove(0);
      }
    } while (!orderedBundles.isEmpty());

    throw new RuntimeException("Cannot fit order");
  }

  private List<Bundle> inner(int total, List<Bundle> orderedBundles) {

    List<Bundle> result = new ArrayList<>();
    var remain = total;

    for (Bundle bundle : orderedBundles) {
      while (bundle.size() <= remain) {
        remain -= bundle.size();
        result.add(bundle);
      }
    }

    return remain == 0 ? result : null;
  }
}
