package hand;

import card.Card;

/**
 * Created by msimpson on 4/21/16.
 */
public interface Hand {
  void addCard(Card toAdd);
  boolean isEmpty();
  Card removeCard();
  int size();
}
