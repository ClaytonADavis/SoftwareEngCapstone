Feature: Allow datatype information to be added to arguments so that non-string
  arguments can be used.
  
  The argument parser library should allow clients to add identifiers that can
  represent non-string values as well. In particular, the library should support
  strings, integers, and floats. The general use of the library by a client
  would proceed as follows:
  
  1. add identifiers to represent arguments by position on the command-line,
     specifying their datatype and description (for the help/usage message)
  2. take the strings from the command-line and associate them with their 
     identifiers according to the position
  3. use the identifiers to get their associated values and allow the client
     to pull the value of the specified type, rather than always as a string

  New circumstances that require special care are
     * if the associated value cannot be converted to its designated type
  
  All previous functionality should persist (e.g., help/usage information).
  
  
  In order to demonstrate this functionality, you will need to create another
  demo program called "OverlappingRectangles" that, given two rectangles that
  overlap, determines the total area of the two rectangles and the shared area
  of the two rectangles.
  
  A rectangle can be defined by the x and y coordinates of its lower left
  corner and its upper right corner. The program will accept these integer 
  coordinates via the command-line (8 integers in total; x1 y1 x2 y2 x3 y3 x4 y4,
  where the first rectangle is defined by lower left (x1, y1) and upper right
  (x2, y2) and the second rectangle is defined by lower left (x3, y3) and 
  upper right (x4, y4).
  
  The rectangles will intersect (overlap) in some way. For example, given the 
  rectangles defined by rectangle 1 (-3, -0) and (3, 4) and rectangle 2 (0, -1)
  and (9, 2). (You should draw this on graph paper to see the overlap.)
  
  The area of a rectangle equals its width times its length. The area of
  rectangle 1 on the left is 24 (6 times 4). The area of rectangle 2 is 27
  (9 times 3). The area of the overlap section is 6. This means the total area
  of the rectangles is 45 (24 + 27 – 6).

  As another example, consider the rectangles defined by rectangle 1 (0, 0) and
  (3, 3) and rectangle 2 (2, 2) and (4, 4). The area of rectangle 1 is 9
  (3 times 3). The area of rectangle 2 is 4 (2 times 2). The area of the overlap
  section is 1. This means the total area of the rectangles is 12 (9 + 4 – 1).
  
  The rectangles will always overlap in some way. The rectangles will never be
  the same rectangle.

  The program should output two integers (overlap and total), separated by a
  space, representing the area of the overlap and area of the total. For instance,
  for the first example above, the output should be "6 45".


  Scenario Outline: Demonstrate the library functionality by running the demo
    program "OverlappingRectangles" that determines the areas of overlapping
    rectangles.
    
    Given the program "<progname>" has started with arguments "<args>"
    When the user views the terminal
    Then the output should be "<output>"

    Examples:
    | progname              | args                  | output    |
    | OverlappingRectangles | -3 0 3 4 0 -1 9 2     | 6 45      |
    | OverlappingRectangles | 0 0 3 3 2 2 4 4       | 1 12      |
    | OverlappingRectangles | 2 2 5 7 3 4 6 9       | 6 24      |
    | OverlappingRectangles | -4 -2 3 2 -1 -3 5 3   | 16 48     |
    | OverlappingRectangles | 1 2 6 8 2 3 5 7       | 12 30     |
    | OverlappingRectangles | 2 3 5 7 1 2 6 8       | 12 30     |
    | OverlappingRectangles |                       | OverlappingRectangles error: the argument x1 is required |
    | OverlappingRectangles | -3 0 3                | OverlappingRectangles error: the argument y2 is required |
    | OverlappingRectangles | -3 0 3 4 0 -1         | OverlappingRectangles error: the argument x4 is required |
    | OverlappingRectangles | -3 0 3 4 0 -1 9 2 42  | OverlappingRectangles error: the value 42 matches no argument |
    | OverlappingRectangles | -3 0 3 4 z -1 9 2     | OverlappingRectangles error: the value z is not of type integer |
    | OverlappingRectangles | -3 0 3.5 4 0 -1 9 2   | OverlappingRectangles error: the value 3.5 is not of type integer |
    | OverlappingRectangles | -3 0 3 4 0 -1 max 2   | OverlappingRectangles error: the value max is not of type integer |


  Scenario: Provide only the single "-h" argument.
    
    Given the program "OverlappingRectangles" has started with arguments "-h"
    When the user views the terminal
    Then the output should be
    """
    usage: java OverlappingRectangles [-h] x1 y1 x2 y2 x3 y3 x4 y4
    
    Determine the overlap and total area of two rectangles.
    
    positional arguments:
     x1          (integer)     lower-left x for rectangle 1
     y1          (integer)     lower-left y for rectangle 1
     x2          (integer)     upper-right x for rectangle 1
     y2          (integer)     upper-right y for rectangle 1
     x3          (integer)     lower-left x for rectangle 2
     y3          (integer)     lower-left y for rectangle 2
     x4          (integer)     upper-right x for rectangle 2
     y4          (integer)     upper-right y for rectangle 2
    
    named arguments:
     -h, --help  show this help message and exit
    """


  Scenario: Provide the "-h" argument in the midst of correct input.
    
    Given the program "OverlappingRectangles" has started with arguments "-3 0 3 -h 4 0 -1 9 2"
    When the user views the terminal
    Then the output should be
    """
    usage: java OverlappingRectangles [-h] x1 y1 x2 y2 x3 y3 x4 y4
    
    Determine the overlap and total area of two rectangles.
    
    positional arguments:
     x1          (integer)     lower-left x for rectangle 1
     y1          (integer)     lower-left y for rectangle 1
     x2          (integer)     upper-right x for rectangle 1
     y2          (integer)     upper-right y for rectangle 1
     x3          (integer)     lower-left x for rectangle 2
     y3          (integer)     lower-left y for rectangle 2
     x4          (integer)     upper-right x for rectangle 2
     y4          (integer)     upper-right y for rectangle 2
    
    named arguments:
     -h, --help  show this help message and exit
    """


  Scenario: Provide the "-h" argument after too many inputs.
    
    Given the program "OverlappingRectangles" has started with arguments "-3 0 3 4 0 -1 9 2 -h"
    When the user views the terminal
    Then the output should be
    """
    usage: java OverlappingRectangles [-h] x1 y1 x2 y2 x3 y3 x4 y4
    
    Determine the overlap and total area of two rectangles.
    
    positional arguments:
     x1          (integer)     lower-left x for rectangle 1
     y1          (integer)     lower-left y for rectangle 1
     x2          (integer)     upper-right x for rectangle 1
     y2          (integer)     upper-right y for rectangle 1
     x3          (integer)     lower-left x for rectangle 2
     y3          (integer)     lower-left y for rectangle 2
     x4          (integer)     upper-right x for rectangle 2
     y4          (integer)     upper-right y for rectangle 2
    
    named arguments:
     -h, --help  show this help message and exit
    """


  Scenario: Provide only the single "--help" argument.
    
    Given the program "OverlappingRectangles" has started with arguments "--help"
    When the user views the terminal
    Then the output should be
    """
    usage: java OverlappingRectangles [-h] x1 y1 x2 y2 x3 y3 x4 y4
    
    Determine the overlap and total area of two rectangles.
    
    positional arguments:
     x1          (integer)     lower-left x for rectangle 1
     y1          (integer)     lower-left y for rectangle 1
     x2          (integer)     upper-right x for rectangle 1
     y2          (integer)     upper-right y for rectangle 1
     x3          (integer)     lower-left x for rectangle 2
     y3          (integer)     lower-left y for rectangle 2
     x4          (integer)     upper-right x for rectangle 2
     y4          (integer)     upper-right y for rectangle 2
    
    named arguments:
     -h, --help  show this help message and exit
    """


  Scenario: Provide the "--help" argument in the midst of correct input.
    
    Given the program "OverlappingRectangles" has started with arguments "-3 0 3 4 0 -1 --help 9 2"
    When the user views the terminal
    Then the output should be
    """
    usage: java OverlappingRectangles [-h] x1 y1 x2 y2 x3 y3 x4 y4
    
    Determine the overlap and total area of two rectangles.
    
    positional arguments:
     x1          (integer)     lower-left x for rectangle 1
     y1          (integer)     lower-left y for rectangle 1
     x2          (integer)     upper-right x for rectangle 1
     y2          (integer)     upper-right y for rectangle 1
     x3          (integer)     lower-left x for rectangle 2
     y3          (integer)     lower-left y for rectangle 2
     x4          (integer)     upper-right x for rectangle 2
     y4          (integer)     upper-right y for rectangle 2
    
    named arguments:
     -h, --help  show this help message and exit
    """


  Scenario: Provide the "--help" argument after too many inputs.
    
    Given the program "OverlappingRectangles" has started with arguments "-3 0 3 4 0 -1 9 2 --help"
    When the user views the terminal
    Then the output should be
    """
    usage: java OverlappingRectangles [-h] x1 y1 x2 y2 x3 y3 x4 y4
    
    Determine the overlap and total area of two rectangles.
    
    positional arguments:
     x1          (integer)     lower-left x for rectangle 1
     y1          (integer)     lower-left y for rectangle 1
     x2          (integer)     upper-right x for rectangle 1
     y2          (integer)     upper-right y for rectangle 1
     x3          (integer)     lower-left x for rectangle 2
     y3          (integer)     lower-left y for rectangle 2
     x4          (integer)     upper-right x for rectangle 2
     y4          (integer)     upper-right y for rectangle 2
    
    named arguments:
     -h, --help  show this help message and exit
    """



