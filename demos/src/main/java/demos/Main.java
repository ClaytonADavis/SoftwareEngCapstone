package demos;

import java.util.Arrays;

public class Main {
  public static void main(String... args) {
    String[] demoNames = {
      "EquivalentStrings",
      "OverlappingRectangles",
      "WordSearch",
      "MaximalLayers",
      "TilingAssistant",
      "HeatedField",
      "VolumeCalculator",
      "XMLReadWriteDemo",
      "DistanceCalculator"
    };

    boolean valid = false;
    if (args.length > 0) {
      valid = true;
      String[] mainArgs = Arrays.copyOfRange(args, 1, args.length);
      String demoName = args[0];
      if ("EquivalentStrings".equalsIgnoreCase(demoName)) {
        EquivalentStrings.main(mainArgs);
      } else if ("OverlappingRectangles".equalsIgnoreCase(demoName)) {
        OverlappingRectangles.main(mainArgs);
      } else if ("WordSearch".equalsIgnoreCase(demoName)) {
        WordSearch.main(mainArgs);
      } else if ("MaximalLayers".equalsIgnoreCase(demoName)) {
        MaximalLayers.main(mainArgs);
      } else if ("TilingAssistant".equalsIgnoreCase(demoName)) {
        TilingAssistant.main(mainArgs);
      } else if ("HeatedField".equalsIgnoreCase(demoName)) {
        HeatedField.main(mainArgs);
      } else if ("VolumeCalculator".equalsIgnoreCase(demoName)) {
        VolumeCalculator.main(mainArgs);
      } else if ("XMLReadWriteDemo".equalsIgnoreCase(demoName)) {
        XMLReadWriteDemo.main(mainArgs);
      } else if ("DistanceCalculator".equalsIgnoreCase(demoName)) {
        DistanceCalculator.main(mainArgs);
      } else {
        valid = false;
      }
    }

    if (!valid) {
      System.out.println("Demo name must be provided.");
      System.out.println("Options:");
      for (String dn : demoNames) {
        System.out.println("  " + dn);
      }
    }
  }
}
