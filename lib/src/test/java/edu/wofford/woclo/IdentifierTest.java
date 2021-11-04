package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class IdentifierTest {
  @Test
  public void tesDefaulttConstructer() {
    Identifier iden = new Identifier();
    assertEquals("", iden.getName());
  }

  @Test
  public void testConstructer() {
    Identifier iden = new Identifier("arg1", "String", "5");
    assertEquals("arg1", iden.getName());
    assertEquals("5", iden.getValue());
  }

  @Test
  public void testAddData() {
    Identifier iden = new Identifier("arg1", "String", "5");
    iden.addData("6");
    assertEquals("6", iden.getValue());
  }

  public void testAddDataInt() {
    Identifier iden = new Identifier("arg1", "int", "5");
    iden.addData("6");
    assertEquals(6, iden.getValue());
  }

  public void testAddDataFloat() {
    Identifier iden = new Identifier("arg1", "float", "5");
    iden.addData("6.6");
    assertEquals(6.6, iden.getValue());
  }

  public void testErrorMessage() {
    Identifier iden = new Identifier("arg1", "float", "5");
    assertEquals("", iden.errorMessage());
  }

  public void testDataTypeException() {
    Identifier iden = new Identifier("arg1", "float", "Richard");
    try {
      iden.getValue();
    } catch (IncorrectArgumentTypeException e) {
      assert (true);
      return;
    }
    assert (false);
  }

  public void testDataTypeExceptionInt() {
    Identifier iden = new Identifier("arg1", "int", "Richard");
    try {
      iden.getValue();
    } catch (IncorrectArgumentTypeException e) {
      assert (true);
      return;
    }
    assert (false);
  }
}
