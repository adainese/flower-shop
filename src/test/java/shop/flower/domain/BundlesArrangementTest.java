package shop.flower.domain;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BundlesArrangementTest {

  private static final Collection<Bundle> BUNDLES =
      List.of(
          new Bundle(5, Money.ONE),
          new Bundle(10, Money.ONE.multiply(9L)),
          new Bundle(10, Money.ONE.multiply(9L)));
  private static BundlesArrangement target =
      BundlesArrangement.fromBundles(new Item("00", "dummy"), BUNDLES);

  @Test
  void getTotalQuantity() {
    assertThat(target.getTotalQuantity()).isEqualTo(25);
  }

  @Test
  void getTotalCost() {
    assertThat(target.getTotalCost()).isEqualTo(Money.ONE.multiply(19L));
  }

  @Test
  void arrangement() {
    assertThat(target.arrangement())
        .containsExactlyInAnyOrderEntriesOf(
            Map.of(new Bundle(5, Money.ONE), 1, new Bundle(10, Money.ONE.multiply(9L)), 2));
  }
}
