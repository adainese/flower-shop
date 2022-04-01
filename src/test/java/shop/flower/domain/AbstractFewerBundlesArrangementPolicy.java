package shop.flower.domain;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

abstract class AbstractFewerBundlesArrangementPolicy {

  protected abstract ArrangementPolicy dut();

  private static final Map<String, Set<Bundle>> BUNDLES =
      Map.ofEntries(
          entry("R12", Set.of(new Bundle(5), new Bundle(10))),
          entry("L09", Set.of(new Bundle(3), new Bundle(6), new Bundle(9))),
          entry("T58", Set.of(new Bundle(3), new Bundle(5), new Bundle(9))));

  @Test
  void zeroToEmpty() {
    assertThat(dut().arrange(0, BUNDLES.get("R12"))).isEmpty();
  }

  @Test
  void fiveRosesOneBundle() {
    assertThat(dut().arrange(5, BUNDLES.get("R12"))).hasSize(1).containsExactly(new Bundle(5));
  }

  @Test
  void tenRosesOneBundle() {
    assertThat(dut().arrange(10, BUNDLES.get("R12"))).hasSize(1).containsExactly(new Bundle(10));
  }

  @Test
  void fifteenRosesTwoBundles() {
    assertThat(dut().arrange(15, BUNDLES.get("R12")))
        .hasSize(2)
        .containsExactly(new Bundle(10), new Bundle(5));
  }

  /**
   * This test could theoretically pass also with 2 x 6 bundles but 9 + 3 is selected because the
   * arranging algorithm use a DFS. This must be considered if new requirements come along
   */
  @Test
  void twelveLiliesTwoBundles() {
    assertThat(dut().arrange(12, BUNDLES.get("L09")))
        .hasSize(2)
        .containsExactly(new Bundle(9), new Bundle(3));
  }

  @Test
  void tenTulipsTwoBundles() {
    assertThat(dut().arrange(10, BUNDLES.get("T58")))
        .hasSize(2)
        .containsExactly(new Bundle(5), new Bundle(5));
  }

  /**
   * This test is important to check if the arrangement algorithm can retrace its steps away from a
   * dead branch. i.e. the algorithm would initially add a 5-sized bundle and then a second because
   * it still fits, but then it'll become stuck and unable to find a 1-sized bundle. So it must step
   * back once and try with the nearest smaller bundle, and so on.
   */
  @Test
  void elevenTulipsThreeBundles() {
    assertThat(dut().arrange(11, BUNDLES.get("T58")))
        .hasSize(3)
        .containsExactly(new Bundle(5), new Bundle(3), new Bundle(3));
  }

  @Test
  void thirtyEightTulipsSixBundles() {
    assertThat(dut().arrange(38, BUNDLES.get("T58")))
        .hasSize(6)
        .containsExactly(
            new Bundle(9),
            new Bundle(9),
            new Bundle(9),
            new Bundle(5),
            new Bundle(3),
            new Bundle(3));
  }
}
