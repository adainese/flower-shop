package shop.flower.domain;

import java.util.UUID;

public record OrderId(UUID id) {

  public static OrderId withId(String id) {
    return new OrderId(UUID.fromString(id));
  }

  public static OrderId newId() {
    return new OrderId(UUID.randomUUID());
  }
}
