package player;

import hand.Hand;

/**
 * Created by msimpson on 4/17/16.
 */
public abstract class Player<T extends Player> {

  private String name;
  protected Hand hand;

  public Player(String name, Hand hand){
    this.name = name;
    this.hand = hand;
  }

  public Hand getHand() {
    return this.hand;
  }

  public String getName() {
    return this.name;
  }

  public boolean hasCards() {
    return !this.hand.isEmpty();
  }

  public int handSize() {
    return this.hand.size();
  }

  public void depleteHand() {
    while(!this.hand.isEmpty()) {
      this.hand.removeCard();
    }
  }

}