package ui;

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
        blocks = new PackageBlockBuilder(diagram, 1000, 900).getBlocks();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (PackageBlock block : blocks) {
            block.draw(g);
            block.drawArrows(g);
        }
    }

    // private void moveBlocks() {
    //     for (PackageBlock block : blocks) {
    //         int deltaX = 0;
    //         int deltaY = 0;

    //         for (PackageBlock other : blocks) {
    //             if (block.equals(other)) {
    //                 continue;
    //             }
                
    //             double mag = 
    //         }
    //     }
    // }

    // private double angleAway(PackageBlock from, PackageBlock to) {
    //     double deltaX = (double)(to.getX() - from.getX());
    //     double deltaY = (double)(to.getY() - from.getY());

    //     return Math.atan(deltaY/deltaX);
    // }

    // private double distAway(PackageBlock from, PackageBlock to) {
    //     double deltaX = (double)(to.getX() - from.getX());
    //     double deltaY = (double)(to.getY() - from.getY());

    //     return Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaY, 2.0));
    // }
}
