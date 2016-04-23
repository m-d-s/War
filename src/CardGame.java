import card.*;
import deck.DeckFactory;
import game.GameType;

import java.util.ArrayList;

/**
 * Created by msimpson on 4/17/16.
 */
public class CardGame {

  private ArrayList<Card> deck;
  private GameType theGame;

  public CardGame(GameType game) {
    this.deck = DeckFactory.makeDeck(4, 13);
    this.theGame = game;
  }

  public void play() {
    this.theGame.play(this.deck);
  }

  public void rematch() {
    this.theGame.rematch(this.deck);
  }
}
