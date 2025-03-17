package ui;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.ClassPath;
import model.PackageDiagram;

public class PackageBlockBuilder {
    private PackageDiagram diagram;
    private List<PackageBlock> blocks;
    private List<PackageClass> classes;

    public PackageBlockBuilder(PackageDiagram diagram, int maxWidth, int maxHeight) {
        this.diagram = diagram;
        initBlocks(maxWidth, maxHeight);
        initClasses();
        connectClasses();
    }

    public List<PackageBlock> getBlocks() {
        return blocks;
    }

    private void initBlocks(int maxWidth, int maxHeight) {
        blocks = new ArrayList<>();
        Random rng = new Random();

        for (ClassPath classPath : diagram.getClasses()) {
            ClassPath truncated = new ClassPath(classPath.getPackagePath(), null);
            PackageBlock block = new PackageBlock(truncated.stringify(), rng.nextInt(maxWidth), rng.nextInt(maxHeight));
            if (!blocks.contains(block)) {
                blocks.add(block);
            }
        }
    }

    private void initClasses() {
        classes = new ArrayList<>();

        for (ClassPath classPath : diagram.getClasses()) {
            String blockName = new ClassPath(classPath.getPackagePath(), null).stringify();
            for (int i = 0; i < blocks.size(); i++) {
                if (blockName.equals(blocks.get(i).getName())) {
                    PackageClass pkgClass = new PackageClass(classPath.getClassName(), blocks.get(i));
                    classes.add(pkgClass);
                    blocks.get(i).addClass(pkgClass);
                    break;
                }
            }
        }
    }

    private void connectClasses() {
        for (SimpleEntry<ClassPath, ClassPath> importPair : diagram.getImports()) {
            PackageClass importer = findClass(importPair.getKey());
            PackageClass imported = findClass(importPair.getValue());

            importer.addImport(imported);
        }
    }

    private PackageClass findClass(ClassPath classPath) {
        String stringifiedPath = new ClassPath(classPath.getPackagePath(), null).stringify();
        for (PackageClass pkgClass : classes) {
            boolean namesMatch = classPath.getClassName().equals(pkgClass.getName());
            boolean pathsMatch = stringifiedPath.equals(pkgClass.getParent().getName());
            if (namesMatch && pathsMatch) {
                return pkgClass;
            }
        }

        return null;
    }
}
