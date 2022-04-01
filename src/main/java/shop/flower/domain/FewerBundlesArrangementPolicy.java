package shop.flower.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Comparator.comparingInt;
import static java.util.Objects.requireNonNull;

public class FewerBundlesArrangementPolicy implements ArrangementPolicy {

  @Override
  public List<Bundle> arrange(int total, Set<Bundle> availableBundles) {
    requireNonNull(availableBundles, "availableBundles can't be null");

    var orderedBundles =
        availableBundles.stream().sorted(comparingInt(Bundle::size).reversed()).toList();

    return new DFSListWalker(orderedBundles).arrange(total);
  }

  /**
   * This class is used to allow its parent class to be thread-safe and
   * to make it easier to swap other algorithms if needed
   */
  private record DFSListWalker(List<Bundle> availableBundles) {

    sealed interface Result permits Result.Success, Result.Failure {
      boolean isSuccess();
      record Success(List<Bundle> bundles) implements Result {
        @Override public boolean isSuccess() {
          return true;
        }
      };
      record Failure() implements Result {
        @Override public boolean isSuccess() {
          return false;
        }
      };
    }

    public List<Bundle> arrange(int total) {
      return availableBundles.stream()
          .map(bundle -> inner(bundle, total))
          .filter(Result::isSuccess)
          .map(result -> ((Result.Success)result).bundles)
          .findFirst()
          .orElseThrow(() -> new RuntimeException("Cannot fit order"));
    }

    private Result inner(Bundle bundle, int remain) {
      if (remain == 0) {
        return new Result.Success(new ArrayList<>());
      } else if (remain >= bundle.size()) {
        for (Bundle children : availableBundles) {
          if (bundle.size() >= children.size()) {
            var result = inner(children, remain - bundle.size());
            if (result instanceof Result.Success success) {
              success.bundles.add(0, bundle);
              return success;
            }
          }
        }
      }

      return new Result.Failure();
    }
  }
}
