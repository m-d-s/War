package deck

import card.Card

/**
 * Created by msimpson on 4/23/16.
 */
class CardDeckTest extends GroovyTestCase {
    ArrayList<Card> cards;
    Deck deck;
    void setUp() {
        super.setUp()
        this.cards = new ArrayList<>();
        deck = new CardDeck(this.cards);
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
            accounted[card.suit][card.rank] = true;
        }

        for(int i = 0; i < numSuits; ++i) {
            for(int j = 0; j < numRanks; j++) {
                assertEquals(true, accounted[i][j]);
            }
        }
    }

    void testDeal() {
        this.deck.create(4,13);
        assertEquals(true, cards.get(0) == this.deck.deal());
    }
}
