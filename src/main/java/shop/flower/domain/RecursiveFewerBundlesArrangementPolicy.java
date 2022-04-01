package shop.flower.domain;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Comparator.comparingInt;

public class RecursiveFewerBundlesArrangementPolicy implements ArrangementPolicy {

  @Override
  public @NotNull List<Bundle> arrange(int total, @NotNull Set<Bundle> availableBundles) {

    var orderedBundles =
        availableBundles.stream()
            .sorted(comparingInt(Bundle::size).reversed())
            .toArray(Bundle[]::new);

    return new RecursiveWorker(total, orderedBundles).arrange();
  }

  private static class RecursiveWorker {
    private final int total;
    private final Bundle[] orderedBundleChoices;
    private final int listSize;

    public RecursiveWorker(int total, Bundle[] orderedBundleChoices) {
      this.total = total;
      this.orderedBundleChoices = orderedBundleChoices;
      this.listSize = orderedBundleChoices.length;
    }

    public List<Bundle> arrange() {
      return inner(0, 0, total, new ArrayList<>());
    }

    private List<Bundle> inner(
        int startIndex, int currentIndex, int remain, List<Bundle> partialResult) {
      if (remain == 0) {
        return partialResult;
      }
      if (currentIndex == listSize) {
        startIndex++;
        currentIndex = startIndex;
        remain = total;
        partialResult.clear();
      }
      if (startIndex == listSize) {
        throw new RuntimeException("Cannot fit order");
      }

      var bundle = orderedBundleChoices[currentIndex];
      if (bundle.size() > remain) {
        currentIndex++;
      } else {
        partialResult.add(bundle);
        remain -= bundle.size();
      }
      return inner(startIndex, currentIndex, remain, partialResult);
    }
  }
}
