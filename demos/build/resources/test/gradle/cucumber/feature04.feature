Feature: Allow named arguments with single values, which may have description
  and datatype information, after all positional arguments.
  
  The argument parser library should allow clients to add (optional) non-positional
  identifiers that (for now) must come after all positional arguments when entered
  on the command-line. These named arguments have the same properties as positionals
  (identifier, datatype, description), but because they are optional, they also
  must have a default value, which will be used as the value whenever it is not
  specified on the command-line.
  
  Since they are not positional, named arguments must be given as two distinct 
  parts, side by side. The first is the identifier, which must always be preceded
  by two hyphens. The second is the value that should be associated with that 
  named argument. So, if the named argument's identifier were "myarg1" and was
  of type integer, then it might be listed on the command line as
  --myarg1 17
  so that the value of the "myarg1" identifier would be set to 17.

  Suppose the client had a program called "MyProg" that expected three integer
  positional arguments (say "x", "y", and "z"), along with two named arguments;
  one of type string with identifier "myarg1" and default value "foo", and
  another of type integer with identifier "myarg2" and default value 42. 
  In that case, the following calls would all be legal:
  
  java MyProg 5 2 8                            parsing to x=5,y=2,z=8,myarg1="foo",myarg2=42
  java MyProg 5 2 8 --myarg1 bar               parsing to x=5,y=2,z=8,myarg1="bar",myarg2=42
  java MyProg 5 2 8 --myarg2 17                parsing to x=5,y=2,z=8,myarg1="foo",myarg2=17
  java MyProg 5 2 8 --myarg2 17 --myarg1 bar   parsing to x=5,y=2,z=8,myarg1="bar",myarg2=17

  New circumstances that require special care are
     * if a given named argument on the command-line was not specified by the client
     * if a named argument's associated value is not of the appropriate type
     * if a named argument is not followed by its corresponding value
  
  All previous functionality should persist (e.g., help/usage information).
  Additionally, any named arguments should be listed in the usage in square
  brackets, along with their name again in allcaps to represent the value to
  be given. The default values should be listed after the description. This 
  is most clearly understood in the expected output in the help/usage cases below.
  
  
  In order to demonstrate this functionality, you will need to create another
  demo program called "WordSearch" that allows a user to find a word (if it
  exists) in a grid of letters. The command-line arguments to the program will 
  be two strings, the grid and the word to find, in that order. The default
  size for the grid should be 5x5 (so 25 letters in the first string argument).
  However, named arguments for "width" and "height" can be given to change the
  grid dimensions. The output of the program should be either the grid locations
  of each of the letters (format specified below) or a statement that the word
  is not found (once again, particular format is below).
  
  The objective of this demo program is to locate words hidden in a grid of 
  letters. The word might be written in a horizontal or vertical orientation. 
  It might be written from bottom to top (upside down) or left to right 
  (backwards). Words can also be written with changes in direction. A word that
  starts out  being written from top to bottom might take a turn in the middle 
  and continue left to right.

  For example, if you are searching for the word "halifax", it might be written
  like this:

  h
  a
  l
  i f a x

  If you are looking for "yellow", it might appear like this:

  w
  o l
    l
    e y

  Here is an example of a word search grid.

  s	o	f	t	s
  w	e	s	k	a
  o	l	z	i	l
  k	l	q	m	t
  r	e	y	o	y

  Suppose you want to find the word "eskimo".

  It can be found beginning at location Row 2, Column 2 and the path is
  expressed as follows:
  e:2,2 s:2,3 k:2,4 i:3,4 m:4,4 o:5,4
  
  Words will always be contiguous in the grid, and the turns must be 90 degrees.
  Using the sample grid above, the word "salty" is:
  s:1,5 a:2,5 l:3,5 t:4,5 y:5,5

  (Note that the grid locations are given as Row/Column where the first 
   Row/Column is numbered 1 rather than 0.)

  All letters will be lower case as will the words to be searched for. The same
  letter may appear more than once but correct combinations of letters will only
  appear once. For example, if the hidden word is "eskimo" the letters 'e' and 
  's' may appear many times individually but will only appear "together" once.
  
  If a word does not appear in the grid, then the output should be 
  "<word> not found"
  where <word> corresponds to the target word.


  Scenario Outline: Demonstrate the library functionality by running the demo
    program "WordSearch" that finds a target word in a grid of letters if it exists.
    
    Given the program "<progname>" has started with arguments "<args>"
    When the user views the terminal
    Then the output should be "<output>"

    Examples:
    | progname   | args                                                                      | output                                                 |
    | WordSearch | softsweskaolzilklqmtreyoy soft                                            | s:1,1 o:1,2 f:1,3 t:1,4                                |
    | WordSearch | softsweskaolzilklqmtreyoy eskimo                                          | e:2,2 s:2,3 k:2,4 i:3,4 m:4,4 o:5,4                    |
    | WordSearch | softsweskaolzilklqmtreyoy salty                                           | s:1,5 a:2,5 l:3,5 t:4,5 y:5,5                          |
    | WordSearch | softsweskaolzilklqmtreyoy yellow                                          | y:5,3 e:5,2 l:4,2 l:3,2 o:3,1 w:2,1                    |
    | WordSearch | softsweskaolzilklqmtreyoy pizza                                           | pizza not found                                        |
    | WordSearch | softwaeskqermilvcqputeromocertpuiopprogram milk --width 6 --height 7      | m:3,1 i:3,2 l:3,3 k:2,3                                |
    | WordSearch | softwaeskqermilvcqputeromocertpuiopprogram software --width 6 --height 7  | s:1,1 o:1,2 f:1,3 t:1,4 w:1,5 a:1,6 r:2,6 e:2,5        |
    | WordSearch | softwaeskqermilvcqputeromocertpuiopprogram program --height 7 --width 6   | p:6,1 r:7,1 o:7,2 g:7,3 r:7,4 a:7,5 m:7,6              |
    | WordSearch | softwaeskqermilvcqputeromocertpuiopprogram computer --width 6 --height 7  | c:5,3 o:5,2 m:5,1 p:4,1 u:4,2 t:4,3 e:4,4 r:4,5        |
    | WordSearch | softwaeskqermilvcqputeromocertpuiopprogram milveecom --height 7 --width 6 | m:3,1 i:3,2 l:3,3 v:3,4 e:4,4 e:5,4 c:5,3 o:5,2 m:5,1  |
    | WordSearch | softwaeskqermilvcqputeromocertpuiopprogram retro --width 6 --height 7     | retro not found                                        |
    | WordSearch | softwaeskqermilvcqputeromocertpuiopprogram sepptuoipqcf --width 14 --height 3   | s:1,8 e:2,8 p:3,8 p:3,7 t:2,7 u:2,6 o:3,6 i:3,5 p:2,5 q:2,4 c:2,3 f:1,3 |
    | WordSearch | soptwerhomnioroeroer sophomore --height 4                                 | s:1,1 o:1,2 p:1,3 h:2,3 o:2,4 m:2,5 o:3,5 r:4,5 e:4,4  |
    | WordSearch | soptwerhomnioroeroer senior --height 4                                    | s:1,1 e:2,1 n:3,1 i:3,2 o:3,3 r:3,4                    |
    | WordSearch | soptwerhomnioroeroer noroom --width 4                                     | n:3,3 o:4,3 r:4,2 o:4,1 o:3,1 m:3,2                    |
    | WordSearch | softwaeskqermilvcqputeromocertpuiopprogram retro --width 4 --height 5     | WordSearch error: grid dimensions (4 x 5) do not match grid length (42) |
    | WordSearch |                                                                           | WordSearch error: the argument grid is required        |
    | WordSearch | softsweskanmzilkcqmtrufoy                                                 | WordSearch error: the argument target is required      |
    | WordSearch | softsweskanmzilkcqmtrufoy eskimo snow                                     | WordSearch error: the value snow matches no argument   |
    | WordSearch | soptwerhomnioroeroer noroom --width                                       | WordSearch error: no value for width                   |
    | WordSearch | soptwerhomnioroeroer noroom --height                                      | WordSearch error: no value for height                  |
    | WordSearch | soptwerhomnioroeroer noroom --height --width                              | WordSearch error: the value --width is not of type integer  |


  Scenario: Provide only the single "-h" argument.
    
    Given the program "WordSearch" has started with arguments "-h"
    When the user views the terminal
    Then the output should be
    """
    usage: java WordSearch [-h] [--width WIDTH] [--height HEIGHT] grid target
    
    Find a target word in a grid.
    
    positional arguments:
     grid             (string)      the grid to search
     target           (string)      the target word
    
    named arguments:
     -h, --help       show this help message and exit
     --width WIDTH    (integer)     the grid width (default: 5)
     --height HEIGHT  (integer)     the grid height (default: 5)
    """


  Scenario: Provide the "-h" argument in the midst of correct input.
    
    Given the program "WordSearch" has started with arguments "softsweskanmzilkcqmtrufoy -h eskimo"
    When the user views the terminal
    Then the output should be
    """
    usage: java WordSearch [-h] [--width WIDTH] [--height HEIGHT] grid target
    
    Find a target word in a grid.
    
    positional arguments:
     grid             (string)      the grid to search
     target           (string)      the target word
    
    named arguments:
     -h, --help       show this help message and exit
     --width WIDTH    (integer)     the grid width (default: 5)
     --height HEIGHT  (integer)     the grid height (default: 5)
    """


  Scenario: Provide the "-h" argument after too many inputs.
    
    Given the program "WordSearch" has started with arguments "softsweskanmzilkcqmtrufoy eskimo other -h"
    When the user views the terminal
    Then the output should be
    """
    usage: java WordSearch [-h] [--width WIDTH] [--height HEIGHT] grid target
    
    Find a target word in a grid.
    
    positional arguments:
     grid             (string)      the grid to search
     target           (string)      the target word
    
    named arguments:
     -h, --help       show this help message and exit
     --width WIDTH    (integer)     the grid width (default: 5)
     --height HEIGHT  (integer)     the grid height (default: 5)
    """


  Scenario: Provide only the single "--help" argument.
    
    Given the program "WordSearch" has started with arguments "--help"
    When the user views the terminal
    Then the output should be
    """
    usage: java WordSearch [-h] [--width WIDTH] [--height HEIGHT] grid target
    
    Find a target word in a grid.
    
    positional arguments:
     grid             (string)      the grid to search
     target           (string)      the target word
    
    named arguments:
     -h, --help       show this help message and exit
     --width WIDTH    (integer)     the grid width (default: 5)
     --height HEIGHT  (integer)     the grid height (default: 5)
    """


  Scenario: Provide the "--help" argument in the midst of correct input.
    
    Given the program "WordSearch" has started with arguments "softsweskanmzilkcqmtrufoy --help eskimo"
    When the user views the terminal
    Then the output should be
    """
    usage: java WordSearch [-h] [--width WIDTH] [--height HEIGHT] grid target
    
    Find a target word in a grid.
    
    positional arguments:
     grid             (string)      the grid to search
     target           (string)      the target word
    
    named arguments:
     -h, --help       show this help message and exit
     --width WIDTH    (integer)     the grid width (default: 5)
     --height HEIGHT  (integer)     the grid height (default: 5)
    """


  Scenario: Provide the "--help" argument after too many inputs.
    
    Given the program "WordSearch" has started with arguments "softsweskanmzilkcqmtrufoy eskimo other --help"
    When the user views the terminal
    Then the output should be
    """
    usage: java WordSearch [-h] [--width WIDTH] [--height HEIGHT] grid target
    
    Find a target word in a grid.
    
    positional arguments:
     grid             (string)      the grid to search
     target           (string)      the target word
    
    named arguments:
     -h, --help       show this help message and exit
     --width WIDTH    (integer)     the grid width (default: 5)
     --height HEIGHT  (integer)     the grid height (default: 5)
    """



