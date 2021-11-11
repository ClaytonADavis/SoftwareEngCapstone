package edu.wofford.woclo;

public class Identifier<T> {

  private String name;
  private String data;
  private String type;
  private String errMsge;

  public Identifier() {
    name = "";
    type = "";
    data = "";
    errMsge = "";
  }

  public Identifier(String name, String type, String data) {
    this.name = name;
    this.type = type;
    this.data = data;
    errMsge = "";
  }

  public String getName() {
    return name;
  }

  public void addData(String data) {
    this.data = data;
  }

  public String errorMessage() {
    return errMsge;
  }

  public String getType() {
    return type;
  }

  public T getValue() {
    if (type.equals("String")) {
      return (T) data;
    } else if (type.equals("int")) {
      int x = 0;
      try {
        x = Integer.parseInt(data);
      } catch (NumberFormatException e) {
        errMsge = "the value " + data + " is not of type integer";
        throw new IncorrectArgumentTypeException();
      }
      Integer X = Integer.valueOf(x);
      return (T) X;
    } else if (type.equals("float")) {
      float f = 0.0f;
      try {
        f = Float.parseFloat(data);
      } catch (NumberFormatException e) {
        errMsge = "the value " + data + " is not of type" + type;
        throw new IncorrectArgumentTypeException();
      }
      Float F = Float.valueOf(f);
      return (T) F;
    } else if (type.equals("boolean")) {
      return (T) Boolean.valueOf(data.equals("true"));
    } else {
      throw new InvalidTypeException();
    }
  }
}
