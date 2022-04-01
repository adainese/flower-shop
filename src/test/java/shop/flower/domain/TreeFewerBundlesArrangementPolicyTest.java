package shop.flower.domain;

class TreeFewerBundlesArrangementPolicyTest extends AbstractFewerBundlesArrangementPolicy {

  private ArrangementPolicy dut = new TreeFewerBundlesArrangementPolicy();

  @Override
  protected ArrangementPolicy dut() {
    return dut;
  }
}
