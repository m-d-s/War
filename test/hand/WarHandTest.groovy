package hand

import card.Card;
/**
 * Testing of the WarHand class
 */
class WarHandTest extends GroovyTestCase {
    Card card;
    Hand toTest;
    Queue<Card> queue;

    void setUp() {
        super.setUp()
        card = new Card(1,0);
        queue = new LinkedList<>();
        toTest = new WarHand(queue);
    }

    /**
     * cards get added to the hand in fifo order and
     * size increases
     */
    void testAddCard() {
        toTest.addCard(card);
        toTest.addCard(new Card(0,1));
        assertEquals(true, queue.peek().suit  == card.suit &&
                           queue.peek().rank == card.rank);
        assertEquals(true, queue.size() == 2);
    }

    /**
     * cards get removed from the hand in fifo order and
     * size decreases
     */
    void testRemoveCard() {
        Card removed,
             other = new Card(0,1);

        toTest.addCard(card);
        toTest.addCard(other);
        removed = toTest.removeCard();
        assertEquals(true, removed.suit  == card.suit &&
                           removed.rank == card.rank);
        assertEquals(true, queue.size() == 1);
        removed = toTest.removeCard();
        assertEquals(true, removed.suit  == other.suit &&
                           removed.rank == other.rank);
        assertEquals(true, queue.isEmpty());
        assertEquals(true, queue.size() == 0);

    }

    /**
     * testing emptiness
     */
    void testIsEmpty() {
        assertEquals(true, toTest.isEmpty());
        queue.add(card)
        assertEquals(false, toTest.isEmpty());
    }

    /**
     * testing size properties
     */
    void testSize() {
        assertEquals(true, toTest.size() == 0);
        queue.add(card);
        assertEquals(true, toTest.size() == 1);
        queue.add(card);
        assertEquals(true, toTest.size() == 2);
    }
}
