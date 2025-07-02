import java.io.*;

public class Save {
    // Method to save the game state to a file
    public static void saveGame(WarGame game, String filepath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeObject(game); // Writes the game status to the file
        }
    }
    // Method to load a saved game from a file
    public static WarGame loadGame(String filepath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            return (WarGame) ois.readObject();// Reads the game status from the file
        }
    }
}


