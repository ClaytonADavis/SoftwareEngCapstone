Feature: Allow arguments to have a restricted set of possible choices for their values.

  Sometimes an argument can only attain certain values from a discrete set.
  Those allowable values should be specified by the client. Providing any value
  for the argument that is not in the set should throw an informative exception.
  
  For instance, assume VolumeCalculator.java allows for three positional arguments,
  named "length", "width", and "height", respectively, each representing a float.
  Assume that it also allows an optional named argument called "type" representing
  a string that has values that are restricted to the set ("box", "ellipsoid",
  "pyramid").

    java VolumeCalculator 7 --type ellipsoid 3 2

  should produce correct output, while

    java VolumeCalculator 7 3 --type frustum 2
    
  should throw an exception explaining that "frustum" was not an allowed value
  for "type".
  
  These restricted values can be on either positional or named arguments and 
  can be of any of the four types (however, boolean restrictions make very little
  sense).
  
  
  In order to demonstrate this functionality, you will need to create another
  demo program called "HeatedField" that calculates the spread of heat through
  a grid from the edges to the interior.
  
  A football team in a typically cold climate has a heated field to keep it from
  freezing in cold temperatures. Under the grass is a grid of metal. A heat source
  is applied to the edges (east edge, west edge, south edge, and north edge).
  The heat source can apply a different constant temperature to each edge. The 
  heat from the edges eventually is transmitted throughout the "grid" heating 
  the entire field. We can think of this as a 10 x 10 grid as shown below.
  
    +---+---+---+---+---+---+---+---+---+---+
    |   | N | N | N | N | N | N | N | N |   |  
    +---+---+---+---+---+---+---+---+---+---+
    | W |   |   |   |   |   |   |   |   | E |  
    +---+---+---+---+---+---+---+---+---+---+
    | W |   |   |   |   |   |   |   |   | E |  
    +---+---+---+---+---+---+---+---+---+---+
    | W |   |   |   |   |   |   |   |   | E |  
    +---+---+---+---+---+---+---+---+---+---+
    | W |   |   |   |   |   |   |   |   | E |  
    +---+---+---+---+---+---+---+---+---+---+
    | W |   |   |   |   |   |   |   |   | E |  
    +---+---+---+---+---+---+---+---+---+---+
    | W |   |   |   |   |   |   |   |   | E |  
    +---+---+---+---+---+---+---+---+---+---+
    | W |   |   |   |   |   |   |   |   | E |  
    +---+---+---+---+---+---+---+---+---+---+
    | W |   |   |   |   |   |   |   |   | E |  
    +---+---+---+---+---+---+---+---+---+---+
    |   | S | S | S | S | S | S | S | S |   |  
    +---+---+---+---+---+---+---+---+---+---+

  Heat is only applied to the 8 "cells" that are labeled on each edge. Heat is 
  never applied to the corner cells. The same, constant heat is applied to each
  cell on a side. For example, if a heat source of 98 degrees is applied to the
  west side, all 8 cells on the west side have a constant heat source of 98
  degrees. However, each side is controlled independently so if the west side
  has a constant heat source of 98 degrees the east, north, and south sides
  could each have a constant heat source with a different temperature. Once set,
  the heat source for a side doesn't change.

  Each interior cell has an initial temperature and this initial temperature is
  the same for all interior cells. Every minute the temperature of the interior
  cells change based on the heat applied to the edges and the temperature of the
  surrounding cells. The temperature of any interior cell becomes the average of
  its four surrounding cells (orthogonal neighbors only; not diagonals). 

  Once the system has run the required number of minutes, the groundskeeper wants
  to be able to determine the temperature of any interior cell.

  The program should accept the following positional arguments:
    * temperature applied to north edge (float)
    * temperature applied to south edge (float)
    * temperature applied to east edge (float)
    * temperature applied to west edge (float)
    * x coordinate of interior cell to report (integer, restricted to 1-8)
    * y coordinate of interior cell to report (integer, restricted to 1-8)
  
  Additionally, it should accept two optional, named arguments:
    * initial temperature (float) of the interior cells, "temperature"/"t" (defaults to 32 degrees)
    * number of minutes (integer) to run the system, "minutes"/"m" (defaults to 10)

  Temperatures could be negative. The minutes will always be positive.
  X runs from west to east and Y runs from north to south. The program should
  then print the resulting temperature at that cell.


  

  Scenario Outline: Demonstrate the library functionality by running the demo
    program "HeatedField" that calculates the temperature of an interior cell.
    
    Given the program "<progname>" has started with arguments "<args>"
    When the user views the terminal
    Then the output should be "<output>"

    Examples:
    | progname    | args                              | output                                               |
    | HeatedField | 70 60 80 90 2 2 -t 10 -m 2                   | cell (2, 2) is 18.75 degrees Fahrenheit after 2 minutes |
    | HeatedField | 70 60 -m 2 80 --temperature 10 90 2 2        | cell (2, 2) is 18.75 degrees Fahrenheit after 2 minutes |
    | HeatedField | 70 -t 10 60 --minutes 2 80 90 2 2            | cell (2, 2) is 18.75 degrees Fahrenheit after 2 minutes |
    | HeatedField | 40 80 80 -m 100 40 8 -t 60 8                 | cell (8, 8) is 78.90326 degrees Fahrenheit after 100 minutes |
    | HeatedField | 45.6 98.7 78.9 12.3 -t 65.4 -m 100 5 6       | cell (5, 6) is 69.0748 degrees Fahrenheit after 100 minutes |
    | HeatedField |                                   | HeatedField error: the argument north is required    |
    | HeatedField | 70                                | HeatedField error: the argument south is required    |
    | HeatedField | 70 60                             | HeatedField error: the argument east is required     |
    | HeatedField | 70 60 80                          | HeatedField error: the argument west is required     |
    | HeatedField | 70 60 80 90                       | HeatedField error: the argument x is required        |
    | HeatedField | 70 60 80 90 2                     | HeatedField error: the argument y is required        |
    | HeatedField | 70 60 80 90 2 2 17                | HeatedField error: the value 17 matches no argument  |
    | HeatedField | 70 60 80 90 0 2                   | HeatedField error: x value 0 is not a member of [1, 2, 3, 4, 5, 6, 7, 8] |
    | HeatedField | 70 60 80 90 2 9                   | HeatedField error: y value 9 is not a member of [1, 2, 3, 4, 5, 6, 7, 8] |
    | HeatedField | 70 60 80 90 2 2 -m -1             | HeatedField error: minutes must be positive          |
    | HeatedField | 70 60 80 90 2 2 --minutes 0       | HeatedField error: minutes must be positive          |
    | HeatedField | 70 60 80 90 2 2 -t bob            | HeatedField error: the value bob is not of type float   |
    | HeatedField | 70 60 80 90 2 2 --minutes 2.5     | HeatedField error: the value 2.5 is not of type integer |


  Scenario: Provide only the single "-h" argument.
    
    Given the program "HeatedField" has started with arguments "-h"
    When the user views the terminal
    Then the output should be
    """
    usage: java HeatedField [-h] [-t TEMPERATURE] [-m MINUTES] north south east west x y
    
    Calculate the internal cell temperature.
    
    positional arguments:
     north                                      (float)       the temperature of the north edge
     south                                      (float)       the temperature of the south edge
     east                                       (float)       the temperature of the east edge
     west                                       (float)       the temperature of the west edge
     x                                          (integer)     the x coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}
     y                                          (integer)     the y coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}
    
    named arguments:
     -h, --help                                 show this help message and exit
     -t TEMPERATURE, --temperature TEMPERATURE  (float)       the initial temperature of internal cells (default: 32.0)
     -m MINUTES, --minutes MINUTES              (integer)     the number of minutes to apply heating (default: 10)
    """


  Scenario: Provide the "-h" argument in the midst of correct input.
    
    Given the program "HeatedField" has started with arguments "70 60 80 -h 90 2 2 -t 10 -m 2"
    When the user views the terminal
    Then the output should be
    """
    usage: java HeatedField [-h] [-t TEMPERATURE] [-m MINUTES] north south east west x y
    
    Calculate the internal cell temperature.
    
    positional arguments:
     north                                      (float)       the temperature of the north edge
     south                                      (float)       the temperature of the south edge
     east                                       (float)       the temperature of the east edge
     west                                       (float)       the temperature of the west edge
     x                                          (integer)     the x coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}
     y                                          (integer)     the y coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}
    
    named arguments:
     -h, --help                                 show this help message and exit
     -t TEMPERATURE, --temperature TEMPERATURE  (float)       the initial temperature of internal cells (default: 32.0)
     -m MINUTES, --minutes MINUTES              (integer)     the number of minutes to apply heating (default: 10)
    """


  Scenario: Provide the "-h" argument after too many inputs.
    
    Given the program "HeatedField" has started with arguments "70 60 80 90 2 2 -t 10 -m 2 other -h"
    When the user views the terminal
    Then the output should be
    """
    usage: java HeatedField [-h] [-t TEMPERATURE] [-m MINUTES] north south east west x y
    
    Calculate the internal cell temperature.
    
    positional arguments:
     north                                      (float)       the temperature of the north edge
     south                                      (float)       the temperature of the south edge
     east                                       (float)       the temperature of the east edge
     west                                       (float)       the temperature of the west edge
     x                                          (integer)     the x coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}
     y                                          (integer)     the y coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}
    
    named arguments:
     -h, --help                                 show this help message and exit
     -t TEMPERATURE, --temperature TEMPERATURE  (float)       the initial temperature of internal cells (default: 32.0)
     -m MINUTES, --minutes MINUTES              (integer)     the number of minutes to apply heating (default: 10)
    """


  Scenario: Provide only the single "--help" argument.
    
    Given the program "HeatedField" has started with arguments "--help"
    When the user views the terminal
    Then the output should be
    """
    usage: java HeatedField [-h] [-t TEMPERATURE] [-m MINUTES] north south east west x y
    
    Calculate the internal cell temperature.
    
    positional arguments:
     north                                      (float)       the temperature of the north edge
     south                                      (float)       the temperature of the south edge
     east                                       (float)       the temperature of the east edge
     west                                       (float)       the temperature of the west edge
     x                                          (integer)     the x coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}
     y                                          (integer)     the y coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}
    
    named arguments:
     -h, --help                                 show this help message and exit
     -t TEMPERATURE, --temperature TEMPERATURE  (float)       the initial temperature of internal cells (default: 32.0)
     -m MINUTES, --minutes MINUTES              (integer)     the number of minutes to apply heating (default: 10)
    """


  Scenario: Provide the "--help" argument in the midst of correct input.
    
    Given the program "HeatedField" has started with arguments "70 --help 60 80 90 2 2 -t 10 -m 2"
    When the user views the terminal
    Then the output should be
    """
    usage: java HeatedField [-h] [-t TEMPERATURE] [-m MINUTES] north south east west x y
    
    Calculate the internal cell temperature.
    
    positional arguments:
     north                                      (float)       the temperature of the north edge
     south                                      (float)       the temperature of the south edge
     east                                       (float)       the temperature of the east edge
     west                                       (float)       the temperature of the west edge
     x                                          (integer)     the x coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}
     y                                          (integer)     the y coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}
    
    named arguments:
     -h, --help                                 show this help message and exit
     -t TEMPERATURE, --temperature TEMPERATURE  (float)       the initial temperature of internal cells (default: 32.0)
     -m MINUTES, --minutes MINUTES              (integer)     the number of minutes to apply heating (default: 10)
    """


  Scenario: Provide the "--help" argument after too many inputs.
    
    Given the program "HeatedField" has started with arguments "70 60 80 90 2 2 -t 10 -m 2 other --help"
    When the user views the terminal
    Then the output should be
    """
    usage: java HeatedField [-h] [-t TEMPERATURE] [-m MINUTES] north south east west x y
    
    Calculate the internal cell temperature.
    
    positional arguments:
     north                                      (float)       the temperature of the north edge
     south                                      (float)       the temperature of the south edge
     east                                       (float)       the temperature of the east edge
     west                                       (float)       the temperature of the west edge
     x                                          (integer)     the x coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}
     y                                          (integer)     the y coordinate of the internal cell {1, 2, 3, 4, 5, 6, 7, 8}
    
    named arguments:
     -h, --help                                 show this help message and exit
     -t TEMPERATURE, --temperature TEMPERATURE  (float)       the initial temperature of internal cells (default: 32.0)
     -m MINUTES, --minutes MINUTES              (integer)     the number of minutes to apply heating (default: 10)
    """



