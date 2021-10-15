Feature: Allow named arguments to be specified as required arguments.

  Until now, named arguments have been treated as optional. There are times,
  however, when named arguments should be required. In this case, their default
  values will not matter because failure to include the argument/value pair
  will throw an exception just like any other missing argument.

  The XML should now include (only if a named argument is required) the
  following tag:
  
  <required/>

  For instance, the following would be valid (only a subset of the full XML):
  
  <named>
    <default>
      <value>4</value>
    </default>
    <type>integer</type>
    <description>the maximum number of decimal places for the volume</description>
    <required/>
    <name>precision</name>
    <shortname>p</shortname>
  </named>
  
  If a named argument is required, the help/usage documentation should show it
  without the square brackets. For instance, for the VolumeCalculator example
  above with the precision required, the initial usage line would have
  
  -p PRECISION
  
  instead of
  
  [-p PRECISION]
  
  Additionally, the default value should not appear in the help/usage, since the
  true value must be provided. 
  
  Everything else should be the same.
  
  
  In order to demonstrate this functionality, the "VolumeCalculator" demo
  program should suffice, since it takes an XML string for its argument setup.
  
  Scenario: Provide the example XML with required precision and legal, messy arguments.
    
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
      </namedArgs>
    </arguments>
    """
    When the user views the terminal
    Then the output should be "125.66"


  Scenario: Provide the example XML with required precision and illegal, messy arguments.
    
    Given the program "VolumeCalculator" has started with arguments "2 --type ellipsoid 5 3" and XML
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
      </namedArgs>
    </arguments>
    """
    When the user views the terminal
    Then the output should be "VolumeCalculator error: required named argument precision not given"


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
          <required/>
          <default>
            <value>7</value>
          </default>
          <type>integer</type>
          <description>the maximum number of decimal places for the volume</description>
          <name>precision</name>
        </named>
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
      </namedArgs>
    </arguments>
    """
    When the user views the terminal
    Then the output should be
    """
    usage: java VolumeCalculator [-h] --precision PRECISION [-t TYPE] length width height
    
    Calculate the volume.
    
    positional arguments:
     length                 (float)       the length of the volume
     width                  (float)       the width of the volume
     height                 (float)       the height of the volume
    
    named arguments:
     -h, --help             show this help message and exit
     --precision PRECISION  (integer)     the maximum number of decimal places for the volume
     -t TYPE, --type TYPE   (string)      the type of volume {box, pyramid, ellipsoid} (default: box)
    """


