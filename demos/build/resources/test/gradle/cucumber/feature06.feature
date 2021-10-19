Feature: Allow named arguments to serve as flags (true if present).

  Any boolean-valued named argument is called a "flag". Flag arguments never
  take values (e.g., "--myarg false"). If the argument is present, that implies
  that it has taken the value of true (e.g., "--myarg"). If it is not present,
  then it has the default value of false. In fact, we have been using this
  concept implicitly already for the help/usage arguments "-h" and "--help". If
  either is present, then "show help/usage" is true. (Note, however, that the
  "help flag" is more powerful than typical flags, because it is provided by
  default and takes precedence over everything else when being parsed.)

  The argument parser library should allow support for flags (so in some sense
  the datatype boolean has been added to the list of types for named arguments),
  and that should be the only allowed use of boolean named arguments.

  Flag arguments do not display datatype information or default values in the
  help/usage information. For instance, a flag argument "myarg" might have 
  the following help/usage (contrasted with a "num" integer named argument):
  
  --myarg         some description here
  --num NUM       (integer) a number (default: 0)
  

  
  In order to demonstrate this functionality, you will need to create another
  demo program called "MaximalLayers" that calculates boundary regions of points
  in the plane.

  Consider a group of points. Each point is made of an x and y coordinate. A 
  point from that group is considered maximal if there is no other point in the
  group that is "north" and "east" of it. Consider the following points:

  5,5  4,9  10,2  2,3  15,7

  We can identify the points (4,9) and (15,7) as maximal. Mathematically this
  is true because there are no other points that have a larger x coordinate AND
  a larger y coordinate.

  For example, point (4,9) is maximal because there is no other point "north"
  of it (i.e there is no other point with a larger y value). Point (15,7) is
  maximal because there is no other point "east" of it (i.e. there is no other
  point with a larger x value). These maximal points (A and B) can be joined to
  form a maximal layer (Layer 1) as shown below.

  10|
  9 |--------A
  8 |        | 
  7 |        +-------------------B
  6 |                            |
  5 |        o                   |
  4 |                            |
  3 |  o                         |
  2 |                  o         |
  1 |_ _ _ _ _ _ _ _ _ _ _ _ _ _ | 
  0  1 2 3 4 5 6 7 8 9 1 1 1 1 1 1
                       0 1 2 3 4 5


  Additional maximal layers can be created in the same manner by using the rule:
  * Each layer consists of the points that are maximal excluding the points in
    the previous layer.
  This results in 3 layers for this group of points as shown below.

  10|
  9 |--------A
  8 |        | 
  7 |        +-------------------B
  6 |                            |
  5 |--------C                   |
  4 |        |                   |
  3 |--E     |                   |
  2 |  |     +---------D         |
  1 |_ | _ _ _ _ _ _ _ | _ _ _ _ | 
  0  1 2 3 4 5 6 7 8 9 1 1 1 1 1 1
                       0 1 2 3 4 5


  The program will be given a group of points via the command-line. It must
  identify the points in each layer as shown in the sample output below. The
  input will a single comma-separated string with the x and y coordinates for
  at least 1 and up to 15 points. The coordinates will be integer values
  between 1 and 20. Some points may have the same x coordinate and some points
  may have the same y coordinate but there will be no duplicate points. That is,
  no points will have the same x AND y coordinates. 

  The input for the example above would be
  5,5,4,9,10,2,2,3,15,7
  
  It should also accept flags for whether the output layers should be sorted by
  their x coordinates (--sortedX) or their y coordinates (--sortedY). If both
  flags are set, then it should sort first by y and then by x (so that the final
  result is sorted by x first, with duplicate x values sorted by y). If neither
  flag is set, then it should keep the points in the same relative order as the
  input.

  
  Scenario Outline: Demonstrate the library functionality by running the demo
    program "MaximalLayers" that the successive maximal layers of points.
    
    Given the program "<progname>" has started with arguments "<args>"
    When the user views the terminal
    Then the output should be "<output>"

    Examples:
    | progname      | args                                       | output                                        |
    | MaximalLayers | 5,5,4,9,10,2,2,3,15,7                                                         | 1:(4,9)(15,7) 2:(5,5)(10,2) 3:(2,3)  |
    | MaximalLayers | 5,5,4,9,10,2,2,3,15,7 --sortedX                                               | 1:(4,9)(15,7) 2:(5,5)(10,2) 3:(2,3)  |
    | MaximalLayers | 5,5,4,9,10,2,2,3,15,7 --sortedY                                               | 1:(15,7)(4,9) 2:(10,2)(5,5) 3:(2,3)  |
    | MaximalLayers | 5,5,4,9,10,2,2,3,15,7 --sortedX --sortedY                                     | 1:(4,9)(15,7) 2:(5,5)(10,2) 3:(2,3)  |
    | MaximalLayers | --sortedY 5,5,4,9,10,2,2,3,15,7 --sortedX                                     | 1:(4,9)(15,7) 2:(5,5)(10,2) 3:(2,3)  |
    | MaximalLayers | 5,5,10,2,2,3,15,7,2,14,1,1,15,2,1,7,7,7,1,4,12,10,15,15                       | 1:(15,7)(15,2)(15,15) 2:(2,14)(12,10) 3:(10,2)(1,7)(7,7) 4:(5,5) 5:(2,3)(1,4) 6:(1,1)  |
    | MaximalLayers | 5,5,10,2,2,3,15,7,2,14,1,1,15,2,1,7,7,7,1,4,12,10,15,15 --sortedX             | 1:(15,7)(15,2)(15,15) 2:(2,14)(12,10) 3:(1,7)(7,7)(10,2) 4:(5,5) 5:(1,4)(2,3) 6:(1,1)  |
    | MaximalLayers | 5,5,10,2,2,3,15,7,2,14,1,1,15,2,1,7,7,7,1,4,12,10,15,15 --sortedY             | 1:(15,2)(15,7)(15,15) 2:(12,10)(2,14) 3:(10,2)(1,7)(7,7) 4:(5,5) 5:(2,3)(1,4) 6:(1,1)  |
    | MaximalLayers | --sortedX 5,5,10,2,2,3,15,7,2,14,1,1,15,2,1,7,7,7,1,4,12,10,15,15 --sortedY   | 1:(15,2)(15,7)(15,15) 2:(2,14)(12,10) 3:(1,7)(7,7)(10,2) 4:(5,5) 5:(1,4)(2,3) 6:(1,1)  |
    | MaximalLayers | 6,2,13,18,9,9,20,10,19,19,12,12,3,3,2,15,13,13,5,12,2,14,1,20                 | 1:(20,10)(19,19)(1,20) 2:(13,18)(13,13) 3:(12,12)(2,15)(5,12)(2,14) 4:(9,9) 5:(6,2)(3,3)  |
    | MaximalLayers | --sortedX 6,2,13,18,9,9,20,10,19,19,12,12,3,3,2,15,13,13,5,12,2,14,1,20       | 1:(1,20)(19,19)(20,10) 2:(13,18)(13,13) 3:(2,15)(2,14)(5,12)(12,12) 4:(9,9) 5:(3,3)(6,2)  |
    | MaximalLayers | 6,2,13,18,9,9,20,10,19,19,12,12,3,3,2,15,13,13,5,12,2,14,1,20 --sortedY       | 1:(20,10)(19,19)(1,20) 2:(13,13)(13,18) 3:(12,12)(5,12)(2,14)(2,15) 4:(9,9) 5:(6,2)(3,3)  |
    | MaximalLayers | 6,2,13,18,9,9,20,10,19,19,12,12,3,3,2,15,13,13,5,12,2,14,1,20 --sortedX --sortedY   | 1:(1,20)(19,19)(20,10) 2:(13,13)(13,18) 3:(2,14)(2,15)(5,12)(12,12) 4:(9,9) 5:(3,3)(6,2)  |
    | MaximalLayers |                                            | MaximalLayers error: the argument points is required     |
    | MaximalLayers | 5,5,4,9,10,2,2,3,15,7 extra                | MaximalLayers error: the value extra matches no argument |
    | MaximalLayers | 5,5,4,9,10,2,2,3,15                        | MaximalLayers error: 15 is an unpaired x coordinate      |
    | MaximalLayers | 5,5,4,9,10,x,2,3,15,7                      | MaximalLayers error: the value x is not of type integer  |


  Scenario: Provide only the single "-h" argument.
    
    Given the program "MaximalLayers" has started with arguments "-h"
    When the user views the terminal
    Then the output should be
    """
    usage: java MaximalLayers [-h] [--sortedX] [--sortedY] points
    
    Sort the points into layers.
    
    positional arguments:
     points      (string)      the data points
    
    named arguments:
     -h, --help  show this help message and exit
     --sortedX   sort layers by x coordinate
     --sortedY   sort layers by y coordinate
    """


  Scenario: Provide the "-h" argument in the midst of correct input.
    
    Given the program "MaximalLayers" has started with arguments "-h 5,5,4,9,10,2,2,3,15,7"
    When the user views the terminal
    Then the output should be
    """
    usage: java MaximalLayers [-h] [--sortedX] [--sortedY] points
    
    Sort the points into layers.
    
    positional arguments:
     points      (string)      the data points
    
    named arguments:
     -h, --help  show this help message and exit
     --sortedX   sort layers by x coordinate
     --sortedY   sort layers by y coordinate
    """


  Scenario: Provide the "-h" argument after too many inputs.
    
    Given the program "MaximalLayers" has started with arguments "5,5,4,9,10,2,2,3,15,7 other -h"
    When the user views the terminal
    Then the output should be
    """
    usage: java MaximalLayers [-h] [--sortedX] [--sortedY] points
    
    Sort the points into layers.
    
    positional arguments:
     points      (string)      the data points
    
    named arguments:
     -h, --help  show this help message and exit
     --sortedX   sort layers by x coordinate
     --sortedY   sort layers by y coordinate
    """


  Scenario: Provide only the single "--help" argument.
    
    Given the program "MaximalLayers" has started with arguments "--help"
    When the user views the terminal
    Then the output should be
    """
    usage: java MaximalLayers [-h] [--sortedX] [--sortedY] points
    
    Sort the points into layers.
    
    positional arguments:
     points      (string)      the data points
    
    named arguments:
     -h, --help  show this help message and exit
     --sortedX   sort layers by x coordinate
     --sortedY   sort layers by y coordinate
    """


  Scenario: Provide the "--help" argument in the midst of correct input.
    
    Given the program "MaximalLayers" has started with arguments "--help 5,5,4,9,10,2,2,3,15,7"
    When the user views the terminal
    Then the output should be
    """
    usage: java MaximalLayers [-h] [--sortedX] [--sortedY] points
    
    Sort the points into layers.
    
    positional arguments:
     points      (string)      the data points
    
    named arguments:
     -h, --help  show this help message and exit
     --sortedX   sort layers by x coordinate
     --sortedY   sort layers by y coordinate
    """


  Scenario: Provide the "--help" argument after too many inputs.
    
    Given the program "MaximalLayers" has started with arguments "5,5,4,9,10,2,2,3,15,7 other --help"
    When the user views the terminal
    Then the output should be
    """
    usage: java MaximalLayers [-h] [--sortedX] [--sortedY] points
    
    Sort the points into layers.
    
    positional arguments:
     points      (string)      the data points
    
    named arguments:
     -h, --help  show this help message and exit
     --sortedX   sort layers by x coordinate
     --sortedY   sort layers by y coordinate
    """



