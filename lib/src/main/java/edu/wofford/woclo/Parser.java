package edu.wofford.woclo;

import java.util.*;

/**
 * This class takes and array of command line arguments and maps them to a list of identifiers
 * provided by the user. It allows the user to request arguments by specific identifiers. It also
 * recognizes when a help flag is in the arguments.
 */
public class Parser {
<<<<<<< HEAD
  private List<String> identifier;
=======
  /** The list of identifiers. */
  private List<String> identifyer;
  /** A map of strings that relates the identifiers to the command line arguments. */
>>>>>>> f8060f883aa2d53d76c3036609e79bc1a040771e
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
  /** This is the constructer for the Parser class. It takes no arguments. */
  public Parser() {
    identifier = new ArrayList<String>();
    map = new HashMap<String, String>();
  }
<<<<<<< HEAD

  public void addIdentifier(String id) {
    identifier.add(id);
=======
  /**
   * This method adds an Identifier to the end of the Identifier list. It takes the Identifier as a
   * string and retuns void.
   */
  public void addIdentifyer(String id) {
    identifyer.add(id);
>>>>>>> f8060f883aa2d53d76c3036609e79bc1a040771e
  }
  /** This method returns the Identifier at the given index. */
  public String getIdendtifyer(int i) {
    return identifier.get(i);
  }
<<<<<<< HEAD

  public void addIdentifierAtIndex(String id, int i) {
    identifier.set(i, id);
=======
  /** This method adds the given identifier to the list at the given index. */
  public void addIdentifyerAtIndex(String id, int i) {
    identifyer.set(i, id);
>>>>>>> f8060f883aa2d53d76c3036609e79bc1a040771e
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
  public String getValue(String command) {
    return map.get(command);
  }
<<<<<<< HEAD

  public void addIdentifierArray(String[] idArr) {
=======
  /**
   * This method allows the user to pass an array of stings to be added to the end of the idenitifer
   * list.
   */
  public void addIdentifyerArray(String[] idArr) {
>>>>>>> f8060f883aa2d53d76c3036609e79bc1a040771e
    for (String s : idArr) {
      identifier.add(s);
    }
  }
}
