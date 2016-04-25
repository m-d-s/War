package hand;

import card.Card;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by msimpson on 4/21/16.
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

  public void addCard(Card toAdd) {
    hand.add(toAdd);
  }

  public boolean isEmpty() {
    return hand.isEmpty();
  }

  public Card removeCard() {
    return hand.poll();
  }

  public int size() {
    return hand.size();
  }
}
