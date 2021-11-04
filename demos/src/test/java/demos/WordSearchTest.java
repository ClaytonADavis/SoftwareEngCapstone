package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WordSearchTest {
  @Test
  void testFindWord() {
    WordSearch test = new WordSearch("softsweskaolzilklqmtreyoy", 5, 5);
    assertEquals("e:2,2 s:2,3 k:2,4 i:3,4 m:4,4 o:5,4", test.findWord("eskimo"));
  }

  @Test
  void testFindWordSpace() {
    WordSearch test = new WordSearch("softsweskaolzilklqmtreyoy", 5, 5);
    assertEquals("  not found", test.findWord(" "));
  }

  @Test
  void testFindWordCap() {
    WordSearch test = new WordSearch("softsweskaolzilklqmtreyoy", 5, 5);
    assertEquals("Eskimo not found", test.findWord("Eskimo"));
  }

  @Test
  void testFindWordNotFound() {
    WordSearch test = new WordSearch("softsweskaolzilklqmtreyoy", 5, 5);
    assertEquals("cool not found", test.findWord("cool"));
  }

  @Test
  void testFindWordLoop() {
    WordSearch test = new WordSearch("softsweskaolzilklqmtreyoy", 5, 5);
    assertEquals("soews not found", test.findWord("soews"));
  }

  @Test
  void testFindWordLetter() {
    WordSearch test = new WordSearch("softsweskaolzilklqmtreyoy", 5, 5);
    assertEquals("s:1,1", test.findWord("s"));
  }

  @Test
  void testFindWord2() {
    WordSearch test = new WordSearch("softsweskaolzilklqmtreyoy", 5, 5);
    assertEquals("saoky not found", test.findWord("saoky"));
  }

  @Test
  void testFindWord3() {
    WordSearch test = new WordSearch("softsweskaolzilklqmtreyoy", 5, 5);
    assertEquals(
        "f:1,3 t:1,4 k:2,4 i:3,4 m:4,4 q:4,3 l:4,2 l:3,2 e:2,2 s:2,3 z:3,3",
        test.findWord("ftkimqllesz"));
  }

  @Test
  void testFindWord4() {
    WordSearch test = new WordSearch("softsweskaolzilklqmtreyoy");
    assertEquals("altyoyerkowsoftb not found", test.findWord("altyoyerkowsoftb"));
  }


}
