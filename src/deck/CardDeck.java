package deck;

import card.Card;
import rand.Shuffle;

import java.util.ArrayList;

/**
 * Created by msimpson on 4/23/16.
 */
public class CardDeck implements Deck {
  ArrayList<Card> deck;
  Shuffle jumbler;
  int pos;

  public CardDeck() {
    pos     = 0;
    deck    = new ArrayList<>();
    jumbler = Shuffle.getInstance();
  }

  /* for testing purposes*/
  public CardDeck(ArrayList<Card> deck) {
    this.pos  = 0;
    this.deck = deck;
    jumbler   = Shuffle.getInstance();
  }

  /* Create the deck of cards */
  public void create( int numberOfSuits, int numberOfRanks ) {
    int rank = 0,
        suit = 0,
        length = numberOfSuits * numberOfRanks;

    this.depleteDeck();
    // generates one card of each suit per value
    for(int i = 0; i < length; ++i ) {
      this.deck.add(new Card(rank, suit));
      // if all cards of a given suit have been created
      if(++rank == numberOfSuits) {
        suit++;
        rank = 0;
      }
    }
  }

  /* deal a card from the deck */
  public Card deal() {
    pos %= deck.size();
    return deck.get(pos++);
  }

  private void depleteDeck() {
    while(!this.deck.isEmpty()) {
      this.deck.remove(0);
    }
  }

  /* Shuffle the deck */
  public void shuffle() {
    this.jumbler.shuffle(this.deck);
  }
}
