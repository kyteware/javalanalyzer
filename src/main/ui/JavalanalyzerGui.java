// loosely based on https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

package ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

// the root of the gui
public class JavalanalyzerGui extends JFrame implements ActionListener {
    private List<JavaProject> projects;
    private JsonReader reader;
    private JsonWriter writer;

    private JPanel projectsPanel;
    private JTextField textInput;
    private JTextArea logText;
    private PackageGraph graph;
    
    public JavalanalyzerGui() {
        super("Javalanalyzer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        
        projects = new ArrayList<>();
        projects.add(new JavaProject(Paths.get("/home/dworv/hacking-210/ProjectStarter")));
        projects.add(new JavaProject(Paths.get("./sample projects/ProjectOne")));
        JsonReader reader = new JsonReader("./data/save.json");
        JsonWriter writer = new JsonWriter("./data/save.json");

        graph = new PackageGraph();

        buildMainpanel();
	    buildSidepanel();

        setSize(700, 400);
        setVisible(true);
    }

    private void buildSidepanel() {
        JPanel sidePanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        textInput = new JTextField(20);
        inputPanel.add(textInput);

        JButton addButton = new JButton("+");
        addButton.addActionListener(this);
        addButton.setActionCommand("addPressed");
        inputPanel.add(addButton);

        projectsPanel = new JPanel();
        projectsPanel.setLayout(new BoxLayout(projectsPanel, BoxLayout.Y_AXIS));
        regenerateProjectsPanel();

        sidePanel.add(inputPanel, BorderLayout.NORTH);
        sidePanel.add(new JScrollPane(projectsPanel), BorderLayout.CENTER);
        sidePanel.setMaximumSize(new Dimension(300, 1000));

        add(sidePanel, BorderLayout.WEST);
    }

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

    private void buildMainpanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        logText = new JTextArea();
        logText.setEditable(false);

        PackageGraph graph = new PackageGraph();
        mainPanel.add(graph, BorderLayout.CENTER);

        JScrollPane scrollable = new JScrollPane(logText);
        scrollable.setPreferredSize(new Dimension(100, 120));
        scrollable.setMaximumSize(new Dimension(100, 120));
        System.out.println(scrollable.getPreferredSize());
        mainPanel.add(scrollable, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent event) {
        System.out.println(event.getActionCommand());
        String command = event.getActionCommand();
        if (command.equals("addPressed")) {
            handleAddPressed();
        } else if (command.startsWith("open")) {
            handleOpen(Integer.valueOf(command.substring(4)));
        } else if (command.startsWith("rm")) {
            handleRm(Integer.valueOf(command.substring(2)));
        }

        regenerateProjectsPanel();
    }

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

    private void handleOpen(int i) {
        reloadFiles(projects.get(i));
        graph.setDiagram(projects.get(i).genPackageDiagram());
    }

    private void handleRm(int i) {
        projects.remove(i);
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
