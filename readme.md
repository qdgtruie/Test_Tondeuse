First attempt to Tondeuse.

### Required configuration ###
* JDK 11 (tested on amazon-corretto-11.jdk)
* Gradle 5.4.1
* Git

### Technologies ####
* Functional programming: [Java 11](https://docs.aws.amazon.com/fr_fr/corretto/latest/corretto-11-ug/downloads-list.html), [Vavr](http://www.vavr.io/)
* Tests: [JUnit](https://junit.org/junit5/), [jqwik](https://jqwik.net/) [AssertJ](http://joel-costigliola.github.io/assertj/index.html), [Mockito](http://mockito.org/)
* Behavior Driven Development: [JBehave](http://jbehave.org/)
* Code generation: [Lombok](https://projectlombok.org)
* UML: [PlantUML](http://plantuml.com)

### Running from your IDE ###

* The **ConsoleApp** class
* The automated unit testing class **ConsoleAppTest**
* The JUnitStory **AutoMowerStories** (*acceptance testing*)

### Compilation ###
```
./gradlew install
```

### Running ###
```
java -jar target/automower-1.0.0-SNAPSHOT.jar [file]
```

### The algorithm description ###
1. Parsing of the input file
2. Creation of instructions list and needed objects in memory
3. Execution of instructions



## Specifications ##

### Goal ###

Build a java 11 program that implement the following mower’s specification.

### The Story ###

<-- tell the story /-->

### Example​ ###

input file
```
5 5
1 2 N
LFLFLFLFF
3 3 E
FFRFFRFRRF
```

result
```
1 3 N
5 1 E
```
