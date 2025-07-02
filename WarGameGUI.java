import javax.swing.*; // Import for Java Swing components (e.g., JFrame, JLabel, JButton)
import javax.swing.border.EmptyBorder; // Import for creating borders around components
import java.awt.*; // Import for AWT components and layout managers
import java.awt.event.ActionEvent; // Import for handling action events
import java.awt.event.ActionListener; // Import for receiving action events
import java.io.IOException;
import java.net.URL; // Import for handling URLs
import java.util.HashMap; // Import for using HashMap collection
import java.util.List; // Import for using List collection

public class WarGameGUI {
    private JFrame frame; // Main frame of the GUI
    private WarGame game; // Instance of the WarGame class
    private JLabel player1Cards; // Label to display the number of cards for player 1
    private JLabel player2Cards; // Label to display the number of cards for player 2
    private JTextArea gameLog; // Text area to display the game log
    private JLabel player1CardImageFaceUp; // Label for player 1's face-up card image
    private JLabel player1CardImageFaceDown; // Label for player 1's face-down card image
    private JLabel player2CardImageFaceUp; // Label for player 2's face-up card image
    private JLabel player2CardImageFaceDown; // Label for player 2's face-down card image
    private JLabel warPlayer1CardImageFaceUp; // Label for player 1's war card
    private JLabel warPlayer2CardImageFaceUp; // Label for player 2's war card
    private JPanel endGamePanel; // Panel to display the end game message
    private JLabel endGameLabel; // Label for the end game image
    private JLabel endGameMessageLabel; // Label for the end game message text
    private HashMap<String, ImageIcon> cardImages; // HashMap to store card images

