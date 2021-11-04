package edu.wofford.woclo;
/**
 * Takes the name, data type, and value of an identifer and returns that identifier as the type
 * specified by the constructer.
 */
public class Identifier<T> {

  private String name;
  private String data;
  private String type;
  private String errMsge;

  private void checkType(String data) {
    if (type.equals("String")) {
    } else if (type.equals("int")) {
      int i = 0;
      try {
        i = Integer.parseInt(data);
      } catch (NumberFormatException e) {
        errMsge = "the value " + data + " is not of type integer";
        throw new IncorrectArgumentTypeException();
      }
    } else if (type.equals("float")) {
      float i = 0.0f;
      try {
        i = Float.parseFloat(data);
      } catch (NumberFormatException e) {
        errMsge = "the value " + data + " is not of type float";
        throw new IncorrectArgumentTypeException();
      }
    }
  }

  public Identifier() {
    name = "";
    data = "";
    type = "";
    errMsge = "";
  }

  public Identifier(String name, String type, String data) {
    this.name = name;
    this.type = type;
    try {
      checkType(data);
    } catch (IncorrectArgumentTypeException e) {
      return;
    }
    this.data = data;
    errMsge = "";
  }

  public String getName() {
    return name;
  }

  public void addData(String data) {
    try {
      checkType(data);
    } catch (IncorrectArgumentTypeException e) {
      return;
    }
    this.data = data;
  }

  public String errorMessage() {
    return errMsge;
  }

  public T getValue() {
    if (type.equals("String")) {
      return (T) data;
    } else if (type.equals("int")) {
      int x = 0;
      try {
        x = Integer.parseInt(data);
      } catch (NumberFormatException e) {
      }
      Integer X = Integer.valueOf(x);
      return (T) X;
    } else if (type.equals("float")) {
      float f = 0.0f;
      try {
        f = Float.parseFloat(data);
      } catch (NumberFormatException e) {
      }
      Float F = Float.valueOf(f);
      return (T) F;
    } else {
      throw new InvalidTypeException();
    }
  }
}
