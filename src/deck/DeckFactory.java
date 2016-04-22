package deck;

import card.*;

import java.util.ArrayList;

/**
 * Created by msimpson on 4/20/16.
 */
public class DeckFactory {
  public static ArrayList<Card> makeDeck(){
    int value = 2;
    int suit = 0;
    int length = 52;
    ArrayList<Card> deck = new ArrayList<>();
    for(int i = 0; i < length; ++i ) {
      // generates one card of each suit per value
      switch (suit %= 4) {
        case 0:
          deck.add(new Heart(value));
          break;
        case 1:
          deck.add(new Club(value));
          break;
        case 2:
          deck.add(new Spade(value));
          break;
        case 3:
          deck.add(new Diamond(value));
      }
      // increment value if all suits have been generated
      if(suit % 4 == 3) value++;
      suit++;
    }
    return deck;
  }
}
