package shop.flower.application.ports.output;

import shop.flower.domain.Bundle;
import shop.flower.domain.Item;

import java.util.Set;

public interface ItemsOutputPort {

  Item fetchItemByCode(String code);

  Set<Bundle> listBundlesByItemCode(String code);
}
