package hand;

import card.Card;

public interface Hand {
  void addCard(Card toAdd);
  boolean isEmpty();
  Card removeCard();
  int size();
}
