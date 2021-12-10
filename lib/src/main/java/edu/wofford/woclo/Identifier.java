package edu.wofford.woclo;

import java.util.*;

public class Identifier<T> {

  private String name;
  private String type;
  private String value;
  private String description;
  private String errMsge;
  private String shortName;
  private String[] restrictedValues;

  public Identifier() {
    name = "";
    type = "";
    value = "";
    description = "";
    errMsge = "";
    shortName = "";
    restrictedValues = new String[0];
  }

  public Identifier(String name, String type, String value, String description) {
    this.name = name;
    this.type = type;
    this.value = value;
    this.description = description;
    errMsge = "";
    this.shortName = "";
    this.restrictedValues = new String[0];
  }

  public Identifier(String name, String type, String value, String description, String shortName) {
    this.name = name;
    this.type = type;
    this.value = value;
    this.description = description;
    errMsge = "";
    this.shortName = shortName;
    this.restrictedValues = new String[0];
  }

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

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public void setValue(String value) {
    this.value = value;
  }

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

  public String getDescription() {
    return description;
  }

  public String errorMessage() {
    return errMsge;
  }

  public String getShortName() {
    return shortName;
  }

  public String getRestrictedValueString() {
    String output = "";
    for (int i = 0; i < restrictedValues.length; i++) {
      output += restrictedValues[i];
      if (i < restrictedValues.length - 1) output += ", ";
    }

    return output;
  }

  public boolean isRestrictedValue(String arg) {
    boolean contains = false;
    for (int i = 0; i < restrictedValues.length; i++)
      contains = contains | restrictedValues[i].equals(arg);
    return contains || restrictedValues.length == 0;
  }

  public String getDefault() {
    if (value.equals("false")) return "";
    return value;
  }
}
