package deck;

import card.Card;

import java.util.ArrayList;

/**
 * Created by msimpson on 4/20/16.
 */
public class DeckFactory {
  public static ArrayList<Card> makeDeck(int suitCt, int rankCount){
    int rank = 0,
        suit = 0,
        length = suitCt * rankCount;
    ArrayList<Card> deck = new ArrayList<>();
    // generates one card of each suit per value
    for(int i = 0; i < length; ++i ) {
      deck.add(new Card(suit, rank));
      // if all cards of a given suit have been created
      if(++rank == suitCt) {
        suit++;
        rank = 0;
      }
    }
    return deck;
  }
}
