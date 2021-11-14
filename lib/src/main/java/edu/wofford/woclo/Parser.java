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

  private ArrayList<String> optionalIdentifierNames;
  private HashMap<String, Identifier> ids;
  private HashMap<String, Identifier> optional;
  private String progName;
  private String helpMsg;
  private String usage;
  /** A private function that returns true if the command line contains a help flag. */
  private boolean getHelp(String[] argArr) {
    for (String s : argArr) {
      if (s.equals("-h") || s.equals("--help")) {
        return true;
      }
    }
    return false;
  }

  public String getHelpMessage() {
    return helpMsg;
  }

  private void constructHelpMsg() {
    helpMsg += " [-h]";
    // add optional and position arg names to start of helpMsg
    for (int i = 0; i < optionalIdentifierNames.size(); i++) {
      helpMsg += " [--" + optionalIdentifierNames.get(i);
      if (optional.get("--" + optionalIdentifierNames.get(i)).getType() != "boolean")
        helpMsg += " " + optionalIdentifierNames.get(i).toUpperCase();
      helpMsg += "]";
    }
    for (String idName : identifierNames) helpMsg += " " + idName;
    // add usage message on new line
    helpMsg += "\n\n" + usage;

    // determine how much whitespace needed between arg names and types in positional and named arg
    // lists
    int maxArgLength = "-h, --help".length();
    for (String idName : identifierNames) maxArgLength = Math.max(maxArgLength, idName.length());
    for (int i = 0; i < optionalIdentifierNames.size(); i++) {
      // if named arg not boolean include uppercase name in length calculation
      if (optional.get("--" + optionalIdentifierNames.get(i)).getType() != "boolean") {
        maxArgLength =
            Math.max(
                maxArgLength,
                ("--"
                            + optionalIdentifierNames.get(i)
                            + optionalIdentifierNames.get(i).toUpperCase())
                        .length()
                    + 1);
      } else {
        maxArgLength = Math.max(maxArgLength, ("--" + optionalIdentifierNames.get(i)).length());
      }
    }

    // add positional descriptions
    helpMsg += "\n\npositional arguments:";
    for (String idName : identifierNames) {
      // add argname
      helpMsg += "\n " + idName;
      // add whitespace
      for (int i = 0; i < maxArgLength - idName.length() + 2; i++) helpMsg += " ";
      // add type
      helpMsg += "(" + ids.get(idName).getType() + ")";
      // add whitespace (16 = number of chars between start of type and description)
      for (int i = 0; i < 12 - ids.get(idName).getType().length(); i++) helpMsg += " ";
      // add description
      helpMsg += ids.get(idName).getDescription();
    }
    // add named arguements
    helpMsg += "\n\nnamed arguments:\n -h, --help";
    for (int i = 0; i < maxArgLength - "-h, --help".length() + 2; i++) helpMsg += " ";
    helpMsg += "show this help message and exit";

    for (String idName : optionalIdentifierNames) {
      // add argname and determine if boolean flag
      // 1 for not bool 2 for is bool. used to add whitespace later
      int isBool = 1;
      if (optional.get("--" + idName).getType() != "boolean") {
        helpMsg += "\n --" + idName + " " + idName.toUpperCase();
        isBool = 2;
      } else {
        helpMsg += "\n --" + idName;
      }
      // add whitespace. if is boolean multiply by isBool constant determined earlier
      for (int i = 0; i < maxArgLength - isBool * idName.length() - 1; i++) helpMsg += " ";
      // add type
      helpMsg += "(" + optional.get("--" + idName).getType() + ")";
      // add whitespace (16 = number of chars between start of type and description)
      for (int i = 0; i < 12 - optional.get("--" + idName).getType().length(); i++) helpMsg += " ";
      // add description
      helpMsg += optional.get("--" + idName).getDescription();
    }
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
        if (s.equals("integer")) {
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
  public Parser(String name, String usage) {
    identifierNames = new ArrayList<String>();
    optionalIdentifierNames = new ArrayList<String>();
    ids = new HashMap<String, Identifier>();
    optional = new HashMap<String, Identifier>();
    progName = name;
    helpMsg = "usage: java " + name;
    this.usage = usage;
  }

  /**
   * This method adds an Identifier to the end of the Identifier list. It takes the Identifier as a
   * string and retuns void.
   */
  public void addIdentifier(String id, String description) {
    Identifier Iden = new Identifier(id, "string", "", description);
    ids.put(id, Iden);
    identifierNames.add(id);
  }

  public void addIdentifier(String id, String description, String type) {
    Identifier Iden = new Identifier(id, type, "", description);
    ids.put(id, Iden);
    identifierNames.add(id);
  }

  public void addOptionalIdentifier(String id, String description) {
    Identifier Iden = new Identifier(id, "boolean", "false", description);
    optional.put("--" + id, Iden);
    optionalIdentifierNames.add(id);
  }

  public void addOptionalIdentifier(String id, String description, String type, String Default) {
    Identifier Iden = new Identifier(id, type, Default, description);
    optional.put("--" + id, Iden);
    optionalIdentifierNames.add(id);
  }

  /**
   * This method takes an array of strings and maps them in order to the idenifier array. It also
   * checks if there is a help flag and throws an execption if one is present.
   */
  public void parseCommandLine(String[] commandLine) {
    if (getHelp(commandLine)) {
      constructHelpMsg();
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
              + identifierNames.get(commandLine.length)
              + " is required.");
      throw new NotEnoughArgsException();
    }
    if (commandLine.length > ids.size()) {
      System.out.println(
          progName + " error: the value " + commandLine[ids.size()] + " matches no argument.");
      throw new TooManyArgsException();
    }

    String key = "";
    for (int i = 0; i < commandLine.length; i++) {
      key = identifierNames.get(i);
      Identifier t = ids.get(key);
      t.addData(commandLine[i]);
      ids.replace(key, t);
    }
  }
  /**
   * This method takes a string representing the desired argument and returns the argument as a
   * string.
   */
  public <T> T getValue(String command) {
    if (optional.containsKey("--" + command)) {
      return (T) optional.get("--" + command).getValue();
    } else {
      try {
        ids.get(command).getValue();
      } catch (IncorrectArgumentTypeException e) {
        System.out.println(progName + " error: " + ids.get(command).errorMessage());
      }
      return (T) ids.get(command).getValue();
    }
  }
}
