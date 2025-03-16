package ui;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import model.ClassPath;

public class PackageBlock {
    private String name;
    private int x;
    private int y;
    private List<PackageClass> classes;

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

    public String getName() {
        return name;
    }

    public List<PackageClass> getClasses() {
        return classes;
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
