package edu.wofford.woclo;

import java.util.*;

/**
 * This class takes and array of command line arguments and maps them to a list of identifiers
 * provided by the user. It allows the user to request arguments by specific identifiers. It also
 * recognizes when a help flag is in the arguments.
 */
public class Parser {
  /** The list of identifiers. */
  private ArrayList<String> identifierNames;
  private HashMap<String, Identifier> ids;
  private HashMap<String, Identifier> optional;
  private String progName;
  /** A private function that returns true if the command line contains a help flag. */
  private boolean getHelp(String[] argArr) {
    for (String s : argArr) {
      if (s.equals("-h") || s.equals("--help")) {
        return true;
      }
    }
    return false;
  }

  private String[] setOptionals(String[] command) {
    List<String> noOpt = new ArrayList<String>();
    for (int i = 0; i < command.length; i++) {
      if (optional.containsKey(command[i])) {
        try {
          optional.get(command[i]).addData(command[i + 1]);
        } catch (ArrayIndexOutOfBoundsException e) {
          System.out.println(
              progName + " error: no value for " + optional.get(command[i]).getName());
          throw new MissingArgumentException();
        }
        String s = optional.get(command[i]).getType();
        if (s.equals("int")) {
          try {
            int x = (int) optional.get(command[i]).getValue();
          } catch (IncorrectArgumentTypeException e) {
            System.out.println(
                progName + " error: the value " + command[i + 1] + " is not of type integer");
          }
        } else if (s.equals("float")) {
          float f = 0f;
          try {
            f = (float) optional.get(command[i]).getValue();
          } catch (IncorrectArgumentTypeException e) {
            System.out.println(
                progName + " error: the value " + command[i + 1] + " is not of type float");
          }
        }
        i++;
      } else {
        noOpt.add(command[i]);
      }
    }
    String[] temp = new String[noOpt.size()];
    for (int i = 0; i < noOpt.size(); i++) {
      temp[i] = noOpt.get(i);
    }
    return temp;
  }

  /** This is the constructer for the Parser class. It takes no arguments. */
  public Parser(String name) {
    identifierNames = new ArrayList<String>();
    ids = new HashMap<String, Identifier>();
    optional = new HashMap<String, Identifier>();
    progName = name;
  }

  /**
   * This method adds an Identifier to the end of the Identifier list. It takes the Identifier as a
   * string and retuns void.
   */
  public void addIdentifier(String id) {
    Identifier Iden = new Identifier(id, "String", "");
    ids.put(id, Iden);
    identifierNames.add(id);
  }

  public void addIdentifier(String id, String type) {
    Identifier Iden = new Identifier(id, type, "");
    ids.put(id, Iden);
    identifierNames.add(id);
  }

  public void addOptionalIdentifier(String id) {
    Identifier Iden = new Identifier(id, "Boolean", "false");
    optional.put("--" + id, Iden);
  }

  public void addOptionalIdentifier(String id, String type, String Default) {
    Identifier Iden = new Identifier(id, type, Default);
    optional.put("--" + id, Iden);
  }


  /**
   * This method takes an array of strings and maps them in order to the idenifier array. It also
   * checks if there is a help flag and throws an execption if one is present.
   */
  public void parseCommandLine(String[] commandLine) {
    if (getHelp(commandLine)) {
      throw new HelpException();
    }
    String[] noOpt = new String[commandLine.length];
    try {
      noOpt = setOptionals(commandLine);
    } catch (MissingArgumentException e) {
      throw new MissingArgumentException();
    }
    commandLine = noOpt;
    if (commandLine.length < ids.size()) {
      System.out.println(
          progName
              + " error: the argument "
              + identifierNames.get(commandLine.length).getName()
              + " is required.");
      throw new NotEnoughArgsException();
    }
    if (commandLine.length > ids.size()) {
      System.out.println(
          progName
              + " error: the value "
              + commandLine[ids.size()]
              + " matches no argument.");
      throw new TooManyArgsException();
    }

    String key = "";
    for (int i = 0; i < size; i++) {
      key = identifierNames.get(i); 
      ids.replace(key, ids.get(key).addData(commandLine[i]));
    }
  }
  /**
   * This method takes a string representing the desired argument and returns the argument as a
   * string.
   */
  public <T> T getValue(String command) {
    int x = 0;
    if (optional.containsKey("--" + command)) {
      return (T) optional.get("--" + command).getValue();
    } else {
      try {
        ids.get(x).getValue();
      } catch (IncorrectArgumentTypeException e) {
        System.out.println(progName + " error: " + ids.get(x).errorMessage());
      }
      return (T) identifiers.get(x).getValue();
    }
    return null;
  }
}
