package deck

import card.Card

/**
 * Testing of the CardDeck class. Shuffling is omitted from these tests, the
 * shuffle routine gets implicitly tested in the statisticalTest method
 * within the WarTest.
 */
class CardDeckTest extends GroovyTestCase {
    ArrayList<Card> cards;
    Deck deck;
    void setUp() {
        super.setUp()
        this.cards = new ArrayList<>();
        deck = new CardDeck(this.cards);
    }

    /**
     * testing deck creation
     */
    void testCreate() {
        int numSuits = 13,
            numRanks = 21;
        boolean[][] accounted = new boolean[numSuits][numRanks];

        /* a boolean matrix that will account for every card in the deck*/
        for(int i = 0; i < numSuits; ++i) {
            for(int j = 0; j < numRanks; j++) {
                accounted[i][j] = false;
            }
        }
        /* deck begins empty */
        assertEquals(true, this.cards.isEmpty());
        this.deck.create(numSuits, numRanks);
        /* is not empty when stuff is added */
        assertEquals(false, this.cards.isEmpty());
        /* has the right dimensions */
        assertEquals(true, this.cards.size() == numSuits * numRanks);
        /* start counting cards*/
        for(Card card : cards) {
            accounted[card.suit][card.rank] = true;
        }
        /* make sure all expected cards are present in the deck*/
        for(int i = 0; i < numSuits; ++i) {
            for(int j = 0; j < numRanks; j++) {
                assertEquals(true, accounted[i][j]);
            }
        }
    }
    /**
     * test to make sure that decks deal from the top
     */
    void testDeal() {
        this.deck.create(4,13);
        assertEquals(true, cards.get(0) == this.deck.deal());
    }
}
