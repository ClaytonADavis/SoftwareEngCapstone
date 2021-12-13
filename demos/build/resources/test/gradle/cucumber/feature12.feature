Feature: Allow named arguments to be grouped into mutually exclusive groups.

  Occasionally, there are distinct groups of named arguments that do not make
  sense when they are combined. For instance, you may have a named flag "quiet"
  that causes the program to produce no console output, and you may also have a
  named argument "precision" that specifies the decimal precision that values
  should be printed to the console. Clearly, these two arguments are mutually
  exclusive, and they should be treated as such. 

  You should assume that if there is more than one group of mutually exclusive
  arguments, then it should be illegal to use more than one argument from each
  such group.

  The XML format should accommodate this new feature as follows:
    <arguments>
      <positionalArgs>
        ...
      </positionalArgs>
      <namedArgs>
        ...
      </namedArgs>
      <mutuallyExclusive>
        <group>
          <name>foo</name>
          <name>bar</name>
        </group>
        <group>
          <name>alpha</name>
          <name>beta</name>
          <name>gamma</name>
        </group>
      </mutuallyExclusive>
    </arguments>
    
  Here, the "mutuallyExclusive" block may be absent. If it is present, then it
  must contain at least one "group" block. Also, any "group" block must contain
  at least two "name" elements. Adding this new component to the XML format should
  not interfere with any previous feature.
  
  
  In order to demonstrate this functionality, the "VolumeCalculator" demo
  needs to be modified to print a message if there is a mutual exclusion error.
  The message should be of the form (for the example above, assuming both "alpha"
  and "gamma" were passed into the parse method):
  "VolumeCalculator error: mutual exclusion [alpha, beta, gamma]"
  program should suffice, since it takes an XML string for its argument setup.
  
  Scenario: Provide an XML two additional flags with one mutual exclusion group, legal arguments.
    
    Given the program "VolumeCalculator" has started with arguments "2 --type ellipsoid 5 -p 2 3" and XML
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
          <required/>
          <description>the maximum number of decimal places for the volume</description>
          <name>precision</name>
          <shortname>p</shortname>
        </named>
        <named>
          <name>foo</name>
          <type>boolean</type>
          <shortname>f</shortname>
          <default><value>false</value></default>
        </named>
        <named>
          <name>bar</name>
          <type>boolean</type>
          <default><value>false</value></default>
        </named>
      </namedArgs>
      <mutuallyExclusive>
        <group>
          <name>foo</name>
          <name>precision</name>
        </group>
      </mutuallyExclusive>
    </arguments>
    """
    When the user views the terminal
    Then the output should be "125.66"


  Scenario: Provide an XML two additional flags with one mutual exclusion group, mututally exclusive arguments.
    
    Given the program "VolumeCalculator" has started with arguments "2 -f --type ellipsoid 5 -p 2 3" and XML
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
          <required/>
          <description>the maximum number of decimal places for the volume</description>
          <name>precision</name>
          <shortname>p</shortname>
        </named>
        <named>
          <name>foo</name>
          <type>boolean</type>
          <shortname>f</shortname>
          <default><value>false</value></default>
        </named>
        <named>
          <name>bar</name>
          <type>boolean</type>
          <default><value>false</value></default>
        </named>
      </namedArgs>
      <mutuallyExclusive>
        <group>
          <name>foo</name>
          <name>precision</name>
        </group>
      </mutuallyExclusive>
    </arguments>
    """
    When the user views the terminal
    Then the output should be "VolumeCalculator error: mutual exclusion [foo, precision]"


  Scenario: Provide the example XML with required precision and only the single "-h" argument.
    
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
          <required/>
          <description>the maximum number of decimal places for the volume</description>
          <name>precision</name>
          <shortname>p</shortname>
        </named>
        <named>
          <name>foo</name>
          <type>boolean</type>
          <shortname>f</shortname>
          <default><value>false</value></default>
        </named>
        <named>
          <name>bar</name>
          <type>boolean</type>
          <default><value>false</value></default>
        </named>
      </namedArgs>
      <mutuallyExclusive>
        <group>
          <name>foo</name>
          <name>precision</name>
        </group>
        <group>
          <name>type</name>
          <name>bar</name>
        </group>
      </mutuallyExclusive>
    </arguments>
    """
    When the user views the terminal
    Then the output should be
    """
    usage: java VolumeCalculator [-h] [-t TYPE] -p PRECISION [-f] [--bar] length width height
    
    Calculate the volume.
    
    positional arguments:
     length                               (float)       the length of the volume
     width                                (float)       the width of the volume
     height                               (float)       the height of the volume
    
    named arguments:
     -h, --help                           show this help message and exit
     -t TYPE, --type TYPE                 (string)      the type of volume {box, pyramid, ellipsoid} (default: box)
     -p PRECISION, --precision PRECISION  (integer)     the maximum number of decimal places for the volume
     -f, --foo                            
     --bar                                
    
    mutually exclusive:
     [foo, precision]
     [type, bar]
    """


