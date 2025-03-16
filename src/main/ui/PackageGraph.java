package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.PackageDiagram;

public class PackageGraph extends JPanel {
    private List<PackageBlock> blocks;

    public PackageGraph() {
        blocks = new ArrayList<>();

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

    public void setDiagram(PackageDiagram diagram) {
        blocks = new PackageBlockBuilder(diagram, 500, 300).getBlocks();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.CYAN);
        g.drawRect(10, 10, 5 * blocks.size(), 10);
    } 
}
