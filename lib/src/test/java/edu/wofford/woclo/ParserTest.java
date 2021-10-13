package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class ParserTest {
  @Test
  public void testAddIdentifyer() {
    Parser parse = new Parser();
    parse.addIdentifyer("Arg1");
    assertEquals("Arg1", parse.getIdendtifyer(0));
  }

  @Test
  public void testAddIdentifyerAtIndex() {
    Parser parse = new Parser();
    parse.addIdentifyer("Arg1");
    parse.addIdentifyer("Args2");
    parse.addIdentifyer("Args3");
    parse.addIdentifyerAtIndex("Args4", 1);
    assertEquals("Arg4", parse.getIdendtifyer(1));
  }
}
