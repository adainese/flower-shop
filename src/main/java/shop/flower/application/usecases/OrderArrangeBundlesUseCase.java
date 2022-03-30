package shop.flower.application.usecases;

import shop.flower.domain.BundlesArrangement;
import shop.flower.domain.OrderId;

import java.util.List;

public interface OrderArrangeBundlesUseCase {

  List<BundlesArrangement> arrangeOrderBundles(OrderId orderId);
}
