package edu.wofford.woclo;

import java.util.*;

/**
 * This class takes and array of command line arguments and maps their values to a list of arguments
 * specified by the user. Positional Arguments, which are represented on the command line by only
 * their values are supported. Positional values on the command line are mapped sequentially to the
 * positional identifiers added to the parser object in the order they were added by the user. Named
 * arguments, which are represented by their argument name (or shortform name if specified by the
 * user) and value on the command line are supported. They can be recognized in any order on a list
 * of command line values. Arguments of types string, integer, float, and boolean are supported.
 * Named arguments of type boolean do not have corresponding command line values. Default values can
 * be specified for named arguments. Restrictions can be set for both positional and named
 * arguments. The help flag (-h or --help) is reserved for displaying a customizable usage message.
 * Command line values mapped to identifiers can be retrieved by using their corresponding argument
 * name. XML files can also be used to specify which positional or named identifiers are expected on
 * the command line. The XML file conatining the argument information must be represented as a
 * string and be the first argument in the command line. XML file information can also be saved if
 * in addition to an XML file the name of the file to save the information to is specified as the
 * second argument on the command line.
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
   * Makes a new parser object with the specified program name and usage message.
   *
   * @param name The name of the program.
   * @param usage Desired usage message.
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
   * Returns true if the list of command line values contains a help (-h or --help) flag.
   *
   * @param args Array of command line values.
   */
  private boolean getHelp(String[] args) {
    for (String s : args) {
      if (s.equals("-h") || s.equals("--help")) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns a help message conatining the program name and usage message specified in the
   * contructor as well as details about all specified postional and named arguments.
   */
  public String getHelpMessage() {
    return helpMsg;
  }

  /**
   * Creates a help message using the program name, usage message, and details from the positional
   * and named arguments currently in the parser object.
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
      } else {
        shortLong += "--" + opIdName;
        helpMsg += "\n " + shortLong;
      }
      // add whitespace.
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
      // add restrictions and default
      String restrictions = optionalIdentifiers.get("--" + opIdName).getRestrictedValueString();
      if (!restrictions.equals("")) helpMsg += " {" + restrictions + "}";
      String defaultValue = optionalIdentifiers.get("--" + opIdName).getDefault();
      if (!defaultValue.equals("")) helpMsg += " (default: " + defaultValue + ")";
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
   * adds a positional iddentifier of the specified identifierName with the specified description to
   * the parser object of type String.
   *
   * @param identifierName Name of new identifier.
   * @param description Description of new identifier.
   */
  public void addIdentifier(String identifierName, String description) {
    Identifier Iden = new Identifier(identifierName, "string", "", description, "", new String[0]);
    identifiers.put(identifierName, Iden);
    identifierNames.add(identifierName);
  }
  /**
   * adds a positional iddentifier of the specified identifierName with the specified description to
   * the parser object of type string, integer or float.
   *
   * @param identifierName Name of new identifier.
   * @param description Description of new identifier.
   * @param type Value type of new identifier (string, integer, float)
   */
  public void addIdentifier(String identifierName, String description, String type) {
    Identifier Iden = new Identifier(identifierName, type, "", description, "", new String[0]);
    identifiers.put(identifierName, Iden);
    identifierNames.add(identifierName);
  }

  /**
   * adds a positional iddentifier of the specified identifierName with the specified description to
   * the parser object of type string. Possible values of the new identifier are restriced to only
   * the values in the list of restriced values.
   *
   * @param identifierName Name of new identifier.
   * @param description Description of new identifier.
   * @param type Value type of new identifier (string, integer, float)
   * @param restrictedValues List of restriced values for identifier.
   */
  public void addIdentifier(
      String identifierName, String description, String type, String[] restrictedValues) {
    Identifier Iden =
        new Identifier(identifierName, "string", "", description, "", restrictedValues);
    identifiers.put(identifierName, Iden);
    identifierNames.add(identifierName);
  }

  /**
   * adds a positional iddentifier of the specified identifierName with the specified description to
   * the parser object of type integer. Possible values of the new identifier are restriced to only
   * the values in the list of restriced values.
   *
   * @param identifierName Name of new identifier.
   * @param description Description of new identifier.
   * @param type Value type of new identifier (string, integer, float)
   * @param restrictedValues List of restriced values for identifier.
   */
  public void addIdentifier(
      String identifierName, String description, String type, int[] restrictedValues) {
    String[] temp = new String[restrictedValues.length];
    for (int i = 0; i < restrictedValues.length; i++) temp[i] = String.valueOf(restrictedValues[i]);
    Identifier Iden = new Identifier(identifierName, "integer", "", description, "", temp);
    identifiers.put(identifierName, Iden);
    identifierNames.add(identifierName);
  }

  /**
   * adds a positional iddentifier of the specified identifierName with the specified description to
   * the parser object of type float. Possible values of the new identifier are restriced to only
   * the values in the list of restriced values.
   *
   * @param identifierName Name of new identifier.
   * @param description Description of new identifier.
   * @param type Value type of new identifier (string, integer, float)
   * @param restrictedValues List of restriced values for identifier.
   */
  public void addIdentifier(
      String identifierName, String description, String type, float[] restrictedValues) {
    String[] temp = new String[restrictedValues.length];
    for (int i = 0; i < restrictedValues.length; i++) temp[i] = String.valueOf(restrictedValues[i]);
    Identifier Iden = new Identifier(identifierName, "float", "", description, "", temp);
    identifiers.put(identifierName, Iden);
    identifierNames.add(identifierName);
  }

  /**
   * adds a positional iddentifier of the specified identifierName with the specified description to
   * the parser object of type boolean. Possible values of the new identifier are restriced to only
   * the values in the list of restriced values.
   *
   * @param identifierName Name of new identifier.
   * @param description Description of new identifier.
   * @param type Value type of new identifier (string, integer, float)
   * @param restrictedValues List of restriced values for identifier.
   */
  public void addIdentifier(
      String identifierName, String description, String type, boolean[] restrictedValues) {
    Identifier Iden = new Identifier(identifierName, "boolean", "", description, "", new String[0]);
    identifiers.put(identifierName, Iden);
    identifierNames.add(identifierName);
  }

  /**
   * adds an optional identifier with the specified identifierName, description and default value of
   * type string, integer, or float.
   *
   * @param identifierName Name of new identifier.
   * @param description Description of new identifier.
   * @param type Value type of new identifier (string, integer, float, boolean)
   * @param default Default value of new identifer.
   */
  public void addOptionalIdentifier(
      String identifierName, String description, String type, String Default) {
    Identifier Iden = new Identifier(identifierName, type, Default, description, "", new String[0]);
    optionalIdentifiers.put("--" + identifierName, Iden);
    optionalIdentifierNames.add(identifierName);
  }

  /**
   * adds an optional identifier with the specified identifierName, description, short form name and
   * default value of type string, integer, or float.
   *
   * @param identifierName Name of new identifier.
   * @param description Description of new identifier.
   * @param type Value type of new identifier (string, integer, float, boolean)
   * @param default Default value of new identifer.
   * @param shortId Short form name of new identifier.
   */
  public void addOptionalIdentifier(
      String identifierName, String description, String type, String Default, String shortId) {
    Identifier Iden =
        new Identifier(identifierName, type, Default, description, shortId, new String[0]);
    optionalIdentifiers.put("--" + identifierName, Iden);
    optionalIdentifiers.put("-" + shortId, Iden);
    optionalIdentifierNames.add(identifierName);
  }

  /**
   * adds an optional identifier with the specified identifierName, description, short form name,
   * default value and restricted values. of type string.
   *
   * @param identifierName Name of new identifier.
   * @param description Description of new identifier.
   * @param type Value type of new identifier (string, integer, float, boolean)
   * @param default Default value of new identifer.
   * @param shortId Short form name of new identifier.
   * @param restrictedValues List of restricted values for new identifier.
   */
  public void addOptionalIdentifier(
      String identifierName,
      String description,
      String type,
      String Default,
      String shortId,
      String[] restrictedValues) {
    Identifier Iden =
        new Identifier(identifierName, type, Default, description, shortId, restrictedValues);
    optionalIdentifiers.put("--" + identifierName, Iden);
    optionalIdentifiers.put("-" + shortId, Iden);
    optionalIdentifierNames.add(identifierName);
  }

  /**
   * adds an optional identifier with the specified identifierName, description, short form name,
   * default value and restricted values. of type integer.
   *
   * @param identifierName Name of new identifier.
   * @param description Description of new identifier.
   * @param type Value type of new identifier (string, integer, float, boolean)
   * @param default Default value of new identifer.
   * @param shortId Short form name of new identifier.
   * @param restrictedValues List of restricted values for new identifier.
   */
  public void addOptionalIdentifier(
      String identifierName,
      String description,
      String type,
      String Default,
      String shortId,
      int[] restrictedValues) {
    String[] temp = new String[restrictedValues.length];
    for (int i = 0; i < restrictedValues.length; i++) temp[i] = String.valueOf(restrictedValues[i]);
    Identifier Iden =
        new Identifier(identifierName, "integer", Default, description, shortId, temp);
    optionalIdentifiers.put("--" + identifierName, Iden);
    optionalIdentifiers.put("-" + shortId, Iden);
    optionalIdentifierNames.add(identifierName);
  }

  /**
   * adds an optional identifier with the specified identifierName, description, short form name,
   * default value and restricted values. of type float.
   *
   * @param identifierName Name of new identifier.
   * @param description Description of new identifier.
   * @param type Value type of new identifier (string, integer, float, boolean)
   * @param default Default value of new identifer.
   * @param shortId Short form name of new identifier.
   * @param restrictedValues List of restricted values for new identifier.
   */
  public void addOptionalIdentifier(
      String identifierName,
      String description,
      String type,
      String Default,
      String shortId,
      float[] restrictedValues) {
    String[] temp = new String[restrictedValues.length];
    for (int i = 0; i < restrictedValues.length; i++) temp[i] = String.valueOf(restrictedValues[i]);
    Identifier Iden = new Identifier(identifierName, "float", Default, description, shortId, temp);
    optionalIdentifiers.put("--" + identifierName, Iden);
    optionalIdentifiers.put("-" + shortId, Iden);
    optionalIdentifierNames.add(identifierName);
  }

  /**
   * adds an optional identifier with the specified identifierName, description, short form name,
   * default value and restricted values. of type boolean.
   *
   * @param identifierName Name of new identifier.
   * @param description Description of new identifier.
   * @param type Value type of new identifier (string, integer, float, boolean)
   * @param default Default value of new identifer.
   * @param shortId Short form name of new identifier.
   * @param restrictedValues List of restricted values for new identifier.
   */
  public void addOptionalIdentifier(
      String identifierName,
      String description,
      String type,
      String Default,
      String shortId,
      boolean[] restrictedValues) {
    Identifier Iden =
        new Identifier(identifierName, "boolean", Default, description, shortId, new String[0]);
    optionalIdentifiers.put("--" + identifierName, Iden);
    optionalIdentifiers.put("-" + shortId, Iden);
    optionalIdentifierNames.add(identifierName);
  }

  /**
   * Maps a string array of command line values to their corresponding ids in a hashmap. Positional
   * values are sequentially from the command line values to the list of identifiers in the order
   * the identifiers were added by the user. named argumentents are mapped according to their flag
   * name. Throws a HelpException if the help (-h --help) flag is present. Throws a
   * MissingArgumentException if a named argument is present in the given array of command line
   * values but does not have a corresponding identifier specified by the user in the parser object.
   * Throws a NotEnooughArgsException if the are less positional argument values in the array of
   * command line values than identifiers specified by the user. Throws a TooManyArgsException if
   * the are more positional argument values in the array of command line values than identifiers
   * specified by the user. Throws an InformativeException if a command line value does not match a
   * restriceted value specified by the user. Throws an InvalidXMLException if the given XML file is
   * not the correct format or the arguments specified in the XML file are missing required details.
   *
   * @param commandLine String array of command line values.
   * @throws HelpException
   * @throws MissingArgumentExcpetion
   * @throws NotEnoughArgsException
   * @throws TooManyArgsException
   * @throws InformativeException
   */
  public void parseCommandLine(String[] commandLine) {
    parseArgList(commandLine, false, false);
  }

  /**
   * Maps a string array of command line values to their corresponding ids in a hashmap. Positional
   * values are sequentially from the command line values to the list of identifiers in the order
   * the identifiers were added by the user. named argumentents are mapped according to their flag
   * name. Throws a HelpException if the help (-h --help) flag is present. Throws a
   * MissingArgumentException if a named argument is present in the given array of command line
   * values but does not have a corresponding identifier specified by the user in the parser object.
   * Throws a NotEnooughArgsException if the are less positional argument values in the array of
   * command line values than identifiers specified by the user. Throws a TooManyArgsException if
   * the are more positional argument values in the array of command line values than identifiers
   * specified by the user. Throws an InformativeException if a command line value does not match a
   * restriceted value specified by the user.
   *
   * @param commandLine String array of command line values.
   * @throws HelpException
   * @throws MissingArgumentExcpetion
   * @throws NotEnoughArgsException
   * @throws TooManyArgsException
   * @throws InformativeException
   */
  public void parseCommandLine(String[] commandLine, boolean readXML) {
    parseArgList(commandLine, readXML, false);
  }

  /**
   * Maps a string array of command line values to their corresponding ids in a hashmap. Positional
   * values are sequentially from the command line values to the list of identifiers in the order
   * the identifiers were added by the user. named argumentents are mapped according to their flag
   * name. Throws a HelpException if the help (-h --help) flag is present. Throws a
   * MissingArgumentException if a named argument is present in the given array of command line
   * values but does not have a corresponding identifier specified by the user in the parser object.
   * Throws a NotEnooughArgsException if the are less positional argument values in the array of
   * command line values than identifiers specified by the user. Throws a TooManyArgsException if
   * the are more positional argument values in the array of command line values than identifiers
   * specified by the user. Throws an InformativeException if a command line value does not match a
   * restriceted value specified by the user. Throws an InvalidXMLException if the given XML file is
   * not the correct format or the arguments specified in the XML file are missing required details.
   *
   * @param commandLine String array of command line values.
   * @throws HelpException
   * @throws MissingArgumentExcpetion
   * @throws NotEnoughArgsException
   * @throws TooManyArgsException
   * @throws InformativeException
   */
  public void parseCommandLine(String[] commandLine, boolean readXML, boolean writeXML) {
    if (!readXML && writeXML) {
      parseArgList(commandLine, true, writeXML);
    } else {
      parseArgList(commandLine, readXML, writeXML);
    }
  }

  private void parseArgList(String[] commandLine, boolean readXML, boolean writeXML) {
    String firstArg = "";
    String secondArg = "";
    if (commandLine.length > 0) firstArg = commandLine[0];
    if (commandLine.length > 1) secondArg = commandLine[1];
    if (readXML) {
      XMLParser XMLData;
      if (writeXML) {
        XMLData = new XMLParser(firstArg, secondArg);
        XMLData.writeXMLToFile();
        return;
      } else {
        XMLData = new XMLParser(firstArg, "");
        for (Identifier p : XMLData.getPositionalIdentifiers()) {
          identifiers.put(p.getName(), p);
          identifierNames.add(p.getName());
        }
        for (Identifier o : XMLData.getOptionalIdentifiers()) {
          optionalIdentifiers.put("--" + o.getName(), o);
          optionalIdentifierNames.add(o.getName());
          if (!o.getShortName().equals("")) optionalIdentifiers.put("-" + o.getShortName(), o);
        }
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
   * Returns the value of the identifier associated with the given argumentName of its specified
   * type. Throws an IncorrectArgumentTypeException if the value cannot be converted to its expected
   * type.
   *
   * @param argumentName Name of argument to retrieve value of.
   * @throws IncorrectArgumentTypeException
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
