// loosely based on https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

package ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import model.JavaProject;
import persistence.JsonReader;
import persistence.JsonWriter;

// the root of the gui
public class JavalanalyzerGui extends JFrame implements ActionListener {
    private List<JavaProject> projects;
    private JsonReader reader;
    private JsonWriter writer;

    private JPanel projectsPanel;
    private JTextField textInput;
    
    public JavalanalyzerGui() {
        super("Javalanalyzer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        
        projects = new ArrayList<>();
        projects.add(new JavaProject(Paths.get("/home/dworv/hacking-210/ProjectStarter")));
        projects.add(new JavaProject(Paths.get("/home/dworv/hacking-210/ProjectStarter")));
        JsonReader reader = new JsonReader("./data/save.json");
        JsonWriter writer = new JsonWriter("./data/save.json");

		buildSidepanel();

        setSize(500, 400);
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

        add(sidePanel);
    }

    private void regenerateProjectsPanel() {
        projectsPanel.removeAll();
        for (int i=0; i<projects.size(); i++) {
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

    public void handleAddPressed() {
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

    public void handleOpen(int i) {
        System.out.println(projects.get(i).getName());
    }

    public void handleRm(int i) {
        projects.remove(i);
    }
}
