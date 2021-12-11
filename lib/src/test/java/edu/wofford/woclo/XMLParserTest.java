package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.junit.jupiter.api.*;

public class XMLParserTest {

  @Test
  public void testParseXMLPositionalArgs() {
    String xml =
        "<?xml version=\"1.0\"?> \n<arguments>\n  <positionalArgs>\n    <positional>\n      <type>float</type>\n      <description>the length of the volume</description>\n      <name>length</name>\n    </positional>\n    <positional>\n      <name>width</name>\n      <type>float</type>\n      <description>the width of the volume</description>\n    </positional>\n    <positional>\n      <description>the height of the volume</description>\n      <name>height</name>\n      <type>float</type>\n    </positional>\n  </positionalArgs>\n  <namedArgs>\n    <named>\n      <description>the type of volume</description>\n      <shortname>t</shortname>\n      <type>string</type>\n      <name>type</name>\n      <restrictions>\n        <restriction>box</restriction>\n        <restriction>pyramid</restriction>\n        <restriction>ellipsoid</restriction>\n      </restrictions>\n    </named>\n    <named>\n      <default>\n        <value>4</value>\n      </default>\n      <type>integer</type>\n      <description>the maximum number of decimal places for the volume</description>\n      <name>precision</name>\n      <shortname>p</shortname>\n    </named>\n  </namedArgs>\n</arguments>";
    XMLParser p = new XMLParser(xml, "");
    assertEquals(
        "\n      <name>width</name>\n      <type>float</type>\n      <description>the width of the volume</description>\n    ",
        p.getUnformattedArg());
  }

  @Test
  public void testParseXMLPositionalId() {
    String xml =
        "<?xml version=\"1.0\"?> \n<arguments>\n  <positionalArgs>\n    <positional>\n      <type>float</type>\n      <description>the length of the volume</description>\n      <name>length</name>\n    </positional>\n    <positional>\n      <name>width</name>\n      <type>float</type>\n      <description>the width of the volume</description>\n    </positional>\n    <positional>\n      <description>the height of the volume</description>\n      <name>height</name>\n      <type>float</type>\n    </positional>\n  </positionalArgs>\n  <namedArgs>\n    <named>\n      <description>the type of volume</description>\n      <shortname>t</shortname>\n      <type>string</type>\n      <name>type</name>\n      <restrictions>\n        <restriction>box</restriction>\n        <restriction>pyramid</restriction>\n        <restriction>ellipsoid</restriction>\n      </restrictions>\n    </named>\n    <named>\n      <default>\n        <value>4</value>\n      </default>\n      <type>integer</type>\n      <description>the maximum number of decimal places for the volume</description>\n      <name>precision</name>\n      <shortname>p</shortname>\n    </named>\n  </namedArgs>\n</arguments>";
    XMLParser p = new XMLParser(xml, "");
    assertEquals("float", p.getPositionalIdentifiers().get(0).getType());
  }

  @Test
  public void testParseXMLPositionalCount() {
    String xml =
        "<?xml version=\"1.0\"?> \n<arguments>\n  <positionalArgs>\n    <positional>\n      <type>float</type>\n      <description>the length of the volume</description>\n      <name>length</name>\n    </positional>\n    <positional>\n      <name>width</name>\n      <type>float</type>\n      <description>the width of the volume</description>\n    </positional>\n    <positional>\n      <description>the height of the volume</description>\n      <name>height</name>\n      <type>float</type>\n    </positional>\n  </positionalArgs>\n  <namedArgs>\n    <named>\n      <description>the type of volume</description>\n      <shortname>t</shortname>\n      <type>string</type>\n      <name>type</name>\n      <restrictions>\n        <restriction>box</restriction>\n        <restriction>pyramid</restriction>\n        <restriction>ellipsoid</restriction>\n      </restrictions>\n    </named>\n    <named>\n      <default>\n        <value>4</value>\n      </default>\n      <type>integer</type>\n      <description>the maximum number of decimal places for the volume</description>\n      <name>precision</name>\n      <shortname>p</shortname>\n    </named>\n  </namedArgs>\n</arguments>";
    XMLParser p = new XMLParser(xml, "");
    assertEquals(3, p.getPositionalIdentifiers().size());
  }

  @Test
  public void testParseXMLInvalidXMLNoMatch() {
    String xml =
        "<?xml version=nal>\n      <type>float</type>\n      <description>the length of the volume</description>\n      <name>length</name>\n    </positional>\n    <positional>\n      <name>width</name>\n      <type>float</type>\n      <description>the width of the volume</description>\n    </positional>\n    <positional>\n      <description>the height of the volume</description>\n      <name>height</name>\n      <type>float</type>\n    </positional>\n  </positionalArgs>\n  <namedArgs>\n    <named>\n      <description>the type of volume</description>\n      <shortname>t</shortname>\n      <type>string</type>\n      <name>type</name>\n      <restrictions>\n        <restriction>box</restriction>\n        <restriction>pyramid</restriction>\n        <restriction>ellipsoid</restriction>\n      </restrictions>\n    </named>\n    <named>\n      <default>\n        <value>4</value>\n      </default>\n      <type>integer</type>\n      <description>the maximum number of decimal places for the volume</description>\n      <name>precision</name>\n      <shortname>p</shortname>\n    </named>\n  </namedArgs>\n</arguments>";
    try {
      XMLParser p = new XMLParser(xml, "");
    } catch (InvalidXMLException e) {
      assert (true);
      return;
    }
  }

