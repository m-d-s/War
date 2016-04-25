import card.Card
import deck.CardDeck
import hand.Hand
import player.Warrior
import deck.*

/**
 * Created by msimpson on 4/20/16.
 */
class WarriorTest extends GroovyTestCase {
    Hand hand;
    Deck deck;
    Card handCard;
    Warrior genghis;
    ArrayList<Card> topThree;

    void setUp() {
        super.setUp()
        genghis  = new Warrior("genghis");
        deck     = new CardDeck();
        hand     = genghis.getHand();
        topThree = new ArrayList<>();

        deck.create(4,13);
        topThree.add(deck.deal());
        topThree.add(deck.deal());
        topThree.add(deck.deal());
    }

    void testGetTopCard() {
        Card first  = deck.deal(),
             second = deck.deal();
        hand.addCard(first);
        hand.addCard(second);
        //hand exhibits queue behavior
        assertEquals(true, genghis.getTopCard().equals(first));
        assertEquals(true, genghis.getTopCard().equals(second));
    }

    void testPlaceAtBottom() {
        Card first  = topThree.remove(0),
             second = topThree.get(0),
             third  = topThree.get(1);

        //add the fourth card in the deck to the hand
        hand.addCard(first);
        //place top three cards at bottom of the hand
        genghis.placeAtBottom(topThree);
        //Cards come out ordered 4th, 1st, 2nd, 3rd
        assertEquals(true, genghis.getTopCard().equals(first));
        assertEquals(true, genghis.getTopCard().equals(second));
        assertEquals(true, genghis.getTopCard().equals(third));
    }

    void testGetHand() {
        hand.addCard(topThree.get(0));
        hand.addCard(topThree.get(1));
        hand.addCard(topThree.get(2));

        for(int i = 0; i < 3; ++i) {
            handCard = genghis.getTopCard();
            assertEquals(true, handCard.equals(topThree.get(i)));
            assertEquals(true, handCard.class.equals(topThree.get(i).class));
        }
    }

    void testGetName() {
        assertEquals(true, genghis.getName().equals("genghis"));
    }

    void testHasCards() {
        assertEquals(false, hand.isEmpty() == genghis.hasCards());
    }

    void testHandSize() {
        genghis.placeAtBottom(topThree);
        assertEquals(true, genghis.handSize() == 3);
        genghis.getTopCard();
        genghis.getTopCard();
        genghis.getTopCard();
    }

    void testDepleteHand() {
        genghis.placeAtBottom(topThree);
        assertEquals(false, hand.isEmpty());
        genghis.depleteHand();
        assertEquals(true, hand.isEmpty());
    }
}
