package ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.PackageDiagram;

// extension of jpanel for displaying package diagrams
public class PackageGraph extends JPanel {
    private List<PackageBlock> blocks;
    boolean lines;
    boolean blue;

    // EFFECTS: instantiate a new package graph
    public PackageGraph() {
        blocks = new ArrayList<>();
        lines = false;
        blue = false;

        new Thread(() -> {
            while (true) {
                repaint();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    System.out.println("couldn't sleep?????");
                    System.exit(1);
                }
            }
        }).start();
    }

    // MODIFIES: this
    // EFFECTS: reset the graph with a new diagram
    public void setDiagram(PackageDiagram diagram) {
        blocks = new PackageBlockBuilder(diagram, 1000, 900).getBlocks();
    }

    // MODIFIES: this
    // EFFECTS: set whether or not lines are drawn
    public void setLines(boolean lines) {
        this.lines = lines;
    }

    // MODIFIES: this
    // EFFECTS: set whether or not the block borders are blue
    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    // MODIFIES: g
    // EFFECTS: paint the graph to the panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (PackageBlock block : blocks) {
            block.draw(g, blue);
            if (lines) {
                block.drawArrows(g);
            }
        }
    }
}
