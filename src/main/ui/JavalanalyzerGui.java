// loosely based on https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

package ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import model.JavaProject;
import model.exception.CodeException;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.ReadError;
import persistence.WriteError;

// the root of the gui
public class JavalanalyzerGui extends JFrame implements ActionListener {
    private List<JavaProject> projects;
    private JsonReader reader;
    private JsonWriter writer;

    private JPanel projectsPanel;
    private JTextField textInput;
    private JTextArea logText;
    private PackageGraph graph;
    
    // EFFECTS: instantiates the gui
    public JavalanalyzerGui() {
        super("Javalanalyzer");
        setUndecorated(false);
        setResizable(false); 
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fails checkstyle fon my machine or no apparent reason
        
        projects = new ArrayList<>();
        reader = new JsonReader("./data/save.json");
        writer = new JsonWriter("./data/save.json");

        buildMainpanel();
	    buildSidepanel(); // fails checkstyle on my machine for no apparent reason

        setSize(1300, 1000);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: builds the sidepanel of the gui
    private void buildSidepanel() {
        JPanel sidePanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        textInput = new JTextField(20);
        inputPanel.add(textInput);
        inputPanel.add(buildButton("+", "addPressed"));
        inputPanel.add(buildButton("📥", "savePressed"));
        inputPanel.add(buildButton("📤", "loadPressed"));

        projectsPanel = new JPanel();
        projectsPanel.setLayout(new BoxLayout(projectsPanel, BoxLayout.Y_AXIS));
        regenerateProjectsPanel();

        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        settingsPanel.add(buildToggle("Lines", "toggleLines", 
                ev -> handleLines(ev.getStateChange() == ItemEvent.SELECTED)
        ));
        settingsPanel.add(buildToggle("Blue", "toggleBlue", 
                ev -> handleBlue(ev.getStateChange() == ItemEvent.SELECTED)
        ));

        sidePanel.add(inputPanel, BorderLayout.NORTH);
        sidePanel.add(new JScrollPane(projectsPanel), BorderLayout.CENTER);
        sidePanel.add(settingsPanel, BorderLayout.SOUTH);
        sidePanel.setMaximumSize(new Dimension(300, 1000));

        add(sidePanel, BorderLayout.WEST);
    }

    // EFFECTS: returns a button as described
    private JButton buildButton(String label, String message) {
        JButton button = new JButton(label);
        button.addActionListener(this);
        button.setActionCommand(message);
        return button;
    }

    // EFFECTS: returns a toggle button as described
    private JToggleButton buildToggle(String label, String message, ItemListener listener) {
        JToggleButton toggle = new JToggleButton(label);
        toggle.addItemListener(listener);
        toggle.setActionCommand(message);
        return toggle;
    }

    // REQUIRES: projectsPanel exists
    // MODIFIES: this
    // EFFECTS: regenerates the projects panel (important or else it won't get redrawn)
    private void regenerateProjectsPanel() {
        projectsPanel.removeAll();
        for (int i = 0; i < projects.size(); i++) {
            JavaProject project = projects.get(i);

            JPanel projectPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

            JButton openButton = new JButton("Open");
            openButton.setActionCommand("open" + String.valueOf(i));
            openButton.addActionListener(this);

            JButton rmButton = new JButton("X");
            rmButton.setActionCommand("rm" + String.valueOf(i));
            rmButton.addActionListener(this);

            projectPanel.add(new JLabel(project.getName()));
            projectPanel.add(openButton);
            projectPanel.add(rmButton);
            
            projectPanel.setAlignmentX(Component.TOP_ALIGNMENT);
            projectPanel.setMaximumSize(projectPanel.getPreferredSize());
            projectsPanel.add(projectPanel);
        }

        projectsPanel.revalidate();
        projectsPanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: builds the main panel
    private void buildMainpanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        logText = new JTextArea();
        logText.setEditable(false);

        graph = new PackageGraph();
        mainPanel.add(graph, BorderLayout.CENTER);

        JScrollPane scrollable = new JScrollPane(logText);
        scrollable.setPreferredSize(new Dimension(700, 100));
        scrollable.setMaximumSize(new Dimension(700, 100));
        System.out.println(scrollable.getPreferredSize());
        mainPanel.add(scrollable, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: handles any button action being performed (toggles handled seperately)
    public void actionPerformed(ActionEvent event) {
        System.out.println(event.getActionCommand());
        String command = event.getActionCommand();
        if (command.equals("addPressed")) {
            handleAddPressed();
        } else if (command.equals("savePressed")) {  
            handleSave();
        } else if (command.equals("loadPressed")) {
            handleLoad();
        } else if (command.startsWith("open")) {
            handleOpen(Integer.valueOf(command.substring(4)));
        } else if (command.startsWith("rm")) {
            handleRm(Integer.valueOf(command.substring(2)));
        }

        regenerateProjectsPanel();
    }

    // MODIFIES: this
    // EFFECTS: handle the + button being pressed
    private void handleAddPressed() {
        try {
            Path path = Paths.get(textInput.getText());
            JavaProject newProject = new JavaProject(path);
            if (!new File(newProject.getMainPath().toString()).isDirectory()) {
                System.out.println("That isn't a java project, it doesn't have a /src/main directory...");
                return;
            }
            projects.add(newProject);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid path... (good path looks like /path/to/my/Project)");
        }
    }

    // REQUIRES: 0 <= i < projects.size()
    // MODIFIES: this
    // EFFECTS: handles the open button being pressed
    private void handleOpen(int i) {
        reloadFiles(projects.get(i));
        graph.setDiagram(projects.get(i).genPackageDiagram());
    }

    // REQUIRES: 0 <= i < projects.size()
    // MODIFIES: this
    // EFFECTS: handles the x button being pressed
    private void handleRm(int i) {
        projects.remove(i);
    }

    // MODIFIES: this
    // EFFECTS: saves the projects to disk
    private void handleSave() {
        try {
            writer.write(projects);
        } catch (WriteError e) {
            System.out.println("couldn't save to file...");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads saved projects from disk
    private void handleLoad() {
        try {
            projects = reader.read();
        } catch (ReadError e) {
            System.out.println("couldn't load from file...");
        }
    }

    // MODIFIES: this
    // EFFECTS: handle the lines toggle being pressed
    private void handleLines(boolean on) {
        graph.setLines(on);
    }

    // MODIFIES: this
    // EFFECTS: handle the blue toggle being pressed
    private void handleBlue(boolean on) {
        graph.setBlue(on);
    }

    // MODIFIES: project
    // EFFECTS: reload all project files
    private void reloadFiles(JavaProject project) {
        project.clearClasses();
        logText.setText("");

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
            logText.setText(logText.getText() + "\nLoaded java file: " + path.toString());
        } catch (CodeException e) {
            logText.setText(logText.getText() + "\nFailed to load java file: " + path.toString());
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
