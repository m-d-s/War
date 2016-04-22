package hand;

import card.Card;
import card.Heart;

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
  public void addCard(Card toAdd) {
    hand.add(toAdd);
  }

  public Card removeCard() {
    return hand.poll();
  }

  public boolean isEmpty() {
    return hand.isEmpty();
  }
  public int size() {
    return hand.size();
  }
}
