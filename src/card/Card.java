package card;

/**
 * A simple data structure to encapsulate two integer
 * values that represent a playing card
 */
public class Card {
  public int rank;
  public int suit;
  public Card(int suit, int rank) {
    this.rank = rank;
    this.suit = suit;
  }
}