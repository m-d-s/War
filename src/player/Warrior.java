package player;

import card.Card;
import hand.WarHand;

import java.util.ArrayList;

/**
 * Created by msimpson on 4/18/16.
 */
public class Warrior extends Player<Warrior> {

  public Warrior(String name) {
    super(name, new WarHand());
  }

  public Card getTopCard() {
    return this.hand.removeCard();
  }

  public void placeAtBottom(ArrayList<Card> winnings) {
    int length = winnings.size();
    for(int i = 0; i < length; ++i) {
      this.hand.addCard(winnings.remove(0));
    }
  }
}
