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
    assertEquals("Args4", parse.getIdendtifyer(1));
  }

  @Test
  public void testParseCommandLine() {
    Parser parse = new Parser();
    parse.addIdentifyer("Arg1");
    parse.addIdentifyer("Arg2");
    parse.addIdentifyer("Arg3");
    parse.addIdentifyer("Arg4");
    String[] commands = {"1", "2", "3", "4"};
    try {
      parse.parseCommandLine(commands);
    } catch (LessArgs e) {
      assert (false);
    }
    try {
      parse.parseCommandLine(commands);
    } catch (MoreArgs e) {
      assert (false);
    }
    assertEquals("2", parse.getValue("Arg2"));
  }

  @Test
  public void testLessArgs() {
    Parser parse = new Parser();
    parse.addIdentifyer("Arg1");
    parse.addIdentifyer("Arg2");
    parse.addIdentifyer("Arg3");
    parse.addIdentifyer("Arg4");
    String[] commands = {"1", "2", "3"};
    try {
      parse.parseCommandLine(commands);
    } catch (LessArgs e) {
      assert (true);
    }
  }

  @Test
  public void testMoreArgs() {
    Parser parse = new Parser();
    parse.addIdentifyer("Arg1");
    parse.addIdentifyer("Arg2");
    parse.addIdentifyer("Arg3");
    parse.addIdentifyer("Arg4");
    String[] commands = {"1", "2", "3", "4", "5"};
    try {
      parse.parseCommandLine(commands);
    } catch (MoreArgs e) {
      assert (true);
    }
  }

  @Test
  public void testAddIdentifyerArray() {
    Parser parse = new Parser();
    String[] idArr = {"Arg1", "Arg2", "Arg3"};
    parse.addIdentifyerArray(idArr);
    assertEquals("Arg3", parse.getIdendtifyer(2));
  }

  @Test
  public void testHelpDefault() {
    Parser parse = new Parser();
    parse.addIdentifyer("Arg1");
    parse.addIdentifyer("Arg2");
    parse.addIdentifyer("Arg3");
    parse.addIdentifyer("Arg4");
    String[] commands = {"1", "2", "3", "4", "-h"};
    try {
      parse.parseCommandLine(commands);
    } catch (HelpException e) {
      assert (true);
    }
  }
}
