package shop.flower.domain;

public record Bundle(int size, Money cost) {

  public Bundle(int size){
    this(size, Money.parse("$0.00"));
  }
}
