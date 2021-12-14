package edu.wofford.woclo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

/**
 * Class which handles the reading and writing of argument information in XML files. Converts a
 * string containing the contents of an XML file to two lists containing positional and named
 * identifier information. Throws an InvalidXMLException if XML string is not the correct format.
 * XML file information can be saved to a new file specified by the user.
 */
public class XMLParser {

  private String[] positionalArgs;
  private String[] namedArgs;
  private ArrayList<Identifier> identifiers;
  private ArrayList<Identifier> optionalIdentifiers;
  private boolean matches = false;
  private String regexStr =
      "^<.*>\\s*<arguments>\\s*<positionalArgs>\\s*<positional>\\s*([\r\n\\w\\d\\W\\D]*)\\s*</positional>\\s*</positionalArgs>\\s*<namedArgs>\\s*<named>\\s*([\r\n\\w\\d\\W\\D]*)\\s*</named>\\s*</namedArgs>\\s*</arguments>$";
  private String fileName = "";
  private String xml = "";

  /**
   * Reads positional and named argument information from the string conatining the XML file.
   * Positional and named argument information are added to two separate lists. Throws an
   * InvalidXMLException if the XML string is not the correct format.
   *
   * @param xml String conataining contents of XML file.
   * @param fileName Name of file to write XML file information to.
   * @throws InvalidXMLException
   */
  public XMLParser(String xml, String fileName) {
    this.xml = xml;
    this.fileName = fileName;
    if (fileName.equals("")) {
      Pattern argsPattern = Pattern.compile(regexStr);
      Matcher argsMatcher = argsPattern.matcher(xml);
      matches = argsMatcher.matches();
      if (!matches) throw new InvalidXMLException();

      positionalArgs = argsMatcher.group(1).split("</positional>\\s*<positional>");
      namedArgs = argsMatcher.group(2).split("</named>\\s*<named>");

      identifiers = new ArrayList<Identifier>();
      optionalIdentifiers = new ArrayList<Identifier>();

      for (String arg : positionalArgs) identifiers.add(assignIdentifier(arg));
      // identifiers.add(assignIdentifier(positionalArgs[0]));
      for (String arg : namedArgs) optionalIdentifiers.add(assignIdentifier(arg));
    }
  }

  /**
   * Returns the list of positional identifiers.
   *
   * @return
   */
  public ArrayList<Identifier> getPositionalIdentifiers() {
    return identifiers;
  }

  /**
   * Returns the list of named identifiers
   *
   * @return
   */
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
    String[] defaultVals = getParamList("value", getArgParameter("default", arg));
    if (defaultVals.length > 0) defaultValue = defaultVals[0];
    restrictions = getParamList("restriction", getArgParameter("restrictions", arg));

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

  /** Writes the information in teh XML file string to a new file. */
  public void writeXMLToFile() {
    try {
      File newXML = new File(fileName);
      newXML.createNewFile();

      FileWriter XMLWriter = new FileWriter(fileName);
      XMLWriter.write(xml);
      XMLWriter.close();
    } catch (IOException e) {
      System.out.println("error when writing to file");
    }
  }
}
