package edu.wofford.woclo;

import java.util.*;
import java.util.regex.*;

/**
 * This class takes and array of command line arguments and maps them to a list of identifiers
 * provided by the user. It allows the user to request arguments by specific identifiers. It also
 * recognizes when a help flag is in the arguments.
 */
public class Parser {
  /** The list of identifiers. */
  private ArrayList<String> identifierNames;

  private ArrayList<String> optionalIdentifierNames;
  private HashMap<String, Identifier> identifiers;
  private HashMap<String, Identifier> optionalIdentifiers;
  private String programName;
  private String helpMsg;
  private String usage;

  /**
   * Contructor. Takes program name and usage message as required arguments.
   *
   * @param name
   * @param usage
   */
  public Parser(String name, String usage) {
    identifierNames = new ArrayList<String>();
    optionalIdentifierNames = new ArrayList<String>();
    identifiers = new HashMap<String, Identifier>();
    optionalIdentifiers = new HashMap<String, Identifier>();
    programName = name;
    helpMsg = "";
    this.usage = usage;
  }

  /**
   * Returns true if the command line contains a help flag.
   *
   * @param argArr
   */
  private boolean getHelp(String[] args) {
    for (String s : args) {
      if (s.equals("-h") || s.equals("--help")) {
        return true;
      }
    }
    return false;
  }

  /** Returns a help message */
  public String getHelpMessage() {
    return helpMsg;
  }

