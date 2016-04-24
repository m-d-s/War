package deck

import card.Card

/**
 * Created by msimpson on 4/23/16.
 */
class FrenchDeckNoJkrsTest extends GroovyTestCase {
    ArrayList<Card> cards;
    Deck deck;
    void setUp() {
        super.setUp()
        this.cards = new ArrayList<>();
        deck = new FrenchDeckNoJkrs(this.cards);
    }

    void testCreate() {
        int numSuits = 13,
            numRanks = 21;
        boolean[][] accounted = new boolean[numSuits][numRanks];

        for(int i = 0; i < numSuits; ++i) {
            for(int j = 0; j < numRanks; j++) {
                accounted[i][j] = false;
            }
        }

        assertEquals(true, this.cards.isEmpty());
        this.deck.create(numSuits, numRanks);
        assertEquals(false, this.cards.isEmpty());
        assertEquals(true, this.cards.size() == numSuits * numRanks);

        for(Card card : cards) {
            accounted[card.suit][card.value] = true;
        }

        for(int i = 0; i < numSuits; ++i) {
            for(int j = 0; j < numRanks; j++) {
                assertEquals(true, accounted[i][j]);
            }
        }
    }
//   TODO: maybe leave it out, but explain
//    void testShuffle() {
//        ArrayList<Card> otherCards = new ArrayList<>();
//
//        this.deck.create(4,13);
//
//        for(Card card : this.cards) {
//            otherCards.add(new Card(card.suit, card.value));
//        }
//        this.deck.shuffle();
//
//    }

    void testDeal() {
        this.deck.create(4,13);
        assertEquals(true, cards.get(0) == this.deck.deal());
    }
}
