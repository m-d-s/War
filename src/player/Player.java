package player;

import hand.Hand;

/**
 * Player is an abstract class that defines the essential
 * attributes and behaviors of a card player
 */
public abstract class Player {
  private String name;
  protected Hand hand;

  public Player(String name, Hand hand){
    this.name = name;
    this.hand = hand;
  }

  /**
   * depleteHand removes all cards from a player's hand
   */
  public void depleteHand() {
    while(!this.hand.isEmpty()) {
      this.hand.removeCard();
    }
  }

  public Hand getHand() {
    return this.hand;
  }

  public String getName() { return this.name; }

  public int handSize() {
    return this.hand.size();
  }

  public boolean hasCards() { return !this.hand.isEmpty(); }
}