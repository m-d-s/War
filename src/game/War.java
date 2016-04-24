package game;


import card.Card;
import deck.Deck;
import deck.FrenchDeckNoJkrs;
import player.Warrior;
import rand.Shuffle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created by msimpson on 4/18/16.
 */
public class War implements GameType {

  private ArrayList<Warrior> players;
  private ArrayList<Warrior> contenders;
  private Shuffle jumbler;
  private Deck deck;
  private int numSuits;
  private int numRanks;

  public War(int numberOfPlayers, int numberOfSuits, int numberOfRanks) {
    this.numSuits   = numberOfSuits;
    this.numRanks   = numberOfRanks;
    this.players    = new ArrayList<>();
    this.contenders = new ArrayList<>();
    this.jumbler    = Shuffle.getInstance();
    this.deck       = new FrenchDeckNoJkrs();

    this.deck.create(numberOfSuits, numberOfRanks);
    for(int i = 0; i < numberOfPlayers; ++i) {
      this.players.add(new Warrior("Player " + i));
    }
  }

  /* For testing purposes */
  public War(ArrayList<Warrior> players, Deck deck, int numSuits, int numRanks) {
    this.deck       = deck;
    this.players    = players;
    this.numSuits   = numSuits;
    this.numRanks   = numRanks;
    this.contenders = new ArrayList<>();
    this.jumbler    = Shuffle.getInstance();
  }

  public void play() {
    boolean victory = false;
    this.deck.shuffle();
    this.deal();

    while(!victory) {
      this.addPlayersToContenders();
      if(this.contenders.size() > 1) {
        this.round();
      } else {
        victory = true;
      }
    }

    if(contenders.isEmpty() && players.size() <= numSuits) {
      System.out.print("Game ended in a tie");
    } else {
      System.out.println(contenders.get(0).getName() + " is the winner!");
    }

  }

  public void rematch() {
    /* rotate players to cycle first dealt */
    Collections.rotate(players,1);
    /* remove all cards from players hands */
    for(Warrior player : players) {
      player.depleteHand();
    }
    /* remove the winner of the last game */
    this.contenders.remove(0);
    this.play();
  }

  private void round() {
    ArrayList<Card> loot = new ArrayList<>();

    /* begin laying down cards */
    this.flipAndCompare(loot);
    /* a victor for the round has been declared */
    jumbler.shuffle(loot);
    this.contenders.get(0).placeAtBottom(loot);
  }

  private void flipAndCompare(ArrayList<Card> loot) {
    int value,
        max = -1,
        numContenders = this.contenders.size();
    Card addToLoot;

    /* loop through the remaining contenders */
    for(int i = 0; i < numContenders; ++i) {
      addToLoot = this.contenders.get(i).getTopCard();
      value = addToLoot.value;
      if(max != value) {
        if(max < value) {
          max = value;
          /* remove any previous contenders */
          for(int j = 0; j < i; j++) {
            this.contenders.remove(0);
            i--;
          }
        } else {
          /* contender is no longer in the running for this round */
          this.contenders.remove(i);
          i--;
        }
        /* update remaining contenders as some may have been evicted */
        numContenders = this.contenders.size();
      }
      loot.add(addToLoot);
    }
    if(this.contenders.size() > 1) {
      /* if more than one player remains, a war needs to occur */
      this.war(loot);
    }
  }

  private void war(ArrayList<Card> loot) {
    Warrior temp;
    int length = this.contenders.size();

    /* loop through all available players */
    for(int i = 0; i < length; ++i) {
      temp = this.contenders.get(i);
      /* each player must sacrifice three cards during a war */
      for(int j = 0; j < 2; ++j) {
        if(temp.hasCards()) { loot.add(temp.getTopCard()); }
      }
      /* if they player ran out of cards, they automatically lose
       * and are evicted from the player pool */
      if(!temp.hasCards()) {
        this.contenders.remove(i--);
        length--;
      }
    }
    /* indirect recursion, all players without cards have been
     * removed from contenders at this point */
    this.flipAndCompare(loot);
  }

  private void deal() {
    int which = 0;
    int numPlayers = players.size();
    int numCards = this.numRanks * this.numSuits;
    /* loop through the deck adding cards to alternating hands */
    for(int i = 0; i < numCards; ++i) {
      which %= numPlayers;
      this.players.get(which).addCard(this.deck.deal());
      which++;
    }
  }

  private void addPlayersToContenders() {
    this.contenders = this.players.stream()
                                  .filter(player -> player.hasCards())
                                  .collect(Collectors.toCollection(ArrayList::new));
  }
}
