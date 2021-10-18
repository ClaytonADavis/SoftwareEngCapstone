package demos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class EquivalentStringsTest {

  @Test
  void testDefaultValues() {
    EquivalentStrings test = new EquivalentStrings("cocoon", "xyxyyz");
    test.buildDict1();
    test.buildDict2();
    assertTrue(test.isEqual());
  }

  @Test
  void testIsRepeat() {
    EquivalentStrings test = new EquivalentStrings("cocoon", "xyxyyz");
    char[][] dict = {{'c', '1'}, {'d', '2'}};
    assertTrue(test.isRepeat('c', dict));
  }

  @Test
  void testSearch() {
    EquivalentStrings test = new EquivalentStrings("cocoon", "xyxyyz");
    char[][] dict = {{'c', '1'}, {'d', '2'}};
    assertEquals('2', test.search('d', dict));
  }

  // set getDicts to private to avoid spotbug errors
  @Test
  void testbuildDict1() {
    EquivalentStrings test = new EquivalentStrings("cocoon", "xyxyyz");
    test.buildDict1();
    char[][] expectedDict = {
      {'c', '1'}, {'o', '2'}, {'c', '1'}, {'o', '2'}, {'o', '2'}, {'n', '3'}
    };
    assertTrue(Arrays.deepEquals(expectedDict, test.getDict1()));
  }

  @Test
  void testbuildDict2() {
    EquivalentStrings test = new EquivalentStrings("cocoon", "xyxyyz");
    test.buildDict2();
    char[][] expectedDict = {
      {'x', '1'}, {'y', '2'}, {'x', '1'}, {'y', '2'}, {'y', '2'}, {'z', '3'}
    };
    assertTrue(Arrays.deepEquals(expectedDict, test.getDict2()));
  }

  @Test
  void testisEqual() {
    EquivalentStrings test = new EquivalentStrings("cocoon", "xyxyyz");
    test.buildDict1();
    test.buildDict2();
    assertTrue(test.isEqual());
  }

  @Test
  void testisEqual2() {
    EquivalentStrings test = new EquivalentStrings("ab", "ba");
    test.buildDict1();
    test.buildDict2();
    assertTrue(test.isEqual());
  }

  // should this pass or fail??
  @Test
  void testisEqual3() {
    EquivalentStrings test = new EquivalentStrings("", "");
    test.buildDict1();
    test.buildDict2();
    assertTrue(test.isEqual());
  }

  @Test
  void testisEqual4() {
    EquivalentStrings test = new EquivalentStrings(" ", " ");
    test.buildDict1();
    test.buildDict2();
    assertTrue(test.isEqual());
  }

  @Test
  void testisEqual5() {
    EquivalentStrings test = new EquivalentStrings("aaaaa", "aaaaa");
    test.buildDict1();
    test.buildDict2();
    assertTrue(test.isEqual());
  }

  @Test
  void testisEqual6() {
    EquivalentStrings test = new EquivalentStrings("aaaaa", "bbbbb");
    test.buildDict1();
    test.buildDict2();
    assertTrue(test.isEqual());
  }

  @Test
  void testisEqual7() {
    EquivalentStrings test = new EquivalentStrings("  ", "aa");
    test.buildDict1();
    test.buildDict2();
    assertTrue(test.isEqual());
  }

  @Test
  void testisEqual8() {
    EquivalentStrings test = new EquivalentStrings("", " ");
    test.buildDict1();
    test.buildDict2();
    assertFalse(test.isEqual());
  }
}
