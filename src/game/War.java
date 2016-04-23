package game;


import card.Card;
import hand.Hand;
import player.Warrior;
import rand.Shuffle;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by msimpson on 4/18/16.
 */
public class War implements GameType {

  private Warrior p1;
  private Warrior p2;
  private Shuffle jumbler;

  public War() {
    String[] names = getPlayerNames();
    this.jumbler = Shuffle.getInstance();
    this.p1 = new Warrior(names[0]);
    this.p2 = new Warrior(names[1]);
  }

  public void play(ArrayList<Card> deck) {
    ArrayList<Card> loot = new ArrayList<>();
    boolean which;
    String winner;

    this.jumbler.shuffle(deck);
    this.deal(deck);

    while(p1.hasCards() && p2.hasCards()) {
      assert loot.isEmpty();
      which = flip(loot);
      this.jumbler.shuffle(loot);
      if(which) {
        this.p1.placeAtBottom(loot);
      } else {
        this.p2.placeAtBottom(loot);
      }
    }

    winner = p1.hasCards() ? p1.getName() : p2.getName();
    System.out.println(winner + " is the winner!");
  }

  public void rematch(ArrayList<Card> deck) {
    Warrior temp = this.p1;
    //Swap players so that the first dealt gets toggled
    this.p1 = this.p2;
    this.p2 = temp;
    // empty the players hands
    this.p1.depleteHand();
    this.p2.depleteHand();
    this.play(deck);
  }

  private boolean flip(ArrayList<Card> loot) {
    Card p1Card, p2Card;
    boolean p1WinsLoot;
    p1Card = this.p1.getTopCard();
    p2Card = this.p2.getTopCard();
    loot.add(p1Card);
    loot.add(p2Card);
    if(p1Card.value == p2Card.value) {
      if(this.p1.hasCards() && this.p2.hasCards()) {
        return this.war(loot);
      } else {
        p1WinsLoot = this.p1.hasCards();
      }
    } else {
      p1WinsLoot = p1Card.value > p2Card.value;
    }
    return p1WinsLoot;
  }

  private boolean war(ArrayList<Card> loot) {
    int p1handSize = this.p1.handSize(),
        p2HandSize = this.p2.handSize();
    for(int i = 0; i < 2 && p1handSize > 1 && p2HandSize > 1; ++i) {
      loot.add(this.p1.getTopCard());
      loot.add(this.p2.getTopCard());
      p1handSize--;
      p2HandSize--;
    }
    return flip(loot);
  }

  private void deal(ArrayList<Card> deck) {
    boolean which = true;
    Hand p1Hand = this.p1.getHand(),
         p2Hand = this.p2.getHand();
    // loop through the deck adding cards to alternating hands
    for(Card next : deck) {
      if(which) {
        p1Hand.addCard(next);
      } else {
        p2Hand.addCard(next);
      }
      which = !which;
    }
  }

  private String[] getPlayerNames() {
    String[] names = new String[2];
    try{
      BufferedReader br =
          new BufferedReader(new InputStreamReader(System.in));

      System.out.println("player.Player 1, enter your name: ");
      names[0] = br.readLine();

      System.out.println("player.Player 2, enter your name: ");
      names[1] = br.readLine();
    }catch(IOException io){
      io.printStackTrace();
    }
    return names;
  }
}
