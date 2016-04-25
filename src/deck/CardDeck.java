package deck;

import card.Card;
import rand.Shuffle;

import java.util.ArrayList;

/**
 * CardDeck defines a collection of Card objects with a specified interface.
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

  /**
   *  create the deck of cards of size (numberOfSuits * numberOfRanks)
   *  @param numberOfSuits
   *      denotes the number of suits to assign to the deck
   *  @param numberOfRanks
   *      denotes the number of ranks to assign to the deck
   */
  public void create( int numberOfSuits, int numberOfRanks ) {
    int rank   = 0,
        suit   = 0,
        length = numberOfSuits * numberOfRanks;
    /* in case a deck had already been created */
    this.depleteDeck();
    /* generates one card of each suit per rank */
    for(int i = 0; i < length; ++i ) {
      this.deck.add(new Card(rank, suit));
      /* once all ranks of a given suit have been created */
      if(++rank == numberOfSuits) {
        suit++;
        rank = 0;
      }
    }
  }

  /**
   *  deal a card from the deck
   */
  public Card deal() {
    /* pos cycles through the deck*/
    this.pos %= deck.size();
    return this.deck.get(this.pos++);
  }

  /**
   *  shuffle the deck
   */
  public void shuffle() {
    this.jumbler.shuffle(this.deck);
  }

  /**
   * depleteDeck removes all cards from the deck
   */
  private void depleteDeck() {
    while(!this.deck.isEmpty()) {
      this.deck.remove(0);
    }
  }
}