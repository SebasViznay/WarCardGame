import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private Queue<Card> deck; // Queue to represent the player's deck of cards
    private ArrayList<Card> playedCards; // List to keep track of cards played by the player

    public Player() {
        deck = new LinkedList<>();
        playedCards = new ArrayList<>();
    }

    public void addCard(Card card) {
        deck.offer(card); // Adds a card to the player's deck
    }

    public Card playCard() {
        Card card = deck.poll(); // Plays a card from the top of the deck
        if (card != null) {
            playedCards.add(card);  // Adds the played card to the list of played cards
        }
        return card;
    }

    public void addCards(ArrayList<Card> cards) {
        deck.addAll(cards);
    }

    public int getCardCount() {
        return deck.size(); // Returns the number of cards in the players deck
    }

    public boolean hasCards() {
        return !deck.isEmpty();
    }

    public ArrayList<Card> getPlayedCards() {
        return playedCards; 
    }

    public void clearPlayedCards() {
        playedCards.clear();
    }

    public Card getLastPlayedCard() {
        if (playedCards.isEmpty()) {
            return null;
        }
        return playedCards.get(playedCards.size() - 1);
    }
}
