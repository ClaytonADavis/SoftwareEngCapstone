package edu.wofford.woclo;

import java.util.*;
import java.util.regex.*;

public class XMLParser {

  private String[] positionalArgs;
  private String[] namedArgs;
  private ArrayList<Identifier> identifiers;
  private ArrayList<Identifier> optionalIdentifiers;
  private boolean matches = false;
  private String regexStr =
      "^<.*> \n<arguments>\\s*<positionalArgs>\\s*<positional>\\s*([\r\n\\w\\d\\W\\D]*)\\s*</positional>\\s*</positionalArgs>\\s*<namedArgs>\\s*<named>\\s*([\r\n\\w\\d\\W\\D]*)\\s*</named>\\s*</namedArgs>\\s*</arguments>$";

  public boolean matches() {
    return matches;
  }

  public String getUnformattedArg() {
    return positionalArgs[1];
  }

  public XMLParser(String xml) {
    Pattern argsPattern = Pattern.compile(regexStr);
    Matcher argsMatcher = argsPattern.matcher(xml);
    matches = argsMatcher.matches();

    positionalArgs = argsMatcher.group(1).split("</positional>\\s*<positional>");
    namedArgs = argsMatcher.group(2).split("</named>\\s*<named>");

    identifiers = new ArrayList<Identifier>();
    optionalIdentifiers = new ArrayList<Identifier>();

    for (String arg : positionalArgs) identifiers.add(assignIdentifier(arg));
    // identifiers.add(assignIdentifier(positionalArgs[0]));
    for (String arg : namedArgs) optionalIdentifiers.add(assignIdentifier(arg));
  }

  public ArrayList<Identifier> getPositionalIdentifiers() {
    return identifiers;
  }

  public ArrayList<Identifier> getOptionalIdentifiers() {
    return optionalIdentifiers;
  }

  private Identifier assignIdentifier(String arg) {
    String type;
    String description;
    String name;
    String[] restrictions;
    String shortname = "";
    String defaultValue = "";

    type = getArgParameter("type", arg);
    description = getArgParameter("description", arg);
    name = getArgParameter("name", arg);
    shortname = getArgParameter("shortname", arg);
    defaultValue = getParamList("default", arg)[0];
    restrictions = getParamList("restrictions", arg);

    if (type.equals("") || description.equals("") || name.equals(""))
      throw new InvalidXMLException();

    return new Identifier(name, type, defaultValue, description, shortname, restrictions);
  }

  private String getArgParameter(String argParam, String arg) {
    Pattern paramPattern =
        Pattern.compile(
            "[\r\n\\w\\d\\W\\D]*\\s*<"
                + argParam
                + ">([\r\n\\w\\d\\W\\D]*)</"
                + argParam
                + ">\\s*[\r\n\\w\\d\\W\\D]*");
    Matcher paramMatcher = paramPattern.matcher(arg);
    matches = paramMatcher.matches();
    if (matches) return paramMatcher.group(1);
    return "";
  }

  private String[] getParamList(String argListName, String r) {
    Pattern paramListPattern =
        Pattern.compile(
            "^\\s*<" + argListName + ">([\r\n\\w\\d\\W\\D]*)</" + argListName + ">\\s*$");
    Matcher paramListMatcher = paramListPattern.matcher(r);
    boolean paramListMatch = paramListMatcher.matches();
    if (paramListMatch)
      return paramListMatcher
          .group(1)
          .split("\\s*</" + argListName + ">\\s*<" + argListName + ">\\s*");

    return new String[0];
  }
}
