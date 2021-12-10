package demos;

import edu.wofford.woclo.*;

public class XMLReadWriteDemo {

  public static void main(String... args) {
    Parser p = new Parser("XMLReadWriteDemo", "Read and write an XML file");
    p.parseCommandLine(args, true, true);
  }
}
