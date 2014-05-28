jarversion
==========

Get min/max (major) version numbers from compiled class files in a jars.

For each jar given as argument print

    file.jar <min> <max>

where min is the minimum number version number of any class file in the jar.
For jars without class files, no output is printed.

Example

    $ jarversion file1.jar file2.jar
    file1.jar 50 50
    file2.jar 51 51

Major Number to Java version

- 49: Java 1.5
- 50: Java 1.6
- 51: Java 1.7
