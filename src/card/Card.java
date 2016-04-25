package card;

public class Card {
  public int value;
  public int suit;
  public Card(int suit, int rank) {
    this.value = rank;
    this.suit = suit;
  }
}