import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class GameController {
  private final static Random RAND = new Random();
  private final static Scanner STDIN_SCANNER = new Scanner(System.in);
  private static final String ANSI_GREEN = "\u001B[32m";
  private static final String ANSI_RESET = "\u001B[0m";

  private List<String> questionList = new ArrayList<>();
  private int failureLimit = 10;

  public GameController(String filePath) throws Exception {
    BufferedReader br = Files.newBufferedReader(Paths.get(filePath));
    String line;
    while ((line = br.readLine()) != null) {
      if (line.trim().isEmpty()) {
        continue;
      }
      questionList.add(line);
    }
  }

  public GameController(String filePath, int failureLimit) throws Exception {
    this(filePath);
    this.failureLimit = failureLimit;
  }

  public GameController(String[] questionArray) {
    this.questionList = Arrays.asList(questionArray);
  }

  public GameController(String[] questionArray, int failureLimit) {
    this(questionArray);
    this.failureLimit = failureLimit;
  }

  private String getQuestionString() {
    return questionList.get(RAND.nextInt(questionList.size()));
  }

  public void play() {
    Set<Character> hitChars = new HashSet<>();
    int failureCount = 0;
    StringBuilder failedCharactersString = new StringBuilder(failureLimit * 2);

    String questionString = getQuestionString();
    Question question = new Question(questionString);
    System.out.println("Here's the question.");
    System.out.println(question.getCurrentString());
    while (true) {
      if (failureCount == failureLimit) {
        System.out.println("You lose!");
        System.out.printf("The correct word was '%s'!\n", questionString);
        return;
      }

      char inputCharacter = '_';
      try {
        System.out.print("Guess a letter: ");
        System.out.print(ANSI_GREEN);
        String input = STDIN_SCANNER.nextLine();
        if (input.length() != 1) {
          throw new Exception();
        }
        inputCharacter = input.charAt(0);
        System.out.print(ANSI_RESET);
      }
      catch (Exception e) {
        System.out.print(ANSI_RESET);
        continue;
      }

      if (!hitChars.contains(inputCharacter)) {
        if (question.tryCharacter(inputCharacter)) {
          hitChars.add(inputCharacter);
          if (question.getUnopenedCount() == 0) {
            break;
          }
        } else {
          failedCharactersString.append(new char[]{inputCharacter, ' '});
          failureCount++;
        }
      }

      System.out.printf("You are guessing: %s\n", question.getCurrentString());
      System.out.printf("You have guessed (%d) wrong letters: %s\n", failureCount, failedCharactersString);
    }

    System.out.println("You win!");
    System.out.printf("You have guessed '%s' correctly.\n", question.getCurrentString());
  }
}
