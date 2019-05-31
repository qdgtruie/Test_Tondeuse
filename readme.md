First attempt to Tondeuse.

### Required configuration ###
* JDK 11 (tested on amazon-corretto-11.jdk)
* Gradle 5.4.1
* Git

![Build status](https://travis-ci.com/qdgtruie/Test_Tondeuse.svg?branch=master)

### Technologies ####
* Language programming: [Java 11](https://docs.aws.amazon.com/fr_fr/corretto/latest/corretto-11-ug/downloads-list.html), [Vavr](http://www.vavr.io/)
* Tests: [JUnit](https://junit.org/junit5/), [jqwik](https://jqwik.net/), [AssertJ](http://joel-costigliola.github.io/assertj/index.html), [Mockito](http://mockito.org/)
* Behavior Driven Development: [JBehave](http://jbehave.org/)
* Code generation: [Lombok](https://projectlombok.org)
* UML: [PlantUML](http://plantuml.com)
* Code analysis: [sonarcloud](https://sonarcloud.io)
* Packaging : [Docker]()
* Run : [Kubernetes]()

### Running from your IDE ###

* The **ConsoleApp** class
* The automated unit testing class **ConsoleAppTest**
* The JUnitStory **AutoMowerStories** (*acceptance testing*)

### Compilation ###
```
./gradlew bootJar
```

### Running as Java Application ###
```
./gradlew bootRun
```
### Running as docker container ###
```
docker build -t tondeuse
docker run --rm tondeuse -p8080:8080
```

### Overall apporach  ###

1. Parsing of the input file
2. Creation of instructions list and needed objects in memory
3. Execution of instructions
4. run CI/CD

##### Model description #####
![Class diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/qdgtruie/Test_Tondeuse/master/src/main/resources/diagrams/classDiagram-configuration.puml)

##### Sequence description #####
![Sequence diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/qdgtruie/Test_Tondeuse/master/src/main/resources/diagrams/classDiagram-configuration.puml)

##### CI/CD Sequence description #####
1. Use of Travis to build, run property based testing and run sonarcloud
2. Package as a Docker Image
3. Push image into a Kubernetes instance

## Specifications ##

### Goal ###

Build a java 11 program that implement the following mower’s specification.

### The Story ###

The company MownItNow wants to develop an automower for square surfaces.
The mower can be programmed to go throughout the whole surface. Mower's position is represented by coordinates (X,Y) and a characters indicate the orientation according to cardinal notations (N,E,W,S). The lawn is divided in grid to simplify navigation.
For example, the position can be 0,0,N, meaning the mower is in the lower left of the lawn, and oriented to the north.
To control the mower, we send a simple sequence of characters. Possibles characters are L,R,F. L and R turn the mower at 90° on the left or right without moving the mower. F means the mower move forward from one space in the direction in which it faces and without changing the orientation.
If the position after moving is outside the lawn, mower keep it's position. Retains its orientation and go to the next command.
We assume the position directly to the north of (X,Y) is (X,Y+1).
To program the mower, we can provide an input file constructed as follows:
The first line correspond to the coordinate of the upper right corner of the lawn. The bottom left corner is assumed as (0,0). The rest of the file can control multiple mowers deployed on the lawn. Each mower has 2 next lines :
The first line give mower's starting position and orientation as "X Y O". X and Y being the position and O the orientation.
The second line give instructions to the mower to go throughout the lawn. Instructions are characters without spaces.
Each mower move sequentially, meaning that the second mower moves only when the first has fully performed its series of instructions.
When a mower has finished, it give the final position and orientation.


### Example​ ###

Input file
```
5 5
1 2 N
LFLFLFLFF
3 3 E
FFRFFRFRRF
```

Expected result
```
1 3 N
5 1 E
```
