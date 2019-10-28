import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question {
  private final char PLACEHOLDER = '_';
  private Map<Character, List<Integer>> secretStringIndexMap;
  private StringBuilder currentString;

  public Question(String secretString) {
    initialize(secretString, PLACEHOLDER);
  }

  public Question(String secretString, char placeholder) {
    initialize(secretString, placeholder);
  }

  private void initialize(String secretString, char placeholder) {
    int length = secretString.length();
    currentString = new StringBuilder(length);
    secretStringIndexMap = new HashMap<>();
    for (int i = 0; i < length; i++) {
      char c = secretString.charAt(i);
      if (c == ' ') {
        currentString.append(' ');
      } else {
        List<Integer> list = secretStringIndexMap.get(c);
        if (list == null) {
          list = new ArrayList<Integer>();
          list.add(i);
          secretStringIndexMap.put(c, list);
        } else {
          list.add(i);
        }
        currentString.append(placeholder);
      }
    }
  }

  public boolean tryCharacter(char c) {
    List<Integer> list = secretStringIndexMap.get(c);
    if (list == null) {
      return false;
    }
    for (int i: list) {
      currentString.setCharAt(i, c);
    }
    secretStringIndexMap.remove(c);
    return true;
  }

  public String getCurrentString() {
    return currentString.toString();
  }

  public int getUnopenedCount() {
    return secretStringIndexMap.size();
  }
}
