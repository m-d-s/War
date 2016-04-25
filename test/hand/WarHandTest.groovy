package hand

import card.Card;
/**
 * Created by msimpson on 4/24/16.
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

    void testAddCard() {
        toTest.addCard(card);
        assertEquals(true, queue.peek().suit  == card.suit &&
                           queue.peek().rank == card.rank);
    }

    void testRemoveCard() {
        Card removed,
             other = new Card(0,1);

        toTest.addCard(card);
        toTest.addCard(other);
        removed = toTest.removeCard();
        assertEquals(true, removed.suit  == card.suit &&
                           removed.rank == card.rank);
        removed = toTest.removeCard();
        assertEquals(true, removed.suit  == other.suit &&
                           removed.rank == other.rank);
        assertEquals(true, queue.isEmpty());
    }

    void testIsEmpty() {
        assertEquals(true, toTest.isEmpty());
        queue.add(card)
        assertEquals(false, toTest.isEmpty());
    }

    void testSize() {
        assertEquals(true, toTest.size() == 0);
        queue.add(card);
        assertEquals(true, toTest.size() == 1);
        queue.add(card);
        assertEquals(true, toTest.size() == 2);
    }
}
