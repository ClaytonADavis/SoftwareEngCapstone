package demos;

import edu.wofford.woclo.*;
import java.awt.*;
import java.util.*;

public class WordSearch {
  private int width = 5;
  private int height = 5;
  private char[][] grid;
  private ArrayList<Point> path;
  private int[][] solution;
  int numVisit;

  public WordSearch(String grid) {
    path = new ArrayList<Point>();
    numVisit = 1;
    this.grid = new char[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.grid[i][j] = grid.charAt(width * i + j);
      }
    }
    this.solution = new int[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.solution[i][j] = 0;
      }
    }
  }

  public WordSearch(String grid, int width, int height) {
    this.width = width;
    numVisit = 1;
    this.height = height;
    path = new ArrayList<Point>();
    this.grid = new char[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.grid[i][j] = grid.charAt(width * i + j);
      }
    }
    this.solution = new int[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.solution[i][j] = 0;
      }
    }
  }

  private boolean searchWord(String target, int row, int col) {
    if (solution[row][col] != 0 || target.charAt(0) != grid[row][col]) {
      return false;
    }

    if (target.length() == 1) {
      solution[row][col] = numVisit++;
      return true;
    }

    solution[row][col] = numVisit++;

    if (row + 1 < grid.length && searchWord(target.substring(1), row + 1, col)) {
      return true;
    }
    if (row - 1 >= 0 && searchWord(target.substring(1), row - 1, col)) {
      return true;
    }
    if (col + 1 < grid[0].length && searchWord(target.substring(1), row, col + 1)) {
      return true;
    }
    if (col - 1 >= 0 && searchWord(target.substring(1), row, col - 1)) {
      return true;
    }

    solution[row][col] = 0;
    numVisit = numVisit - 1;
    return false;
  }

  private boolean exist(String target) {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (searchWord(target, i, j)) {
          return true;
        }
      }
    }
    return false;
  }

  private Point findPoint(int element) {
    int row = 0;
    int col = 0;
    for (int i = 0; i < solution.length; i++) {
      for (int j = 0; j < solution[0].length; j++) {
        if (solution[i][j] == element) {
          row = i + 1;
          col = j + 1;
        }
      }
    }
    Point z = new Point(row, col);
    return z;
  }

  private String toString(String target) {
    String s = "";
    for (int i = 1; i <= target.length(); i++) {
      path.add(findPoint(i));
    }
    int numEle = path.size();
    for (int i = 0; i < numEle; i++) {
      double xT = path.get(i).getX();
      double yT = path.get(i).getY();
      int x = (int) xT;
      int y = (int) yT;
      char c = target.charAt(i);
      String letter = String.valueOf(c);
      if (i == numEle - 1) {
        s = s + letter + ":" + x + "," + y;
      } else {
        s = s + letter + ":" + x + "," + y + " ";
      }
    }
    return s;
  }

  public String findWord(String target) {
    if (exist(target)) {
      return toString(target);
    } else {
      return (target + " not found");
    }
  }

  public static void main(String... args) {
    Parser parse = new Parser("WordSearch", "Find a target word in a grid.");
    parse.addIdentifier("grid", "the grid to search", "string");
    parse.addIdentifier("target", "the target word", "string");
    parse.addOptionalIdentifier("width", "the grid width", "integer", "5");
    parse.addOptionalIdentifier("height", "the grid height", "integer", "5");
    try {
      parse.parseCommandLine(args);
    } catch (TooManyArgsException e) {
      return;
    } catch (NotEnoughArgsException e) {
      return;
    } catch (HelpException e) {
      System.out.println(parse.getHelpMessage());
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
    if (width * height != grid.length()) {
      System.out.println(
          "WordSearch error: grid dimensions ("
              + width
              + " x "
              + height
              + ") do not match grid length ("
              + grid.length()
              + ")");
    } else {
      WordSearch searcher = new WordSearch(grid, width, height);
      System.out.println(searcher.findWord(target));
    }
  }
}
