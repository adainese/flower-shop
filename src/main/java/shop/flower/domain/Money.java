package shop.flower.domain;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

/**
 * This class is here to keep thing simple, but it shouldn't be expanded without considering JSR-354
 * Java Money first
 */
public record Money(BigDecimal value) {

  public static final Money ZERO = new Money(BigDecimal.ZERO);
  public static final Money ONE = new Money(BigDecimal.ONE);

  private static final String unit = "$";
  private static final int MAX_SCALE = 2;

  public Money {
    if(value.scale() > MAX_SCALE) {
      throw new IllegalArgumentException("Max decimal digits: " + MAX_SCALE);
    }
    value = value.setScale(MAX_SCALE);
  }

  public static Money parse(String text) {
    return new Money(new BigDecimal(requireNonNull(text).replace(unit, "")));
  }

  @Override public String toString() {
    return unit + value.toPlainString();
  }

  public Money add(Money augend) {
    return new Money(this.value().add(augend.value()));
  }

  public Money multiply(long multiplicand) {
    return new Money(this.value().multiply(new BigDecimal(multiplicand)));
  }
}
