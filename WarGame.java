import java.io.Serializable;
import java.util.ArrayList;

public class WarGame implements Serializable{
    private static final long serialVersionUID = 1L;
    private Player player1;
    private Player player2;
    private Deck deck;

    public WarGame() {
        deck = new Deck();
        player1 = new Player();
        player2 = new Player();
        dealCards(); // Deals the cards to both players at the start of the game
    }

    private void dealCards() {
        while (!deck.isEmpty()) {
            player1.addCard(deck.drawCard()); // Deals a card to player 1
            player2.addCard(deck.drawCard()); // Deals a card to player 2
        }
    }

    public String playRound() {
        if (!player1.hasCards() || !player2.hasCards()) {
            return "Game over";
        }

        player1.clearPlayedCards();
        player2.clearPlayedCards();

        ArrayList<Card> warPile = new ArrayList<>(); // Pile of cards involved in the current round
        Card player1Card = player1.playCard();
        Card player2Card = player2.playCard();

        warPile.add(player1Card); // Adds player 1's card to the pile
        warPile.add(player2Card); // Adds player 2's card to the pile

        if (player1Card.getValue() > player2Card.getValue()) {
            player1.addCards(warPile); // Player 1 wins and takes all cards in the pile
            return "Student wins the round!";
        } else if (player1Card.getValue() < player2Card.getValue()) {
            player2.addCards(warPile); // Player 2 wins and takes all cards in the pile
            return "Computer wins the round!";
        } else {
            return war(player1Card, player2Card, warPile); // A tie occurs, leading to war
        }
    }

    private String war(Card player1Card, Card player2Card, ArrayList<Card> warPile) {
        // Check if both players have enough cards to continue the war
        while (player1Card.getValue() == player2Card.getValue()) {
            if (player1.getCardCount() < 4 || player2.getCardCount() < 4) {
                // If either player cannot continue the war, the other player wins
                if (player1.getCardCount() < 4) {
                    return "Computer wins the game!";
                } else {
                    return "Student wins the game!";
                }
            }
    
            // Add two cards face-down from each player
            //for (int i = 0; i < 1; i++) {
            //    warPile.add(player1.playCard());
           //     warPile.add(player2.playCard());
           // }
    
            // Get the next card face-up from each player
            player1Card = player1.playCard();
            player2Card = player2.playCard();
    
            // Add face-up cards to the war pile
            warPile.add(player1Card);
            warPile.add(player2Card);
    
            // Display cards played in the war
            StringBuilder warMessage = new StringBuilder();
            warMessage.append("War: ");
            for (Card card : warPile) {
                warMessage.append(card.toString()).append(", ");
            }
            warMessage.delete(warMessage.length() - 2, warMessage.length()); // Remove the last comma and space
            System.out.println(warMessage.toString());
    
            // Compare face-up cards to determine the winner of the war
            if (player1Card.getValue() > player2Card.getValue()) {
                player1.addCards(warPile); // Player 1 wins the war and takes all cards
                return "Student wins the war!";
            } else if (player1Card.getValue() < player2Card.getValue()) {
                player2.addCards(warPile); // Player 2 wins the war and takes all cards
                return "Computer wins the war!";
            }
        }
        return "";
    }    

    public Player getPlayer1() {
        return player1;  // Returns player 1
    }

    public Player getPlayer2() {
        return player2; // Returns player 2
    }

    public boolean isGameOver() {
        return !player1.hasCards() || !player2.hasCards(); // Checks if the game is over
    }

    public Player getWinner() {
        if (!player1.hasCards()) {
            return player2; // Returns player 2 as the winner if player 1 is out of cards
        } else {
            return player1; // Returns player 1 as the winner if player 2 is out of cards
        }
    }
}
