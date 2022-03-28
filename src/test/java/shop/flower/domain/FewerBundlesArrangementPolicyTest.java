package shop.flower.domain;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

class FewerBundlesArrangementPolicyTest {

  private static final Map<String, Set<Bundle>> BUNDLES =
      Map.ofEntries(
          entry("R12", Set.of(new Bundle(5), new Bundle(10))),
          entry("L09", Set.of(new Bundle(3), new Bundle(6), new Bundle(9))),
          entry("T58", Set.of(new Bundle(3), new Bundle(5), new Bundle(9))));

  private FewerBundlesArrangementPolicy dut = new FewerBundlesArrangementPolicy();

  @Test
  void zeroToEmpty() {
    assertThat(dut.arrange(0, BUNDLES.get("R12"))).isEmpty();
  }

  @Test
  void fiveRosesOneBundle() {
    assertThat(dut.arrange(5, BUNDLES.get("R12"))).hasSize(1).containsExactly(new Bundle(5));
  }

  @Test
  void tenRosesOneBundle() {
    assertThat(dut.arrange(10, BUNDLES.get("R12"))).hasSize(1).containsExactly(new Bundle(10));
  }

  @Test
  void fifteenRosesTwoBundles() {
    assertThat(dut.arrange(15, BUNDLES.get("R12")))
        .hasSize(2)
        .containsExactly(new Bundle(10), new Bundle(5));
  }

  @Test
  void twelveLiliesTwoBundles() {
    assertThat(dut.arrange(12, BUNDLES.get("L09")))
        .hasSize(2)
        .containsExactly(new Bundle(9), new Bundle(3));
  }

  @Test
  void tenTulipsTwoBundles() {
    assertThat(dut.arrange(10, BUNDLES.get("T58")))
        .hasSize(2)
        .containsExactly(new Bundle(5), new Bundle(5));
  }
}
