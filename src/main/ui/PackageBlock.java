package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class PackageBlock {
    private String name;
    private int x;
    private int y;
    private List<PackageClass> classes;

    private int WIDTH = 170;

    public PackageBlock(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.classes = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return 25 + 15 * classes.size();
    }

    public int getRectX() {
        return x - WIDTH / 2;
    }

    public int getRectY() {
        return y - getHeight() / 2;
    }

    public String getName() {
        return name;
    }

    public List<PackageClass> getClasses() {
        return classes;
    }

    public int getYForClass(PackageClass cls) {
        for (int i = 0; i < classes.size(); i++) {
            if (cls.equals(classes.get(i))) {
                return getRectY() + (i+2) * 15;
            }
        }

        return -5; // shouldn't happen
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void addClass(PackageClass newClass) {
        classes.add(newClass);
    }

    public void draw(Graphics g) {
        g.setColor(Color.GRAY);

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
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PackageBlock other = (PackageBlock) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
