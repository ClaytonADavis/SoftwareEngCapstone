package edu.wofford.woclo;

import java.util.*;
import java.util.regex.*;

public class XMLParser {

  private String[] positionalArgs;
  private String[] namedArgs;
  private ArrayList<Identifier> identifiers;
  private ArrayList<Identifier> optionalIdentifiers;
  private String regexStr =
      "^<.*> \n<arguments>\\s*<positionalArgs>\\s*<positional>\\s*([\r\n\\w\\d\\W\\D]*)\\s*</positional>\\s*</positionalArgs>\\s*<namedArgs>\\s*<named>\\s*([\r\n\\w\\d\\W\\D]*)\\s*</named>\\s*</namedArgs>\\s*</arguments>$";
  private String[] requiredParams = {"name", "type", "description"};
  private String[] optionalParams = {"restrictions", "default", "shortID"};

  public XMLParser(String xml) {
    Pattern argsPattern = Pattern.compile(regexStr);
    Matcher argsMatcher = argsPattern.matcher(xml);
    boolean matches = argsMatcher.matches();

    positionalArgs = argsMatcher.group(1).split("</positional>\\s*<positional>");
    namedArgs = argsMatcher.group(2).split("</named>\\s*<named>");

    identifiers = new ArrayList<Identifier>();
    optionalIdentifiers = new ArrayList<Identifier>();
  }

  public void addIdentifier(String id, String description, String type, String[] restrictedValues) {
    Identifier Iden = new Identifier(id, type, "", description, "", restrictedValues);
    identifiers.add(Iden);
  }

  public void addIdentifier(String id, String description, String type, int[] restrictedValues) {
    String[] temp = new String[restrictedValues.length];
    for (int i = 0; i < restrictedValues.length; i++) temp[i] = String.valueOf(restrictedValues[i]);
    Identifier Iden = new Identifier(id, type, "", description, "", temp);
    identifiers.add(Iden);
  }

  public void addIdentifier(String id, String description, String type, float[] restrictedValues) {
    String[] temp = new String[restrictedValues.length];
    for (int i = 0; i < restrictedValues.length; i++) temp[i] = String.valueOf(restrictedValues[i]);
    Identifier Iden = new Identifier(id, type, "", description, "", temp);
    identifiers.add(Iden);
  }

  public void addIdentifier(
      String id, String description, String type, boolean[] restrictedValues) {
    Identifier Iden = new Identifier(id, type, "", description, "", new String[0]);
    identifiers.add(Iden);
  }

  private void assignIdentifiers(String arg) {
      String type;
      String description;
      String name;
      ArrayList<String> restrictions = new ArrayList<String>();
      String shortID = "";
      String defaultVal = "";

      type = getArgParameter("type", arg);
      description = getArgParameter("description", arg);
      name = getArgParameter("name", arg);
      shortID = getArgParameter("shortname", arg);
      defaultVal = getArgParameter("default", arg);
      
      if (type.equals("") || description.equals("") || name.equals("")) throw new InvalidXMLException;

  }

  private String getArgParameter(String argParam, String arg) {
    // try catch to see if arg is missing
    Pattern paramPattern =
        Pattern.compile(
            "[\r\n\\w\\d\\W\\D]*\\s*<"
                + argParam
                + ">([\r\n\\w\\d\\W\\D]*)</"
                + argParam
                + ">\\s*[\r\n\\w\\d\\W\\D]*");
    Matcher paramMatcher = paramPattern.matcher(arg);
    boolean matches = paramMatcher.matches();
    if (matches) return paramMatcher.group(1);
    return "";
  }

  private String[] getParamList(String argListName, String r) {
    Pattern paramListPattern =
        Pattern.compile(
            "^\\s*<" + argListName + ">([\r\n\\w\\d\\W\\D]*)</" + argListName + ">\\s*$");
    Matcher paramListMatcher = paramListPattern.matcher(r);
    boolean paramListMatch = paramListMatcher.matches();
    return paramListMatcher
        .group(1)
        .split("\\s*</" + argListName + ">\\s*<" + argListName + ">\\s*");
  }
}
