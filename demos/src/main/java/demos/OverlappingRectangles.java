package demos;

import edu.wofford.woclo.*;

public class OverlappingRectangles {

  public int getIntersectingArea(int[] points) {
    int x = Math.min(points[2], points[6]) - Math.max(points[0], points[4]);
    int y = Math.min(points[3], points[7]) - Math.max(points[1], points[5]);
    return x * y;
  }

  public int getTotalArea(int[] points) {
    int sx1 = Math.abs(points[2] - points[0]);
    int sy1 = Math.abs(points[1] - points[3]);
    int sx2 = Math.abs(points[4] - points[6]);
    int sy2 = Math.abs(points[5] - points[7]);
    return (sx1 * sy1) + (sx2 * sy2);
  }

  public static void main(String... args) {
    String[] idArray = {"x1", "y1", "x2", "y2", "x3", "y3", "x4", "y4"};
    String[] idType = {"int", "int", "int", "int", "int", "int", "int", "int"};
    Parser parse = new Parser("OverlappingRectangles", idArray, idType);
    try {
      parse.parseCommandLine(args);
    } catch (TooManyArgsException e) {
      return;
    } catch (NotEnoughArgsException e) {
      return;
    } catch (HelpException e) {
      System.out.println(
          "usage: java OverlappingRectangles [-h] x1 y1 x2 y2 x3 y3 x4 y4\n\nDetermine the overlap and total area of two rectangles.\n\npositional arguments:\n x1          (integer)     lower-left x for rectangle 1\n y1          (integer)     lower-left y for rectangle 1\n x2          (integer)     upper-right x for rectangle 1\n y2          (integer)     upper-right y for rectangle 1\n x3          (integer)     lower-left x for rectangle 2\n y3          (integer)     lower-left y for rectangle 2\n x4          (integer)     upper-right x for rectangle 2\n y4          (integer)     upper-right y for rectangle 2\n\nnamed arguments:\n -h, --help  show this help message and exit");
      return;
    }
    int[] rectangles = new int[8];
    for (int i = 0; i < 8; i++) {
      try {
        rectangles[i] = parse.getValue(idArray[i]);
      } catch (IncorrectArgumentTypeException e) {
        return;
      }
    }
    OverlappingRectangles rectangleArea = new OverlappingRectangles();
    int intersectingArea = rectangleArea.getIntersectingArea(rectangles);
    System.out.println(
        Integer.toString(intersectingArea)
            + " "
            + Integer.toString(rectangleArea.getTotalArea(rectangles) - intersectingArea));
  }
}
