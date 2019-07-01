First attempt to Tondeuse.

### Required configuration ###
* JDK 11 (tested on amazon-corretto-11.jdk)
* Gradle 5.4.1
* Git

![Build status](https://travis-ci.com/qdgtruie/Test_Tondeuse.svg?branch=master)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=qdgtruie_Test_Tondeuse&metric=coverage)](https://sonarcloud.io/dashboard?id=qdgtruie_Test_Tondeuse)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=qdgtruie_Test_Tondeuse&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=qdgtruie_Test_Tondeuse)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=qdgtruie_Test_Tondeuse&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=qdgtruie_Test_Tondeuse)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=qdgtruie_Test_Tondeuse&metric=alert_status)](https://sonarcloud.io/dashboard?id=qdgtruie_Test_Tondeuse)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=qdgtruie_Test_Tondeuse&metric=sqale_index)](https://sonarcloud.io/dashboard?id=qdgtruie_Test_Tondeuse)


### Technologies ####
* Language programming: [Java 11/corretto](https://docs.aws.amazon.com/fr_fr/corretto/latest/corretto-11-ug/downloads-list.html), [Vavr](http://www.vavr.io/)
* UnitTests: [JUnit](https://junit.org/junit5/)
* Property based testing: [jqwik](https://jqwik.net/)
* Behavior Driven Development: [JBehave](http://jbehave.org/)
* Code generation: [Lombok](https://projectlombok.org)
* UML: [PlantUML](http://plantuml.com)
* CI : [Travis CI](https://travis-ci.com)
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
### Running within Kubernetes ###
Set ```.travis.yml``` config to point to your own kubernetes cluster, trigger a build and then :
```
curl  http://<service-public-ip>:8080
```

### Overall approach  ###

1. Parsing of the input file
2. Creation of instructions list and needed objects in memory
3. Execution of instructions
4. run CI/CD

##### Model description #####
![Class diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/qdgtruie/Test_Tondeuse/master/src/main/resources/diagrams/classDiagram-configuration.puml)

##### Activity description #####
![Activity diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/qdgtruie/Test_Tondeuse/master/src/main/resources/diagrams/activity.puml)

##### CI/CD Sequence description #####
1. Use of Travis to build, run property based testing and run sonarcloud
2. Package as a Docker Image
3. Push image into a GKE instance

### Opinionated approach ###
0. used the brief as a kind of (ubiquitus language) to design the domain model (leading to rich structure). 
1. ConfigurationProviders is the only part using interface based approach to ensure extensibility
2. Configuration domain model has high cohesion.
3. No heavy framework dependency (e.g. spring injection) at the business logic level (domain).
4. Exception are rewrapped in "business exception" and still capture the frames. 
Moving to "exception less" design through functional is another route. 
5. Code do need some comment. Self explanatory code is an aspirational and good thing, but in practice code do deserve some comments.
6. Using code generation with `lombok is OK. (Really, I am not joking)
7. Thread safety is not a focus for this exercise (structure immutability is not a concern).  

### Possible improvements ###
0. Add a `corretto-11` build as soon as Travis-CI community support it.
1. Make implementation more modular :
    - Deployables should be separated for domain, configuration providers, tests, and actual frontend (console, springboot, etc..)
    - Leveraging `module-info` along new deployable structure
2. Rework observability
    - log should be streamed and centralized in log management system
    - metrics should be exposed (JMX, actuator) at the engine level without creating dependency to heavy framework     
3. Move mowers in parallel through multithreading 
    - change `MownerController::runMowner` to trigger threads. Depending on concurrency level, fibers could be best suited.
    - leverage `Mowner::addPositionChecker` to check for collisions
    - collision checks should stop as soon as one collision is detected (instead iterating on all checkers)
    - manage state : either `MownerController` keeps track of all mowers or each `Mowner` registers with `addPositionChecker` on others.
4. Move to a more functional implementation
    - use of `vavr ` could be more consistent (currently only used for conveniance)
5. Move to an Actor based model (e.g. akka)

### Specifications ###

#### Goal ####

Build a java 11 program that implement the following mower’s specification.

#### The Story ####

The company MowItNow wants to develop an automower for square surfaces.
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


#### Example​ ####

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
