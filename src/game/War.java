package game;


import card.Card;
import deck.DeckFactory;
import hand.Hand;
import player.Warrior;
import rand.Shuffle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by msimpson on 4/18/16.
 */
public class War implements GameType {

  private ArrayList<Warrior> players;
  private ArrayList<Warrior> contenders;
  private Shuffle jumbler;
  private ArrayList<Card> deck;

  public War(int numberOfPlayers, int numberOfSuits, int numberOfRanks) {
    this.players = new ArrayList<>();
    this.contenders = new ArrayList<>();
    this.deck = DeckFactory.makeDeck(numberOfSuits,numberOfRanks);
    this.jumbler = Shuffle.getInstance();
    for(int i = 0; i < numberOfPlayers; ++i) {
      this.players.add(new Warrior("Player " + i));
    }
  }

  public void play() {
    ArrayList<Card> loot = new ArrayList<>();
    boolean victory = false;
    this.jumbler.shuffle(deck);
    this.deal();

    while(!victory) {
      assert loot.isEmpty();
      assert contenders.isEmpty();
      this.addPlayersToContenders();
      if(this.contenders.size() > 1) {
        this.round();
      } else {
        victory = true;
      }
    }
    System.out.println(contenders.get(0).getName() + " is the winner!");
  }

  public void rematch() {
    Collections.rotate(players,1);
    for(Warrior player : players) {
      player.depleteHand();
    }
    //remove the winner of the last game
    this.contenders.remove(0);
    this.play();
  }

  private void round() {
    ArrayList<Card> loot = new ArrayList<>();

    this.flipAndCompare(loot);
    // a victor for the round has been declared
    jumbler.shuffle(loot);
    this.contenders.get(0).placeAtBottom(loot);
    //remove the last contender before the next round
    this.contenders.remove(0);
  }

  private void flipAndCompare(ArrayList<Card> loot) {
    Card addToLoot;
    int value,
        max = -1,
        numContenders = this.contenders.size();
    //loop through the remaining contenders
    for(int i = 0; i < numContenders; ++i) {
      addToLoot = this.contenders.get(i).getTopCard();
      value = addToLoot.value;
      //if their cards value is greater than or equal to
      if(max != value) {
        if(max < value) {
          // reset max value
          max = value;
          //remove any previous contenders
          for(int j = 0; j < i; j++) {
            this.contenders.remove(0);
            i--;
          }
        } else {
          //contender is no longer in the running for this round
          this.contenders.remove(i);
          i--;
        }
        //update remaining contenders as some may have been evicted
        numContenders = this.contenders.size();
      }
      //add players card to the loot
      loot.add(addToLoot);
    }
    if(this.contenders.size() > 1) {
      // if more than one player remains, a war needs to occur
      this.war(loot);
    }
  }

  private void war(ArrayList<Card> loot) {
    Warrior temp;
    int length = this.contenders.size();
    //loop through all available players
    for(int i = 0; i < length; ++i) {
      temp = this.contenders.get(i);
      //each player must sacrifice three cards during a war
      for(int j = 0; j < 2; ++j) {
        if(temp.hasCards()) { loot.add(temp.getTopCard()); }
      }
      //if they player ran out of cards, they automatically lose
      //and are evicted from the player pool
      if(!temp.hasCards()) {
        this.contenders.remove(i--);
        length--;
      }
    }
    // continue round by calling flip and passing current loot
    // all players are guaranteed to have at least one card in their hand
    this.flipAndCompare(loot);
  }

  private void deal() {
    int which = 0;
    int numPlayers = players.size();
    // loop through the deck adding cards to alternating hands
    for(Card next : this.deck) {
      which %= numPlayers;
      this.players.get(which).addCard(next);
      which++;
    }
  }

  private void addPlayersToContenders() {
    this.contenders = this.players.stream()
                                  .filter(player -> player.hasCards())
                                  .collect(Collectors.toCollection(ArrayList::new));
  }
}
