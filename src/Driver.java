import java.nio.file.NoSuchFileException;

public class Driver {
  public static void main(String[] args) {
    GameController gameController = null;
    try {
      gameController = new GameController(
        "../data/cities.txt",
        10
      );
    }
    catch (NoSuchFileException e) {
      System.err.printf("Could not start Game: file not found: %s\n", e.getFile());
    }
    catch (Exception e) {
      System.err.printf("Could not start Game: %s\n", e);
    }
    if (gameController == null) {
      return;
    }
    gameController.play();
  }
}
