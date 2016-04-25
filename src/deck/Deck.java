package deck;
import card.Card;

public interface Deck {
  /* Create the deck of cards */
  void create( int numberOfSuits, int numberOfRanks );

  /* deal a card from the deck */
  Card deal();

  /* Shuffle the deck */
  void shuffle();
}
