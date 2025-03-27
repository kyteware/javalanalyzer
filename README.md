# Javalanalyzer

My term project will be to build a tool that can parse java code and visualize it using various kinds of graphs. It will be able to take a project existing on a user's computer and generate package diagrams and class diagrams for it. It is intended to be used by developers who want to analyzer their code.

I am choosing this project because I am interested in programming languages and visualization, and this project incorporates both of those in an interesting and challenging way.

## User Stories

- [x] As a user, I want to be able to select a new Java project to analyze, specifying its path.
- [x] As a user, I want to be able to see a list of previously analyzed java projects.
- [x] As a user, I want to be able to remove a Java project from my list of previously visualized java projects.
- [x] PHASE 1 REQUIREMENT: As a user, I want to be able to load and reload the project files of a java project *meaning I can add and remove elements by editing the java project*.
- [x] PHASE 1 REQUIREMENT: As a user, I want to be able to see all of the files I've loaded from a java project.
- [x] As a user, I want to be able to generate a package diagram of my project.
- [x] PHASE 2 REQUIREMENT: As a user, I want to be able to save my list of recently accessed projects to a file.
- [x] PHASE 2 REQUIREMENT: As a user, I want to be able to load my previously saved accessed projects from a file.

## Instructions for End User

- You can generate the first required action related to the user story "load and reload the project files of a java project", displaying the lines in the graph, by pressing the "lines" button
-  You can generate the second required action related to the user story "load and reload the project files of a java project", coloring the package boxes blue, but pressing the "blue" button
- You can locate my visual component by adding a project and then pressing "open"
- You can save the state of my application by pressing the down arrow button on the top left
- You can reload the state of my application by pressing the up arrow button the the top left

## Phase 4: Task 2
```
Thu Mar 27 14:54:29 PDT 2025    Read the program state from JSON at path ./data/save.json
Thu Mar 27 14:54:30 PDT 2025    Loaded classes of Java project ./sample projects/ProjectOne cleared
Thu Mar 27 14:54:30 PDT 2025    Java code tokenized (8 tokens found)
Thu Mar 27 14:54:30 PDT 2025    Java file ./sample projects/ProjectOne/src/main/model/Class1.java loaded with 0 imports
Thu Mar 27 14:54:30 PDT 2025    Java code tokenized (8 tokens found)
Thu Mar 27 14:54:30 PDT 2025    Java file ./sample projects/ProjectOne/src/main/model/Class2.java loaded with 0 imports
Thu Mar 27 14:54:30 PDT 2025    New package diagram with 2 classes and 0 imports
dworv@framework:~/hacking-210/ProjectStarter$  cd /home/dworv/hacking-210/ProjectStarter ; /usr/bin/env /usr/lib/jvm/java-21-openjdk/bin/java @/tmp/cp_cmbrbxwadw8nar1j4ochftfly.argfile ui.MainGui 
Thu Mar 27 15:02:24 PDT 2025    Read the program state from JSON at path ./data/save.json
Thu Mar 27 15:02:26 PDT 2025    Loaded classes of Java project /home/dworv/hacking-210/ProjectStarter cleared
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (45 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/ui/Main.java loaded with 0 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (1726 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/ui/Javalanalyzer.java loaded with 12 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (21 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/ui/InputException.java loaded with 12 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (1912 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/ui/JavalanalyzerGui.java loaded with 34 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (40 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/ui/MainGui.java loaded with 34 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (318 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/ui/PackageGraph.java loaded with 39 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (751 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/ui/PackageBlock.java loaded with 43 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (495 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/ui/PackageClass.java loaded with 46 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (581 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/ui/PackageBlockBuilder.java loaded with 52 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (232 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/Tokenizer.java loaded with 56 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (651 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/TokenTree.java loaded with 58 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (416 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/feature/ImportStatement.java loaded with 66 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (299 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/feature/PackageStatement.java loaded with 73 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (355 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/feature/JavaFileCode.java loaded with 82 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (289 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/Utils.java loaded with 87 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (577 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/JavaProject.java loaded with 93 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (344 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/ClassPath.java loaded with 95 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (622 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/PackageDiagram.java loaded with 98 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (20 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/exception/CodeException.java loaded with 98 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (27 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/exception/UnexpectedToken.java loaded with 98 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (25 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/exception/TooManyPackageDecls.java loaded with 98 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (37 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/exception/UnsupportedWildcardImport.java loaded with 98 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (28 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/exception/NoMoreTokens.java loaded with 98 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (305 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/Event.java loaded with 100 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (313 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/model/EventLog.java loaded with 103 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (684 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/persistence/JsonReader.java loaded with 119 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (619 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/persistence/JsonWriter.java loaded with 132 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (16 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/persistence/ReadError.java loaded with 132 imports
Thu Mar 27 15:02:26 PDT 2025    Java code tokenized (16 tokens found)
Thu Mar 27 15:02:26 PDT 2025    Java file /home/dworv/hacking-210/ProjectStarter/src/main/persistence/WriteError.java loaded with 132 imports
Thu Mar 27 15:02:26 PDT 2025    New package diagram with 29 classes and 49 imports
Thu Mar 27 15:02:37 PDT 2025    Loaded classes of Java project ./sample projects/ProjectOne cleared
Thu Mar 27 15:02:37 PDT 2025    Java code tokenized (8 tokens found)
Thu Mar 27 15:02:37 PDT 2025    Java file ./sample projects/ProjectOne/src/main/model/Class1.java loaded with 0 imports
Thu Mar 27 15:02:37 PDT 2025    Java code tokenized (8 tokens found)
Thu Mar 27 15:02:37 PDT 2025    Java file ./sample projects/ProjectOne/src/main/model/Class2.java loaded with 0 imports
Thu Mar 27 15:02:37 PDT 2025    New package diagram with 2 classes and 0 imports
```

## Phase 4: Task 3

As I was looking at my UML diagram, I noticed a few interesting features. First of all, almost all of the classes have associations/aggregations exclusively of `ClassPath`. This can partially be attributed to the nature of my program; a lot of its responsibilities involve parsing Java code, which is heavily dependant on working with `String`s (not included in the UML diagram) and `ClassPath`s. However, there was one other design flaw I noticed while working on my UML diagram. My `ImportStatement` and `PackageStatement` classes both have very similar behaviour.

If I were to make one refactoring to the structure of my program, I would extract the shared behaviour of `ImportStatement` and `PackageStatement` into an abstract class `ClassPathStatement`. This would eliminate code duplication in my project, reducing the complexity and making it easier to understand. 