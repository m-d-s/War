package game

import card.Card
import deck.CardDeck;
/**
 * Testing of the War Class
 */
class WarTest extends GroovyTestCase {
    GameType warTest;
    boolean[] winTable;

    void testPlay() {
        explicitWarScenario();
        explicitTieScenario();
        projectedPlayerWins();
        statisticalTest();
    }

    /**
     * This test chooses a random player and gives them cards with higher
     * values than any other player. It then plays a match and asserts that
     * the projected player wins the game. It repeats this test over an
     * inclusive range of [2..100] players.
     */
    void projectedPlayerWins() {
        int winner,
            tracker,
            numCards,
            numPlayers = 2,
            projectedWinner;
        Card toAdd;
        ArrayList<Card> rigged;
        Random rand = new Random(System.currentTimeMillis());

        for(int i = 0; i < 100; ++i) {
            winTable = new boolean[numPlayers];
            projectedWinner = rand.nextInt(numPlayers);
            tracker = projectedWinner;
            rigged = new ArrayList<>();
            /* keeping the total number of cards a factor of the
             * number of players makes sure the projected player
             * gets the right cards during the game */
            numCards = numPlayers * 10;
            for(int j  = 0; j < numCards; j++) {
                if(tracker == 0) {
                    toAdd = new Card(0 , numPlayers);
                    tracker =  numPlayers - 1;
                } else {
                    toAdd = new Card(0 ,j % numPlayers);
                    tracker--;
                }
                rigged.add(toAdd)
            }
            /* shuffle turned off, fed rigged deck */
            warTest = new War(new CardDeck(rigged), numPlayers, numCards, 1, false, winTable);
            warTest.play();
            winner = this.idWinner();
            assertEquals(true, winner == projectedWinner);
            numPlayers++;
        }
    }

    /**
     * This test sets up a game in which a tie is inevitable. There are are many more
     * scenarios where this will happen, though it should be a relatively rare occurrence
     * overall.
     */
    void explicitTieScenario() {
        ArrayList<Card> rigged = new ArrayList<>();
        rigged.add(new Card(0, 0));
        rigged.add(new Card(0, 0));
        this.winTable = new boolean[2]
        this.warTest = new War(new CardDeck(rigged), 2, 2, 1, false, this.winTable);
        this.warTest.play();
        assertEquals(true, -1 == this.idWinner());
    }

    /**
     * This test sets up a game that undergoes a war scenario. It then plays the game
     * and asserts that the correct player won the game.
     */
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

    /**
     * Since the game is inherently random, this test ensures that the win/loss
     * distribution tends towards even partitions over an extended trial.  Four
     * players and a standard deck of cards are given to the game. It runs one
     * hundred thousand trials, while maintaining a win count for each individual
     * player. The results are then asserted to a tolerance of one percent.
     */
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
        System.out.println("Win proportions over the course of " + totalRematches + " games:")
        for(int i = 0; i < 4; ++i) {
            proportion = (float) winCounts[i] / totalRematches;
            System.out.println("Player " + i + "'s win proportion: " + proportion)
            assertEquals(true, proportion > 0.245 && proportion < 0.255);
        }
        proportion = (float) tieCount / totalRematches;
        System.out.println("A proportion of " + proportion + " ties occurred.");
    }

    /**
     * utility function to help identify game winners
     */
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