package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class ParserTest {
  @Test
  public void testAddIdentifier() {
    Parser parse = new Parser();
    parse.addIdentifier("Arg1");
    assertEquals("Arg1", parse.getIdendtifier(0));
  }

  @Test
  public void testAddIdentifierAtIndex() {
    Parser parse = new Parser();
    parse.addIdentifier("Arg1");
    parse.addIdentifier("Args2");
    parse.addIdentifier("Args3");
    parse.addIdentifierAtIndex("Args4", 1);
    assertEquals("Args4", parse.getIdendtifier(1));
  }

  @Test
  public void testParseCommandLine() {
    Parser parse = new Parser();
    parse.addIdentifier("Arg1");
    parse.addIdentifier("Arg2");
    parse.addIdentifier("Arg3");
    parse.addIdentifier("Arg4");
    String[] commands = {"1", "2", "3", "4"};
    try {
      parse.parseCommandLine(commands);
    } catch (NotEnoughArgsException e) {
      assert (false);
    }
    try {
      parse.parseCommandLine(commands);
    } catch (TooManyArgsException e) {
      assert (false);
    }
    assertEquals("2", parse.getValue("Arg2"));
  }

  @Test
  public void testNotEnoughArgsException() {
    Parser parse = new Parser();
    parse.addIdentifier("Arg1");
    parse.addIdentifier("Arg2");
    parse.addIdentifier("Arg3");
    parse.addIdentifier("Arg4");
    String[] commands = {"1", "2", "3"};
    try {
      parse.parseCommandLine(commands);
    } catch (NotEnoughArgsException e) {
      assert (true);
    }
  }

  @Test
  public void testTooManyArgsException() {
    Parser parse = new Parser();
    parse.addIdentifier("Arg1");
    parse.addIdentifier("Arg2");
    parse.addIdentifier("Arg3");
    parse.addIdentifier("Arg4");
    String[] commands = {"1", "2", "3", "4", "5"};
    try {
      parse.parseCommandLine(commands);
    } catch (TooManyArgsException e) {
      assert (true);
    }
  }

  @Test
  public void testAddIdentifierArray() {
    Parser parse = new Parser();
    String[] idArr = {"Arg1", "Arg2", "Arg3"};
    parse.addIdentifierArray(idArr);
    assertEquals("Arg3", parse.getIdendtifier(2));
  }

  @Test
  public void testHelpDefault() {
    Parser parse = new Parser();
    parse.addIdentifier("Arg1");
    parse.addIdentifier("Arg2");
    parse.addIdentifier("Arg3");
    parse.addIdentifier("Arg4");
    String[] commands = {"1", "2", "3", "4", "-h"};
    try {
      parse.parseCommandLine(commands);
    } catch (HelpException e) {
      assert (true);
    }
  }

  @Test
  public void testAddIdWType() {
    String[] ID = {"arg1", "arg2", "arg3"};
    String[] Type = {"String", "float", "int"};
    Parser parse = new Parser(ID, Type);
    String[] arr = {"1", "2.2", "3"};
    parse.parseCommandLine(arr);
    String s = parse.getValue("arg1");
    float f = parse.getValue("arg2");
    int i = parse.getValue("arg3");
    assertEquals("1", s);
    assertTrue(Math.abs(f) > 0.00000001);
    assertEquals(3, i);
  }
}
