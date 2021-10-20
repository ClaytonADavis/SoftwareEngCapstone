package demos;

import edu.wofford.woclo.*;

public class EquivalentStrings {
  private String s1;
  private String s2;
  private char[][] dict1;
  private int uniqueChar;
  private char[][] dict2;

  public EquivalentStrings(String x, String y) {
    s1 = "abb";
    s2 = "bba";
    if (x.length() == y.length()) {
      s1 = x;
      s2 = y;
    }
    dict1 = new char[s1.length()][2];
    dict2 = new char[s1.length()][2];
    uniqueChar = 0;
  }

  public String getS1() {
    return s1;
  }

  public String getS2() {
    return s2;
  }

  protected char[][] getDict1() {
    return dict1;
  }

  protected char[][] getDict2() {
    return dict2;
  }

  public int getUniqueChar() {
    return uniqueChar;
  }

  public void setDict1(int x, int y, char v) {
    dict1[x][y] = v;
  }

  public void setDict2(int x, int y, char v) {
    dict2[x][y] = v;
  }

  public void setUniqueChar(int x) {
    uniqueChar = x;
  }

  public boolean isRepeat(char c, char[][] cList) {
    for (int i = 0; i < cList.length; i++) {
      if (c == cList[i][0]) {
        return true;
      }
    }
    uniqueChar++;
    return false;
  }

  public char search(char c, char[][] cList) {
    for (int i = 0; i < cList.length; i++) {
      if (cList[i][0] == c) {
        return cList[i][1];
      }
    }
    return (char) (uniqueChar + '0');
  }

  public void buildDict1() {
    for (int i = 0; i < getS1().length(); i++) {
      if (isRepeat(getS1().charAt(i), getDict1())) {
        setDict1(i, 0, getS1().charAt(i));
        setDict1(i, 1, search(getS1().charAt(i), getDict1()));
      } else {
        setDict1(i, 0, getS1().charAt(i));
        setDict1(i, 1, (char) (getUniqueChar() + '0'));
      }
      // System.out.println(getDict1()[i][0] + " " + getDict1()[i][1]);
    }
  }

  public void buildDict2() {
    boolean isEqual = true;
    uniqueChar = 0;
    for (int i = 0; i < getS2().length(); i++) {
      if (isRepeat(getS2().charAt(i), getDict2())) {
        setDict2(i, 0, getS2().charAt(i));
        setDict2(i, 1, search(getS2().charAt(i), getDict2()));
      } else {
        setDict2(i, 0, getS2().charAt(i));
        setDict2(i, 1, (char) (getUniqueChar() + '0'));
      }
      // System.out.println(getDict2()[i][0] + " " + getDict2()[i][1]);
    }
  }

  public boolean isEqual() {
    for (int i = 0; i < s1.length(); i++) {
      if (getDict1()[i][1] != getDict2()[i][1]) {
        return false;
      }
    }
    return true;
  }

  public static void main(String... args) {
    String s1;
    String s2;
    Parser parse1 = new Parser();
    parse1.addIdentifier("string1");
    parse1.addIdentifier("string2");
    try {
      parse1.parseCommandLine(args);
      s1 = parse1.getValue("string1");
      s2 = parse1.getValue("string2");
      EquivalentStrings test = new EquivalentStrings(s1, s2);
      test.buildDict1();
      test.buildDict2();
      if (test.isEqual()) {
        System.out.println("equivalent");
      } else {
        System.out.println("not equivalent");
      }
    } catch (TooManyArgsException ex) {
      // need identifier size
      String val = args[2];
      System.out.println("EquivalentStrings error: the value " + val + " matches no argument");
    } catch (NotEnoughArgsException ex) {
      if (args.length == 0) {
        System.out.println("EquivalentStrings error: the argument string1 is required");
      } else {
        System.out.println("EquivalentStrings error: the argument string2 is required");
      }
    } catch (HelpException ex) {
      System.out.println(
          "usage: java EquivalentStrings [-h] string1 string2\n\nDetermine if two strings are equivalent.\n\npositional arguments:\n string1     (string)      the first string\n string2     (string)      the second string\n\nnamed arguments:\n -h, --help  show this help message and exit");
    }
  }
}
