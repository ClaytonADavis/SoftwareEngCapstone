package edu.wofford.woclo;

import java.util.*;

public class Parser {
  private List<String> identifier;
  private Map<String, String> map;

  private boolean getHelp(String[] argArr) {
    for (String s : argArr) {
      if (s.equals("-h") || s.equals("--help")) {
        return true;
      }
    }
    return false;
  }

  public Parser() {
    identifier = new ArrayList<String>();
    map = new HashMap<String, String>();
  }

  public void addIdentifier(String id) {
    identifier.add(id);
  }

  public String getIdendtifyer(int i) {
    return identifier.get(i);
  }

  public void addIdentifierAtIndex(String id, int i) {
    identifier.set(i, id);
  }

  public void parseCommandLine(String[] commandLine) {
    if (getHelp(commandLine)) {
      throw new HelpException();
    }
    if (commandLine.length < identifier.size()) {
      throw new NotEnoughArgsException();
    }
    if (commandLine.length > identifier.size()) {
      throw new TooManyArgsException();
    }
    int size = commandLine.length;
    for (int i = 0; i < size; i++) {
      map.put(identifier.get(i), commandLine[i]);
    }
  }

  public String getValue(String command) {
    return map.get(command);
  }

  public void addIdentifierArray(String[] idArr) {
    for (String s : idArr) {
      identifier.add(s);
    }
  }
}
