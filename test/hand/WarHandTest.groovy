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
        card = new Card(0,1);
        queue = new LinkedList<>();
        toTest = new WarHand(queue);
    }

    void testAddCard() {
        toTest.addCard(card);
        assertEquals(true, queue.peek().suit  == card.suit &&
                           queue.peek().value == card.value);
    }

    void testRemoveCard() {
        Card removed,
             other = new Card(1,0);

        toTest.addCard(card);
        toTest.addCard(other);
        removed = toTest.removeCard();
        assertEquals(true, removed.suit  == card.suit &&
                           removed.value == card.value);
        removed = toTest.removeCard();
        assertEquals(true, removed.suit  == other.suit &&
                           removed.value == other.value);
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
