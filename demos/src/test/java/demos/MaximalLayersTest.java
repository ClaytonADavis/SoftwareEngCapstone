package demos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.Test;

class MaximalLayersTest {
  @Test
  void testMaximalLayers() {
    String input1 = "5,5,10,2,2,3,15,7,2,14,1,1,15,2,1,7,7,7,1,4,12,10,15,15";
    String[] tmp = input1.split(",");
    ArrayList<int[]> testPoints = new ArrayList<int[]>();
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
      testPoints.add(p);
    }
    boolean sortX = false;
    boolean sortY = false;
    MaximalLayers test = new MaximalLayers(testPoints, sortX, sortY);
    test.findAllLayers();
    assertEquals(
        "1:(15,7)(15,2)(15,15) 2:(2,14)(12,10) 3:(10,2)(1,7)(7,7) 4:(5,5) 5:(2,3)(1,4) 6:(1,1)",
        test.toString());
  }

  @Test
  void testMaximalLayersXTrue() {
    String input1 = "5,5,10,2,2,3,15,7,2,14,1,1,15,2,1,7,7,7,1,4,12,10,15,15";
    String[] tmp = input1.split(",");
    ArrayList<int[]> testPoints = new ArrayList<int[]>();
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
      testPoints.add(p);
    }
    boolean sortX = true;
    boolean sortY = false;
    MaximalLayers test = new MaximalLayers(testPoints, sortX, sortY);
    test.findAllLayers();
    assertEquals(
        "1:(15,7)(15,2)(15,15) 2:(2,14)(12,10) 3:(1,7)(7,7)(10,2) 4:(5,5) 5:(1,4)(2,3) 6:(1,1)",
        test.toString());
  }

  @Test
  void testMaximalLayersYTrue() {
    String input1 = "5,5,10,2,2,3,15,7,2,14,1,1,15,2,1,7,7,7,1,4,12,10,15,15";
    String[] tmp = input1.split(",");
    ArrayList<int[]> testPoints = new ArrayList<int[]>();
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
      testPoints.add(p);
    }
    boolean sortX = false;
    boolean sortY = true;
    MaximalLayers test = new MaximalLayers(testPoints, sortX, sortY);
    test.findAllLayers();
    assertEquals(
        "1:(15,2)(15,7)(15,15) 2:(12,10)(2,14) 3:(10,2)(1,7)(7,7) 4:(5,5) 5:(2,3)(1,4) 6:(1,1)",
        test.toString());
  }
}
