Feature: Allow argument information to be loaded from an XML file.

  While specifying command-line arguments in code is fine, it becomes tedious 
  and error-prone whenever multiple programs have exactly the same argument lists.
  In such cases, it would be easier to pull the argument lists directly from a
  file. 
  
  The XML specification provides a human-readable, structured format for
  storing textual data. The XML file should store all of the argument information,
  including whether an argument is positional or named, long-form names,
  short-form names, datatypes, default values, restrictions, etc.

  Assume a program allows for three positional arguments, named "length", "width",
  and "height", respectively, each representing float values. Assume that it
  also allows two optional named arguments: "type", a string that defaults to
  "box" with short-form "t" and restricted to "box", "pyramid", and "ellipsoid",
  and "precision", an integer that defaults to 4 with short-form "p".

  The following XML file could represent that argument list:

    <?xml version="1.0"?>
    <arguments>
      <positionalArgs>
        <positional>
          <type>float</type>
          <description>the length of the volume</description>
          <name>length</name>
        </positional>
        <positional>
          <type>float</type>
          <name>width</name>
          <description>the width of the volume</description>
        </positional>
        <positional>
          <description>the height of the volume</description>
          <name>height</name>
          <type>float</type>
        </positional>
      </positionalArgs>
      <namedArgs>
        <named>
          <description>the type of volume</description>
          <shortname>t</shortname>
          <type>string</type>
          <name>type</name>
          <restrictions>
            <restriction>box</restriction>
            <restriction>pyramid</restriction>
            <restriction>ellipsoid</restriction>
          </restrictions>
          <default>
            <value>box</value>
          </default>
        </named>
        <named>
          <default>
            <value>4</value>
          </default>
          <type>integer</type>
          <description>the maximum number of decimal places for the volume</description>
          <name>precision</name>
          <shortname>p</shortname>
        </named>
      </namedArgs>
    </arguments>


  Add functionality to the argument parser library to allow reading argument
  info from an XML file structured as described above. The library should 
  ensure that the given XML file validates according to a schema that accurately
  represents its components and their relationships and restrictions.
  
  
  In order to demonstrate this functionality, you will need to create another
  demo program called "VolumeCalculator" (as described above) that calculates
  the volume of a box/pyramid/ellipsoid. However, this program will take, as its
  first argument, a string representing the contents of an XML file. The remaining
  arguments will be what should be parsed as normal.
  

  
  Scenario: Provide the example XML and legal, clean arguments.
    
    Given the program "VolumeCalculator" has started with arguments "2 --type pyramid 5 -p 2 3" and XML
    """
    <?xml version="1.0"?>
    <arguments>
      <positionalArgs>
        <positional>
          <type>float</type>
          <description>the length of the volume</description>
          <name>length</name>
        </positional>
        <positional>
          <type>float</type>
          <name>width</name>
          <description>the width of the volume</description>
        </positional>
        <positional>
          <description>the height of the volume</description>
          <name>height</name>
          <type>float</type>
        </positional>
      </positionalArgs>
      <namedArgs>
        <named>
          <description>the type of volume</description>
          <shortname>t</shortname>
          <type>string</type>
          <name>type</name>
          <default>
            <value>box</value>
          </default>
          <restrictions>
            <restriction>box</restriction>
            <restriction>pyramid</restriction>
            <restriction>ellipsoid</restriction>
          </restrictions>
        </named>
        <named>
          <default>
            <value>4</value>
          </default>
          <type>integer</type>
          <description>the maximum number of decimal places for the volume</description>
          <name>precision</name>
          <shortname>p</shortname>
        </named>
      </namedArgs>
    </arguments>
    """
    When the user views the terminal
    Then the output should be "10.0"


  Scenario: Provide the example XML and legal, messy arguments.
    
    Given the program "VolumeCalculator" has started with arguments "2 --type ellipsoid 5 -p 3 3" and XML
    """
    <?xml version="1.0"?>
    <arguments>
      <positionalArgs>
        <positional>
          <type>float</type>
          <description>the length of the volume</description>
          <name>length</name>
        </positional>
        <positional>
          <type>float</type>
          <name>width</name>
          <description>the width of the volume</description>
        </positional>
        <positional>
          <description>the height of the volume</description>
          <name>height</name>
          <type>float</type>
        </positional>
      </positionalArgs>
      <namedArgs>
        <named>
          <description>the type of volume</description>
          <shortname>t</shortname>
          <type>string</type>
          <name>type</name>
          <default>
            <value>box</value>
          </default>
          <restrictions>
            <restriction>box</restriction>
            <restriction>pyramid</restriction>
            <restriction>ellipsoid</restriction>
          </restrictions>
        </named>
        <named>
          <default>
            <value>4</value>
          </default>
          <type>integer</type>
          <description>the maximum number of decimal places for the volume</description>
          <name>precision</name>
          <shortname>p</shortname>
        </named>
      </namedArgs>
    </arguments>
    """
    When the user views the terminal
    Then the output should be "125.664"


  Scenario: Provide the example XML and illegal arguments (missing positional).
    
    Given the program "VolumeCalculator" has started with arguments "2 5" and XML
    """
    <?xml version="1.0"?>
    <arguments>
      <positionalArgs>
        <positional>
          <type>float</type>
          <description>the length of the volume</description>
          <name>length</name>
        </positional>
        <positional>
          <type>float</type>
          <name>width</name>
          <description>the width of the volume</description>
        </positional>
        <positional>
          <description>the height of the volume</description>
          <name>height</name>
          <type>float</type>
        </positional>
      </positionalArgs>
      <namedArgs>
        <named>
          <description>the type of volume</description>
          <shortname>t</shortname>
          <type>string</type>
          <name>type</name>
          <default>
            <value>box</value>
          </default>
          <restrictions>
            <restriction>box</restriction>
            <restriction>pyramid</restriction>
            <restriction>ellipsoid</restriction>
          </restrictions>
        </named>
        <named>
          <default>
            <value>4</value>
          </default>
          <type>integer</type>
          <description>the maximum number of decimal places for the volume</description>
          <name>precision</name>
          <shortname>p</shortname>
        </named>
      </namedArgs>
    </arguments>
    """
    When the user views the terminal
    Then the output should be "VolumeCalculator error: the argument height is required"


  Scenario: Provide the example XML and illegal arguments (extra positional).
    
    Given the program "VolumeCalculator" has started with arguments "2 5 3 8" and XML
    """
    <?xml version="1.0"?>
    <arguments>
      <positionalArgs>
        <positional>
          <type>float</type>
          <description>the length of the volume</description>
          <name>length</name>
        </positional>
        <positional>
          <type>float</type>
          <name>width</name>
          <description>the width of the volume</description>
        </positional>
        <positional>
          <description>the height of the volume</description>
          <name>height</name>
          <type>float</type>
        </positional>
      </positionalArgs>
      <namedArgs>
        <named>
          <description>the type of volume</description>
          <shortname>t</shortname>
          <type>string</type>
          <name>type</name>
          <default>
            <value>box</value>
          </default>
          <restrictions>
            <restriction>box</restriction>
            <restriction>pyramid</restriction>
            <restriction>ellipsoid</restriction>
          </restrictions>
        </named>
        <named>
          <default>
            <value>4</value>
          </default>
          <type>integer</type>
          <description>the maximum number of decimal places for the volume</description>
          <name>precision</name>
          <shortname>p</shortname>
        </named>
      </namedArgs>
    </arguments>
    """
    When the user views the terminal
    Then the output should be "VolumeCalculator error: the value 8 matches no argument"


  Scenario: Provide invalid XML (missing positional name) and legal, clean arguments.
    
    Given the program "VolumeCalculator" has started with arguments "2 --type pyramid 5 -p 2 3" and XML
    """
    <?xml version="1.0"?>
    <arguments>
      <positionalArgs>
        <positional>
          <type>float</type>
          <description>the length of the volume</description>
          <name>length</name>
        </positional>
        <positional>
          <type>float</type>
          <description>the width of the volume</description>
        </positional>
        <positional>
          <description>the height of the volume</description>
          <name>height</name>
          <type>float</type>
        </positional>
      </positionalArgs>
      <namedArgs>
        <named>
          <description>the type of volume</description>
          <shortname>t</shortname>
          <type>string</type>
          <name>type</name>
          <default>
            <value>box</value>
          </default>
          <restrictions>
            <restriction>box</restriction>
            <restriction>pyramid</restriction>
            <restriction>ellipsoid</restriction>
          </restrictions>
        </named>
        <named>
          <default>
            <value>4</value>
          </default>
          <type>integer</type>
          <description>the maximum number of decimal places for the volume</description>
          <name>precision</name>
          <shortname>p</shortname>
        </named>
      </namedArgs>
    </arguments>
    """
    When the user views the terminal
    Then the output should be "VolumeCalculator error: invalid XML"


  Scenario: Provide invalid XML (missing named default) and legal, clean arguments.
    
    Given the program "VolumeCalculator" has started with arguments "2 --type pyramid 5 -p 2 3" and XML
    """
    <?xml version="1.0"?>
    <arguments>
      <positionalArgs>
        <positional>
          <type>float</type>
          <description>the length of the volume</description>
          <name>length</name>
        </positional>
        <positional>
          <name>width</type>
          <type>float</type>
          <description>the width of the volume</description>
        </positional>
        <positional>
          <description>the height of the volume</description>
          <name>height</name>
          <type>float</type>
        </positional>
      </positionalArgs>
      <namedArgs>
        <named>
          <description>the type of volume</description>
          <shortname>t</shortname>
          <type>string</type>
          <name>type</name>
          <restrictions>
            <restriction>box</restriction>
            <restriction>pyramid</restriction>
            <restriction>ellipsoid</restriction>
          </restrictions>
        </named>
        <named>
          <default>
            <value>4</value>
          </default>
          <type>integer</type>
          <description>the maximum number of decimal places for the volume</description>
          <name>precision</name>
          <shortname>p</shortname>
        </named>
      </namedArgs>
    </arguments>
    """
    When the user views the terminal
    Then the output should be "VolumeCalculator error: invalid XML"


  Scenario: Provide the example XML and only the single "-h" argument.
    
    Given the program "VolumeCalculator" has started with arguments "-h" and XML
    """
    <?xml version="1.0"?>
    <arguments>
      <positionalArgs>
        <positional>
          <type>float</type>
          <description>the length of the volume</description>
          <name>length</name>
        </positional>
        <positional>
          <type>float</type>
          <name>width</name>
          <description>the width of the volume</description>
        </positional>
        <positional>
          <description>the height of the volume</description>
          <name>height</name>
          <type>float</type>
        </positional>
      </positionalArgs>
      <namedArgs>
        <named>
          <description>the type of volume</description>
          <shortname>t</shortname>
          <type>string</type>
          <name>type</name>
          <default>
            <value>box</value>
          </default>
          <restrictions>
            <restriction>box</restriction>
            <restriction>pyramid</restriction>
            <restriction>ellipsoid</restriction>
          </restrictions>
        </named>
        <named>
          <default>
            <value>4</value>
          </default>
          <type>integer</type>
          <description>the maximum number of decimal places for the volume</description>
          <name>precision</name>
          <shortname>p</shortname>
        </named>
      </namedArgs>
    </arguments>
    """
    When the user views the terminal
    Then the output should be
    """
    usage: java VolumeCalculator [-h] [-t TYPE] [-p PRECISION] length width height
    
    Calculate the volume.
    
    positional arguments:
     length                               (float)       the length of the volume
     width                                (float)       the width of the volume
     height                               (float)       the height of the volume
    
    named arguments:
     -h, --help                           show this help message and exit
     -t TYPE, --type TYPE                 (string)      the type of volume {box, pyramid, ellipsoid} (default: box)
     -p PRECISION, --precision PRECISION  (integer)     the maximum number of decimal places for the volume (default: 4)
    """


  Scenario: Provide the example XML and only the single "--help" argument.
    
    Given the program "VolumeCalculator" has started with arguments "--help" and XML
    """
    <?xml version="1.0"?>
    <arguments>
      <positionalArgs>
        <positional>
          <type>float</type>
          <description>the length of the volume</description>
          <name>length</name>
        </positional>
        <positional>
          <type>float</type>
          <name>width</name>
          <description>the width of the volume</description>
        </positional>
        <positional>
          <description>the height of the volume</description>
          <name>height</name>
          <type>float</type>
        </positional>
      </positionalArgs>
      <namedArgs>
        <named>
          <description>the type of volume</description>
          <shortname>t</shortname>
          <type>string</type>
          <name>type</name>
          <default>
            <value>box</value>
          </default>
          <restrictions>
            <restriction>box</restriction>
            <restriction>pyramid</restriction>
            <restriction>ellipsoid</restriction>
          </restrictions>
        </named>
        <named>
          <default>
            <value>4</value>
          </default>
          <type>integer</type>
          <description>the maximum number of decimal places for the volume</description>
          <name>precision</name>
          <shortname>p</shortname>
        </named>
      </namedArgs>
    </arguments>
    """
    When the user views the terminal
    Then the output should be
    """
    usage: java VolumeCalculator [-h] [-t TYPE] [-p PRECISION] length width height
    
    Calculate the volume.
    
    positional arguments:
     length                               (float)       the length of the volume
     width                                (float)       the width of the volume
     height                               (float)       the height of the volume
    
    named arguments:
     -h, --help                           show this help message and exit
     -t TYPE, --type TYPE                 (string)      the type of volume {box, pyramid, ellipsoid} (default: box)
     -p PRECISION, --precision PRECISION  (integer)     the maximum number of decimal places for the volume (default: 4)
    """


  Scenario: Provide a limited XML and only the single "--help" argument.
    
    Given the program "VolumeCalculator" has started with arguments "--help" and XML
    """
    <?xml version="1.0"?>
    <arguments>
      <positionalArgs>
        <positional>
          <type>float</type>
          <description>the length of the volume</description>
          <name>length</name>
        </positional>
        <positional>
          <type>float</type>
          <name>width</name>
          <description>the width of the volume</description>
        </positional>
        <positional>
          <description>the height of the volume</description>
          <name>height</name>
          <type>float</type>
        </positional>
      </positionalArgs>
      <namedArgs>
        <named>
          <description>the type of volume</description>
          <type>string</type>
          <name>type</name>
          <default>
            <value>box</value>
          </default>
        </named>
      </namedArgs>
    </arguments>
    """
    When the user views the terminal
    Then the output should be
    """
    usage: java VolumeCalculator [-h] [--type TYPE] length width height
    
    Calculate the volume.
    
    positional arguments:
     length       (float)       the length of the volume
     width        (float)       the width of the volume
     height       (float)       the height of the volume
    
    named arguments:
     -h, --help   show this help message and exit
     --type TYPE  (string)      the type of volume (default: box)
    """

