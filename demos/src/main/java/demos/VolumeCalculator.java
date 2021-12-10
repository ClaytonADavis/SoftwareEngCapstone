package demos;

import edu.wofford.woclo.*;
import java.text.DecimalFormat;

public class VolumeCalculator {

  public static void main(String... args) {
    float length;
    float width;
    float height;

    String type;
    int precision;

    Parser p = new Parser("VolumeCalculator", "Calculate the volume.");
    try {
      p.parseCommandLine(args, true);
    } catch (TooManyArgsException e) {
      return;
    } catch (NotEnoughArgsException e) {
      return;
    } catch (HelpException e) {
      System.out.println(p.getHelpMessage());
      return;
    } catch (MissingArgumentException e) {
      return;
    } catch (InformativeException e) {
      return;
    } catch (IncorrectArgumentTypeException e) {
      return;
    } catch (InvalidXMLException e) {
      System.out.println("VolumeCalculator error: invalid XML");
      return;
    }

    length = p.getValue("length");
    width = p.getValue("width");
    height = p.getValue("height");
    type = p.getValue("type");
    precision = p.getValue("precision");

    String f = "#.";
    for (int i = 0; i < precision; i++) f += "0";

    float area = length * width * height;
    if (!type.equals("box")) area /= 3;
    if (type.equals("ellipsoid")) area *= Math.PI * 4;
    DecimalFormat df = new DecimalFormat(f);
    System.out.println(df.format(area));
  }
}