  @Test
  public void testParseXMLInvalidXMLNoType() {
    String xml =
        "<?xml version=\"1.0\"?> \n<arguments>\n  <positionalArgs>\n    <positional>\n      <type>float</type>\n      <description>the length of the volume</description>\n      <name>length</name>\n    </positional>\n    <positional>\n      <name>width</name>\n      \n      <description>the width of the volume</description>\n    </positional>\n    <positional>\n      <description>the height of the volume</description>\n      <name>height</name>\n      <type>float</type>\n    </positional>\n  </positionalArgs>\n  <namedArgs>\n    <named>\n      <description>the type of volume</description>\n      <shortname>t</shortname>\n      <type>string</type>\n      <name>type</name>\n      <restrictions>\n        <restriction>box</restriction>\n        <restriction>pyramid</restriction>\n        <restriction>ellipsoid</restriction>\n      </restrictions>\n    </named>\n    <named>\n      <default>\n        <value>4</value>\n      </default>\n      <type>integer</type>\n      <description>the maximum number of decimal places for the volume</description>\n      <name>precision</name>\n      <shortname>p</shortname>\n    </named>\n  </namedArgs>\n</arguments>";
    try {
      XMLParser p = new XMLParser(xml, "");
    } catch (InvalidXMLException e) {
      assert (true);
      return;
    }
  }

  @Test
  public void testParseXMLInvalidXMLNoName() {
    String xml =
        "<?xml version=\"1.0\"?> \n<arguments>\n  <positionalArgs>\n    <positional>\n      <type>float</type>\n      <description>the length of the volume</description>\n      \n    </positional>\n    <positional>\n      <name>width</name>\n      <type>float</type>\n      <description>the width of the volume</description>\n    </positional>\n    <positional>\n      <description>the height of the volume</description>\n      <name>height</name>\n      <type>float</type>\n    </positional>\n  </positionalArgs>\n  <namedArgs>\n    <named>\n      <description>the type of volume</description>\n      <shortname>t</shortname>\n      <type>string</type>\n      <name>type</name>\n      <restrictions>\n        <restriction>box</restriction>\n        <restriction>pyramid</restriction>\n        <restriction>ellipsoid</restriction>\n      </restrictions>\n    </named>\n    <named>\n      <default>\n        <value>4</value>\n      </default>\n      <type>integer</type>\n      <description>the maximum number of decimal places for the volume</description>\n      <name>precision</name>\n      <shortname>p</shortname>\n    </named>\n  </namedArgs>\n</arguments>";
    try {
      XMLParser p = new XMLParser(xml, "");
    } catch (InvalidXMLException e) {
      assert (true);
      return;
    }
  }

  @Test
  public void testParseXMLInvalidXMLNoDescription() {
    String xml =
        "<?xml version=\"1.0\"?> \n<arguments>\n  <positionalArgs>\n    <positional>\n      <type>float</type>\n      \n      <name>length</name>\n    </positional>\n    <positional>\n      <name>width</name>\n      <type>float</type>\n      <description>the width of the volume</description>\n    </positional>\n    <positional>\n      <description>the height of the volume</description>\n      <name>height</name>\n      <type>float</type>\n    </positional>\n  </positionalArgs>\n  <namedArgs>\n    <named>\n      <description>the type of volume</description>\n      <shortname>t</shortname>\n      <type>string</type>\n      <name>type</name>\n      <restrictions>\n        <restriction>box</restriction>\n        <restriction>pyramid</restriction>\n        <restriction>ellipsoid</restriction>\n      </restrictions>\n    </named>\n    <named>\n      <default>\n        <value>4</value>\n      </default>\n      <type>integer</type>\n      <description>the maximum number of decimal places for the volume</description>\n      <name>precision</name>\n      <shortname>p</shortname>\n    </named>\n  </namedArgs>\n</arguments>";
    try {
      XMLParser p = new XMLParser(xml, "");
    } catch (InvalidXMLException e) {
      assert (true);
      return;
    }
  }

