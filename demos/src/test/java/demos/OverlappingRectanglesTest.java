package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OverlappingRectanglesTest {
  @Test
  void testIntersectingArea() {
    int[] testPoints = {-3, 0, 3, 4, 0, -1, 9, 2};
    OverlappingRectangles test = new OverlappingRectangles();
    int intersectingArea = test.getIntersectingArea(testPoints);
    assertEquals(6, intersectingArea);
  }

  @Test
  void testTotalArea() {
    int[] testPoints = {-3, 0, 3, 4, 0, -1, 9, 2};
    OverlappingRectangles test = new OverlappingRectangles();
    int totalArea = test.getTotalArea(testPoints);
    assertEquals(51, totalArea);
  }
}
