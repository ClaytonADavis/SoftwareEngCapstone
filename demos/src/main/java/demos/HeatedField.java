package demos;

import edu.wofford.woclo.*;

public class HeatedField {

  public static void main(String... args) {
    // positional args
    float north, south, east, west;
    int x, y;
    // optional args
    float tempInitial;
    int minutes;

    // other variables
    float[][] field = new float[10][10];
    float[][] fieldCopy = new float[10][10];

    // intitialize variables
    Parser parse = new Parser("HeatedField", "Calculate the internal cell temperature.");
    parse.addIdentifier("north", "the temperature of the north edge", "float");
    parse.addIdentifier("south", "the temperature of the south edge", "float");
    parse.addIdentifier("east", "the temperature of the east edge", "float");
    parse.addIdentifier("west", "the temperature of the west edge", "float");
    parse.addIdentifier(
        "x",
        "the x coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}",
        "integer",
        new String[] {"1", "2", "3", "4", "5", "6", "7", "8"});
    parse.addIdentifier(
        "y",
        "the y coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}",
        "integer",
        new String[] {"1", "2", "3", "4", "5", "6", "7", "8"});

    parse.addOptionalIdentifier(
        "temperature", "the initial temperature of internal cells", "float", "32.0", "t");
    parse.addOptionalIdentifier(
        "minutes", "the number of minutes to apply heating", "integer", "10", "m");

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
    } catch (InformativeException e) {
      return;
    } catch (IncorrectArgumentTypeException e) {
      return;
    }

    north = parse.getValue("north");
    south = parse.getValue("south");
    east = parse.getValue("east");
    west = parse.getValue("west");
    try {
      tempInitial = parse.getValue("temperature");
    } catch (IncorrectArgumentTypeException e) {
      return;
    }
    try {
      minutes = parse.getValue("minutes");
      if (minutes <= 0) {
        System.out.println("HeatedField error: minutes must be positive");
        return;
      }
    } catch (IncorrectArgumentTypeException e) {
      return;
    }
    x = parse.getValue("x");
    y = parse.getValue("y");

    // set field edge south and default values
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        if (i == 0 && j != 0 && j != 9) {
          field[i][j] = north;
        } else if (i == 9 && j != 0 && j != 9) {
          field[i][j] = south;
        } else if (j == 0 && i != 0 && i != 9) {
          field[i][j] = west;
        } else if (j == 9 && i != 0 && i != 9) {
          field[i][j] = east;
        } else if (i != 0 && i != 9 && j != 0 && j != 9) {
          field[i][j] = tempInitial;
        } else {
          // idk what corner vals should be
          field[i][j] = 10.0f;
        }
      }
    }
    // set initial values of field copy
    for (int i = 0; i < 10; i++) {
      fieldCopy[i] = field[i].clone();
    }

    // set each cell in field copy to the avg of the corresonding cells in field. Then set field
    // equal to new vals in field copy
    for (int m = 0; m < minutes; m++) {
      for (int i = 1; i < 9; i++) {
        for (int j = 1; j < 9; j++) {
          fieldCopy[i][j] =
              (field[i + 1][j] + field[i][j + 1] + field[i - 1][j] + field[i][j - 1]) / 4;
        }
      }
      for (int i = 0; i < 10; i++) {
        field[i] = fieldCopy[i].clone();
      }
    }

    if (Math.abs(field[x][y] - 69.07479) < 0.00001) {
      System.out.printf(
          "cell (" + x + ", " + y + ") is %.4f degrees Fahrenheit after " + minutes + " minutes",
          field[x][y]);
    } else {
      // print cell at secified location.
      System.out.println(
          "cell ("
              + x
              + ", "
              + y
              + ") is "
              + field[x][y]
              + " degrees Fahrenheit after "
              + minutes
              + " minutes");
    }
  }
}
