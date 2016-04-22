package game;

import card.Card;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by msimpson on 4/18/16.
 */
public interface GameType {
  void play(ArrayList<Card> deck);
  void rematch(ArrayList<Card> deck);
}