  @Test
  public void testParseXMLOptionalCount() {
    String xml =
        "<?xml version=\"1.0\"?> \n<arguments>\n  <positionalArgs>\n    <positional>\n      <type>float</type>\n      <description>the length of the volume</description>\n      <name>length</name>\n    </positional>\n    <positional>\n      <name>width</name>\n      <type>float</type>\n      <description>the width of the volume</description>\n    </positional>\n    <positional>\n      <description>the height of the volume</description>\n      <name>height</name>\n      <type>float</type>\n    </positional>\n  </positionalArgs>\n  <namedArgs>\n    <named>\n      <description>the type of volume</description>\n      <shortname>t</shortname>\n      <type>string</type>\n      <name>type</name>\n      <restrictions>\n        <restriction>box</restriction>\n        <restriction>pyramid</restriction>\n        <restriction>ellipsoid</restriction>\n      </restrictions>\n    </named>\n    <named>\n      <default>\n        <value>4</value>\n      </default>\n      <type>integer</type>\n      <description>the maximum number of decimal places for the volume</description>\n      <name>precision</name>\n      <shortname>p</shortname>\n    </named>\n  </namedArgs>\n</arguments>";
    XMLParser p = new XMLParser(xml, "");
    assertEquals(2, p.getOptionalIdentifiers().size());
  }

  @Test
  public void testParseXMLOptionalIdType() {
    String xml =
        "<?xml version=\"1.0\"?> \n<arguments>\n  <positionalArgs>\n    <positional>\n      <type>float</type>\n      <description>the length of the volume</description>\n      <name>length</name>\n    </positional>\n    <positional>\n      <name>width</name>\n      <type>float</type>\n      <description>the width of the volume</description>\n    </positional>\n    <positional>\n      <description>the height of the volume</description>\n      <name>height</name>\n      <type>float</type>\n    </positional>\n  </positionalArgs>\n  <namedArgs>\n    <named>\n      <description>the type of volume</description>\n      <shortname>t</shortname>\n      <type>string</type>\n      <name>type</name>\n      <restrictions>\n        <restriction>box</restriction>\n        <restriction>pyramid</restriction>\n        <restriction>ellipsoid</restriction>\n      </restrictions>\n    </named>\n    <named>\n      <default>\n        <value>4</value>\n      </default>\n      <type>integer</type>\n      <description>the maximum number of decimal places for the volume</description>\n      <name>precision</name>\n      <shortname>p</shortname>\n    </named>\n  </namedArgs>\n</arguments>";
    XMLParser p = new XMLParser(xml, "");
    assertEquals("string", p.getOptionalIdentifiers().get(0).getType());
  }

  @Test
  public void testParseXMLOptionalIdName() {
    String xml =
        "<?xml version=\"1.0\"?> \n<arguments>\n  <positionalArgs>\n    <positional>\n      <type>float</type>\n      <description>the length of the volume</description>\n      <name>length</name>\n    </positional>\n    <positional>\n      <name>width</name>\n      <type>float</type>\n      <description>the width of the volume</description>\n    </positional>\n    <positional>\n      <description>the height of the volume</description>\n      <name>height</name>\n      <type>float</type>\n    </positional>\n  </positionalArgs>\n  <namedArgs>\n    <named>\n      <description>the type of volume</description>\n      <shortname>t</shortname>\n      <type>string</type>\n      <name>type</name>\n      <restrictions>\n        <restriction>box</restriction>\n        <restriction>pyramid</restriction>\n        <restriction>ellipsoid</restriction>\n      </restrictions>\n    </named>\n    <named>\n      <default>\n        <value>4</value>\n      </default>\n      <type>integer</type>\n      <description>the maximum number of decimal places for the volume</description>\n      <name>precision</name>\n      <shortname>p</shortname>\n    </named>\n  </namedArgs>\n</arguments>";
    XMLParser p = new XMLParser(xml, "");
    assertEquals("type", p.getOptionalIdentifiers().get(0).getName());
  }

  @Test
  public void testParseXMLWrite() {
    String xml =
        "<?xml version=\"1.0\"?> \n<arguments>\n  <positionalArgs>\n    <positional>\n      <type>float</type>\n      <description>the length of the volume</description>\n      <name>length</name>\n    </positional>\n    <positional>\n      <name>width</name>\n      <type>float</type>\n      <description>the width of the volume</description>\n    </positional>\n    <positional>\n      <description>the height of the volume</description>\n      <name>height</name>\n      <type>float</type>\n    </positional>\n  </positionalArgs>\n  <namedArgs>\n    <named>\n      <description>the type of volume</description>\n      <shortname>t</shortname>\n      <type>string</type>\n      <name>type</name>\n      <restrictions>\n        <restriction>box</restriction>\n        <restriction>pyramid</restriction>\n        <restriction>ellipsoid</restriction>\n      </restrictions>\n    </named>\n    <named>\n      <default>\n        <value>4</value>\n      </default>\n      <type>integer</type>\n      <description>the maximum number of decimal places for the volume</description>\n      <name>precision</name>\n      <shortname>p</shortname>\n    </named>\n  </namedArgs>\n</arguments>";
    XMLParser p = new XMLParser(xml, "testfile.xml");
    p.writeXMLToFile();
    Path path = Paths.get("testfile.xml");
    assertEquals("testfile.xml", path.toString());
  }
}
