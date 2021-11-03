package edu.wofford.woclo;

import java.util.*;

/**
 * This class takes and array of command line arguments and maps them to a list of identifiers
 * provided by the user. It allows the user to request arguments by specific identifiers. It also
 * recognizes when a help flag is in the arguments.
 */
public class Parser {
  /** The list of identifiers. */
  private List<Identifier> identifiers;

  private Map<String, Identifier> optional;
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
        optional.get(command[i]).addData(command[i + 1]);
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
    identifiers = new ArrayList<Identifier>();
    optional = new HashMap<String, Identifier>();
    progName = name;
  }

  public Parser(String name, String[] idArray, String[] typeArray) {
    this(name);
    for (int i = 0; i < idArray.length; i++) {
      Identifier id = new Identifier(idArray[i], typeArray[i], "");
      identifiers.add(id);
    }
  }

  /**
   * This method adds an Identifier to the end of the Identifier list. It takes the Identifier as a
   * string and retuns void.
   */
  public void addIdentifier(String id) {
    Identifier Iden = new Identifier(id, "String", "");
    identifiers.add(Iden);
  }

  public void addIdentifier(String id,String type, String Default) {
    Identifier Iden = new Identifier(id, type, Default);
    String name = "--" + id;
    optional.put(name, Iden);
  }

  /** This method returns the Identifier at the given index. */
  public String getIdendtifier(int i) {
    return identifiers.get(i).getName();
  }
  /** This method adds the given identifier to the list at the given index. */
  public void addIdentifierAtIndex(String id, int i) {
    identifiers.add(i, new Identifier(id, "String", ""));
  }
  /**
   * This method takes an array of strings and maps them in order to the idenifier array. It also
   * checks if there is a help flag and throws an execption if one is present.
   */
  public void parseCommandLine(String[] commandLine) {
    if (getHelp(commandLine)) {
      throw new HelpException();
    }
    String[] noOpt = setOptionals(commandLine);
    commandLine = noOpt;
    if (commandLine.length < identifiers.size()) {
      System.out.println(
          progName
              + " error: the argument "
              + identifiers.get(commandLine.length).getName()
              + " is required.");
      throw new NotEnoughArgsException();
    }
    if (commandLine.length > identifiers.size()) {
      System.out.println(
          progName
              + " error: the value "
              + commandLine[identifiers.size()]
              + " matches no argument.");
      throw new TooManyArgsException();
    }
    int size = commandLine.length;
    for (int i = 0; i < size; i++) {
      identifiers.get(i).addData(commandLine[i]);
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
      for (Identifier i : identifiers) {
        if (command.equals(i.getName())) {
          x = identifiers.indexOf(i);
          try {
            identifiers.get(x).getValue();
          } catch (IncorrectArgumentTypeException e) {
            System.out.println(progName + " error: " + identifiers.get(x).errorMessage());
          }
          return (T) identifiers.get(x).getValue();
        }
      }
    }
    return null;
  }
  /**
   * This method allows the user to pass an array of stings to be added to the end of the idenitifer
   * list.
   */
  public void addIdentifierArray(String[] idArr) {
    for (String s : idArr) {
      identifiers.add(new Identifier(s, "String", ""));
    }
  }
}
