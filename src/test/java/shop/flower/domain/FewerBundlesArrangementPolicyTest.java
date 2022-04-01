package shop.flower.domain;

class FewerBundlesArrangementPolicyTest extends AbstractFewerBundlesArrangementPolicy {

  private ArrangementPolicy dut = new FewerBundlesArrangementPolicy();

  @Override protected ArrangementPolicy dut() {
    return dut;
  }
}
