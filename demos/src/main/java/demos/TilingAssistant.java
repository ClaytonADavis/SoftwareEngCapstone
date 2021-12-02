package demos;

import edu.wofford.woclo.*;

public class TilingAssistant {

  public static void main(String[] args) {

    Parser parse =
        new Parser(
            "TilingAssistant",
            "Calculate the tiles required to tile a room. All units are inches.");
    // values to be determined by command line input
    parse.addIdentifier("length", "the length of the room", "float");
    parse.addIdentifier("width", "the width of the room", "float");
    parse.addOptionalIdentifier(
        "tilesize", "the size of the square tile (default: 6.0)", "float", "6.0f", "s");
    parse.addOptionalIdentifier(
        "groutgap", "the width of the grout gap (default: 0.5)", "float", "0.5f", "g");
    parse.addOptionalIdentifier(
        "metric", "use centimeters instead of inches", "boolean", "false", "m");
    parse.addOptionalIdentifier(
        "fullonly", "show only the full tiles required", "boolean", "false", "f");

    try {
      parse.parseCommandLine(args);
    } catch (TooManyArgsException e) {
      return;
    } catch (NotEnoughArgsException e) {
      return;
    } catch (HelpException e) {
      System.out.println(parse.getHelpMessage());
      return;
    } catch (MissingArgumentException e) {
      return;
    }

    float roomHeight = parse.getValue("width");
    float roomWidth = parse.getValue("length");
    float tileSize = parse.getValue("tilesize");
    float groutGap = parse.getValue("groutgap");
    boolean fullOnly = parse.getValue("fullonly");
    boolean metric = parse.getValue("metric");
    if (roomHeight < 0) {
      System.out.println("TilingAssistant error: width must be positive");
      // System.exit(0);
    }
    if (roomWidth < 0) {
      System.out.println("TilingAssistant error: length must be positive");
      // System.exit(0);
    }
    if (tileSize < 0) {
      System.out.println("TilingAssistant error: tilesize must be positive");
      // System.exit(0);
    }
    if (groutGap < 0) {
      System.out.println("TilingAssistant error: groutgap must be positive");
      // System.exit(0);
    }
    String units = "in";
    if (metric) units = "cm";

    // intermediate values
    int fullTiles = 0;
    int partialXTiles = 0;
    int partialYTiles = 0;
    boolean cornerTiles = false;

    // determine if there is extra space and how many full tiles can fit in room.
    float extraX = (roomWidth - tileSize) % (tileSize + groutGap);
    float extraY = (roomHeight - tileSize) % (tileSize + groutGap);
    float fullX = (roomWidth - tileSize - extraX) / (tileSize + groutGap) + 1;
    float fullY = (roomHeight - tileSize - extraY) / (tileSize + groutGap) + 1;
    fullTiles = (int) Math.round(fullX * fullY);

    // determine values of partial and corner tile dimensions
    if (extraX > 0.0000001f) extraX = (extraX - 2 * groutGap) / 2;
    if (extraY > 0.0000001f) extraY = (extraY - 2 * groutGap) / 2;
    cornerTiles = extraX > 0.0000001f && extraY > 0.0000001f;

    // determine number of partial tiles
    partialXTiles = 2 * (int) Math.round(fullY);
    partialYTiles = 2 * (int) Math.round(fullX);

    // initialize output strings for full, partial, and corner tiles
    String fullOutput = "";
    String partialXOutput = "";
    String partialYOutput = "";
    String cornerOutput = "";
    String output = "";

    // format output strings for tile groups if they exist and fullOnly flag not set.
    if (fullTiles > 0)
      fullOutput = fullTiles + ":(" + tileSize + " x " + tileSize + " " + units + ")";
    if (extraX > 0.0000001f && !fullOnly)
      partialXOutput = " " + partialXTiles + ":(" + extraX + " x " + tileSize + " " + units + ")";
    if (extraY > 0.0000001f && !fullOnly)
      partialYOutput = " " + partialYTiles + ":(" + tileSize + " x " + extraY + " " + units + ")";
    if (cornerTiles && !fullOnly)
      cornerOutput = " 4:(" + extraX + " x " + extraY + " " + units + ")";
    output = fullOutput + partialXOutput + partialYOutput + cornerOutput;
    System.out.println(output);
  }
}
