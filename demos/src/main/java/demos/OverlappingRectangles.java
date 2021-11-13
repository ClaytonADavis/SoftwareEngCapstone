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
    String[] idType = {
      "integer", "integer", "integer", "integer", "integer", "integer", "integer", "integer"
    };

    Parser parse =
        new Parser(
            "OverlappingRectangles", "Determine the overlap and total area of two rectangles.");
    parse.addIdentifier("x1", "lower-left x for rectangle 1", "integer");
    parse.addIdentifier("y1", "lower-left y for rectangle 1", "integer");
    parse.addIdentifier("x2", "upper-right x for rectangle 1", "integer");
    parse.addIdentifier("y2", "upper-right y for rectangle 1", "integer");
    parse.addIdentifier("x3", "lower-left x for rectangle 2", "integer");
    parse.addIdentifier("y3", "lower-left y for rectangle 2", "integer");
    parse.addIdentifier("x4", "upper-right x for rectangle 2", "integer");
    parse.addIdentifier("y4", "upper-right y for rectangle 2", "integer");
    try {
      parse.parseCommandLine(args);
    } catch (TooManyArgsException e) {
      return;
    } catch (NotEnoughArgsException e) {
      return;
    } catch (HelpException e) {
      System.out.println(parse.getHelpMessage());
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
