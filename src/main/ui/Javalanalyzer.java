package ui;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import model.CodeException;
import model.JavaProject;

// a class representing the runtime of javalanalyzer
public class Javalanalyzer {
    private ArrayList<JavaProject> projects;
    private Scanner input;

    // EFFECTS: build and start javalanalyzer
    public Javalanalyzer() {
        init();
        runMainMenu();
    }

    // EFFECTS: init the fields
    private void init() {
        projects = new ArrayList<>();
        input = new Scanner(System.in);
    }

    // EFFECTS: run the loop for the main menu
    private void runMainMenu() {
        while (true) {
            displayProjectList();
            displayMainOptions();

            try {
                processMainCommand();
            } catch (InputException e) {
                System.out.println("Invalid input, try again...");
            }

            System.out.println("\n");
        }
    }

    // EFFECTS: display the list of existing java projects
    private void displayProjectList() {
        System.out.println("Existing Projects:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.println("[" + String.valueOf(i) + "] " + projects.get(i).getName());
        }
        System.out.println();
    }
    
    // EFFECTS: display the options in the main menu
    private void displayMainOptions() {
        System.out.println("Add a project:");
        System.out.println(">a [PATH]");
        System.out.println("Enter a project");
        System.out.println(">e [ID]");
        System.out.println("Remove a project");
        System.out.println(">r [ID]");
        System.out.println();
    }

    // MODIFIES: this
    // EFFECTS: process a user command in the main menu
    private void processMainCommand() throws InputException {
        System.out.print(">");
        String line = input.nextLine();
        String[] parts = line.split(" ");
        if (parts.length != 2) {
            throw new InputException();
        }
        if (parts[0].equals("a")) {
            processMainAdd(parts[1]);
        } else if (parts[0].equals("e")) {
            processMainEnter(parts[1]);
        } else if (parts[0].equals("r")) {
            processMainRemove(parts[1]);
        } else {
            throw new InputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: process the command to add a new project
    private void processMainAdd(String arg) throws InputException {
        try {
            Path path = Path.of(new URI("file://" + arg));
            JavaProject newProject = new JavaProject(path);
            if (!new File(newProject.getMainPath().toString()).isDirectory()) {
                System.out.println("That isn't a java project, it doesn't have a /src/main directory...");
                throw new InputException();
            }
            projects.add(newProject);
        } catch (URISyntaxException e) {
            System.out.println("Invalid path, must be absolute...");
            throw new InputException();
        }
    }

    // MODIFIES: this
    // EFFECTS: process the command to enter a project
    private void processMainEnter(String arg) throws InputException {
        try {
            int i = Integer.parseInt(arg);
            runProjectMenu(projects.get(i));
        } catch (NumberFormatException e) {
            System.out.println("Invalid argument, second argument should an integer...");
            throw new InputException();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("You must choose an id of an existing project");
        }
    }

    // MODIFIES: this
    // EFFECTS: process the command to remove a project
    private void processMainRemove(String arg) throws InputException {
        try {
            int i = Integer.parseInt(arg);
            System.out.println("Removing project: " + projects.get(i).getName());
            projects.remove(i);
        } catch (NumberFormatException e) {
            System.out.println("Invalid argument, second argument should an integer...");
            throw new InputException();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("You must choose an id of an existing project...");
            throw new InputException();
        }
    }

    // MODIFIES: project
    // EFFECTS: run the loop for a project menu
    private void runProjectMenu(JavaProject project) {
        while (true) {
            displayProjectStatus(project);
            displayProjectOptions();

            try {
                if (processProjectCommand(project)) {
                    break;
                }
            } catch (InputException e) {
                System.out.println("Invalid input, try again...");
            }
        }
    }

    // EFFECTS: display the project status for a given project
    private void displayProjectStatus(JavaProject project) {
        System.out.println("Status on project: " + project.getName());
        System.out.println("Path to code: " + project.getMainPath().toString());
        System.out.println("Number of classes loaded: " + project.numClasses());
        System.out.println();
    }

    // EFFECTS: display the options availible in a project menu
    private void displayProjectOptions() {
        System.out.println("Load/Reload project files:");
        System.out.println(">l");
        System.out.println("Show package diagram:");
        System.out.println(">p");
        System.out.println("Return to main menu: ");
        System.out.println(">x");
        System.out.println();
    }

    // MODIFIES: project
    // EFFECTS: process a command in a project menu
    private boolean processProjectCommand(JavaProject project) throws InputException {
        System.out.print(">");
        String line = input.nextLine();
        if (line.equals("l")) {
            reloadFiles(project);
        } else if (line.equals("p")) {
            System.out.print(project.genPackageDiagram().stringify());
        } else if (line.equals("x")) {
            System.out.println("Returning to main menu");
            return true;
        } else {
            throw new InputException();
        }

        return false;
    }

    // MODIFIES: project
    // EFFECTS: reload all project files
    private void reloadFiles(JavaProject project) {
        project.clearClasses();

        try {
            Files.walk(project.getMainPath()).forEach(path -> handleProjectFile(project, path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    // MODIFIES: project
    // EFFECTS: run the loop for a project menu
    private void handleProjectFile(JavaProject project, Path path) {
        File file = new File(path.toString());
        if (file.isDirectory()) {
            return;
        }
        String contents = loadFile(file);
        if (!path.getFileName().toString().endsWith(".java")) {
            return;
        }

        try {
            project.loadJavaFile(path, contents);
            System.out.println("Loaded java file: " + path.toString());
        } catch (CodeException e) {
            System.out.println("Failed to load java file: " + path.toString());
        }
    }

    // EFFECTS: load the contents of a file
    private String loadFile(File file) {
        try {
            FileReader reader = new FileReader(file);
            StringBuilder builder = new StringBuilder();
            
            int charNum;
            while ((charNum = reader.read()) != -1) {
                builder.append((char)charNum);
            }

            String insides = builder.toString();
            reader.close();

            return insides;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null; // unreachable
    }
}
