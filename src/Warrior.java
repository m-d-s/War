import card.Card;

import java.util.ArrayList;

/**
 * Created by msimpson on 4/18/16.
 */
public class Warrior extends Player<Warrior> {

  public Warrior(String name) {
    super(name);
  }

  public Card getTopCard() {
    return this.hand.poll();
  }

  public void placeAtBottom(ArrayList<Card> winnings) {
    int length = winnings.size();
    for(int i = 0; i < length; ++i) {
      this.hand.add(winnings.remove(0));
    }
  }
}
