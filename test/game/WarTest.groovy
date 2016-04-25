package game

import card.Card
import deck.CardDeck;
/**
 * Created by msimpson on 4/24/16.
 */
class WarTest extends GroovyTestCase {
    GameType warTest;
    boolean[] winTable;

    void testPlay() {
        expectedPlayerWins();
        explicitWarScenario();
        statisticalTest();
    }

    void expectedPlayerWins() {
        ArrayList<Card> rigged;
        int numPlayers = 2,
            numCards,
            winner;

        for(int i = 0; i < 100; ++i) {
            winTable = new boolean[numPlayers];
            rigged = new ArrayList<>();
            numCards = numPlayers * 10;
            for(int j  = 0; j < numCards; j++) {
                rigged.add(new Card(0 ,j % numPlayers))
            }
            warTest = new War(new CardDeck(rigged), numPlayers, numCards, 1, false, winTable);
            warTest.play();
            winner = this.idWinner();
            assertEquals(true, winner == numPlayers - 1);
            numPlayers++;
        }

    }

    void explicitWarScenario() {
        ArrayList<Card> rigged = new ArrayList<>();
        for(int i = 0; i < 8; ++i) {
            rigged.add(new Card(0,0));
        }
        rigged.add(new Card(0,1));
        rigged.add(new Card(0,0));
        this.winTable = new boolean[2]
        warTest = new War(new CardDeck(rigged), 2, 10, 1, false, winTable);
        warTest.play();
        assertEquals(true, 0 == this.idWinner());
    }

    void statisticalTest() {
        int[] winCounts = new int[4];
        float proportion;
        int winner,
            tieCount = 0,
            totalRematches = 100000;
        CardDeck deck = new CardDeck();
        deck.create(4,13)
        this.winTable = new boolean[4];
        this.warTest = new War(deck, 4, 4, 13, true, winTable);
        for(int i = 0; i < 4; ++i) {
            winCounts[i] = 0;
        }
        this.warTest.play();
        winCounts[this.idWinner()]++;
        for(int i = 0; i < totalRematches; ++i) {
            this.warTest.rematch();
            winner = this.idWinner();
            if (winner >= 0) {
                winCounts[winner]++;
            } else {
                tieCount++;
            }
        }
        for(int i = 0; i < 4; ++i) {
            proportion = (float) winCounts[i] / totalRematches;
            assertEquals(true, proportion > 0.245 && proportion < 0.255);
        }
        proportion = (float) tieCount / totalRematches;
        assertEquals(true, proportion < 0.00001);

    }

    int idWinner() {
        boolean[] tracker = this.winTable;
        int length = tracker.length;
        for(int i = 0; i < length; ++i) {
            if(tracker[i]) {
                return i;
            }
        }
        return -1;
    }
}
