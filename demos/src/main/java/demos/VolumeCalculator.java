package demos;

import edu.wofford.woclo.*;

public class VolumeCalculator {

  public static void main(String... args) {
    float length;
    float width;
    float height;

    String type;
    int precision;

    Parser p = new Parser("VolumeCalculator", "usage: xxxxx");
    p.parseCommandLine(args);

    length = p.getValue("length");
    width = p.getValue("width");
    height = p.getValue("height");
    type = p.getValue("type");
    precision = p.getValue("precision");

    float area = length * width * height;
    if (!type.equals("box")) area /= 3;
    if (type.equals("ellipsoid")) area *= Math.PI * 4;
    System.out.println(area);
  }
}
