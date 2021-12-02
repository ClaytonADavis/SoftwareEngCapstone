package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class ParserTest {
  @Test
  public void testAddIdentifier() {
    Parser parse = new Parser("test", "test_usage");
    parse.addIdentifier("Arg1", "test_description");
    String[] test = {"1"};
    parse.parseCommandLine(test);
    assertEquals("1", parse.getValue("Arg1"));
  }

  @Test
  public void testParseCommandLine() {
    Parser parse = new Parser("test", "test_usage");
    parse.addIdentifier("Arg1", "test_description1");
    parse.addIdentifier("Arg2", "test_description2");
    parse.addIdentifier("Arg3", "test_description3");
    parse.addIdentifier("Arg4", "test_description4");
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
    Parser parse = new Parser("test", "test_usage");
    parse.addIdentifier("Arg1", "test_description1");
    parse.addIdentifier("Arg2", "test_description2");
    parse.addIdentifier("Arg3", "test_description3");
    parse.addIdentifier("Arg4", "test_description4");
    String[] commands = {"1", "2", "3"};
    try {
      parse.parseCommandLine(commands);
    } catch (NotEnoughArgsException e) {
      assert (true);
    }
  }

  @Test
  public void testTooManyArgsException() {
    Parser parse = new Parser("test", "test_usage");
    parse.addIdentifier("Arg1", "test_description1");
    parse.addIdentifier("Arg2", "test_description2");
    parse.addIdentifier("Arg3", "test_description3");
    parse.addIdentifier("Arg4", "test_description4");
    String[] commands = {"1", "2", "3", "4", "5"};
    try {
      parse.parseCommandLine(commands);
    } catch (TooManyArgsException e) {
      assert (true);
    }
  }

  @Test
  public void testHelpDefault() {
    Parser parse = new Parser("test", "test_usage");
    parse.addIdentifier("Arg1", "test_description1");
    parse.addIdentifier("Arg2", "test_description2");
    parse.addIdentifier("Arg3", "test_description3");
    parse.addIdentifier("Arg4", "test_description4");
    String[] commands = {"1", "2", "3", "4", "-h"};
    try {
      parse.parseCommandLine(commands);
    } catch (HelpException e) {
      assert (true);
    }
  }

  @Test
  public void testAddIdWType() {
    Parser parse = new Parser("test", "test_usage");
    String[] arr = {"1", "2.2", "3"};
    parse.addIdentifier("arg1", "test_description1", "string");
    parse.addIdentifier("arg2", "test_description2", "float");
    parse.addIdentifier("arg3", "test_description3", "integer");
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
    Parser parse = new Parser("test", "test_usage");
    parse.addIdentifier("arg1", "test_description");
    parse.addOptionalIdentifier("argopt", "test_description1", "string", "12");
    String[] command = {"5"};
    parse.parseCommandLine(command);
    assertEquals("12", parse.getValue("argopt"));
    String[] command2 = {"5", "--argopt", "7"};
    parse.parseCommandLine(command2);
    assertEquals("7", parse.getValue("argopt"));
  }

  @Test
  public void testOptInMiddle() {
    Parser parse = new Parser("test", "test_usage");
    parse.addIdentifier("arg1", "test_description1");
    parse.addIdentifier("arg2", "test_description2");
    parse.addOptionalIdentifier("argopt", "test_description3", "string", "12");
    String[] command = {"5", "7"};
    parse.parseCommandLine(command);
    assertEquals("12", parse.getValue("argopt"));
    String[] command2 = {"5", "--argopt", "7", "6"};
    parse.parseCommandLine(command2);
    assertEquals("7", parse.getValue("argopt"));
  }

  @Test
  public void testMissingOptionalValues() {
    Parser parse = new Parser("test", "test_usage");
    parse.addIdentifier("arg1", "test_description1");
    parse.addIdentifier("arg2", "test_description2");
    parse.addOptionalIdentifier("argopt", "test_description3", "string", "12");
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

  @Test
  public void testConstructHelpMsg() {
    Parser parse = new Parser("test", "test_usage");
    parse.addIdentifier("arg1", "test_description1");
    parse.addIdentifier("arg2", "test_description2");
    String[] command = {"5", "7", "--help"};
    try {
      parse.parseCommandLine(command);
    } catch (HelpException e) {
      assertEquals(
          "usage: java test [-h] arg1 arg2\n\ntest_usage\n\npositional arguments:\n arg1        (string)      test_description1\n arg2        (string)      test_description2\n\nnamed arguments:\n -h, --help  show this help message and exit",
          parse.getHelpMessage());
      return;
    }
    assert (false);
  }

  @Test
  public void testConstructHelpMsgOpt() {
    Parser parse = new Parser("test", "test_usage");
    parse.addIdentifier("arg1", "test_description1");
    parse.addIdentifier("arg2", "test_description2");
    parse.addOptionalIdentifier("argopt", "opt_desc", "boolean", "false");
    String[] command = {"5", "7", "--help"};
    try {
      parse.parseCommandLine(command);
    } catch (HelpException e) {
      assertEquals(
          "usage: java test [-h] [--argopt] arg1 arg2\n\ntest_usage\n\npositional arguments:\n arg1        (string)      test_description1\n arg2        (string)      test_description2\n\nnamed arguments:\n -h, --help  show this help message and exit\n --argopt     opt_desc",
          parse.getHelpMessage());
      return;
    }
    assert (false);
  }

  @Test
  public void testConstructHelpMsgOptNotBol() {
    Parser parse = new Parser("test", "test_usage");
    parse.addIdentifier("arg1", "test_description1");
    parse.addIdentifier("arg2", "test_description2");
    parse.addOptionalIdentifier("argopt", "opt_desc", "string", "default");
    String[] command = {"5", "7", "--help"};
    try {
      parse.parseCommandLine(command);
    } catch (HelpException e) {
      assertEquals(
          "usage: java test [-h] [--argopt ARGOPT] arg1 arg2\n\ntest_usage\n\npositional arguments:\n arg1             (string)      test_description1\n arg2             (string)      test_description2\n\nnamed arguments:\n -h, --help       show this help message and exit\n --argopt ARGOPT  (string)      opt_desc",
          parse.getHelpMessage());
      return;
    }
    assert (false);
  }

  @Test
  public void testOptionalValuesInt() {
    Parser parse = new Parser("test", "test_usage");
    parse.addIdentifier("arg1", "test_description1");
    parse.addIdentifier("arg2", "test_description2");
    parse.addOptionalIdentifier("argopt", "test_description3", "integer", "12");
    parse.addOptionalIdentifier("argopt2", "test_description4", "float", "0.0");
    String[] command = {"5", "7"};
    parse.parseCommandLine(command);
    String[] command2 = {"5", "7", "--argopt", "s", "--argopt2", "a"};
    try {
      parse.parseCommandLine(command2);
      parse.getValue("argopt");
    } catch (IncorrectArgumentTypeException e) {
      assert (true);
      return;
    }
    try {
      parse.parseCommandLine(command2);
      parse.getValue("argopt2");
    } catch (IncorrectArgumentTypeException e) {
      assert (true);
      return;
    }
    assert (false);
  }

  @Test
  public void testOptionalValuesBool() {
    Parser parse = new Parser("test", "test_usage");
    parse.addIdentifier("arg1", "test_description1");
    parse.addIdentifier("arg2", "test_description2");
    parse.addOptionalIdentifier("argopt", "test_description3", "integer", "12");
    parse.addOptionalIdentifier("argopt2", "test_description4", "float", "0.0");
    parse.addOptionalIdentifier("argopt3", "test_description5", "boolean", "false");
    String[] command = {"5", "7"};
    parse.parseCommandLine(command);
    String[] command2 = {"5", "7", "--argopt", "12", "--argopt2", "0.0", "--argopt3"};
    parse.parseCommandLine(command2);
    assertEquals(true, parse.getValue("argopt3"));
  }

  @Test
  public void testShortForm() {
    Parser parse = new Parser("test", "test_usage");
    parse.addOptionalIdentifier("short", "test_description", "string", "5", "s");
    String[] command = {"-s", "7"};
    parse.parseCommandLine(command);
    assertEquals("7", parse.getValue("short"));
  }

  @Test
  public void testShortFormMultiple() {
    Parser parse = new Parser("test", "test_usage");
    parse.addOptionalIdentifier("short", "test_description", "boolean", "false", "s");
    parse.addOptionalIdentifier("tester", "test_description1", "boolean", "false", "t");
    String[] command = {"-s", "--tester"};
    parse.parseCommandLine(command);
    assertEquals(true, parse.getValue("short"));
  }

  @Test
  public void testShortFormMultipleFlags() {
    Parser parse = new Parser("test", "test_usage");
    parse.addOptionalIdentifier("short", "test_description", "boolean", "false", "s");
    parse.addOptionalIdentifier("tester", "test_description1", "boolean", "false", "t");
    String[] command = {"-st"};
    parse.parseCommandLine(command);
    assertEquals(true, parse.getValue("tester"));
  }
}
