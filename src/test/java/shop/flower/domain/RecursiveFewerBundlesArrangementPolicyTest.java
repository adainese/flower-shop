package shop.flower.domain;

class RecursiveFewerBundlesArrangementPolicyTest extends AbstractFewerBundlesArrangementPolicy {

  private ArrangementPolicy dut = new RecursiveFewerBundlesArrangementPolicy();

  @Override
  protected ArrangementPolicy dut() {
    return dut;
  }
}
