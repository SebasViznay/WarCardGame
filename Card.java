import java.io.Serializable;

public class Card implements Serializable{
    private static final long serialVersionUID = 1L;
    private String rank;  // The rank of the card (e.g., "Ace", "2", "King")
    private String suit;  // The suit of the card (e.g., "hearts", "spades")
    private int value;  // The value of the card used for comparing cards in the game

    public Card(String rank, String suit, int value) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;  // Returns a string representation of the card
    }

    public String getImageName() {
        return rank.toLowerCase() + "_of_" + suit.toLowerCase();
    }
}
