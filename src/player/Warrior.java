package player;

import card.Card;
import hand.WarHand;
import java.util.ArrayList;

/**
 * The Warrior class is a subclass of a Player. It represents
 * a player specifically for game War. It includes
 * structures and behaviors that are unique to a player of the
 * game of War.
 */
public class Warrior extends Player<Warrior> {

  /**
   * Constructor passes an instance of WarHand to the Player class
   * allowing for hand behavior that is specific to the game of War.
   * @param name
   *    the name of the player
   */
  public Warrior(String name) {
    super(name, new WarHand());
  }

  /**
   * Adds a card to the hand with no side effects to
   * the source of the card
   * @param toAdd
   *    the card to add to the hand
   */
  public void addCard(Card toAdd) {
    this.hand.addCard(toAdd);
  }

  /**
   * Removes and returns the top card in the deck
   */
  public Card getTopCard() {
    return this.hand.removeCard();
  }

  /**
   * Removes cards from the head of the winnings array and
   * puts them at the tail of the warriors hand
   * @param winnings
   *    and array of cards to put at the bottom of the hand
   */
  public void placeAtBottom(ArrayList<Card> winnings) {
    int length = winnings.size();
    for(int i = 0; i < length; ++i) {
      this.hand.addCard(winnings.remove(0));
    }
  }
}