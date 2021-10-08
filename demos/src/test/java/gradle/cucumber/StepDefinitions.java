package gradle.cucumber;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import demos.Main;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.ElementSelectors;
import org.xmlunit.matchers.CompareMatcher;

public class StepDefinitions {
  private String progname = "";
  private String progargs = "";
  private String xmlString = "";
  private String output = "";

  @Given("the program {string} has started with arguments {string}")
  public void the_program_has_started_with_arguments(String progname, String progargs) {
    this.progname = progname;
    this.progargs = progargs;
    xmlString = "";
    output = "";
  }

  @Given("the program {string} has started with arguments {string} and XML")
  public void the_program_has_started_with_arguments_and_XML_multiline(
      String progname, String progargs, String docString) {
    this.progname = progname;
    this.progargs = progargs;
    xmlString = docString;
    output = "";
  }

  @When("the user views the terminal")
  public void the_user_views_the_terminal() {
    PrintStream originalOut = System.out;
    try {
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      try {
        System.setOut(new PrintStream(outContent, false, "UTF-8"));
        try {
          int startArgs = 1;
          if (xmlString.length() > 0) {
            startArgs = 2;
          }
          String[] pargs = new String[0];
          if (progargs.length() > 0) {
            pargs = progargs.split(" ");
          }
          String[] args = new String[pargs.length + startArgs];
          args[0] = progname;
          if (xmlString.length() > 0) {
            args[1] = xmlString;
          }
          for (int i = 0; i < pargs.length; i++) {
            args[startArgs + i] = pargs[i];
          }
          Main.main(args);
        } catch (NoSuchElementException e) {
        }
        output = outContent.toString("UTF-8");
      } catch (UnsupportedEncodingException e) {
      }
    } finally {
      System.setOut(originalOut);
    }
  }

  @Then("the output should be {string}")
  public void the_output_should_be(String expectedOutput) {
    String s = output.trim();
    assertThat(s, containsString(expectedOutput));
  }

  @Then("the output should be")
  public void the_output_should_be_multiline(String docString) {
    String s = output.trim();
    assertThat(s, containsString(docString.trim()));
  }

  @Then("the file {string} should exist and contain")
  public void the_file_should_exist_and_contain(String filename, String docString) {
    Path p = Paths.get(filename);
    assertTrue(p.toFile().exists(), "The file " + filename + " does not exist.");
    try {
      String content = new String(Files.readAllBytes(p), Charset.forName("UTF-8"));
      assertThat(
          content,
          CompareMatcher.isSimilarTo(docString)
              .ignoreComments()
              .ignoreWhitespace()
              .normalizeWhitespace()
              .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndText)));
    } catch (IOException e) {
      assertTrue(false, "An IOException occurred: " + e);
    } finally {
      if (p.toFile().exists()) {
        if (!p.toFile().delete()) {
          System.out.println(
              "StepDefinitions#the_file_should_exist_and_contain could not remove the file "
                  + filename);
        }
      }
    }
  }
}
