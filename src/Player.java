import card.Card;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by msimpson on 4/17/16.
 */
public abstract class Player<T extends Player> {

  private String name;
  protected Queue<Card> hand;

  public Player(String name){
    this.name = name;
    this.hand = new LinkedList<>();
  }

  public Queue<Card> getHand() {
    return this.hand;
  }

  public String getName() {
    return this.name;
  }

  public boolean hasCards() {
    return !hand.isEmpty();
  }

  public int handSize() {
    return this.hand.size();
  }

  public void depleteHand() {
    while(!hand.isEmpty()) {
      hand.poll();
    }
  }
}
