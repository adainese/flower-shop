package shop.flower.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MoneyTest {

  @Test
  void moreThanMaxScaleThrows() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> new Money(BigDecimal.valueOf(1234L, 3)))
        .withMessage("Max decimal digits: 2");
  }

  @Test
  void parse() {
    assertThat(Money.parse("$12.34"))
        .extracting(Money::value)
        .isEqualTo(BigDecimal.valueOf(1234L, 2));
  }

  @Test
  void toStringFormat() {
    assertThat(new Money(BigDecimal.valueOf(1234L, 2)))
        .extracting(Money::toString)
        .isEqualTo("$12.34");
  }

  @Test
  void toStringFormat_fillDigits() {
    assertThat(new Money(BigDecimal.valueOf(1234L, 0)))
        .extracting(Money::toString)
        .isEqualTo("$1234.00");
  }

  @Test
  void add() {
    var first = new Money(BigDecimal.valueOf(1234L, 1));
    var second = new Money(BigDecimal.valueOf(1234L, 2));
    assertThat(first.add(second)).isEqualTo(new Money(BigDecimal.valueOf(13574L, 2)));
  }

  @Test
  void add_doNotModifyOperands() {
    var first = new Money(BigDecimal.valueOf(1234L, 1));
    var second = new Money(BigDecimal.valueOf(1234L, 2));
    first.add(second);
    assertThat(first).isEqualTo(new Money(BigDecimal.valueOf(1234L, 1)));
    assertThat(second).isEqualTo(new Money(BigDecimal.valueOf(1234L, 2)));
  }

  @Test
  void multiply() {
    var money = new Money(BigDecimal.valueOf(1234L, 1));
    assertThat(money.multiply(10L)).isEqualTo(new Money(BigDecimal.valueOf(1234L, 0)));
  }

  @Test
  void multiply_doNotModifyOperand() {
    var money = new Money(BigDecimal.valueOf(1234L, 1));
    money.multiply(10L);
    assertThat(money).isEqualTo(new Money(BigDecimal.valueOf(1234L, 1)));
  }
}
