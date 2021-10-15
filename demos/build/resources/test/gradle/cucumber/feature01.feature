Feature: Allow string-valued positional arguments and retrieve them from the command-line.
  
  You should implement an argument parser library that is able to do the following:
  
  1. add identifiers to represent arguments by position on the command-line
  2. take the strings from the command-line and associate them with their identifiers
  3. use the identifiers to get their associated values

  Circumstances that require special care are 
     * if there are more identifiers than arguments to be associated
     * if there are more arguments than chosen identifiers


  To fully test this functionality, you will also need to create a demonstration
  program called "EquivalentStrings" that determines if two strings are
  "equivalent". Equivalent means one string can be transformed into the other
  by substituting one letter for another consistently.
    
  For example, consider the two strings "cocoon" and "zyzyyx". These would be
  considered equivalent because: c -> z, o -> y, n -> x. Each letter in one
  string maps to one and only one letter in the second string and vice versa.
  Note that just because c -> z in one direction (from "cocoon" to "zyzyyx")
  does not mean that z -> c (if there were a "z" in "cocoon", it would not
  necessarily have to map to "c").

  On the other hand, the two strings "banana" and "orange" are not equivalent.
  This is because 'a' in banana would have to map to 'r', 'n', and 'e' in
  orange. There is no one-to-one substitution that works. Based on this
  definition of "equivalent", strings of different lengths cannot be equivalent.
    
  The "EquivalentStrings" demonstration program should accept exactly two 
  string arguments from the command line, and it should print to standard
  output either "equivalent" or "not equivalent", depending on whether the
  two strings are equivalent.
    
  If there are too few arguments (fewer than two), then the program should
  output either "EquivalentStrings error: the argument string1 is required"
  or "EquivalentStrings error: the argument string2 is required", depending
  on which is the first missing argument.
    
  If there are too many arguments (more than two), then the program should 
  output "EquivalentStrings error: the value <X> matches no argument", where
  "<X>" is replaced with the first extra value.

  Scenario Outline: Demonstrate the library functionality by running the demo
    program "EquivalentStrings" that determines if two strings are equivalent.
    
    Given the program "<progname>" has started with arguments "<args>"
    When the user views the terminal
    Then the output should be "<output>"

    Examples:
    | progname          | args                 | output         |
    | EquivalentStrings | zyzyyx cocoon        | equivalent     |
    | EquivalentStrings | banana orange        | not equivalent |
    | EquivalentStrings | child horse          | equivalent     |
    | EquivalentStrings | fizz bugs            | not equivalent |
    | EquivalentStrings | darker posse         | not equivalent |
    | EquivalentStrings | albatross bombshell  | equivalent     |
    | EquivalentStrings | fizz buzz            | equivalent     |
    | EquivalentStrings | slim nothing         | not equivalent |
    | EquivalentStrings | gaining toasast      | equivalent     |
    | EquivalentStrings | posed razor          | not equivalent |
    | EquivalentStrings |                      | EquivalentStrings error: the argument string1 is required  |
    | EquivalentStrings | bob                  | EquivalentStrings error: the argument string2 is required  |
    | EquivalentStrings | bob dad mom          | EquivalentStrings error: the value mom matches no argument |
    | EquivalentStrings | bob dad zip mom      | EquivalentStrings error: the value zip matches no argument |


