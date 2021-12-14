package edu.wofford.woclo;

/**
 * Class which represents identifiers for both positional and named arguments on the command line.
 * Identifiers are represented by their name or short form name and can be of type string, integer,
 * float, or boolean. When retrieving the value associated with an identifier the value is converted
 * to that identifier's specified type. Possible values that can associated with the identifier can
 * be restricted with an array of restricted values. Each identifier has a description and error
 * message asssociated with it. The error message is specified when trying to retrieve an
 * identifier's value but that value cannot be converted to the identifier's specified type.
 */
public class Identifier<T> {

  private String name;
  private String type;
  private String value;
  private String description;
  private String errMsge;
  private String shortName;
  private String[] restrictedValues;

  /**
   * Constructs a new identifier with the specified name, type, value, and description.
   *
   * @param name Name of new identifer.
   * @param type Value type of new identifier.
   * @param value Value of new identifer.
   * @param description Description of new identifer.
   */
  public Identifier(String name, String type, String value, String description) {
    this.name = name;
    this.type = type;
    this.value = value;
    this.description = description;
    errMsge = "";
    this.shortName = "";
    this.restrictedValues = new String[0];
  }

  /**
   * Constructs a new identifier with the specified name, type, value, description and short form
   * name.
   *
   * @param name Name of new identifer.
   * @param type Value type of new identifier.
   * @param value Value of new identifer.
   * @param description Description of new identifer.
   * @param shortName Short form name of new Identifier.
   */
  public Identifier(String name, String type, String value, String description, String shortName) {
    this.name = name;
    this.type = type;
    this.value = value;
    this.description = description;
    errMsge = "";
    this.shortName = shortName;
    this.restrictedValues = new String[0];
  }

  /**
   * Constructs a new identifier with the specified name, type, value, description, short form name
   * and restricted values.
   *
   * @param name Name of new identifer.
   * @param type Value type of new identifier.
   * @param value Value of new identifer.
   * @param description Description of new identifer.
   * @param shortName Short form name of new Identifier.
   * @param restrictedValues Restricted values of new identifier.
   */
  public Identifier(
      String name,
      String type,
      String value,
      String description,
      String shortName,
      String[] restrictedValues) {
    this.name = name;
    this.type = type;
    this.value = value;
    this.description = description;
    errMsge = "";
    this.shortName = shortName;
    this.restrictedValues = restrictedValues;
  }

  /**
   * Returns the name of this identifier.
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the type of this identifier.
   *
   * @return
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the value of this identifier to the specified value.
   *
   * @param value New value of identifer.
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Returns the value associated with this identifier and converts it to this identifier's
   * specified type if possible. Throws an IncorrectArgumentTypeException if the value associated
   * with this identifier cannot be converted t this identifier's specified type. Throws an
   * InvalidTypeException if this identifier's specified tpye is not any of the following; string,
   * integer, float, boolean.
   *
   * @return
   * @throws IncorrectArgumentTypeException
   * @throws InvalidTypeException
   */
  public T getValue() {
    if (type.equals("string")) {
      return (T) value;
    } else if (type.equals("integer")) {
      int x = 0;
      try {
        x = Integer.parseInt(value);
      } catch (NumberFormatException e) {
        errMsge = "the value " + value + " is not of type integer";
        throw new IncorrectArgumentTypeException();
      }
      Integer X = Integer.valueOf(x);
      return (T) X;
    } else if (type.equals("float")) {
      float f = 0.0f;
      try {
        f = Float.parseFloat(value);
      } catch (NumberFormatException e) {
        errMsge = "the value " + value + " is not of type" + type;
        throw new IncorrectArgumentTypeException();
      }
      Float F = Float.valueOf(f);
      return (T) F;
    } else if (type.equals("boolean")) {
      return (T) Boolean.valueOf(value.equals("true"));
    } else {
      throw new InvalidTypeException();
    }
  }

  /**
   * Returns the description of this identifier.
   *
   * @return
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the error message of this identifer.
   *
   * @return
   */
  public String errorMessage() {
    return errMsge;
  }

  /**
   * Returns the short form name of this identifier.
   *
   * @return
   */
  public String getShortName() {
    return shortName;
  }

  /**
   * Returns a string of the array of restriced values for this identifier.
   *
   * @return
   */
  public String getRestrictedValueString() {
    String output = "";
    for (int i = 0; i < restrictedValues.length; i++) {
      output += restrictedValues[i];
      if (i < restrictedValues.length - 1) output += ", ";
    }

    return output;
  }

  /**
   * Returns true if the specified argument value is in the list of restricted values. False
   * otherwise.
   *
   * @param arg Value to check to see if restricted.
   * @return
   */
  public boolean isRestrictedValue(String arg) {
    boolean contains = false;
    for (int i = 0; i < restrictedValues.length; i++)
      contains = contains | restrictedValues[i].equals(arg);
    return contains || restrictedValues.length == 0;
  }

  /**
   * Returns the default value of this identifier.
   *
   * @return
   */
  public String getDefault() {
    if (value.equals("false")) return "";
    return value;
  }
}
