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
    int[] rectangles = {-3, 0, 3, 4, 0, -1, 9, 2};
    OverlappingRectangles rectangleArea = new OverlappingRectangles();
    int intersectingArea = rectangleArea.getIntersectingArea(rectangles);
    System.out.println(
        Integer.toString(intersectingArea)
            + " "
            + Integer.toString(rectangleArea.getTotalArea(rectangles) - intersectingArea));
  }
}
