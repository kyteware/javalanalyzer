package ui;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import model.JavaProject;

public class Javalanalyzer {
    private ArrayList<JavaProject> projects;
    private Scanner input;

    public Javalanalyzer() {
        init();
        run();
    }

    private void init() {
        projects = new ArrayList<>();
        input = new Scanner(System.in);
    }

    private void run() {
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

    private void displayProjectList() {
        System.out.println("Existing Projects:");
        for (int i=0; i<projects.size(); i++) {
            System.out.println("[" + String.valueOf(i) + "] " + projects.get(i).getName());
        }
        System.out.println();
    }
    
    private void displayMainOptions() {
        System.out.println("Add a project:");
        System.out.println(">a [PATH]");
        System.out.println("Enter a project");
        System.out.println(">e [ID]");
        System.out.println("Remove a project");
        System.out.println(">r [ID]");
        System.out.println();
    }

    private void processMainCommand() throws InputException {
        System.out.print(">");
        String line = input.nextLine();
        String[] parts = line.split(" ");
        if (parts.length != 2) {
            throw new InputException();
        }
        if (parts[0].equals("a")) {
            try {
                Path path = Path.of(new URI("file://" + parts[1]));
                projects.add(new JavaProject(path));
            } catch (URISyntaxException e) {
                System.out.println("Invalid path, must be absolute...");
                throw new InputException();
            }
        }
        else if (parts[0].equals("e")) {
            try {
                int i = Integer.parseInt(parts[1]);
                System.out.println("Opening project: " + projects.get(i).getName());
            } catch (NumberFormatException e) {
                System.out.println("Invalid argument, second argument should an integer...");
                throw new InputException();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("You must choose an id of an existing project");
            }
        }
        else if (parts[0].equals("r")) {
            try {
                int i = Integer.parseInt(parts[1]);
                System.out.println("Removing project: " + projects.get(i).getName());
                projects.remove(i);
            } catch (NumberFormatException e) {
                System.out.println("Invalid argument, second argument should an integer...");
                throw new InputException();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("You must choose an id of an existing project");
            }
        }
        else {
            throw new InputException();
        }
    }
}
