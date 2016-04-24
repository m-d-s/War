package deck;

import card.Card;
import rand.Shuffle;

import java.util.ArrayList;

/**
 * Created by msimpson on 4/23/16.
 */
public class FrenchDeckNoJkrs implements Deck {
  ArrayList<Card> deck;
  Shuffle jumbler;
  int pos;

  public FrenchDeckNoJkrs() {
    pos = 0;
    deck = new ArrayList<>();
    jumbler = Shuffle.getInstance();
  }
  /* Create the deck of cards */
  public void create( int numberOfSuits, int numberOfRanks ) {
    int rank = 0,
        suit = 0,
        length = numberOfSuits * numberOfRanks;
    depleteDeck();
    // generates one card of each suit per value
    for(int i = 0; i < length; ++i ) {
      this.deck.add(new Card(suit, rank));
      // if all cards of a given suit have been created
      if(++rank == numberOfSuits) {
        suit++;
        rank = 0;
      }
    }
  }

  /* Shuffle the deck */
  public void shuffle() {
    this.jumbler.shuffle(this.deck);
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
}
