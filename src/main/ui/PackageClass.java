package ui;

import java.util.ArrayList;
import java.util.List;

public class PackageClass {
    private String name;
    private List<PackageClass> imports;
    private PackageBlock parent;

    public PackageClass(String name, PackageBlock parent) {
        this.name = name;
        this.parent = parent;
        imports = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<PackageClass> getImports() {
        return imports;
    }

    public void addImport(PackageClass pc) {
        imports.add(pc);
    }

    public PackageBlock getParent() {
        return parent;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
        PackageClass other = (PackageClass) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (parent == null) {
            if (other.parent != null)
                return false;
        } else if (!parent.equals(other.parent))
            return false;
        return true;
    }
}
