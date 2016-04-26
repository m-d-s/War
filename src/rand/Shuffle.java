package rand;

import card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Shuffle is a class that offers singleton access to
 * a random number generator for the purposes of shuffling
 * lists of cards. Instance access is granted through the
 * getInstance static method.
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

  /**
   * shuffle the contents of the given list of cards
   * @param cards
   *    output param, shuffled list
   */
  public void shuffle(ArrayList<Card> cards) {
    int swap;
    // Knuth-Fisher–Yates shuffle
    for(int i = cards.size() - 1; i > 0; --i) {
      swap = rnd.nextInt(i+1);
      Collections.swap(cards, i, swap);
    }
  }
}