package game;

import card.Card;
import deck.Deck;
import deck.CardDeck;
import player.Warrior;
import rand.Shuffle;
import validator.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * War is the main driver class for the program. It contains the main execution loop of
 * the game, and is composed of structures and behaviors that are definitive to the game.
 */
public class War implements GameType {
  private Deck deck;
  private int numSuits;
  private int numRanks;
  private Shuffle jumbler;
  private ArrayList<Warrior> players;
  private ArrayList<Warrior> contenders;

  /* for testing purposes */
  private boolean[] winTracker;
  /* for testing purposes */
  private final boolean outputOn;
  /* for testing purposes */
  private final boolean doShuffle;

  public War(int numPlayers, int numSuits, int numRanks) {
    this.doShuffle  = true;
    this.winTracker = null;
    this.outputOn   = true;
    this.deck       = new CardDeck();
    this.contenders = new ArrayList<>();
    this.jumbler    = Shuffle.getInstance();
    this.numSuits   = Validator.positiveDigit(numSuits,
                                              "Please enter a positive integer value for the number of suits");
    this.numRanks   = Validator.positiveDigit(numRanks,
                                              "Please enter a positive integer value for the number of ranks");
    this.initPlayers( Validator.positiveDigit(numPlayers,
                                              "Please enter a positive integer value for the number of players"));
    this.deck.create(this.numSuits, this.numRanks);
  }

  /* for testing purposes */
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

  /**
   * play gets called once to play the first game, after that all subsequent games must
   * be played through rematch
   */
  public void play() {
    String winner;
    this.clearWinTable();
    if(this.doShuffle) { this.deck.shuffle(); }
    this.deal();
    this.gamePlay();
    if(this.contenders.isEmpty()) {
      if(this.outputOn) { System.out.print("Game ended in a tie"); }
    } else {
      winner = this.contenders.get(0).getName();
      if(this.outputOn) { System.out.println(winner + " is the winner!"); }
      if(this.winTracker != null) {
        this.winTracker[Integer.parseInt(winner.replaceAll("\\D+",""))] = true;
      }
    }
  }

  /**
   * rematch can be called to play any number of matches after the initial call to play.
   * It resets the game to a known starting state with all player hands empty, and
   * the player list completely rotated by one position.
   */
  public void rematch() {
    /* rotate players to cycle first dealt */
    Collections.rotate(players,1);
    /* remove all cards from players hands */
    for(Warrior player : players) {
      player.depleteHand();
    }
    this.play();
  }

  /**
   * clearWinTable resets the testing boolean table to all false values
   */
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

  /**
   * deal distributes cards to players one at a time in a clockwise fashion
   * until the deck entire deck has been dealt
   */
  private void deal() {
    Deck deck = this.deck;
    ArrayList<Warrior> players = this.players;
    int which      = 0,
        numPlayers = players.size(),
        numCards   = this.numRanks * this.numSuits;

    /* loop through the deck adding cards to cycling player hands */
    for(int i = 0; i < numCards; ++i) {
      which %= numPlayers;
      players.get(which).addCard(deck.deal());
      which++;
    }
  }

  private void filterContenders() {
    this.contenders = this.players.stream()
                          .filter(player -> player.hasCards())
                          .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * flipAndCompare contains the core logic of the game. Each player removes
   * a card from their hand and adds it to the loot. If one player's card had
   * the greatest value, that player wins the round and claims the loot. If
   * there are more than one players, a call to war gets made that will
   * indirectly recurse back. This method assumes that all players within
   * the contenders list have at least one card
   * @param loot
   */
  private void flipAndCompare(ArrayList<Card> loot) {
    Card addToLoot;
    int rank,
        max = -1,
        numContenders = this.contenders.size();

    /* loop through the remaining contenders */
    for(int i = 0; i < numContenders; ++i) {
      addToLoot = this.contenders.get(i).getTopCard();
      rank = addToLoot.rank;
      if(max != rank) {
        if(max < rank) {
          /* set new maximum value */
          max = rank;
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

  /**
   * gamePlay is a small utility loop that keeps the game going until
   * there is one or zero contenders remaining
   */
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

  /**
   * initPlayers initializes and added the required number of players to
   * the players list
   * @param numPlayers
   *    total number of players to initialize
   */
  private void initPlayers(int numPlayers) {
    ArrayList<Warrior> players = new ArrayList<>();
    for(int i = 0; i < numPlayers; ++i) {
      players.add(new Warrior("Player " + i));
    }
    this.players = players;
  }

  /**
   * round encapsulates all of the behavior that represents a single
   * full cycle of play during a game
   */
  private void round() {
    /* loot contains all of the cards that will be claimed by the round winner */
    ArrayList<Card> loot = new ArrayList<>();

    /* begin laying down cards */
    this.flipAndCompare(loot);
    /* shuffle the loot before being claimed by the winning player */
    jumbler.shuffle(loot);
    if(!this.contenders.isEmpty()) {
      this.contenders.get(0).placeAtBottom(loot);
    }
  }

  /**
   * war simply deposits three cards from each remaining contender into the
   * loot. If any player has no cards left as a result, they are ejected
   * from the contenders. Finally, a call is made back to flipAndCompare
   * @param loot
   */
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