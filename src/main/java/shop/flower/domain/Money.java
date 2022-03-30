package shop.flower.domain;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

import static java.math.RoundingMode.UNNECESSARY;
import static java.util.Objects.requireNonNull;

/**
 * This class is here to keep thing simple, but it shouldn't be expanded without considering JSR-354
 * Java Money first
 */
public record Money(@NotNull BigDecimal value) {

  public static final Money ZERO = new Money(BigDecimal.ZERO);
  public static final Money ONE = new Money(BigDecimal.ONE);

  private static final String unit = "$";
  private static final int MAX_SCALE = 2;

  public Money(@NotNull BigDecimal value) {
    requireNonNull(value, "Money can't have null value");
    if(value.scale() > MAX_SCALE) {
      throw new IllegalArgumentException("Max decimal digits: " + MAX_SCALE);
    }
   this.value = value.setScale(MAX_SCALE, UNNECESSARY);
  }

  public static Money parse(@NotNull String text) {
    requireNonNull(text, "text can not be null");
    return new Money(new BigDecimal(text.replace(unit, "")));
  }

  @Override public String toString() {
    return unit + value.toPlainString();
  }

  public Money add(@NotNull Money augend) {
    requireNonNull(augend, "augend can not be null");
    return new Money(this.value().add(augend.value()));
  }

  public Money multiply(long multiplicand) {
    return new Money(this.value().multiply(new BigDecimal(multiplicand)));
  }
}
