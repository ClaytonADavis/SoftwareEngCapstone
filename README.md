

# WoCLO
This repository has been set up to get you started in completing your capstone project.

## Contents
  * [Project Overview](#project-overview)
    + [Prioritized Features](#prioritized-features)
    + [Grading Scheme](#grading-scheme)
  * [Project Details](#project-details)
    + [Definition of Done](#definition-of-done)
    + [Prioritized Features](#prioritized-features-1)
    + [Demonstration Programs](#demonstration-programs)
  * [Technical Details](#technical-details)
    + [Prerequisites](#prerequisites)
    + [Building the Project](#building-the-project)
    + [Directory Structure](#directory-structure)
    + [IDE Setup](#ide-setup)
      - [Intellij](#intellij)
      - [Eclipse](#eclipse)


## Project Overview
-------------------
In this project, you will be creating both a library for parsing command-line options/arguments and several demonstration programs that use the libary.


### Prioritized Features
The core features for this project are as follows and will be explained in detail below:

1. Allow only string-valued positional arguments and retrieve them from the command-line.
2. Allow the inclusion of additional descriptive information on the program and each argument and provide named "-h"/"--help" argument that shows usage and help information by default.
3. Allow datatype information to be added to arguments so that non-string arguments can be used.
4. Allow named arguments with single values, which may have help and datatype information, after all positional arguments.
5. Allow named arguments to be mixed with positional arguments in any order.
6. Allow named arguments to serve as flags (true if present).
7. Allow short-form names for named arguments, in addition to long-form names.
8. Allow arguments to have a restricted set of possible choices for their values.
9. Allow argument information to be loaded from an XML file.
10. Allow argument information to be saved to an XML file.
11. Allow named arguments to be specified as required arguments.
12. Allow named arguments to be grouped into mutually exclusive groups.
13. Allow variable numbers of argument values to be specified by a single argument.



### Grading Scheme
This project is worth 50 points (50% of the total grade for the course). These points are awarded in two ways. First, each of the 4 sprints end in a sprint review, at which point your team's work is evaluated. Each sprint is worth a maximum of 10 points for each member of the team. (Except in highly unusual circumstances, all members receive the same grade for a sprint.) Second, the final 10 points are determined according to how many features you complete by the project's final deadline. That scale is as follows, determined by the **final feature** that your team completes:

| Feature | Points |
|---------|--------|
| 1       | 0      |
| 2       | 0      |
| 3       | 0      |
| 4       | 1      |
| 5       | 2      |
| 6       | 3      |
| 7       | 5      |
| 8       | 7      |
| 9       | 9      |
| 10      | 10     |
| 11      | 11     |
| 12      | 12     |
| 13      | 13     |

Note that it is possible to receive more than 10 points for this component, with any additional points counting as "bonus" toward the final course grade.


## Project Details
------------------

### Definition of Done
The *definition of done* for **any** feature is that it passes all automated acceptance tests, has a codebase that achieves at least 85% branch coverage from its unit tests, and has a fully documented API.


### Prioritized Features

#### Feature 1
Allow only string-valued positional arguments and retrieve them from the command-line.

You should implement an argument parser library that is able to do the following:
  
1. add identifiers to represent arguments by position on the command-line
2. take the strings from the command-line and associate them with their identifiers
3. use the identifiers to get their associated values

Circumstances that require special care are 
   * if there are more identifiers than arguments to be associated
   * if there are more arguments than chosen identifiers


#### Feature 2
Allow the inclusion of additional descriptive information on the program and each argument and provide named "-h"/"--help" argument that shows usage and help information by default.

The argument parser library must now allow the client to add information about each identifier, in particular the type (always string for now) and description of what the argument/identifier represents.
  
Additionally, without the client doing anything extra, the parser must allow for and detect command-line arguments "-h" or "--help". If it sees either of those two arguments during its parsing, it immediately halts and simply produces a help/usage message.


#### Feature 3
Allow datatype information to be added to arguments so that non-string arguments can be used.

The argument parser library should allow clients to add identifiers that can represent non-string values as well. In particular, the library should support strings, integers, and floats. The general use of the library by a client would proceed as follows:
  
1. add identifiers to represent arguments by position on the command-line, specifying their datatype and description (for the help/usage message)
2. take the strings from the command-line and associate them with their identifiers according to the position
3. use the identifiers to get their associated values and allow the client to pull the value of the specified type, rather than always as a string

New circumstances that require special care are
   * if the associated value cannot be converted to its designated type
  
All previous functionality should persist (e.g., help/usage information).


#### Feature 4
Allow named arguments with single values, which may have help and datatype information, after all positional arguments.

The argument parser library should allow clients to add (optional) non-positional identifiers that (for now) must come after all positional arguments when entered on the command-line. These named arguments have the same properties as positionals (identifier, datatype, description), but because they are optional, they also must have a default value, which will be used as the value whenever it is not specified on the command-line.
  
Since they are not positional, named arguments must be given as two distinct parts, side by side. The first is the identifier, which must always be preceded by two hyphens. The second is the value that should be associated with that named argument. So, if the named argument's identifier were "myarg1" and was of type integer, then it might be listed on the command line as

	--myarg1 17

so that the value of the "myarg1" identifier would be set to 17.

Suppose the client had a program called "MyProg" that expected three integer positional arguments (say "x", "y", and "z"), along with two named arguments; one of type string with identifier "myarg1" and default value "foo", and another of type integer with identifier "myarg2" and default value 42. In that case, the following calls would all be legal:
  
	java MyProg 5 2 8                            parsing to x=5,y=2,z=8,myarg1="foo",myarg2=42
	java MyProg 5 2 8 --myarg1 bar               parsing to x=5,y=2,z=8,myarg1="bar",myarg2=42
	java MyProg 5 2 8 --myarg2 17                parsing to x=5,y=2,z=8,myarg1="foo",myarg2=17
	java MyProg 5 2 8 --myarg2 17 --myarg1 bar   parsing to x=5,y=2,z=8,myarg1="bar",myarg2=17

New circumstances that require special care are
   * if a given named argument on the command-line was not specified by the client
   * if a named argument's associated value is not of the appropriate type
   * if a named argument is not followed by its corresponding value
  
All previous functionality should persist (e.g., help/usage information). Additionally, any named arguments should be listed in the usage in square brackets, along with their name again in all-caps to represent the value to be given. The default values should be listed after the description. See the acceptance test cases for examples.


#### Feature 5
Allow named arguments to be mixed with positional arguments in any order.

Rather than placing all named arguments at the end (after all positional arguments), the parsing library should allow them to be placed anywhere in the argument list, including at the very beginning.


#### Feature 6
Allow named arguments to serve as flags (true if present).

Any boolean-valued named argument is called a "flag". Flag arguments never take values (e.g., "--myarg false"). If the argument is present, that implies that it has taken the value of true (e.g., "--myarg"). If it is not present, then it has the default value of false. In fact, we have been using this concept implicitly already for the help/usage arguments "-h" and "--help". If either is present, then "show help/usage" is true. (Note, however, that the  "help flag" is more powerful than typical flags, because it is provided by default and takes precedence over everything else when being parsed.)

The argument parser library should allow support for flags (so in some sense the datatype boolean has been added to the list of types for named arguments), and that should be the only allowed use of boolean named arguments.

Flag arguments do not display datatype information or default values in the help/usage information. For instance, a flag argument "myarg" might have the following help/usage (contrasted with a "num" integer named argument):
  
	--myarg         some description here
	--num NUM       (integer) a number (default: 0)
  


#### Feature 7
Allow short-form names for named arguments, in addition to long-form names.

The short-form name of an argument is a one-character abbreviated name. Whenever entered in the command-line, short-form names always begin with a hyphen (instead of a double-hyphen for long-form names). Short-form flags (because they have no associated values) can also be combined in a single specification. For instance, "-d -a -p" is equivalent to "-dap". Notice that the combination "-dap" is different from a long-form argument called "--dap". Both of those should be possible (albeit confusing).

This short-form name should not be auto-generated from the long-form name; it should be specified by the user. For instance, if a long-form named argument is `digits`, you should not assume that the short-form name will be "d". This is because long-form names may share the same first letters (e.g., "precision" and "print").

Now that the short-form names are available, the "h" help argument should be treated as such (since that is really what it is). Also, it should be an error for the user to try to name another short-form argument "h" (taken by default to be "help").

Finally, the help/usage information should include the short-form names of any argument, and the actual usage line should prefer the short-form name over the long-form name if it exists. See the acceptance tests for examples of this.


#### Feature 8
Allow arguments to have a restricted set of possible choices for their values.

Sometimes an argument can only attain certain values from a discrete set. Those allowable values should be specified by the client. Providing any value for the argument that is not in the set should throw an informative exception.

For instance, assume VolumeCalculator.java allows for three positional arguments, named "length", "width", and "height", respectively, each representing a float. Assume that it also allows an optional named argument called "type" representing a string that has values that are restricted to the set ("box", "ellipsoid", "pyramid").

	java VolumeCalculator 7 --type ellipsoid 3 2

  should produce correct output, while

	java VolumeCalculator 7 3 --type frustum 2
    
should throw an exception explaining that "frustum" was not an allowed value for "type".
  
These restricted values can be on either positional or named arguments and can be of any of the four types (however, boolean restrictions make very little sense).


#### Feature 9
Allow argument information to be loaded from an XML file.

While specifying command-line arguments in code is fine, it becomes tedious and error-prone whenever multiple programs have exactly the same argument lists. In such cases, it would be easier to pull the argument lists directly from a file. 

The XML specification provides a human-readable, structured format for storing textual data. The XML file should store all of the argument information, including whether an argument is positional or named, long-form names, short-form names, datatypes, default values, restrictions, etc.

Assume a program allows for three positional arguments, named "length", "width", and "height", respectively, each representing float values. Assume that it also allows two optional named arguments: "type", a string that defaults to "box" with short-form "t" and restricted to "box", "pyramid", and "ellipsoid", and "precision", an integer that defaults to 4 with short-form "p".

The following XML file could represent that argument list:

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
          <restrictions>
            <restriction>box</restriction>
            <restriction>pyramid</restriction>
            <restriction>ellipsoid</restriction>
          </restrictions>
          <default>
            <value>box</value>
          </default>
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


Add functionality to the argument parser library to allow reading argument info from an XML file structured as described above. The library should ensure that the given XML file **validates** according to a schema that accurately represents its components and their relationships and restrictions.



#### Feature 10
Allow argument information to be saved to an XML file.

If an argument list has already been implemented in Java code, it would be helpful to be able to save the list information to a file for use later. Since the functionality already exists to read that information from an XML file, XML provides an excellent format for this data. The XML file should store all of the argument information (including long-form names, short-form names, datatypes, default values, etc.), just as in the case of reading the argument information from an XML file.

Add functionality to the argument parser library to allow writing argument info to an XML file structured as before.



#### Feature 11
Allow named arguments to be specified as required arguments.

Until now, named arguments have been treated as optional. There are times, however, when named arguments should be required. In this case, their default values will not matter because failure to include the argument/value pair will throw an exception just like any other missing argument.

The XML should now include (only if a named argument is required) the following tag:
  
	<required/>

For instance, the following would be valid (only a subset of the full XML):
  
	<named>
		<default>
			<value>4</value>
		</default>
		<type>integer</type>
		<description>the maximum number of decimal places for the volume</description>
		<required/>
		<name>precision</name>
		<shortname>p</shortname>
	</named>
  
If a named argument is required, the help/usage documentation should show it without the square brackets. For instance, for the VolumeCalculator example above with the precision required, the initial usage line would have
  
	-p PRECISION
  
instead of
  
	[-p PRECISION]
  
Additionally, the default value should not appear in the help/usage, since the true value must be provided. 


#### Feature 12
Allow named arguments to be grouped into mutually exclusive groups.

Occasionally, there are distinct groups of named arguments that do not make sense when they are combined. For instance, you may have a named flag "quiet" that causes the program to produce no console output, and you may also have a named argument "precision" that specifies the decimal precision that values should be printed to the console. Clearly, these two arguments are mutually exclusive, and they should be treated as such. 

You should assume that if there is more than one group of mutually exclusive arguments, then it should be illegal to use more than one argument from each such group.

The XML format should accommodate this new feature as follows:

    <arguments>
      <positionalArgs>
        ...
      </positionalArgs>
      <namedArgs>
        ...
      </namedArgs>
      <mutuallyExclusive>
        <group>
          <name>foo</name>
          <name>bar</name>
        </group>
        <group>
          <name>alpha</name>
          <name>beta</name>
          <name>gamma</name>
        </group>
      </mutuallyExclusive>
    </arguments>
    
Here, the "mutuallyExclusive" block may be absent. If it is present, then it must contain at least one "group" block. Also, any "group" block must contain at least two "name" elements. Adding this new component to the XML format should not interfere with any previous feature.



#### Feature 13
Allow variable numbers of argument values to be specified by a single argument.

Until now, each argument has had at most one value associated with it. Sometimes, however, it is useful to allow a single argument to have multiple values. In this case, the client must specify exactly how many values should be associated with a particular argument. This should only work for non-flags.
  
Whenever parsing a multi-valued argument, nothing can come between the values. For instance, no optional named argument can separate value 2 and value 3. All values for a single argument must be contiguous.

The XML format should accommodate this new feature as follows:

    <arguments>
      <positionalArgs>
        ...
        <positional>
          <name>numbers</name>
          <type>integer</type>
          <arity>3</arity>
        </positional>
        ...
      </positionalArgs>
      <namedArgs>
        ...
        <named>
          <name>options</name>
          <type>string</type>
          <default>
            <value>spam</value>
            <value>eggs</value>
          </default>
        </named>
        ...
      </namedArgs>
      <mutuallyExclusive>
        ...
      </mutuallyExclusive>
    </arguments>
    
Here, a positional argument must have its "arity" set manually in the XML. However, a named argument has its "arity" set automatically based on its default value dimension. Adding this new component to the XML format should not interfere with any previous feature.



### Demonstration Programs

#### Equivalent Strings (Feature 1)
This program determines if two strings are "equivalent". Equivalent means one
string can be transformed into the other by substituting one letter for another
consistently.
    
For example, consider the two strings "cocoon" and "zyzyyx". These would be
considered equivalent because: c -> z, o -> y, n -> x. Each letter in one
string maps to one and only one letter in the second string and vice versa.
Note that just because c -> z in one direction (from "cocoon" to "zyzyyx")
does not mean that z -> c (if there were a "z" in "cocoon", it would not
necessarily have to map to "c").

On the other hand, the two strings "banana" and "orange" are not equivalent.
This is because 'a' in banana would have to map to 'r', 'n', and 'e' in
orange. There is no one-to-one substitution that works. Based on this
definition of "equivalent", strings of different lengths cannot be equivalent.
    
This demonstration program should accept exactly two string arguments from the
command line, and it should print to standard output either "equivalent" or
"not equivalent", depending on whether the two strings are equivalent.

#### Overlapping Rectangles (Feature 3)
This program is given two rectangles that overlap and determines the total area of the two rectangles and the shared area of the two rectangles.
  
A rectangle can be defined by the x and y coordinates of its lower left corner and its upper right corner. The program will accept these integer coordinates via the command-line (8 integers in total; x1 y1 x2 y2 x3 y3 x4 y4, where the first rectangle is defined by lower left (x1, y1) and upper right (x2, y2) and the second rectangle is defined by lower left (x3, y3) and upper right (x4, y4).

The rectangles will intersect (overlap) in some way. For example, given the rectangles defined by rectangle 1 (-3, -0) and (3, 4) and rectangle 2 (0, -1) and (9, 2). (You should draw this on graph paper to see the overlap.)

The area of a rectangle equals its width times its length. The area of rectangle 1 on the left is 24 (6 times 4). The area of rectangle 2 is 27 (9 times 3). The area of the overlap section is 6. This means the total area of the rectangles is 45 (24 + 27 – 6).

As another example, consider the rectangles defined by rectangle 1 (0, 0) and (3, 3) and rectangle 2 (2, 2) and (4, 4). The area of rectangle 1 is 9 (3 times 3). The area of rectangle 2 is 4 (2 times 2). The area of the overlap section is 1. This means the total area of the rectangles is 12 (9 + 4 – 1).

The rectangles will always overlap in some way. The rectangles will never be the same rectangle.

The program should output two integers (overlap and total), separated by a space, representing the area of the overlap and area of the total. For instance, for the first example above, the output should be "6 45".

#### Word Search (Feature 4)
This program allows a user to find a word (if it exists) in a grid of letters. The command-line arguments to the program will be two strings, the grid and the word to find, in that order. The default size for the grid should be 5x5 (so 25 letters in the first string argument). However, named arguments for "width" and "height" can be given to change the grid dimensions. The output of the program should be either the grid locations of each of the letters (format specified below) or a statement that the word is not found (once again, particular format is below).

The objective of this demo program is to locate words hidden in a grid of letters. The word might be written in a horizontal or vertical orientation. It might be written from bottom to top (upside down) or left to right (backwards). Words can also be written with changes in direction. A word that starts out  being written from top to bottom might take a turn in the middle and continue left to right.

For example, if you are searching for the word "halifax", it might be written like this:

	h
	a
	l
	i f a x

If you are looking for "yellow", it might appear like this:

	w
	o l
	  l
	  e y

Here is an example of a word search grid.

	s	o	f	t	s
	w	e	s	k	a
	o	l	z	i	l
	k	l	q	m	t
	r	e	y	o	y

Suppose you want to find the word "eskimo".

It can be found beginning at location Row 2, Column 2 and the path is expressed as follows:

	e:2,2 s:2,3 k:2,4 i:3,4 m:4,4 o:5,4

Words will always be contiguous in the grid, and the turns must be 90 degrees. Using the sample grid above, the word "salty" is:

	s:1,5 a:2,5 l:3,5 t:4,5 y:5,5

(Note that the grid locations are given as Row/Column where the first Row/Column is numbered 1 rather than 0.)

All letters will be lower case as will the words to be searched for. The same letter may appear more than once but correct combinations of letters will only appear once. For example, if the hidden word is "eskimo" the letters 'e' and 's' may appear many times individually but will only appear "together" once.

If a word does not appear in the grid, then the output should be 

	"<word> not found"

where `<word>` corresponds to the target word.


#### Maximal Layers (Feature 6)
This program calculates boundary regions of points in the plane.

Consider a group of points. Each point is made of an x and y coordinate. A point from that group is considered maximal if there is no other point in the group that is "north" and "east" of it. Consider the following points:

	5,5  4,9  10,2  2,3  15,7

We can identify the points (4,9) and (15,7) as maximal. Mathematically this is true because there are no other points that have a larger x coordinate AND a larger y coordinate.

For example, point (4,9) is maximal because there is no other point "north" of it (i.e there is no other point with a larger y value). Point (15,7) is maximal because there is no other point "east" of it (i.e. there is no other point with a larger x value). These maximal points (A and B) can be joined to form a maximal layer (Layer 1) as shown below.

	10|
	9 |--------A
	8 |        | 
	7 |        +-------------------B
	6 |                            |
	5 |        o                   |
	4 |                            |
	3 |  o                         |
	2 |                  o         |
	1 |_ _ _ _ _ _ _ _ _ _ _ _ _ _ | 
	0  1 2 3 4 5 6 7 8 9 1 1 1 1 1 1
	                     0 1 2 3 4 5


Additional maximal layers can be created in the same manner by using the rule:
* Each layer consists of the points that are maximal excluding the points in
  the previous layer.
  
This results in 3 layers for this group of points as shown below.

	10|
	9 |--------A
	8 |        | 
	7 |        +-------------------B
	6 |                            |
	5 |--------C                   |
	4 |        |                   |
	3 |--E     |                   |
	2 |  |     +---------D         |
	1 |_ | _ _ _ _ _ _ _ | _ _ _ _ | 
	0  1 2 3 4 5 6 7 8 9 1 1 1 1 1 1
	                     0 1 2 3 4 5

The program will be given a group of points via the command-line. It must identify the points in each layer as shown in the sample output below. The input will a single comma-separated string with the x and y coordinates for at least 1 and up to 15 points. The coordinates will be integer values between 1 and 20. Some points may have the same x coordinate and some points may have the same y coordinate but there will be no duplicate points. That is, no points will have the same x AND y coordinates. 

The input for the example above would be

	5,5,4,9,10,2,2,3,15,7

It should also accept flags for whether the output layers should be sorted by their x coordinates (--sortedX) or their y coordinates (--sortedY). If both flags are set, then it should sort first by y and then by x (so that the final result is sorted by x first, with duplicate x values sorted by y). If neither flag is set, then it should keep the points in the same relative order as the input.


#### Tiling Assistant (Feature 7)
This program calculates the number of tiles needed to tile a room of a specified size. It should accept two required float arguments for the length and width of the room. All rooms are rectangular (and may be square). The size of the tile is optional (with longname "tilesize" and shortname "s") and defaults to 6.0. (It is always a square.) The "grout gap" is also optional and defaults to 0.5 (with longname "groutgap" and shortname "g"). Each tile that is next to another tile must have a small space for the tile grout (the grout gap) between them. All values are floats, must be greater than 0, and are in inches.

The program should also accept two optional flags, "metric"/"m" and "fullonly"/"f". The first changes the units from inches to centimeters. The second only prints the full tiles (no reduced sides or corner tiles). 

It is standard procedure to have partial tiles "split" so there is equal spacing on each opposite side. For example, assume a tile is 4 inches wide and the floor length used 50 tiles with 3 inches left over. These 3 inches must be divided so there are 1.5 inches of tile on one side and 1.5 inches of tile on the opposite side. Partial tiles will exist only on the outside (or perimeter) of the floor. Partial tiles, if they exist, may be shortened lengthwise or widthwise, or both. Only tiles in the corner will be reduced in both length
and width. There will be either 4 reduced size corner tiles or 0 reduced size corner tiles.

Each tile (full or partial) that is next to another tile must have a small space for the tile grout (the grout gap). Tiles that are next to the wall do not have a grout gap.

The program should output the number of full tiles (followed by their dimensions), the number of reduced length tiles, if any (followed by their dimensions), the number of reduced width tiles, if any (followed by their dimensions), and the number of corner tiles, if any (followed by their dimensions). 

For instance, the following would be a possible output:

	"20:(4.75 x 4.75 in) 8:(1.625 x 4.75 in) 10:(4.75 x 0.875 in) 4:(1.625 x 0.875 in)"


#### Heated Field (Feature 8)
This program calculates the spread of heat through a grid from the edges to the interior. A football team in a typically cold climate has a heated field to keep it from freezing in cold temperatures. Under the grass is a grid of metal. A heat source is applied to the edges (east edge, west edge, south edge, and north edge). The heat source can apply a different constant temperature to each edge. The heat from the edges eventually is transmitted throughout the "grid" heating the entire field. We can think of this as a 10 x 10 grid as shown below.

	  +---+---+---+---+---+---+---+---+---+---+
	  |   | N | N | N | N | N | N | N | N |   |  
	  +---+---+---+---+---+---+---+---+---+---+
	  | W |   |   |   |   |   |   |   |   | E |  
	  +---+---+---+---+---+---+---+---+---+---+
	  | W |   |   |   |   |   |   |   |   | E |  
	  +---+---+---+---+---+---+---+---+---+---+
	  | W |   |   |   |   |   |   |   |   | E |  
	  +---+---+---+---+---+---+---+---+---+---+
	  | W |   |   |   |   |   |   |   |   | E |  
	  +---+---+---+---+---+---+---+---+---+---+
	  | W |   |   |   |   |   |   |   |   | E |  
	  +---+---+---+---+---+---+---+---+---+---+
	  | W |   |   |   |   |   |   |   |   | E |  
	  +---+---+---+---+---+---+---+---+---+---+
	  | W |   |   |   |   |   |   |   |   | E |  
	  +---+---+---+---+---+---+---+---+---+---+
	  | W |   |   |   |   |   |   |   |   | E |  
	  +---+---+---+---+---+---+---+---+---+---+
	  |   | S | S | S | S | S | S | S | S |   |  
	  +---+---+---+---+---+---+---+---+---+---+

Heat is only applied to the 8 "cells" that are labeled on each edge. Heat is never applied to the corner cells. The same, constant heat is applied to each cell on a side. For example, if a heat source of 98 degrees is applied to the west side, all 8 cells on the west side have a constant heat source of 98 degrees. However, each side is controlled independently so if the west side has a constant heat source of 98 degrees the east, north, and south sides could each have a constant heat source with a different temperature. Once set, the heat source for a side doesn't change.

Each interior cell has an initial temperature and this initial temperature is the same for all interior cells. Every minute the temperature of the interior cells change based on the heat applied to the edges and the temperature of the surrounding cells. The temperature of any interior cell becomes the average of its four surrounding cells (orthogonal neighbors only; not diagonals). 

Once the system has run the required number of minutes, the groundskeeper wants to be able to determine the temperature of any interior cell.

The program should accept the following positional arguments:
  * temperature applied to north edge (float)
  * temperature applied to south edge (float)
  * temperature applied to east edge (float)
  * temperature applied to west edge (float)
  * x coordinate of interior cell to report (integer, restricted to 1-8)
  * y coordinate of interior cell to report (integer, restricted to 1-8)

Additionally, it should accept two optional, named arguments:
  * initial temperature (float) of the interior cells, "temperature"/"t" (defaults to 32 degrees)
  * number of minutes (integer) to run the system, "minutes"/"m" (defaults to 10)

Temperatures could be negative. The minutes will always be positive. X runs from west to east and Y runs from north to south. The program should then print the resulting temperature at that cell. For instance,

	cell (5, 4) is 19.05 degrees Fahrenheit after 2 minutes


#### Volume Calculator (Feature 9)
This program allows for three positional arguments, named "length", "width", and "height", respectively, each representing float values. It also allows two optional named arguments: "type", a string that defaults to "box" with short-form "t" and restricted to "box", "pyramid", and "ellipsoid", and "precision", an integer that defaults to 4 with short-form "p".

However, its first argument is always a string representing the contents of an XML file. This XML file string determines the particular configuration of the parser. The remaining   arguments will be what should be parsed as normal.

The following XML file would represent that argument list:

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
	        <restrictions>
	          <restriction>box</restriction>
	          <restriction>pyramid</restriction>
	          <restriction>ellipsoid</restriction>
	        </restrictions>
	        <default>
	          <value>box</value>
	        </default>
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

The output of the program should be the single numeric value of the volume.

#### Distance Calculator (Feature 13)
This program should take a single positional argument that represents 3 integer values (for a point in 3D space). It should also take an optional named argument "from" that represents 3 integer values (another point in 3D space) for the source point. This source point should default to the origin (0, 0, 0) if unspecified. It should also accept an optional named argument called "measure" that is restricted to the strings "euclidean" or "manhattan", defaulting to "euclidean". This argument determines the distance calculation to be used. Finally, it should accept an optional named argument called "dimensions" that represents 3 integers restricted to either 0 or 1. If the dimension value is 0, then that dimension should be left out of the distance calculation. The default value for "dimensions" should be (1, 1, 1). Consult the "help/usage" test below for specifics on the names/types/defaults of each.

The program should output the distance from the source to the destination point, using the specified measure and dimensions, rounded to 3 decimal places.



## Technical Details
--------------------
### Prerequisites
This project assumes that you have already [installed Java 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) on your system. 

### Structure
This project actually includes two subprojects---`lib` and `demos`. The `lib` subproject contains the library for parsing command-line options. The `demos` subproject contains the demonstration programs that must be implemented for the acceptance tests to showcase the library functionality.

### Building the Project
After you have cloned the repository, you should be able to navigate to the root directory containing the **gradle.build** file. There, you can build the project by running the command

`gradlew build`

Then, you can run the unit test coverage report.

`gradlew jacocoTestReport`

Then, you can run the acceptance tests. 

`gradlew cucumber`

You can even do multiple things in one statement:

`gradlew build jacocoTestReport cucumber`

When you want to get rid of all of the temporary files (like compiled class files and such), you can say

`gradlew clean`

If you want to do a full build and reporting from a clean project, you can just string it all together:

`gradlew clean build jacocoTestReport cucumber`

If you want to create the generated documentation (based on your Javadoc comments), you can say

`gradlew javadoc`



### Directory Structure
The directory structure that is assumed by Gradle (it can be changed if you want, but that requires changes to the Gradle build file) is as follows:

    project root  (root directory of project)
               |
                - build.gradle  (contains the instructions for the build tasks)
               |
                - demos  (root directory of the source code for the lib subproject)
               |      |
               |       - build.gradle  (build file for the subproject)
               |      |
               |       - src
               |           |
               |            - main  (root directory of normal source code)
               |           |     |
               |           |      - java  (all packages go here)
               |           |     |     |
               |           |     |      - edu    
               |           |     |          |
               |           |     |           - wofford
               |           |     |                   |
               |           |     |                    - woclo  (source code goes here)
               |           |     |
               |           |      - resources (contains a configuration file for cucumber)                               
               |           |
               |            - test  (root directory of unit test code)
               |                 |
               |                  - java  (all packages go here)
               |                 |     |
               |                 |      - demos  (unit test source code goes here)
               |                 |     |
               |                 |      - gradle
               |                 |             |
               |                 |              - cucumber  (cucumber step definition source code goes here)
               |                 |
               |                  - resources
               |                            |
               |                            - gradle
               |                                   |
               |                                    - cucumber  (cucumber feature files go here)
               |                              
                - gradlew  (the Unix Gradle script)
               |
                - gradlew.bat  (the Windows Gradle script)
               |
                - gradle  (all configuration and scripts for the Gradle wrapper go here)
               |
                - lib  (root directory of the source code for the lib subproject)
               |    |
               |     - build.gradle  (build file for the subproject)
               |    |
               |     - src 
               |         |
               |          - main  (root directory of normal source code)
               |         |     |
               |         |      - java  (all packages go here)
               |         |     |     |
               |         |     |      - edu    
               |         |     |          |
               |         |     |           - wofford
               |         |     |                   |
               |         |     |                    - woclo  (source code goes here)
               |         |     |
               |         |      - resources  (any additional resources go here)                     
               |         |
               |          - test  (root directory of unit test code)
               |               |
               |                - java  (all packages go here)
               |               |     |
               |               |      - edu    
               |               |          |
               |               |           - wofford
               |               |                   |
               |               |                    - woclo  (unit test source code goes here)
               |               |      
               |                - resources  (any test resources go here)
               |
                - README.md  (this file)
               | 
                - settings.gradle  (project settings)


After you run `gradlew build`, new **lib/build** and **demos/build** directories will automatically be created. These will contain all of the generated files (compiled class files, JAR files, reports, etc.). The most important things here are as follows:

**lib/build/reports/tests/test/index.html**
: This file holds the results of all of the unit tests.

**demos/build/libs/demos-1.0-all.jar**
: This file is the fully bundled code for the demo programs. You can run this by saying
  `java -jar demos/build/libs/demos-1.0-all.jar` from the project root.

After you run `gradlew cucumber`, a **reports/cucumber** directory will be created in the **demos/build** directory. This will contain the reports for the acceptance tests.

**demos/build/reports/cucumber/index.html**
: This file holds the Cucumber acceptance test results.

After you run `gradlew jacocoTestReport`, a **reports/jacoco/test/html** directory will be created in the **lib/build** directory. This will contain the reports for the Jacoco code coverage.
  
**lib/build/reports/jacoco/test/html/index.html**
: This file holds the unit test code coverage results from JaCoCo.

After you run `gradlew javadoc`, a **docs** directory will be created within the **lib/build** directory. This will contain all of the generated Javadoc documentation for your source files.  

**lib/build/docs/javadoc/index.html**
: This file is the index to the generated documentation.


### IDE Setup

#### Intellij
If you use Intellij, you can set up gradle for it as follows:

`gradlew cleanIdea idea`


#### Eclipse
If you use Eclipse, you can set up gradle for it as follows:

`gradlew cleanEclipse eclipse`


