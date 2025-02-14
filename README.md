# Javalanalyzer

My term project will be to build a tool that can parse java code and visualize it using various kinds of graphs. It will be able to take a project existing on a user's computer and generate package diagrams and class diagrams for it. It is intended to be used by developers who want to analyzer their code.

I am choosing this project because I am interested in programming languages and visualization, and this project incorporates both of those in an interesting and challenging way.

## User Stories

- [x] As a user, I want to be able to select a new Java project to analyze, specifying its path.
- [x] As a user, I want to be able to see a list of previously analyzed java projects.
- [x] As a user, I want to be able to remove a Java project from my list of previously visualized java projects.
- [x] As a user, I want to be able to load and reload the project files of a java project.
- [x] As a user, I want to be able to generate a package diagram of my project.
- [ ] As a user, I want to be able to generate a class diagram of my project.

## Note to T.A.

I don't know exactly which environment you are going to be testing my project in, and I want to make sure that you can successfully generate a package diagram of
a project. Note that although it works on my project, which suggests that it is quite robust, it is possible I missed some cases and possibilities. I will try
running it on more projects written by other people before part 2 of the project is due, and then I'll test it on some bigger projects.

I'm sure that you know how to get the absolute path of a directory, but just in case you forget like I do all the time, run `pwd` while in the root of the project.
If you are on windows (godspeed, also you'll need to run `echo %cd%` for the path instead), you may need to reformat your path into unix style like so:
`C:\\path\\to\\JavaProject` -> `/path/to/JavaProject`.
