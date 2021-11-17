package demos;

import edu.wofford.woclo.*;
import java.util.*;

public class MaximalLayers {
  private ArrayList<ArrayList<Integer>> MaxXList;
  private ArrayList<ArrayList<Integer>> MaxYList;
  private ArrayList<Integer> pointList;
  private boolean sortX;
  private boolean sortY;

  public MaximalLayers(ArrayList<Integer> pointList, boolean sortX, boolean sortY) {
    MaxXList = new ArrayList<ArrayList<Integer>>();
    MaxYList = new ArrayList<ArrayList<Integer>>();
    this.pointList = pointList;
    this.sortX = sortX;
    this.sortY = sortY;
  }

  private void getLayer() {
    ArrayList<Integer> xs = new ArrayList<Integer>();
    ArrayList<Integer> ys = new ArrayList<Integer>();
    // get maximalY values
    boolean maxsLeft = true;
    Integer maxX = 0;
    Integer maxY = 0;
    while (maxsLeft) {
      maxsLeft = false;
      int maxI = 1;
      maxX = 0;
      for (int i = 1; i < pointList.size(); i += 2) {
        if (maxY <= pointList.get(i)) {
          maxX = pointList.get(i - 1);
          maxY = pointList.get(i);
          maxsLeft = true;
          maxI = i;
        }
      }
      if (maxsLeft) {
        xs.add(maxX);
        ys.add(maxY);
        // remove index at maxI then previous index
        pointList.remove(maxI);
        pointList.remove(maxI - 1);
      }
    }
    // get maximalX values
    maxsLeft = true;
    maxX = 0;
    maxY = 0;
    while (maxsLeft) {
      maxsLeft = false;
      int maxI = 0;
      maxY = 0;
      for (int i = 0; i < pointList.size() - 1; i += 2) {
        boolean mXs = true;
        for (int x : xs) {
          if (pointList.get(i) < x) mXs = false;
        }

        if (maxX <= pointList.get(i) && mXs) {
          maxX = pointList.get(i);
          maxY = pointList.get(i + 1);
          maxsLeft = true;
          maxI = i;
        }
      }
      if (maxsLeft) {
        xs.add(maxX);
        ys.add(maxY);
        /*remove index at maxI then next index.
        not maxI + 1 because next index becomes current index after first remove() call*/
        pointList.remove(maxI);
        pointList.remove(maxI);
      }
    }
    MaxXList.add(xs);
    MaxYList.add(ys);
  }

  /*
   * recursively call getLayer until pointList is empty.
   * Then sort by y and x if flags set.
   */
  public void findAllLayers() {
    if (pointList.size() != 0) {
      getLayer();
      findAllLayers();
    } else {
      if (sortY) sortY();
      if (sortX) sortX();
    }
  }

  private void sortX() {
    // TODO sort both lists by x values. not sure if should be asc or desc order
    for (int k = 0; k < MaxXList.size(); k++) {
      ArrayList<Integer> tempX = MaxXList.get(k);
      ArrayList<Integer> tempY = MaxYList.get(k);
      for (int i = 0; i < tempX.size(); i++) {
        int min = i;
        for (int j = i + 1; j < tempX.size(); j++) if (tempX.get(i) > tempX.get(j)) min = j;
        int keyX = tempX.get(min);
        int keyY = tempY.get(min);
        while (min > i) {
          tempX.set(min, tempX.get(min - 1));
          tempY.set(min, tempY.get(min - 1));
          min--;
        }
        tempX.set(i, keyX);
        tempY.set(i, keyY);
      }
    }
  }

  private void sortY() {
    // TODO sort both lists by y values. Not sure if should be asc or desc order
    for (int k = 0; k < MaxYList.size(); k++) {
      ArrayList<Integer> tempX = MaxXList.get(k);
      ArrayList<Integer> tempY = MaxYList.get(k);
      for (int i = 0; i < tempY.size(); i++) {
        int min = i;
        for (int j = i + 1; j < tempY.size(); j++) if (tempY.get(i) > tempY.get(j)) min = j;
        int keyX = tempX.get(min);
        int keyY = tempY.get(min);
        while (min > i) {
          tempX.set(min, tempX.get(min - 1));
          tempY.set(min, tempY.get(min - 1));
          min--;
        }
        tempX.set(i, keyX);
        tempY.set(i, keyY);
      }
    }
  }

  public String toString() {
    String output = "";
    for (int i = 0; i < MaxXList.size(); i++) {
      output += (i + 1) + ":";
      for (int j = 0; j < MaxXList.get(i).size(); j++) {
        output += "(" + MaxXList.get(i).get(j) + "," + MaxYList.get(i).get(j) + ")";
      }
      if (i < MaxXList.size() - 1) output += " ";
    }
    return output;
  }

  public static void main(String[] args) {
    // TODO implement parser library here
    Parser parse = new Parser("MaximalLayers", "Sort the points into layers.");
    parse.addIdentifier("points", "the data points");
    parse.addOptionalIdentifier("sortedX", "sort layers by x coordinate");
    parse.addOptionalIdentifier("sortedY", "sort layers by y coordinate");
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

    String input = "5,5,10,2,2,3,15,7,2,14,1,1,15,2,1,7,7,7,1,4,12,10,15,15";
    String input1 = parse.getValue("points");
    String[] tmp = input1.split(",");
    ArrayList<Integer> points = new ArrayList<Integer>();
    for (int i = 0; i < tmp.length; i++) {
      try {
        points.add(Integer.parseInt(tmp[i]));
      } catch (NumberFormatException e) {
        System.out.println("MaximalLayers error: the value " + tmp[i] + " is not of type integer");
      }
    }
    boolean sortX = parse.getValue("sortedX");
    boolean sortY = parse.getValue("sortedY");

    MaximalLayers test = new MaximalLayers(points, sortX, sortY);
    if (points.size() % 2 == 0) {
      test.findAllLayers();
      System.out.println("\n" + test.toString());
    } else {
      System.out.println(
          "MaximalLayers error: " + points.get(points.size() - 1) + " is an unpaired x coordinate");
    }
  }
}