    private void saveGame(String fileName) {
        try {
            Save.saveGame(game, fileName);
            JOptionPane.showMessageDialog(frame, "Game saved successfully!", "Save Game", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Failed to save the game: " + e.getMessage(), "Save Game", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadGame(String fileName) {
        try {
            game = Save.loadGame(fileName);
            updateCardCounts();
            clearCardImages();
            gameLog.setText(""); // Clear the log as the loaded game doesn't have the old log
            JOptionPane.showMessageDialog(frame, "Game loaded successfully!", "Load Game", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Failed to load the game: " + e.getMessage(), "Load Game", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showSaveDialog() {
        String fileName = JOptionPane.showInputDialog(frame, "Enter file name to save:", "Save Game", JOptionPane.PLAIN_MESSAGE);
        if (fileName != null && !fileName.trim().isEmpty()) {
            saveGame(fileName.trim() + ".ser");
        }
    }
    
    private void showLoadDialog() {
        String fileName = JOptionPane.showInputDialog(frame, "Enter file name to load:", "Load Game", JOptionPane.PLAIN_MESSAGE);
        if (fileName != null && !fileName.trim().isEmpty()) {
            loadGame(fileName.trim() + ".ser");
        }
    }

    public WarGameGUI() {
        loadCardImages();
        game = new WarGame();
        frame = new JFrame("War Card Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 600);  // Resizes the GUI 
        frame.setLocationRelativeTo(null); // Centers the frame on screen
        frame.setLayout(new BorderLayout());

        // Create top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Create the "Group Members" button and add it to the members button panel
        JButton membersButton = new RoundedButton("Group Members");
        topPanel.add(membersButton);

        // Create mid panel to visualize the cards
        JPanel midPanel = new JPanel();
        midPanel.setLayout(new GridBagLayout()); // Uses GridBagLayout for flexible component positioning
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Adds padding between components

        player1Cards = new JLabel("Student: 26 cards"); // Label to display the number of cards for player 1
        player2Cards = new JLabel("Computer: 26 cards"); // Label to display the number of cards for player 2
        player1CardImageFaceUp = new JLabel(); // Label for player 1's face-up card image
        player1CardImageFaceDown = new JLabel(resizeImage(cardImages.get("back"))); // Label for player 1's face-down card image
        player2CardImageFaceUp = new JLabel(); // Label for player 2's face-up card image
        player2CardImageFaceDown = new JLabel(resizeImage(cardImages.get("back"))); // Label for player 2's face-down card image
        warPlayer1CardImageFaceUp = new JLabel();
        warPlayer2CardImageFaceUp = new JLabel();

        gbc.gridx = 0;
        gbc.gridy = 0;
        midPanel.add(player1Cards, gbc); // Adds player 1's card count label to the top panel

        gbc.gridx = 1;
        gbc.gridy = 0;
        midPanel.add(player1CardImageFaceDown, gbc); // Adds player 1's face-down card image to the top panel

        gbc.gridx = 2;
        gbc.gridy = 0;
        midPanel.add(player1CardImageFaceUp, gbc); // Adds player 1's face-up card image to the top panel

        gbc.gridx = 3;
        gbc.gridy = 0;
        midPanel.add(player2CardImageFaceUp, gbc); // Adds player 2's face-up card image to the top panel

        gbc.gridx = 4;
        gbc.gridy = 0;
        midPanel.add(player2CardImageFaceDown, gbc); // Adds player 2's face-down card image to the top panel

        gbc.gridx = 5;
        gbc.gridy = 0;
        midPanel.add(player2Cards, gbc); // Adds player 2's card count label to the top panel

        // Adding components for War display
        gbc.gridx = 2;
        gbc.gridy = 1;
        midPanel.add(warPlayer1CardImageFaceUp, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        midPanel.add(warPlayer2CardImageFaceUp, gbc);

        gameLog = new JTextArea();
        gameLog.setEditable(false); // Makes the game log read-only
        JScrollPane scrollPane = new JScrollPane(gameLog); // Adds the game log to a scroll pane

        endGamePanel = new JPanel();
        endGamePanel.setLayout(new BoxLayout(endGamePanel, BoxLayout.Y_AXIS)); // Uses vertical BoxLayout for the end game panel
        endGamePanel.setOpaque(false); // Makes the end game panel transparent
        endGamePanel.setVisible(false); // Hides the end game panel initially

        endGameLabel = new JLabel();
        endGameLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centers the end game label horizontally
        endGameLabel.setVerticalAlignment(SwingConstants.CENTER); // Centers the end game label vertically

        endGameMessageLabel = new JLabel();
        endGameMessageLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centers the end game message label horizontally
        endGameMessageLabel.setVerticalAlignment(SwingConstants.CENTER); // Centers the end game message label vertically
        endGameMessageLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Sets the font for the end game message
        endGameMessageLabel.setForeground(Color.WHITE); // Sets the text color for the end game message

        endGamePanel.add(endGameLabel); // Adds the end game label to the end game panel
        endGamePanel.add(endGameMessageLabel); // Adds the end game message label to the end game panel

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 6;
        midPanel.add(endGamePanel, gbc); // Adds the end game panel to the mid panel

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5)); // Uses GridLayout to arrange buttons in a row
        JButton playButton = new RoundedButton("Play Round");
        JButton resetButton = new RoundedButton("New Game");
        JButton rulesButton = new RoundedButton("Game Rules");
        JButton saveButton = new RoundedButton("Save Game");
        JButton loadButton = new RoundedButton("Load Game");

        playButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                playRound(); // Calls playRound() when the play button is clicked
            }
        });

        resetButton.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                restartGame(); // Calls restartGame() when the reset button is clicked
            }
        });

        rulesButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(ActionEvent e) {
                showRules(); // Calls showRules() when the rules button is clicked
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveGame(null); // Saves a status of a game
            }
        });
        
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadGame(null); // Load a previous status of a game
            }
        });

        // Adds action listener to the creators button
        membersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCreators(); // Calls showCreators() when the creators button is clicked
            }
        });
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSaveDialog();
            }
        });
        
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLoadDialog();
            }
        });
            
        bottomPanel.add(playButton); // Adds the play button to the bottom panel
        bottomPanel.add(resetButton); // Adds the reset button to the bottom panel
        bottomPanel.add(rulesButton); // Adds the rules button to the bottom panel
        bottomPanel.add(saveButton); // Add Save button to bottom panel
        bottomPanel.add(loadButton); // Add Load button to bottom panel

        frame.add(topPanel,BorderLayout.NORTH); // Adds the top panel to the frame at the top  
        frame.add(midPanel, BorderLayout.CENTER); // Adds the middle panel to the frame at the top
        frame.add(scrollPane, BorderLayout.EAST); // Adds the scroll pane (game log) to the center of the frame
        frame.add(bottomPanel, BorderLayout.SOUTH); // Adds the bottom panel to the frame at the bottom

        showRulesOnStartup(); // Displays the game rules at startup
        frame.setVisible(true); // Makes the frame visible
    }

    // Method to display a message dialog with the creators' names
    private void showCreators() {
        String creators = "Created by:\n-Marco Sebastian Vizñay Cabrera\n-Lucas Henrique de Assis Pacheco\n-Erich La Torre Boeta Kirsten\n-Mohamad Ali Touma\n-Fantola Qudus Ademola\n-Adhamjon Karimov\n-Alan Kakkanattu Saji";
        JOptionPane.showMessageDialog(frame, creators, "Creators", JOptionPane.INFORMATION_MESSAGE); // Shows creators' names in a message dialog
    }

    private void playRound() {
        if (game.isGameOver()) {
            checkGameEnd("Game over");
            return;
        }

        String result = game.playRound(); // Plays a round and gets the result
        updateCardImages(); // Updates the displayed card images
        gameLog.append(result + "\n"); // Appends the result to the game log
        updateCardCounts(); // Updates the displayed card counts
        checkGameEnd(result); // Checks if the game has ended
    }

    private void updateCardCounts() {
        player1Cards.setText("Student: " + game.getPlayer1().getCardCount() + " cards"); // Updates player 1 card count
        player2Cards.setText("Computer: " + game.getPlayer2().getCardCount() + " cards"); // Updates player 2 card count
    }

    private void checkGameEnd(String result) {
        if (game.isGameOver()) {
            if (game.getWinner().equals(game.getPlayer1())) {
                endGameLabel.setIcon(resizeImage(new ImageIcon(getClass().getClassLoader().getResource("resources/smile.png")))); // Sets winning image
                endGameMessageLabel.setText("You won! Congratulations!");
                showEndGameDialog("You won! Congratulations!");
            } else {
                endGameLabel.setIcon(resizeImage(new ImageIcon(getClass().getClassLoader().getResource("resources/sad.png")))); // Sets losing image
                endGameMessageLabel.setText("You lost! Good luck next time!");
                showEndGameDialog("You lost! Good luck next time!");
            }
            endGamePanel.setVisible(true); // Shows end game panel
        } else {
            endGamePanel.setVisible(false); // Hides end game panel
        }
    }

    private void showEndGameDialog(String message) {
        int response = JOptionPane.showOptionDialog(frame, message, "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"Play Again", "Leave"}, "Play Again");

        if (response == JOptionPane.YES_OPTION) {
            restartGame(); // Restarts the game if player chooses to play again
        } else if (response == JOptionPane.NO_OPTION) {
            System.exit(0); // Exits the game if player chooses to leave
        }
    }

    private void showRulesOnStartup() {
        String rules = getGameRules();
        JOptionPane.showMessageDialog(frame, rules, "Game Rules", JOptionPane.INFORMATION_MESSAGE); // Shows rules on startup
    }

    private void showRules() {
        String rules = getGameRules();
        JOptionPane.showMessageDialog(frame, rules, "Game Rules", JOptionPane.INFORMATION_MESSAGE); // Shows rules on button click
    }

    private String getGameRules() {
        return "Rules of War Card Game:\n" +
               "1. The objective of the game is to win all of the cards.\n" +
               "2. The player with the higher card takes both cards and moves them to their stack.\n" +
               "3. If the two cards played are of equal value, then there is a 'war'.\n" +
               "4. Both players place the next 2 cards face down and then another card face-up.\n" +
               "5. The owner of the higher face-up card wins the war and adds all the cards on the table to their deck.\n" +
               "6. If the face-up cards are equal, the battle repeats with another set of face-down/up cards.\n" +
               "7. If a player runs out of cards during a war, they immediately lose. Otherwise, they may play the last card in their deck for the remainder of the war.\n" +
               "8. The game continues until one player has collected all of the cards.";
    }

    private void loadCardImages() {
        cardImages = new HashMap<>();
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "A"};
        String[] suits = {"hearts", "diamonds", "clubs", "spades"};

        ClassLoader classLoader = getClass().getClassLoader();

        for (String suit : suits) {
            for (String rank : ranks) {
                String cardName = rank + "_of_" + suit;
                String imagePath = "resources/" + cardName + ".png";  // Note: no leading slash
                URL imageURL = classLoader.getResource(imagePath);
                if (imageURL == null) {
                    System.out.println("Image not found: " + imagePath); // Logs if image not found
                } else {
                    System.out.println("Loaded image: " + imagePath); // Logs loaded image
                    cardImages.put(cardName, new ImageIcon(imageURL)); // Puts image in HashMap
                }
            }
        }
        URL backImageURL = classLoader.getResource("resources/back.png");
        if (backImageURL != null) {
            cardImages.put("back", new ImageIcon(backImageURL)); // Loads back image
        } else {
            System.out.println("Image not found: resources/back.png"); // Logs if back image not found
        }
    }

    private void updateCardImages() {
        List<Card> player1CardsList = game.getPlayer1().getPlayedCards(); // Gets played cards for player 1
        List<Card> player2CardsList = game.getPlayer2().getPlayedCards(); // Gets played cards for player 2
    
        if (!player1CardsList.isEmpty() && !player2CardsList.isEmpty()) {
            // Update player 1 and player 2 face-up cards
            player1CardImageFaceUp.setIcon(resizeImage(cardImages.get(player1CardsList.get(0).getRank() + "_of_" + player1CardsList.get(0).getSuit())));
            player2CardImageFaceUp.setIcon(resizeImage(cardImages.get(player2CardsList.get(0).getRank() + "_of_" + player2CardsList.get(0).getSuit())));
    
            // Check if it's a war
            if (player1CardsList.size() > 1 && player2CardsList.size() > 1) {
                // Update war card images for player 1 and player 2
                warPlayer1CardImageFaceUp.setIcon(resizeImage(cardImages.get(player1CardsList.get(1).getRank() + "_of_" + player1CardsList.get(1).getSuit())));
                warPlayer2CardImageFaceUp.setIcon(resizeImage(cardImages.get(player2CardsList.get(1).getRank() + "_of_" + player2CardsList.get(1).getSuit())));
            } else {
                // Clear war card images if it's not a war
                warPlayer1CardImageFaceUp.setIcon(null);
                warPlayer2CardImageFaceUp.setIcon(null);
            }
        } else {
            clearCardImages();
        }
    }
    

    private ImageIcon resizeImage(ImageIcon imageIcon) {
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(120, 170,  java.awt.Image.SCALE_SMOOTH); // Resizes image to fit 
        return new ImageIcon(newImg);
    }

    private void clearCardImages() {
        player1CardImageFaceUp.setIcon(resizeImage(cardImages.get("back"))); // Sets back image for player 1
        player2CardImageFaceUp.setIcon(resizeImage(cardImages.get("back"))); // Sets back image for player 2
    }

    private void restartGame() {
        game = new WarGame(); // Creates a new game instance
        gameLog.setText(""); // Clears the game log
        updateCardCounts(); // Updates the card counts
        clearCardImages(); // Clears card images
        endGamePanel.setVisible(false); // Hides end game panel
    }

    public static void main(String[] args) {
        System.out.println("Classpath:");
        System.out.println(System.getProperty("java.class.path")); // Prints classpath
        SwingUtilities.invokeLater(() -> new WarGameGUI()); // Starts the GUI on the Event Dispatch Thread
    }
}

class RoundedButton extends JButton {
	private static final long serialVersionUID = 1L;

	public RoundedButton(String label) {
        super(label);
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.WHITE);
        setBackground(new Color(120, 120, 120));  
        setFont(new Font("Arial", Font.BOLD, 18));
        setBorder(new EmptyBorder(10, 20, 10, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Draws rounded rectangle for button
        super.paintComponent(g2);
        g2.dispose();
    }
}
