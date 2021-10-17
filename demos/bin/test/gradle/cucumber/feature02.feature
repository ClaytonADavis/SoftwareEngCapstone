Feature: Allow the inclusion of additional descriptive information on the program
  and each argument, and automatically (by default) provide named "-h" and "--help"
  arguments that show usage and help information.

  The argument parser library must now allow the client to add information about
  each identifier, in particular the type (always string for now) and description
  of what the argument/identifier represents.
  
  Additionally, without the client doing anything extra, the parser must allow
  for and detect command-line arguments "-h" or "--help". If it sees either of
  those two arguments during its parsing, it immediately halts and simply
  produces a help/usage message. 

  To demonstrate this using the "EquivalentStrings" demo program as described
  previously, it must now allow the additional argument "-h" or "--help" that
  supersedes all others. That means that, regardless of any other arguments
  that may exist or not, the presence of the "-h" or "--help" argument always
  produces the same result.
  
  In this case, the help text should read as follows (where the vertical bars
  represent the left edge of the terminal):
  
  |usage: java EquivalentStrings [-h] string1 string2
  | 
  |Determine if two strings are equivalent.
  |
  |positional arguments:
  | string1     (string)      the first string
  | string2     (string)      the second string
  | 
  |named arguments:
  | -h, --help  show this help message and exit
  
  Other than the additional "-h" or "--help" argument that can be accepted, the
  "EquivalentStrings" demonstration program should behave exactly as before.


  Scenario: Provide only the single "-h" argument.
    
    Given the program "EquivalentStrings" has started with arguments "-h"
    When the user views the terminal
    Then the output should be
    """
    usage: java EquivalentStrings [-h] string1 string2

    Determine if two strings are equivalent.

    positional arguments:
     string1     (string)      the first string
     string2     (string)      the second string

    named arguments:
     -h, --help  show this help message and exit
    """


  Scenario: Provide the "-h" argument in the midst of correct input.
    
    Given the program "EquivalentStrings" has started with arguments "bob -h ted"
    When the user views the terminal
    Then the output should be
    """
    usage: java EquivalentStrings [-h] string1 string2

    Determine if two strings are equivalent.

    positional arguments:
     string1     (string)      the first string
     string2     (string)      the second string

    named arguments:
     -h, --help  show this help message and exit
    """


  Scenario: Provide the "-h" argument after too many inputs.
    
    Given the program "EquivalentStrings" has started with arguments "bob ted ann -h"
    When the user views the terminal
    Then the output should be
    """
    usage: java EquivalentStrings [-h] string1 string2

    Determine if two strings are equivalent.

    positional arguments:
     string1     (string)      the first string
     string2     (string)      the second string

    named arguments:
     -h, --help  show this help message and exit
    """


  Scenario: Provide only the single "--help" argument.
    
    Given the program "EquivalentStrings" has started with arguments "--help"
    When the user views the terminal
    Then the output should be
    """
    usage: java EquivalentStrings [-h] string1 string2

    Determine if two strings are equivalent.

    positional arguments:
     string1     (string)      the first string
     string2     (string)      the second string

    named arguments:
     -h, --help  show this help message and exit
    """


  Scenario: Provide the "--help" argument in the midst of correct input.
    
    Given the program "EquivalentStrings" has started with arguments "bob --help ted"
    When the user views the terminal
    Then the output should be
    """
    usage: java EquivalentStrings [-h] string1 string2

    Determine if two strings are equivalent.

    positional arguments:
     string1     (string)      the first string
     string2     (string)      the second string

    named arguments:
     -h, --help  show this help message and exit
    """


  Scenario: Provide the "--help" argument after too many inputs.
    
    Given the program "EquivalentStrings" has started with arguments "bob ted ann --help"
    When the user views the terminal
    Then the output should be
    """
    usage: java EquivalentStrings [-h] string1 string2

    Determine if two strings are equivalent.

    positional arguments:
     string1     (string)      the first string
     string2     (string)      the second string

    named arguments:
     -h, --help  show this help message and exit
    """


  Scenario Outline: Limited test of previous functionality.
    
    Given the program "<progname>" has started with arguments "<args>"
    When the user views the terminal
    Then the output should be "<output>"
    
    Examples:
    | progname          | args                 | output         |
    | EquivalentStrings | child horse          | equivalent     |
    | EquivalentStrings | albatross bombshell  | equivalent     |
    | EquivalentStrings | slim nothing         | not equivalent |
    | EquivalentStrings | posed razor          | not equivalent |
    | EquivalentStrings |                      | EquivalentStrings error: the argument string1 is required  |
    | EquivalentStrings | bob dad mom          | EquivalentStrings error: the value mom matches no argument |


