package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class ParserTest {
  @Test
  public void testAddIdentifier() {
    Parser parse = new Parser("Test");
    parse.addIdentifier("Arg1");
    String[] test = {"1"};
    parse.parseCommandLine(test);
    assertEquals("1", parse.getValue("Arg1"));
  }

  @Test
  public void testParseCommandLine() {
    Parser parse = new Parser("Test");
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
    Parser parse = new Parser("Test");
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
    Parser parse = new Parser("Test");
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
  public void testHelpDefault() {
    Parser parse = new Parser("Test");
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
    Parser parse = new Parser("Test");
    String[] arr = {"1", "2.2", "3"};
    parse.addIdentifier("arg1", "String");
    parse.addIdentifier("arg2", "float");
    parse.addIdentifier("arg3", "int");
    parse.parseCommandLine(arr);
    String s = parse.getValue("arg1");
    float f = parse.getValue("arg2");
    int i = parse.getValue("arg3");
    assertEquals("1", s);
    assertTrue(Math.abs(f) > 0.00000001);
    assertEquals(3, i);
  }

  @Test
  public void testOptionalIden() {
    Parser parse = new Parser("test");
    parse.addIdentifier("arg1");
    parse.addOptionalIdentifier("argopt", "String", "12");
    String[] command = {"5"};
    parse.parseCommandLine(command);
    assertEquals("12", parse.getValue("argopt"));
    String[] command2 = {"5", "--argopt", "7"};
    parse.parseCommandLine(command2);
    assertEquals("7", parse.getValue("argopt"));
  }

  @Test
  public void testOptInMiddle() {
    Parser parse = new Parser("test");
    parse.addIdentifier("arg1");
    parse.addIdentifier("arg2");
    parse.addOptionalIdentifier("argopt", "String", "12");
    String[] command = {"5", "7"};
    parse.parseCommandLine(command);
    assertEquals("12", parse.getValue("argopt"));
    String[] command2 = {"5", "--argopt", "7", "6"};
    parse.parseCommandLine(command2);
    assertEquals("7", parse.getValue("argopt"));
  }

  @Test
  public void testMissingOptionalValues() {
    Parser parse = new Parser("test");
    parse.addIdentifier("arg1");
    parse.addIdentifier("arg2");
    parse.addOptionalIdentifier("argopt", "String", "12");
    String[] command = {"5", "7"};
    parse.parseCommandLine(command);
    assertEquals("12", parse.getValue("argopt"));
    String[] command2 = {"5", "7", "--argopt"};
    try {
      parse.parseCommandLine(command2);
    } catch (MissingArgumentException e) {
      assert (true);
      return;
    }
    assert (false);
  }
}
