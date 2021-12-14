package edu.wofford.woclo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class IdentifierTest {
  @Test
  public void testConstructer() {
    Identifier iden = new Identifier("arg1", "string", "5", "test");
    assertEquals("arg1", iden.getName());
    assertEquals("5", iden.getValue());
  }

  @Test
  public void testAddData() {
    Identifier iden = new Identifier("arg1", "string", "5", "test");
    iden.setValue("6");
    assertEquals("6", iden.getValue());
  }

  @Test
  public void testAddDataInt() {
    Identifier iden = new Identifier("arg1", "integer", "5", "test");
    iden.setValue("6");
    assertEquals(6, iden.getValue());
  }

  @Test
  public void testAddDataFloat() {
    Identifier iden = new Identifier("arg1", "float", "5", "test");
    iden.setValue("6.6f");
    assertEquals(6.6f, iden.getValue());
  }

  @Test
  public void testErrorMessage() {
    Identifier iden = new Identifier("arg1", "float", "5", "test");
    assertEquals("", iden.errorMessage());
  }

  @Test
  public void testDataTypeException() {
    Identifier iden = new Identifier("arg1", "float", "Richard", "test");
    try {
      iden.getValue();
    } catch (IncorrectArgumentTypeException e) {
      assert (true);
      return;
    }
    assert (false);
  }

  @Test
  public void testDataTypeExceptionInt() {
    Identifier iden = new Identifier("arg1", "integer", "Richard", "test");
    try {
      iden.getValue();
    } catch (IncorrectArgumentTypeException e) {
      assert (true);
      return;
    }
    assert (false);
  }

  @Test
  public void testShortName() {
    Identifier iden = new Identifier("arg1", "integer", "Richard", "test", "t");
    assertEquals("t", iden.getShortName());
  }

  @Test
  public void testRestrictedValString() {
    Identifier iden =
        new Identifier("arg1", "integer", "Richard", "test", "t", new String[] {"1", "2", "3"});
    assertEquals("1, 2, 3", iden.getRestrictedValueString());
  }

  @Test
  public void testIsRestrictedValueTrue() {
    Identifier iden =
        new Identifier("arg1", "integer", "Richard", "test", "t", new String[] {"1", "2", "3"});
    assertEquals(true, iden.isRestrictedValue("1"));
  }

  @Test
  public void testIsRestrictedValueFalse() {
    Identifier iden =
        new Identifier("arg1", "integer", "Richard", "test", "t", new String[] {"1", "2", "3"});
    assertEquals(false, iden.isRestrictedValue("7"));
  }
}
