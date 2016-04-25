package game;


import card.Card;
import deck.Deck;
import deck.CardDeck;
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

  /* for testing purposes */
  private final boolean doShuffle;
  private final boolean outputOn;
  /* for testing purposes */
  private boolean[] winTracker;


  public War(int numberOfPlayers, int numberOfSuits, int numberOfRanks) {
    this.doShuffle  = true;
    this.winTracker = null;
    this.outputOn   = false;
    this.numSuits   = numberOfSuits;
    this.numRanks   = numberOfRanks;
    this.deck       = new CardDeck();
    this.contenders = new ArrayList<>();
    this.jumbler    = Shuffle.getInstance();

    this.initPlayers(numberOfPlayers);
    this.deck.create(numberOfSuits, numberOfRanks);
  }

  /* For testing purposes */
  public War(Deck deck, int numPlayers, int numSuits, int numRanks, boolean doShuffle, boolean[] winTracker) {
    this.deck       = deck;
    this.outputOn   = false;
    this.numSuits   = numSuits;
    this.numRanks   = numRanks;
    this.doShuffle  = doShuffle;
    this.winTracker = winTracker;
    this.contenders = new ArrayList<>();
    this.jumbler    = Shuffle.getInstance();

    this.initPlayers(numPlayers);
  }

  public void play() {
    String winner;
    this.clearWinTable();
    if(this.doShuffle) { this.deck.shuffle(); }
    this.deal();
    this.gamePlay();
    if(contenders.isEmpty()) {
      System.out.print("Game ended in a tie");
    } else {
      winner = contenders.get(0).getName();
      if(outputOn) { System.out.println(winner + " is the winner!"); }
      if(winTracker != null) {
        winTracker[Integer.parseInt(winner.replaceAll("\\D+",""))] = true;
      }
    }
  }

  public void rematch() {
    /* rotate players to cycle first dealt */
    Collections.rotate(players,1);
    /* remove all cards from players hands */
    for(Warrior player : players) {
      player.depleteHand();
    }
    this.play();
  }

  private void clearWinTable() {
    int length;
    boolean[] table = this.winTracker;
    if(table != null) {
      length = table.length;
      for(int i = 0; i < length; i++) {
        table[i] = false;
      }
    }
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

  private void filterContenders() {
    this.contenders = this.players.stream()
                          .filter(player -> player.hasCards())
                          .collect(Collectors.toCollection(ArrayList::new));
  }

  private void flipAndCompare(ArrayList<Card> loot) {
    Card addToLoot;
    int value,
        max = -1,
        numContenders = this.contenders.size();

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

  private void gamePlay() {
    boolean victory = false;
    while(!victory) {
      this.filterContenders();
      if(this.contenders.size() > 1) {
        this.round();
      } else {
        victory = true;
      }
    }
  }

  private void initPlayers(int numPlayers) {
    this.players = new ArrayList<>();
    for(int i = 0; i < numPlayers; ++i) {
      this.players.add(new Warrior("Player " + i));
    }
  }

  private void round() {
    ArrayList<Card> loot = new ArrayList<>();

    /* begin laying down cards */
    this.flipAndCompare(loot);
    /* a victor for the round has been declared */
    jumbler.shuffle(loot);
    if(!this.contenders.isEmpty()) {
      this.contenders.get(0).placeAtBottom(loot);
    }

  }

  private void war(ArrayList<Card> loot) {
    Warrior temp;
    int length = this.contenders.size();

    /* loop through all available players */
    for(int i = 0; i < length; ++i) {
      temp = this.contenders.get(i);
      /* each player must sacrifice three cards during a war */
      for(int j = 0; j < 3; ++j) {
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
}