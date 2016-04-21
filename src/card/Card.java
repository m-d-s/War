package card;

public abstract class Card {
  public int value;
  public Card(int value) {
    this.value = value;
  }

  public boolean equals(Card other) {
    return this.value == other.value;
  }

  public boolean gt(Card other) {
    return this.value > other.value;
  }
}