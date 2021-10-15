package edu.wofford.woclo;

import java.util.*;

public class Parser {
  private List<String> identifyer;
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
    identifyer = new ArrayList<String>();
    map = new HashMap<String, String>();
  }

  public void addIdentifyer(String id) {
    identifyer.add(id);
  }

  public String getIdendtifyer(int i) {
    return identifyer.get(i);
  }

  public void addIdentifyerAtIndex(String id, int i) {
    identifyer.set(i, id);
  }

  public void parseCommandLine(String[] commandLine) {
    if (getHelp(commandLine)) {
      throw new HelpException();
    }
    if (commandLine.length < identifyer.size()) {
      throw new LessArgs();
    }
    if (commandLine.length > identifyer.size()) {
      throw new MoreArgs();
    }
    int size = commandLine.length;
    for (int i = 0; i < size; i++) {
      map.put(identifyer.get(i), commandLine[i]);
    }
  }

  public String getValue(String command) {
    return map.get(command);
  }

  public void addIdentifyerArray(String[] idArr) {
    for (String s : idArr) {
      identifyer.add(s);
    }
  }
}
