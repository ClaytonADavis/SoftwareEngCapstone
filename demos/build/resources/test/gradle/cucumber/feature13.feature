Feature: Allow variable numbers of argument values to be specified by a single argument.

  Until now, each argument has had at most one value associated with it.
  Sometimes, however, it is useful to allow a single argument to have multiple
  values. In this case, the client must specify exactly how many values should
  be associated with a particular argument. This should only work for non-flags.
  
  Whenever parsing a multi-valued argument, nothing can come between the values.
  For instance, no optional named argument can separate value 2 and value 3. All
  values for a single argument must be contiguous.

  The XML format should accommodate this new feature as follows:
    <arguments>
      <positionalArgs>
        ...
        <positional>
          <name>numbers</name>
          <type>integer</type>
          <arity>3</arity>
        </positional>
        ...
      </positionalArgs>
      <namedArgs>
        ...
        <named>
          <name>options</name>
          <type>string</type>
          <default>
            <value>spam</value>
            <value>eggs</value>
          </default>
        </named>
        ...
      </namedArgs>
      <mutuallyExclusive>
        ...
      </mutuallyExclusive>
    </arguments>
    
  Here, a positional argument must have its "arity" set manually in the XML.
  However, a named argument has its "arity" set automatically based on its
  default value dimension. Adding this new component to the XML format should
  not interfere with any previous feature.


  In order to demonstrate this functionality, an additional (but simple)
  "DistanceCalculator" demonstration program needs to be implemented.
  This program should take a single positional argument that represents
  3 integer values (for a point in 3D space). It should also take an optional
  named argument "from" that represents 3 integer values (another point in 3D
  space) for the source point. This source point should default to the origin
  (0, 0, 0) if unspecified. It should also accept an optional named argument
  called "measure" that is restricted to the strings "euclidean" or "manhattan",
  defaulting to "euclidean". This argument determines the distance calculation
  to be used. Finally, it should accept an optional named argument called
  "dimensions" that represents 3 integers restricted to either 0 or 1. If the
  dimension value is 0, then that dimension should be left out of the distance
  calculation. The default value for "dimensions" should be (1, 1, 1). Consult
  the "help/usage" test below for specifics on the names/types/defaults of each.
  
  The program should output the distance from the source to the destination point,
  using the specified measure and dimensions, rounded to 3 decimal places.
  

  Scenario Outline: Demonstrate the library functionality by running the demo
    program "DistanceCalculator" that calculates the distance between points.
    
    Given the program "<progname>" has started with arguments "<args>"
    When the user views the terminal
    Then the output should be "<output>"

    Examples:
    | progname           | args                                         | output            |
    | DistanceCalculator | 1 2 3                                        | 3.742             |
    | DistanceCalculator | 1 2 3 --measure manhattan                    | 6.0               |
    | DistanceCalculator | 1 2 3 --measure manhattan -s 3 2 1           | 4.0               |
    | DistanceCalculator | 1 2 3 --source 3 5 2 --dimensions 1 1 0      | 3.606             |
    | DistanceCalculator | 8 6 7 --source 5 3 0 -m manhattan -d 1 0 1   | 10.0              |
    | DistanceCalculator |                                              | DistanceCalculator error: the argument destination is required                |
    | DistanceCalculator | 1                                            | DistanceCalculator error: not enough values for argument destination (1 < 3)  |
    | DistanceCalculator | 1 2                                          | DistanceCalculator error: not enough values for argument destination (2 < 3)  |
    | DistanceCalculator | 1 2 3 --measure manhattan -s 3 1             | DistanceCalculator error: not enough values for argument source (2 < 3)       |
    | DistanceCalculator | 1 2 3 --source 3 5 2 --dimensions 1          | DistanceCalculator error: not enough values for argument dimensions (1 < 3)   |
    | DistanceCalculator | 1 2 3 4                                      | DistanceCalculator error: the value 4 matches no argument                     |
    | DistanceCalculator | 1 x 3                                        | DistanceCalculator error: the value x is not of type integer                  |
    | DistanceCalculator | 1 2 3 --measure kilometers                   | DistanceCalculator error: measure value kilometers is not a member of [euclidean, manhattan] |


  
  Scenario: Provide only the single "-h" argument.
    
    Given the program "DistanceCalculator" has started with arguments "-h"
    When the user views the terminal
    Then the output should be
    """
    usage: java DistanceCalculator [-h] [-s SOURCE] [-m MEASURE] [-d DIMENSIONS] destination
    
    Find the distance between points.
    
    positional arguments:
     destination                             (integer[3])  the destination point
    
    named arguments:
     -h, --help                              show this help message and exit
     -s SOURCE, --source SOURCE              (integer[3])  the source point (default: [0, 0, 0])
     -m MEASURE, --measure MEASURE           (string)      the distance measure {euclidean, manhattan} (default: euclidean)
     -d DIMENSIONS, --dimensions DIMENSIONS  (integer[3])  the dimensions to include/exclude (default: [1, 1, 1])
    """


