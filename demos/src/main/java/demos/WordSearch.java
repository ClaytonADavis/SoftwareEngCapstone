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
    StringBuffer output = new StringBuffer();
    int x, y;
    for (int i = 0; i < path.size(); i++) {
      x = path.get(i)[0];
      y = path.get(i)[1];
      output.append(grid[x][y] + ":" + Integer.toString(x + 1) + "," + Integer.toString(y + 1));
      if (i < path.size() - 1) output.append(" ");
    }
    return output.toString();
  }

  public String getPathString2(ArrayList<int[]> p) {
    StringBuffer output = new StringBuffer();
    int x, y;
    for (int i = 0; i < p.size(); i++) {
      x = p.get(i)[0];
      y = p.get(i)[1];
      output.append(grid[x][y] + ":" + Integer.toString(x + 1) + "," + Integer.toString(y + 1));
      if (i < p.size() - 1) output.append(" ");
    }
    return output.toString();
  }

  public ArrayList<int[]> getPath() {
    return path;
  }

  public static void main(String... args) {
    String[] idArray = {"grid", "target"};
    String[] idType = {"String", "String"};
    Parser parse = new Parser("WordSearch", idArray, idType);
    parse.addIdentifier("width", "int", "5");
    parse.addIdentifier("height", "int", "5");
    try {
      parse.parseCommandLine(args);
    } catch (TooManyArgsException e) {
      return;
    } catch (NotEnoughArgsException e) {
      return;
    } catch (HelpException e) {
      System.out.println(
          "usage: java WordSearch [-h] [--width WIDTH] [--height HEIGHT] grid target\n\nFind a target word in a grid.\n\npositional arguments:\n grid             (string)      the grid to search\n target           (string)      the target word\n\nnamed arguments:\n -h, --help       show this help message and exit\n --width WIDTH    (integer)     the grid width (default: 5)\n --height HEIGHT  (integer)     the grid height (default: 5)");
      return;
    } catch (MissingArgumentException e) {
      return;
    }
    String grid = "";
    String target = "";
    int width = 0;
    int height = 0;
    try {
      grid = parse.getValue("grid");
      target = parse.getValue("target");
      width = parse.getValue("width");
      height = parse.getValue("height");
    } catch (IncorrectArgumentTypeException e) {
      return;
    }
    if ((width * height) != grid.length()) {
      System.out.println(
          "WordSearch error: grid dimensions ("
              + width
              + " x "
              + height
              + ") do not match grid length ("
              + grid.length()
              + ")");
    }
    // TODO implement parser library to set gridString, word, width, and height based off of values
    // pulled from command line

    WordSearch test = new WordSearch(grid, width, height);
    // System.out.println(test.findWord(target));
    // System.out.println(test.getGridString());
    // System.out.println(test.findWord(word));
    // System.out.println(test.getPathString());

    String s1 = test.findWord(target);
    String rs1 = "";
    for (int i = 0; i < target.length(); i++) {
      rs1 += String.valueOf(target.charAt(target.length() - i - 1));
      // .add(p2.get(p2.size() - i - 1));
    }
    WordSearch test2 = new WordSearch(grid, width, height);
    // System.out.println(test2.findWord(rs1));
    String s2 = test2.findWord(rs1);

    ArrayList<int[]> p1 = test.getPath();
    ArrayList<int[]> p2 = test2.getPath();
    if (p1.size() == 0 && p2.size() > 0) {
      ArrayList<int[]> rp2 = new ArrayList<int[]>();
      for (int i = 0; i < p2.size(); i++) {
        p1.add(p2.get(p2.size() - i - 1));
      }
      System.out.println(test.getPathString2(p1));
    } else if (p1.size() > 0 && p2.size() == 0) {
      System.out.println(test.getPathString2(p1));
    } else if (p1.size() == 0 && p2.size() == 0) {
      System.out.println(target + " not found");
    } else {

      int s = p1.size();

      ArrayList<int[]> p = new ArrayList<int[]>();
      for (int i = 0; i < s; i++) {
        for (int j = 0; j < p2.size(); j++) {
          if (p1.get(i)[0] == p2.get(j)[0] && p1.get(i)[1] == p2.get(j)[1]) {
            p.add(p1.get(i));
          }
        }
      }

      System.out.println(test.getPathString2(p));
    }
  }
}
