Feature: Allow argument information to be saved to an XML file.

  If an argument list has already been implemented in Java code, it would be
  helpful to be able to save the list information to a file for use later. Since
  the functionality already exists to read that information from an XML file,
  XML provides an excellent format for this data. The XML file should store all
  of the argument information (including long-form names, short-form names,
  datatypes, default values, etc.), just as in the case of reading the argument
  information from an XML file.

  Add functionality to the argument parser library to allow writing argument
  info to an XML file structured as before.
  
  
  In order to demonstrate this functionality, you will need to create another
  demo program called "XMLReadWriteDemo" that takes, as its first argument, a
  string representing the contents of an XML file. Its second argument is the
  name of the file to which to write the XML for the arguments created. The
  program should create a parser based on the incoming XML and produce a 
  corresponding XML in the given file that has that argument information.
  
  Note that any programmatic method to write the output XML file would be very
  unlikely to precisely match the incoming XML file contents (e.g., order of
  elements would be different).
  
  
  Scenario: Provide the first XML and a filename.
    
    Given the program "XMLReadWriteDemo" has started with arguments "f10_1.xml" and XML
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
    Then the file "f10_1.xml" should exist and contain
    """
    <?xml version="1.0"?>
    <arguments>
      <positionalArgs>
        <positional>
          <name>length</name>
          <type>float</type>
          <description>the length of the volume</description>
        </positional>
        <positional>
          <name>width</name>
          <type>float</type>
          <description>the width of the volume</description>
        </positional>
        <positional>
          <name>height</name>
          <type>float</type>
          <description>the height of the volume</description>
        </positional>
      </positionalArgs>
      <namedArgs>
        <named>
          <name>type</name>
          <type>string</type>
          <description>the type of volume</description>
          <default>
            <value>box</value>
          </default>
          <restrictions>
            <restriction>box</restriction>
            <restriction>pyramid</restriction>
            <restriction>ellipsoid</restriction>
          </restrictions>
          <shortname>t</shortname>
        </named>
        <named>
          <name>precision</name>
          <type>integer</type>
          <description>the maximum number of decimal places for the volume</description>
          <default>
            <value>4</value>
          </default>
          <shortname>p</shortname>
        </named>
      </namedArgs>
    </arguments>
    """

  Scenario: Provide the second XML and a filename.
    
    Given the program "XMLReadWriteDemo" has started with arguments "f10_2.xml" and XML
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
          <restrictions>
            <restriction>box</restriction>
            <restriction>pyramid</restriction>
          </restrictions>
        </named>
      </namedArgs>
    </arguments>
    """
    When the user views the terminal
    Then the file "f10_2.xml" should exist and contain
    """
    <?xml version="1.0"?>
    <arguments>
      <positionalArgs>
        <positional>
          <name>length</name>
          <type>float</type>
          <description>the length of the volume</description>
        </positional>
        <positional>
          <name>width</name>
          <type>float</type>
        </positional>
        <positional>
          <name>height</name>
          <type>float</type>
          <description>the height of the volume</description>
        </positional>
      </positionalArgs>
      <namedArgs>
        <named>
          <name>type</name>
          <type>string</type>
          <description>the type of volume</description>
          <default>
            <value>box</value>
          </default>
          <restrictions>
            <restriction>pyramid</restriction>
            <restriction>box</restriction>
          </restrictions>
        </named>
      </namedArgs>
    </arguments>
    """

