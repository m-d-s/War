package game

import card.Card
import deck.CardDeck;
/**
 * Created by msimpson on 4/24/16.
 */
class WarTest extends GroovyTestCase {
    GameType warTest;
    boolean[] winTracker;

    void setUp() {
        super.setUp()
    }

    void testPlay() {
        expectedPlayerWins();
        statisticalTest();
    }

    void testRematch() {

    }

    void expectedPlayerWins() {
        ArrayList<Card> rigged;
        int numPlayers = 2,
            numCards,
            winner;

        for(int i = 0; i < 100; ++i) {
            winTracker = new boolean[numPlayers];
            rigged = new ArrayList<>();
            numCards = numPlayers * 10;
            for(int j  = 0; j < numCards; j++) {
                rigged.add(new Card(j % numPlayers , 0))
            }
            warTest = new War(new CardDeck(rigged), numPlayers, numCards, 1, false, winTracker);
            warTest.play();
            winner = this.idWinner();
            assertEquals(true, winner == numPlayers - 1);
            numPlayers++;
        }

    }

    void statisticalTest() {
        int[] winCounts = new int[4];
        float proportion;
        GameType thousandYearsWar;
        int totalRematches = 100000;
        CardDeck deck = new CardDeck();
        deck.create(4,13)
        this.winTracker = new boolean[4];
        thousandYearsWar = new War(deck, 4, 4, 13, true, winTracker);
        for(int i = 0; i < 4; ++i) {
            winCounts[i] = 0;
        }
        thousandYearsWar.play();
        winCounts[this.idWinner()]++;
        for(int i = 0; i < totalRematches; ++i) {
            thousandYearsWar.rematch();
            winCounts[this.idWinner()]++;
        }
        for(int i = 0; i < 4; ++i) {
            proportion = (float) winCounts[i] / totalRematches;
            assertEquals(true, proportion > 0.245 && proportion < 0.255);
        }

    }

    int idWinner() {
        boolean[] tracker = this.winTracker;
        int length = tracker.length;
        for(int i = 0; i < length; ++i) {
            if(tracker[i]) {
                return i;
            }
        }
        return -1;
    }
}
