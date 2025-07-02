import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Deck implements Serializable{
    private static final long serialVersionUID = 1L;
    private ArrayList<Card> cards; // List to hold all the cards in the deck

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"hearts", "diamonds", "clubs", "spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "A"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(ranks[i], suit, values[i])); // Adds a new card with a value corresponding to its rank
            }
        }

        Collections.shuffle(cards); // Shuffles the deck after it is created
    }

    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0); // Draws a card from the top of the deck
        }
        return null;
    }

    public boolean isEmpty() {
        return cards.isEmpty(); // Checks if the deck is empty
    }

    public int size() {
        return cards.size(); // Returns the number of cards remaining in the deck
    }
}
