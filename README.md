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
