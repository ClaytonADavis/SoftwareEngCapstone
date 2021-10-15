package edu.wofford.woclo;

import java.util.*;

public class Parser {
  private List<String> identifyer;
  private Map<String, String> map;

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
    if (commandLine.length < identifyer.size()) {
      throw new lessArgs();
    }
    if (commandLine.length > identifyer.size()) {
      throw new moreArgs();
    }
    int size = commandLine.length;
    for (int i = 0; i < size; i++) {
      map.put(identifyer.get(i), commandLine[i]);
    }
  }

  public String getValue(String command) {
    return map.get(command);
  }
}
