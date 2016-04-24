package rand;

import card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by msimpson on 4/19/16.
 */
public class Shuffle {

  private static Shuffle instance = null;

  private static Random rnd;

  private Shuffle() {
    rnd = new Random(System.currentTimeMillis());
  }

  public static Shuffle getInstance() {
    if(instance == null) {
      instance = new Shuffle();
    }
    return instance;
  }

  public void shuffle(ArrayList<Card> cards) {
    int swap;
    // Knuth-Fisher–Yates shuffle
    for(int i = cards.size() - 1; i > 0; --i) {
      swap = rnd.nextInt(i+1);
      Collections.swap(cards, i, swap);
    }
  }
}
