package hand;

import card.Card;

import java.util.LinkedList;
import java.util.Queue;

/**
 * WarHand defines the structures and behavior that is
 * unique to a hand of cards in the game war. I didn't use
 * a Deck for a player's hand because I saw enough unique
 * functionality that it didn't make sense to force a
 * Hand to be a Deck. I also thought it may be useful
 * if more games were developed in the future.
 */
public class WarHand implements Hand {
  private Queue<Card> hand;

  public WarHand() {
    hand = new LinkedList<>();
  }

  /* for testing purposes */
  public WarHand(Queue<Card> hand) {
    this.hand = hand;
  }

  /**
   * addCard pushes the given Card onto the bottom of the hand
   * @param toAdd
   *    card to be added
   */
  public void addCard(Card toAdd) {
    hand.add(toAdd);
  }

  public boolean isEmpty() {
    return hand.isEmpty();
  }

  /**
   * removeCard pops the top card off of the hand and returns
   * it. That card will no longer be in the hand.
   * @return
   *    the popped card
   */
  public Card removeCard() {
    return hand.poll();
  }

  public int size() {
    return hand.size();
  }
}