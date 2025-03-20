package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

// a representation of a package block in the gui
public class PackageBlock {
    private String name;
    private int posX;
    private int poxY;
    private List<PackageClass> classes;

    private static int WIDTH = 170;

    // EFFECTS: instantiates package block with empty children
    public PackageBlock(String name, int x, int y) {
        this.name = name;
        this.posX = x;
        this.poxY = y;
        this.classes = new ArrayList<>();
    }

    public int getPosX() {
        return posX;
    }

    public int getPoxY() {
        return poxY;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return 25 + 15 * classes.size();
    }

    public int getRectX() {
        return posX - WIDTH / 2;
    }

    public int getRectY() {
        return poxY - getHeight() / 2;
    }

    public String getName() {
        return name;
    }

    public List<PackageClass> getClasses() {
        return classes;
    }

    // EFFECTS: get the absolute y coordinate of a class in the block
    public int getYForClass(PackageClass cls) {
        for (int i = 0; i < classes.size(); i++) {
            if (cls.equals(classes.get(i))) {
                return getRectY() + (i + 2) * 15;
            }
        }

        return -5; // shouldn't happen
    }

    // MODIFIES: this
    // EFFECTS: sets the x of the block
    public void setPosX(int x) {
        this.posX = x;
    }

    // MODIFIES: this
    // EFFECTS: sets the y of the block
    public void setPoxY(int y) {
        this.poxY = y;
    }

    // MODIFIES: this
    // EFFECTS: add a new class to the block
    public void addClass(PackageClass newClass) {
        classes.add(newClass);
    }

    
    // MODIFIES: g
    // EFFECTS: draws the block on g
    public void draw(Graphics g, boolean blue) {
        g.setColor(Color.GRAY);
        if (blue) {
            g.setColor(Color.BLUE);
        }

        int height = getHeight();
        
        g.drawRect(getRectX(), getRectY(), WIDTH, height);

        g.setColor(Color.BLACK);

        g.drawString(name, getRectX() + 2, getRectY() + 15);
        g.drawLine(getRectX(), getRectY() + 16, getRectX() + WIDTH, getRectY() + 16);

        for (int i = 0; i < classes.size(); i++) {
            PackageClass cls = classes.get(i);
            g.drawString(cls.getName(), getRectX() + 5, getYForClass(cls));
        }
    }
 
    // MODIFIES: g
    // EFFECTS: draws the lines of the graph on g
    public void drawArrows(Graphics g) {
        for (PackageClass pkgClass : classes) {
            pkgClass.drawArrows(g);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PackageBlock other = (PackageBlock) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