  /**
   * Creates a help message from the positional and named arguments currently in the parser object.
   */
  private void constructHelpMsg() {
    helpMsg = "usage: java " + programName + " [-h]";
    // add named and positional arg names to start of helpMsg
    for (int i = 0; i < optionalIdentifierNames.size(); i++) {
      if (optionalIdentifiers.get("--" + optionalIdentifierNames.get(i)).getShortName() != "") {
        helpMsg +=
            " [-" + optionalIdentifiers.get("--" + optionalIdentifierNames.get(i)).getShortName();
      } else {
        helpMsg += " [--" + optionalIdentifierNames.get(i);
      }
      if (optionalIdentifiers.get("--" + optionalIdentifierNames.get(i)).getType() != "boolean")
        helpMsg += " " + optionalIdentifierNames.get(i).toUpperCase();
      helpMsg += "]";
    }
    for (String idName : identifierNames) helpMsg += " " + idName;
    // add usage message on new line
    helpMsg += "\n\n" + usage;

    // determine how much whitespace needed between arg names and types in positional and named arg
    // lists
    int maxArgLength = "-h, --help".length();

    for (String idName : identifierNames) {
      maxArgLength = Math.max(maxArgLength, idName.length());
    }
    for (int i = 0; i < optionalIdentifierNames.size(); i++) {
      // if named arg not boolean include uppercase name in length calculation
      String shortLong = "";
      if (!optionalIdentifiers
          .get("--" + optionalIdentifierNames.get(i))
          .getShortName()
          .equals("")) {
        shortLong =
            "-" + optionalIdentifiers.get("--" + optionalIdentifierNames.get(i)).getShortName();

        if (optionalIdentifiers.get("--" + optionalIdentifierNames.get(i)).getType() != "boolean") {
          shortLong += " " + optionalIdentifierNames.get(i) + ", ";
        } else {
          shortLong += ", ";
        }
      }
      if (optionalIdentifiers.get("--" + optionalIdentifierNames.get(i)).getType() != "boolean") {
        maxArgLength =
            Math.max(
                maxArgLength,
                (shortLong
                            + "--"
                            + optionalIdentifierNames.get(i)
                            + optionalIdentifierNames.get(i).toUpperCase())
                        .length()
                    + 1);
      } else {
        maxArgLength =
            Math.max(maxArgLength, (shortLong + "--" + optionalIdentifierNames.get(i)).length());
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
      helpMsg += "(" + identifiers.get(idName).getType() + ")";
      // add whitespace (16 = number of chars between start of type and description)
      for (int i = 0; i < 12 - identifiers.get(idName).getType().length(); i++) helpMsg += " ";
      // add description
      helpMsg += identifiers.get(idName).getDescription();
    }

    // add named arguements
    // add help message
    helpMsg += "\n\nnamed arguments:\n -h, --help";
    for (int i = 0; i < maxArgLength - "-h, --help".length() + 2; i++) helpMsg += " ";
    helpMsg += "show this help message and exit";
    for (String opIdName : optionalIdentifierNames) {
      // add argname and determine if boolean flag
      // is bool used to account for extra whitespace in non-boolean named arguements. ie. --op OP
      int isBool = 1;
      String shortLong = "";
      if (!optionalIdentifiers.get("--" + opIdName).getShortName().equals("")) {
        shortLong = "-" + optionalIdentifiers.get("--" + opIdName).getShortName();
        if (optionalIdentifiers.get("--" + opIdName).getType() != "boolean") {
          shortLong += " " + opIdName.toUpperCase() + ", ";
        } else {
          shortLong += ", ";
        }
      }
      if (optionalIdentifiers.get("--" + opIdName).getType() != "boolean") {
        shortLong += "--" + opIdName + " " + opIdName.toUpperCase();
        helpMsg += "\n " + shortLong;
        isBool = 2;
      } else {
        shortLong += "--" + opIdName;
        helpMsg += "\n " + shortLong;
      }
      // add whitespace. if is boolean multiply by isBool constant determined earlier
      for (int i = 0; i < maxArgLength - shortLong.length() + 1; i++) helpMsg += " ";
      // add type
      if (!optionalIdentifiers.get("--" + opIdName).getType().equals("boolean")) {
        helpMsg += " (" + optionalIdentifiers.get("--" + opIdName).getType() + ")";
        // add whitespace (16 = number of chars between start of type and description)
        for (int i = 0; i < 12 - optionalIdentifiers.get("--" + opIdName).getType().length(); i++)
          helpMsg += " ";
      } else {
        helpMsg += " ";
      }
      // add description
      helpMsg += optionalIdentifiers.get("--" + opIdName).getDescription();
    }
  }

  private boolean setOptional(String[] command, int i, int j) {
    String argName = command[i];
    if (j > 0) argName = "-" + String.valueOf(command[i].charAt(j));

    boolean b = false;
    String s = optionalIdentifiers.get(argName).getType();
    try {
      if (s.equals("boolean")) {
        b = true;
        optionalIdentifiers.get(argName).setValue("true");
      } else {
        if (!optionalIdentifiers.get(argName).isRestrictedValue(command[i + 1]))
          throw new InformativeException();
        optionalIdentifiers.get(argName).setValue(command[i + 1]);
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(
          programName + " error: no value for " + optionalIdentifiers.get(argName).getName());
      throw new MissingArgumentException();
    }

    if (s.equals("integer")) {
      try {
        int x = (int) optionalIdentifiers.get(argName).getValue();
      } catch (IncorrectArgumentTypeException e) {
        System.out.println(
            programName + " error: the value " + command[i + 1] + " is not of type integer");
      }
    } else if (s.equals("float")) {
      float f = 0f;
      try {
        f = (float) optionalIdentifiers.get(argName).getValue();
      } catch (IncorrectArgumentTypeException e) {
        System.out.println(
            programName + " error: the value " + command[i + 1] + " is not of type float");
      }
    }
    return b;
  }
  /**
   * Gets optional values from command line then return the argument list without optional values.
   * Throws exceptions for if an argument in the given argument list does not exist or if the
   * argument is of the incorrect type.
   *
   * @param command
   * @throws MissingArgumentexception
   * @throws IncorrectArgumentTypeException
   */
  private String[] setOptionals(String[] command) {
    List<String> noOpt = new ArrayList<String>();
    for (int i = 0; i < command.length; i++) {

      if (command[i].charAt(0) == '-') {
        if (command[i].charAt(1) != '-' && !Character.isDigit(command[i].charAt(1))) {
          for (int j = 1; j < command[i].length(); j++) {
            if (optionalIdentifiers.containsKey("-" + command[i].charAt(j))) {
              boolean s = setOptional(command, i, j);
              if (!s) i++;
            }
          }
        } else {
          if (optionalIdentifiers.containsKey(command[i])) {
            boolean s = setOptional(command, i, 0);
            if (!s) i++;
          } else {
            noOpt.add(command[i]);
          }
        }
      } else {
        if (optionalIdentifiers.containsKey(command[i])) {
          boolean s = setOptional(command, i, 0);
          if (!s) i++;
        } else {
          noOpt.add(command[i]);
        }
      }
    }
    String[] temp = new String[noOpt.size()];
    for (int i = 0; i < noOpt.size(); i++) {
      temp[i] = noOpt.get(i);
    }
    return temp;
  }

  /**
   * adds a positional iddentifier to the parser object of type String.
   *
   * @param id
   * @param description
   */
  public void addIdentifier(String id, String description) {
    Identifier Iden = new Identifier(id, "string", "", description, "", new String[0]);
    identifiers.put(id, Iden);
    identifierNames.add(id);
  }
  /**
   * adds a positional identifier to the parser object of type float, integer, or string.
   *
   * @param id
   * @param description
   * @param type
   */
  public void addIdentifier(String id, String description, String type) {
    Identifier Iden = new Identifier(id, type, "", description, "", new String[0]);
    identifiers.put(id, Iden);
    identifierNames.add(id);
  }

  public void addIdentifier(String id, String description, String type, String[] restrictedValues) {
    Identifier Iden = new Identifier(id, type, "", description, "", restrictedValues);
    identifiers.put(id, Iden);
    identifierNames.add(id);
  }

  public void addIdentifier(String id, String description, String type, int[] restrictedValues) {
    String[] temp = new String[restrictedValues.length];
    for (int i = 0; i < restrictedValues.length; i++) temp[i] = String.valueOf(restrictedValues[i]);
    Identifier Iden = new Identifier(id, type, "", description, "", temp);
    identifiers.put(id, Iden);
    identifierNames.add(id);
  }

  public void addIdentifier(String id, String description, String type, float[] restrictedValues) {
    String[] temp = new String[restrictedValues.length];
    for (int i = 0; i < restrictedValues.length; i++) temp[i] = String.valueOf(restrictedValues[i]);
    Identifier Iden = new Identifier(id, type, "", description, "", temp);
    identifiers.put(id, Iden);
    identifierNames.add(id);
  }

  public void addIdentifier(
      String id, String description, String type, boolean[] restrictedValues) {
    Identifier Iden = new Identifier(id, type, "", description, "", new String[0]);
    identifiers.put(id, Iden);
    identifierNames.add(id);
  }

  /**
   * adds an optional identifier as a type string, integer, or float with a specified default value
   *
   * @param id
   * @param description
   * @param type
   * @param default
   */
  public void addOptionalIdentifier(String id, String description, String type, String Default) {
    Identifier Iden = new Identifier(id, type, Default, description, "", new String[0]);
    optionalIdentifiers.put("--" + id, Iden);
    optionalIdentifierNames.add(id);
  }

  public void addOptionalIdentifier(
      String id, String description, String type, String Default, String shortId) {
    Identifier Iden = new Identifier(id, type, Default, description, shortId, new String[0]);
    optionalIdentifiers.put("--" + id, Iden);
    optionalIdentifiers.put("-" + shortId, Iden);
    optionalIdentifierNames.add(id);
  }

  public void addOptionalIdentifier(
      String id,
      String description,
      String type,
      String Default,
      String shortId,
      String[] restrictedValues) {
    Identifier Iden = new Identifier(id, type, Default, description, shortId, restrictedValues);
    optionalIdentifiers.put("--" + id, Iden);
    optionalIdentifiers.put("-" + shortId, Iden);
    optionalIdentifierNames.add(id);
  }

  public void addOptionalIdentifier(
      String id,
      String description,
      String type,
      String Default,
      String shortId,
      int[] restrictedValues) {
    String[] temp = new String[restrictedValues.length];
    for (int i = 0; i < restrictedValues.length; i++) temp[i] = String.valueOf(restrictedValues[i]);
    Identifier Iden = new Identifier(id, type, Default, description, shortId, temp);
    optionalIdentifiers.put("--" + id, Iden);
    optionalIdentifiers.put("-" + shortId, Iden);
    optionalIdentifierNames.add(id);
  }

  public void addOptionalIdentifier(
      String id,
      String description,
      String type,
      String Default,
      String shortId,
      float[] restrictedValues) {
    String[] temp = new String[restrictedValues.length];
    for (int i = 0; i < restrictedValues.length; i++) temp[i] = String.valueOf(restrictedValues[i]);
    Identifier Iden = new Identifier(id, type, Default, description, shortId, temp);
    optionalIdentifiers.put("--" + id, Iden);
    optionalIdentifiers.put("-" + shortId, Iden);
    optionalIdentifierNames.add(id);
  }

  public void addOptionalIdentifier(
      String id,
      String description,
      String type,
      String Default,
      String shortId,
      boolean[] restrictedValues) {
    Identifier Iden = new Identifier(id, type, Default, description, shortId, new String[0]);
    optionalIdentifiers.put("--" + id, Iden);
    optionalIdentifiers.put("-" + shortId, Iden);
    optionalIdentifierNames.add(id);
  }

  /**
   * Maps a string array of command line values to their corresponding ids in a hashmap. throws
   * exceptions for if a help flag is present or if too few or too many arguments are given.
   *
   * @param commandLine
   * @throws HelpException
   * @throws MissingArgumentExcpetion
   * @throws NotEnoughArgsException
   * @throws TooManyArgsException
   */
  public void parseCommandLine(String[] commandLine) {
    String firstArg = "";
    if (commandLine.length > 0) firstArg = commandLine[0];
    Pattern isXMLPattern = Pattern.compile("^<\\?xml .*\\?>\\s*[\r\n\\w\\d\\W\\D]*");
    Matcher isXMLMatcher = isXMLPattern.matcher(firstArg);
    boolean isXML = isXMLMatcher.matches();
    if (isXML) {
      XMLParser XMLData = new XMLParser(firstArg);
      for (Identifier p : XMLData.getPositionalIdentifiers()) identifiers.put(p.getName(), p);
      for (Identifier o : XMLData.getOptionalIdentifiers()) {
        optionalIdentifiers.put("--" + o.getName(), o);
        if (o.getShortName().equals("")) optionalIdentifiers.put("-" + o.getShortName(), o);
      }

      commandLine = Arrays.copyOfRange(commandLine, 1, commandLine.length);
    }

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
    if (commandLine.length < identifiers.size()) {
      System.out.println(
          programName
              + " error: the argument "
              + identifierNames.get(commandLine.length)
              + " is required.");
      throw new NotEnoughArgsException();
    }
    if (commandLine.length > identifiers.size()) {
      System.out.println(
          programName
              + " error: the value "
              + commandLine[identifiers.size()]
              + " matches no argument.");
      throw new TooManyArgsException();
    }

    String key = "";
    for (int i = 0; i < commandLine.length; i++) {
      key = identifierNames.get(i);
      Identifier t = identifiers.get(key);
      if (!t.isRestrictedValue(commandLine[i])) {
        System.out.println(
            programName
                + " error: "
                + identifiers.get(key).getName()
                + " value "
                + commandLine[i]
                + " is not a member of ["
                + identifiers.get(key).getRestrictedValueString()
                + "]");
        throw new InformativeException();
      }
      t.setValue(commandLine[i]);
      identifiers.replace(key, t);
    }
  }
  /**
   * Takes the name of and argument and returns the value corresponding to the given name of that
   * argument's specified type.
   *
   * @param argumentName
   */
  public <T> T getValue(String argumentName) {
    if (optionalIdentifiers.containsKey("--" + argumentName)) {
      try {
        optionalIdentifiers.get("--" + argumentName).getValue();
      } catch (IncorrectArgumentTypeException e) {
        System.out.println(
            programName + " error: " + optionalIdentifiers.get("--" + argumentName).errorMessage());
      }
      return (T) optionalIdentifiers.get("--" + argumentName).getValue();
    } else {
      try {
        identifiers.get(argumentName).getValue();
      } catch (IncorrectArgumentTypeException e) {
        System.out.println(programName + " error: " + identifiers.get(argumentName).errorMessage());
      }
      return (T) identifiers.get(argumentName).getValue();
    }
  }
}
