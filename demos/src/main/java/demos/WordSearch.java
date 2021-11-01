package demos;

import edu.wofford.woclo.*;
import java.util.ArrayList;

public class WordSearch {
  private int width = 5;
  private int height = 5;
  private char[][] grid;
  private ArrayList<int[]> path;

  public WordSearch(String grid) {
    path = new ArrayList<int[]>();
    this.grid = new char[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.grid[i][j] = grid.charAt(width * i + j);
      }
    }
  }

  public WordSearch(String grid, int width, int height) {
    this.width = width;
    this.height = height;
    path = new ArrayList<int[]>();
    this.grid = new char[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.grid[i][j] = grid.charAt(width * i + j);
      }
    }
  }

  private boolean locationNotOnPath(int x, int y) {
    for (int i = 0; i < path.size(); i++) {
      if (path.get(i)[0] == x && path.get(i)[1] == y) return false;
    }
    return true;
  }

  private boolean isLetterAtLocation(char letter, int x, int y) {
    if (x < 0 || y < 0 || y >= width || x >= height) {
      return false;
    } else if (grid[x][y] == letter && locationNotOnPath(x, y)) {
      path.add(new int[] {x, y});
      return true;
    }
    return false;
  }

  private boolean findWordAtLocation(String word, int x, int y) {
    if (word.length() == 1) {
      if (isLetterAtLocation(word.charAt(0), y, x)) {
        return true;
      }
      return false;
    } else {
      return isLetterAtLocation(word.charAt(0), y, x)
          && (findWordAtLocation(word.substring(1), x + 1, y)
              || findWordAtLocation(word.substring(1), x - 1, y)
              || findWordAtLocation(word.substring(1), x, y + 1)
              || findWordAtLocation(word.substring(1), x, y - 1));
    }
  }

  public String findWord(String word) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        path.clear();
        if (findWordAtLocation(word, i, j)) {
          return getPathString();
        }
      }
    }
    return (word + " not found");
  }

  private String getPathString() {
    String output = "";
    int x, y;
    for (int i = 0; i < path.size(); i++) {
      x = path.get(i)[0];
      y = path.get(i)[1];
      output += grid[x][y] + ":" + Integer.toString(x + 1) + "," + Integer.toString(y + 1);
      if (i < path.size() - 1) output += " ";
    }
    return output;
  }

  public static void main(String... args) {
    String gridString = "softsweskaolzilklqmtreyoy";
    String word = "eskimo";
    int width = 5;
    int height = 5;

    // TODO implement parser library to set gridString, word, width, and height based off of values
    // pulled from command line

    WordSearch test = new WordSearch(gridString, width, height);
    System.out.println(test.findWord(word));
  }
}
