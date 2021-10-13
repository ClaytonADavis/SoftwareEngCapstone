package edu.wofford.woclo;

import java.util.*;

public class Parser {
  private List<String> identifyer;

  public Parser() {
    identifyer = new ArrayList<String>();
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
}
