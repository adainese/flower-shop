package shop.flower;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

public class FewerBundlesArrangementPolicy implements ArrangementPolicy {

  List<Bundle> arrange(int total, Set<Bundle> availableBundles) {

    var orderedBundles =
        availableBundles.stream()
            .sorted(comparingInt(Bundle::size).reversed())
            .collect(Collectors.toCollection(ArrayList::new));

    List<Bundle> result;

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
