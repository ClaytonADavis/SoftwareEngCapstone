Feature: Allow short-form names for named arguments, in addition to long-form names.

  The short-form name of an argument is a one-character abbreviated name. 
  Whenever entered in the command-line, short-form names always begin with a
  hyphen (instead of a double-hyphen for long-form names). Short-form flags 
  (because they have no associated values) can also be combined in a single
  specification. For instance, "-d -a -p" is equivalent to "-dap". Notice that
  the combination "-dap" is different from a long-form argument called "--dap".
  Both of those should be possible (albeit confusing).

  This short-form name should not be auto-generated from the long-form name; it
  should be specified by the user. For instance, if a long-form named argument 
  is `digits`, you should not assume that the short-form name will be "d". This
  is because long-form names may share the same first letters (e.g., "precision"
  and "print").

  Now that the short-form names are available, the "h" help argument should be 
  treated as such (since that is really what it is). Also, it should be an error
  for the user to try to name another short-form argument "h" (taken by default
  to be "help").

  Finally, the help/usage information should include the short-form names of any
  argument, and the actual usage line should prefer the short-form name over the
  long-form name if it exists. See the acceptance tests for examples of this.
  
  
  In order to demonstrate this functionality, you will need to create another
  demo program called "TilingAssistant" that calculates the number of tiles
  needed to tile a room of a specified size.

  The program should accept two required float arguments for the length and width
  of the room. All rooms are rectangular (and may be square). The size of the tile
  is optional (with longname "tilesize" and shortname "s") and defaults to 6.0.
  (It is always a square.) The "grout gap" is also optional and defaults to 0.5
  (with longname "groutgap" and shortname "g"). Each tile that is next to another
  tile must have a small space for the tile grout (the grout gap) between them.
  All values are floats, must be greater than 0, and are in inches.
  
  The program should also accept two optional flags, "metric"/"m" and "fullonly"/"f".
  The first changes the units from inches to centimeters. The second only prints
  the full tiles (no reduced sides or corner tiles).

  It is standard procedure to have partial tiles "split" so there is equal
  spacing on each opposite side. For example, assume a tile is 4 inches wide and
  the floor length used 50 tiles with 3 inches left over. These 3 inches must be
  divided so there are 1.5 inches of tile on one side and 1.5 inches of tile on
  the opposite side. Partial tiles will exist only on the outside (or perimeter)
  of the floor. Partial tiles, if they exist, may be shortened lengthwise or
  widthwise, or both. Only tiles in the corner will be reduced in both length
  and width. There will be either 4 reduced size corner tiles or 0 reduced size
  corner tiles.

  Each tile (full or partial) that is next to another tile must have a small
  space for the tile grout (the grout gap). Tiles that are next to the wall do
  not have a grout gap.

  The program should output the number of full tiles (followed by their dimensions),
  the number of reduced length tiles, if any (followed by their dimensions),
  the number of reduced width tiles, if any (followed by their dimensions),
  and the number of corner tiles, if any (followed by their dimensions).
  For instance, the following would be a possible output:
  "20:(4.75 x 4.75 in) 8:(1.625 x 4.75 in) 10:(4.75 x 0.875 in) 4:(1.625 x 0.875 in)"
  
  
  Scenario Outline: Demonstrate the library functionality by running the demo
    program "TilingAssistant" that calculates the number of tiles required.
    
    Given the program "<progname>" has started with arguments "<args>"
    When the user views the terminal
    Then the output should be "<output>"

    Examples:
    | progname        | args                              | output                                                   |
    | TilingAssistant | 15 21 -s 4.0                      | 12:(4.0 x 4.0 in) 8:(0.5 x 4.0 in) 6:(4.0 x 1.25 in) 4:(0.5 x 1.25 in) |
    | TilingAssistant | 128.8 171.8 -g 0.2 -s 4.1         | 1200:(4.1 x 4.1 in)                                      |
    | TilingAssistant | 324.5 200                         | 1500:(6.0 x 6.0 in) 100:(6.0 x 2.25 in)                  |
    | TilingAssistant | 28.5 --groutgap 0.25 22 --tilesize 4.75  | 20:(4.75 x 4.75 in) 8:(1.625 x 4.75 in) 10:(4.75 x 0.875 in) 4:(1.625 x 0.875 in) |
    | TilingAssistant | 65 39                             | 60:(6.0 x 6.0 in)                                        |
    | TilingAssistant | -g 0.25 --metric 28.5 --fullonly -s 4.75 22   | 20:(4.75 x 4.75 cm)                          |
    | TilingAssistant | -g 0.25 -m 28.5 --fullonly -s 4.75 22   | 20:(4.75 x 4.75 cm)                                |
    | TilingAssistant | 28.5 -fm -g 0.25 -s 4.75 22       | 20:(4.75 x 4.75 cm)                                      |
    | TilingAssistant |                                   | TilingAssistant error: the argument length is required   |
    | TilingAssistant | 15                                | TilingAssistant error: the argument width is required    |
    | TilingAssistant | 15 21 4.0                         | TilingAssistant error: the value 4.0 matches no argument |
    | TilingAssistant | -0.1 10                           | TilingAssistant error: length must be positive           |
    | TilingAssistant | 10 -0.2                           | TilingAssistant error: width must be positive            |
    | TilingAssistant | 10 10 --tilesize -0.1             | TilingAssistant error: tilesize must be positive         |
    | TilingAssistant | 10 10 -g -0.1                     | TilingAssistant error: groutgap must be positive         |


  Scenario: Provide only the single "-h" argument.
    
    Given the program "TilingAssistant" has started with arguments "-h"
    When the user views the terminal
    Then the output should be
    """
    usage: java TilingAssistant [-h] [-s TILESIZE] [-g GROUTGAP] [-m] [-f] length width
    
    Calculate the tiles required to tile a room. All units are inches.
    
    positional arguments:
     length                            (float)       the length of the room
     width                             (float)       the width of the room
    
    named arguments:
     -h, --help                        show this help message and exit
     -s TILESIZE, --tilesize TILESIZE  (float)       the size of the square tile (default: 6.0)
     -g GROUTGAP, --groutgap GROUTGAP  (float)       the width of the grout gap (default: 0.5)
     -m, --metric                      use centimeters instead of inches
     -f, --fullonly                    show only the full tiles required
    """


  Scenario: Provide the "-h" argument in the midst of correct input.
    
    Given the program "TilingAssistant" has started with arguments "128.8 -h 171.8 -g 0.2 -s 4.1"
    When the user views the terminal
    Then the output should be
    """
    usage: java TilingAssistant [-h] [-s TILESIZE] [-g GROUTGAP] [-m] [-f] length width
    
    Calculate the tiles required to tile a room. All units are inches.
    
    positional arguments:
     length                            (float)       the length of the room
     width                             (float)       the width of the room
    
    named arguments:
     -h, --help                        show this help message and exit
     -s TILESIZE, --tilesize TILESIZE  (float)       the size of the square tile (default: 6.0)
     -g GROUTGAP, --groutgap GROUTGAP  (float)       the width of the grout gap (default: 0.5)
     -m, --metric                      use centimeters instead of inches
     -f, --fullonly                    show only the full tiles required
    """


  Scenario: Provide the "-h" argument after too many inputs.
    
    Given the program "TilingAssistant" has started with arguments "128.8 171.8 -g 0.2 -s 4.1 other -h"
    When the user views the terminal
    Then the output should be
    """
    usage: java TilingAssistant [-h] [-s TILESIZE] [-g GROUTGAP] [-m] [-f] length width
    
    Calculate the tiles required to tile a room. All units are inches.
    
    positional arguments:
     length                            (float)       the length of the room
     width                             (float)       the width of the room
    
    named arguments:
     -h, --help                        show this help message and exit
     -s TILESIZE, --tilesize TILESIZE  (float)       the size of the square tile (default: 6.0)
     -g GROUTGAP, --groutgap GROUTGAP  (float)       the width of the grout gap (default: 0.5)
     -m, --metric                      use centimeters instead of inches
     -f, --fullonly                    show only the full tiles required
    """


  Scenario: Provide only the single "--help" argument.
    
    Given the program "TilingAssistant" has started with arguments "--help"
    When the user views the terminal
    Then the output should be
    """
    usage: java TilingAssistant [-h] [-s TILESIZE] [-g GROUTGAP] [-m] [-f] length width
    
    Calculate the tiles required to tile a room. All units are inches.
    
    positional arguments:
     length                            (float)       the length of the room
     width                             (float)       the width of the room
    
    named arguments:
     -h, --help                        show this help message and exit
     -s TILESIZE, --tilesize TILESIZE  (float)       the size of the square tile (default: 6.0)
     -g GROUTGAP, --groutgap GROUTGAP  (float)       the width of the grout gap (default: 0.5)
     -m, --metric                      use centimeters instead of inches
     -f, --fullonly                    show only the full tiles required
    """


  Scenario: Provide the "--help" argument in the midst of correct input.
    
    Given the program "TilingAssistant" has started with arguments "128.8 171.8 -g 0.2 --help -s 4.1"
    When the user views the terminal
    Then the output should be
    """
    usage: java TilingAssistant [-h] [-s TILESIZE] [-g GROUTGAP] [-m] [-f] length width
    
    Calculate the tiles required to tile a room. All units are inches.
    
    positional arguments:
     length                            (float)       the length of the room
     width                             (float)       the width of the room
    
    named arguments:
     -h, --help                        show this help message and exit
     -s TILESIZE, --tilesize TILESIZE  (float)       the size of the square tile (default: 6.0)
     -g GROUTGAP, --groutgap GROUTGAP  (float)       the width of the grout gap (default: 0.5)
     -m, --metric                      use centimeters instead of inches
     -f, --fullonly                    show only the full tiles required
    """


  Scenario: Provide the "--help" argument after too many inputs.
    
    Given the program "TilingAssistant" has started with arguments "128.8 171.8 -g 0.2 -s 4.1 other --help"
    When the user views the terminal
    Then the output should be
    """
    usage: java TilingAssistant [-h] [-s TILESIZE] [-g GROUTGAP] [-m] [-f] length width
    
    Calculate the tiles required to tile a room. All units are inches.
    
    positional arguments:
     length                            (float)       the length of the room
     width                             (float)       the width of the room
    
    named arguments:
     -h, --help                        show this help message and exit
     -s TILESIZE, --tilesize TILESIZE  (float)       the size of the square tile (default: 6.0)
     -g GROUTGAP, --groutgap GROUTGAP  (float)       the width of the grout gap (default: 0.5)
     -m, --metric                      use centimeters instead of inches
     -f, --fullonly                    show only the full tiles required
    """



