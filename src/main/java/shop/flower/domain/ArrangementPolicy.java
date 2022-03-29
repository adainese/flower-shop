package shop.flower.domain;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public interface ArrangementPolicy {
  @NotNull
  List<Bundle> arrange(int total, @NotNull Set<Bundle> availableBundles);
}
