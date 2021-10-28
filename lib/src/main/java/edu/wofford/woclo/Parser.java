package edu.wofford.woclo;

import java.util.*;

/**
 * This class takes and array of command line arguments and maps them to a list of identifiers
 * provided by the user. It allows the user to request arguments by specific identifiers. It also
 * recognizes when a help flag is in the arguments.
 */
public class Parser {
  /** The list of identifiers. */
  private List<String> identifierType;

  private List<String> identifier;
  /** A map of strings that relates the identifiers to the command line arguments. */
  private Map<String, String> map;
  /** A private function that returns true if the command line contains a help flag. */
  private boolean getHelp(String[] argArr) {
    for (String s : argArr) {
      if (s.equals("-h") || s.equals("--help")) {
        return true;
      }
    }
    return false;
  }

  private Integer intCast(String map1) {
    return Integer.parseInt(map.get(map1));
  }

  private Float floatCast(String map1) {
    return Float.parseFloat(map.get(map1));
  }

  /** This is the constructer for the Parser class. It takes no arguments. */
  public Parser() {
    identifier = new ArrayList<String>();
    map = new HashMap<String, String>();
    identifierType = new ArrayList<String>();
  }

  public Parser(String[] idArray, String[] typeArray) {
    this();
    for (String s : idArray) {
      identifier.add(s);
    }
    for (String s : typeArray) {
      identifierType.add(s);
    }
  }

  /**
   * This method adds an Identifier to the end of the Identifier list. It takes the Identifier as a
   * string and retuns void.
   */
  public void addIdentifier(String id) {
    identifier.add(id);
    identifierType.add("String");
  }

  /** This method returns the Identifier at the given index. */
  public String getIdendtifyer(int i) {
    return identifier.get(i);
  }
  /** This method adds the given identifier to the list at the given index. */
  public void addIdentifierAtIndex(String id, int i) {
    identifier.add(i, id);
    identifierType.add(i, "String");
  }
  /**
   * This method takes an array of strigns and maps them in order to the idenifier array. It also
   * checks if there is a help flag and throws an execption if one is present.
   */
  public void parseCommandLine(String[] commandLine) {
    if (getHelp(commandLine)) {
      throw new HelpException();
    }
    if (commandLine.length < identifier.size()) {
      throw new NotEnoughArgsException();
    }
    if (commandLine.length > identifier.size()) {
      throw new TooManyArgsException();
    }
    int size = commandLine.length;
    for (int i = 0; i < size; i++) {
      map.put(identifier.get(i), commandLine[i]);
    }
  }
  /**
   * This method takes a string representing the desired argument and returns the argument as a
   * string.
   */
  public <T> T getValue(String command) {
    int x = identifier.indexOf(command);
    if (identifierType.get(x).equals("String")) {
      return (T) map.get(command);
    } else if (identifierType.get(x).equals("int")) {
      return (T) intCast(command);
    } else {
      return (T) floatCast(command);
    }
  }
  /**
   * This method allows the user to pass an array of stings to be added to the end of the idenitifer
   * list.
   */
  public void addIdentifierArray(String[] idArr) {
    for (String s : idArr) {
      identifier.add(s);
      identifierType.add("String");
    }
  }
}
