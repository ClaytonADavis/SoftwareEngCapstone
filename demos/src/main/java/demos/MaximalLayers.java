package demos;

import edu.wofford.woclo.*;
import java.util.*;

public class MaximalLayers {
  private ArrayList<int[]> points;
  private ArrayList<ArrayList<int[]>> layers;
  private boolean sortX;
  private boolean sortY;

  public MaximalLayers(ArrayList<int[]> points, boolean sortX, boolean sortY) {
    this.points = points;
    layers = new ArrayList<ArrayList<int[]>>();
    this.sortX = sortX;
    this.sortY = sortY;
  }

  private boolean isMaximal(int[] p) {
    for (int i = 0; i < points.size(); i++) {
      if (p[0] < points.get(i)[0] && p[1] < points.get(i)[1]) {
        return false;
      }
    }

    return true;
  }

  private void getLayer() {
    ArrayList<int[]> l = new ArrayList<int[]>();
    ArrayList<int[]> tmpPoints = (ArrayList<int[]>) points.clone();
    // check every element of point to see if it is maximal
    for (int i = 0; i < points.size(); i++) {
      int[] p = points.get(i);
      if (isMaximal(p)) {
        l.add(p);
        tmpPoints.remove(p);
      }
    }
    layers.add(l);
    points = (ArrayList<int[]>) tmpPoints.clone();
  }

  public void findAllLayers() {
    if (points.size() != 0) {
      getLayer();
      findAllLayers();
    } else {
      if (sortY) sortByIndex(1);
      if (sortX) sortByIndex(0);
    }
  }

  private void sortByIndex(int index) {
    // TODO sort both lists by x values. not sure if should be asc or desc order
    for (int k = 0; k < layers.size(); k++) {
      ArrayList<int[]> temp = (ArrayList<int[]>) layers.get(k).clone();
      for (int i = 0; i < temp.size(); i++) {
        int[] t = temp.get(i);
        int ti = i - 1;
        while (ti > -1 && temp.get(ti)[index] > t[index]) {
          temp.set(ti + 1, temp.get(ti));
          ti--;
        }
        temp.set(ti + 1, t);
      }
      layers.set(k, (ArrayList<int[]>) temp.clone());
    }
  }

  public String toString() {
    String output = "";
    for (int i = 0; i < layers.size(); i++) {
      output += (i + 1) + ":";
      for (int j = 0; j < layers.get(i).size(); j++) {
        output += "(" + layers.get(i).get(j)[0] + "," + layers.get(i).get(j)[1] + ")";
      }
      if (i < layers.size() - 1) output += " ";
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
    ArrayList<int[]> points = new ArrayList<int[]>();
    boolean noUnpaired = tmp.length % 2 == 0;

    for (int i = 0; i < tmp.length - 1; i += 2) {
      int x = 0;
      int y = 0;
      try {
        x = Integer.parseInt(tmp[i]);
      } catch (NumberFormatException e) {
        System.out.println("MaximalLayers error: the value " + tmp[i] + " is not of type integer");
      }
      try {
        y = Integer.parseInt(tmp[i + 1]);
      } catch (NumberFormatException e) {
        System.out.println(
            "MaximalLayers error: the value " + tmp[i + 1] + " is not of type integer");
      }
      int[] p = {x, y};
      points.add(p);
    }

    boolean sortX = parse.getValue("sortedX");
    boolean sortY = parse.getValue("sortedY");

    MaximalLayers test = new MaximalLayers(points, sortX, sortY);
    if (noUnpaired) {
      test.findAllLayers();
      System.out.println("\n" + test.toString());
    } else {
      System.out.println(
          "MaximalLayers error: " + tmp[tmp.length - 1] + " is an unpaired x coordinate");
    }
  }
}
