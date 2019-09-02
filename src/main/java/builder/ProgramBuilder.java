/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builder;

import elements.Class;
import elements.Field;
import elements.Method;
import elements.Package;
import elements.Program;
import elements.metrics.QualityAttributes;
import java.util.ArrayList;
import java.util.List;
import calculator.DesignPropertiesCalculator;
import calculator.QualityAttributesCalculator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import readers.Reader;
import visitors.ASTVisitorClass;

/**
 *
 * @author Thainá Mariani
 */
public class ProgramBuilder {

    public List<Program> buildPrograms(String rootPath, Reader reader) {
        List<String> directories = reader.findDirectories(rootPath);
        List<Program> programs = new ArrayList<>();
        for (String directory : directories) {
            Set<String> sourcePaths = reader.findSourcePaths(directory);
            HashMap<String, String> javaFiles = new HashMap<>();
            for (String sourcePath : sourcePaths) {
                javaFiles.putAll(reader.getJavaFiles(directory + "/" + sourcePath));
            }
            List<Class> classes = buildClasses(sourcePaths, javaFiles);
            Program program = buildProgram(directory, classes);
            programs.add(program);
            break;
        }
        return programs;
    }

    public Program buildProgram(String rootPath, String dirPath, Reader reader) {
        Set<String> sourcePaths = reader.findSourcePaths(rootPath + dirPath);

        HashMap<String, String> javaFiles = new HashMap<>();
        for (String sourcePath : sourcePaths) {
            javaFiles.putAll(reader.getJavaFiles(sourcePath));
        }
        if (sourcePaths.size() > 1) {
            sourcePaths = new HashSet();
            sourcePaths.add(rootPath + dirPath);
        }
        List<Class> classes = buildClasses(sourcePaths, javaFiles);
        String name = dirPath.substring(dirPath.indexOf("/") + 1, dirPath.length() - 1).replaceAll("/", "_");
        Program program = buildProgram(name, classes);
        return program;
    }

    public List<Class> buildClasses(Set<String> sourcePaths, HashMap<String, String> files) {
        List<elements.Class> classes = new ArrayList<>();

        for (Map.Entry<String, String> file : files.entrySet()) {
            try {
                ASTParser parser = ASTParser.newParser(AST.JLS10);
                parser.setSource(file.getValue().toCharArray());
                parser.setKind(ASTParser.K_COMPILATION_UNIT);
                parser.setResolveBindings(true);
                parser.setBindingsRecovery(true);
                parser.setStatementsRecovery(true);
                //sourcePaths = new ArrayList<>();
                //sourcePaths.add("/Users/thaina/Downloads/repositories/cmacnaug-meshkeeper/Fri_Nov_06_011719_EST_2009/AR_046ca22155adfd7a40d36cc6ece9267eb4f4335d");
                String[] sourcePathsArray = sourcePaths.toArray(new String[sourcePaths.size()]);
                parser.setEnvironment(null, sourcePathsArray, null, true);
                parser.setUnitName(file.getKey());
                CompilationUnit unit = (CompilationUnit) parser.createAST(null);

                ASTVisitorClass visitorClass = new ASTVisitorClass(unit);
                unit.accept(visitorClass);
                classes.addAll(visitorClass.getClasses());
            } catch (Exception ex) {
                Logger.getLogger(ProgramBuilder.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Not able to build class " + file.getKey());
            }
        }

        return classes;
    }

    public Program buildProgram(String name, List<Class> elements) {
        List<Class> classes = new ArrayList<>();
        List<Class> interfaces = new ArrayList<>();
        for (Class e : elements) {
            if (e.isInterface()) {
                interfaces.add(e);
            } else {
                classes.add(e);
            }
        }
        buildHierarchies(classes);

        buildMetrics(classes);
        buildMetrics(interfaces);

        List<Package> packages = new ArrayList<>();
        buildPackages(classes, packages);
        buildPackages(interfaces, packages);

        Program program = new Program(name, classes, interfaces, packages);
        buildDesignProperties(program);
        buildQualityAttributes(program);

        return program;
    }

    public elements.Class buildClass(TypeDeclaration classNode, List<ImportDeclaration> importNodes) {
        String name = classNode.resolveBinding().getQualifiedName();

        List<Field> fields = new ArrayList<>();
        for (FieldDeclaration fieldDeclaration : classNode.getFields()) {
            fields.add(buildField(fieldDeclaration));
        }

        List<Method> methods = new ArrayList();
        List<Method> constructors = new ArrayList<>();
        for (MethodDeclaration methodDeclaration : classNode.getMethods()) {
            Method method = buildMethod(methodDeclaration);
            if (methodDeclaration.isConstructor()) {
                constructors.add(method);
            } else {
                methods.add(method);
            }
        }

        elements.Class classe = new elements.Class(classNode, name, importNodes, methods, constructors, fields, classNode.isInterface());
        return classe;
    }

    public Method buildMethod(MethodDeclaration methodDeclaration) {
        Method method = new Method(methodDeclaration, methodDeclaration.getName().toString(), getModifiers(methodDeclaration), methodDeclaration.getReturnType2(), methodDeclaration.parameters(), methodDeclaration.isConstructor());
        return method;
    }

    public List<Modifier> getModifiers(BodyDeclaration node) {
        List<Modifier> modifiers = new ArrayList<>();
        List<Object> objects = node.modifiers();
        for (Object object : objects) {
            if (object instanceof Modifier) {
                modifiers.add((Modifier) object);
            }
        }
        return modifiers;
    }

    public Field buildField(FieldDeclaration fieldDeclaration) {
        Field field = new Field(fieldDeclaration, fieldDeclaration.fragments().get(0).toString(), getModifiers(fieldDeclaration));
        return field;
    }

    public void buildHierarchies(List<Class> classes) {
        for (Class c : classes) {
            if (!c.isInterface()) {
                findSuperclass(c, classes);
            }
        }

        for (Class c : classes) {
            if (!c.isInterface()) {
                findSubclasses(c, classes);
            }
        }
    }

    public void buildPackages(List<Class> classes, List<Package> packages) {
        for (Class c : classes) {
            Package pac = findPackage(packages, c.getNode().resolveBinding().getPackage().getName());
            pac.addClass(c);
        }
    }

    public elements.Package findPackage(List<elements.Package> packages, String packageName) {
        for (elements.Package pac : packages) {
            if (pac.getName().equals(packageName)) {
                return pac;
            }
        }

        //if the package does not exist it is created as well as its superpackage and subpackages
        elements.Package pac = new elements.Package(packageName);
        int lastIndexOf = packageName.lastIndexOf(".");
        if (lastIndexOf >= 0) {
            String superPackageName = packageName.substring(0, lastIndexOf);
            Package superpackage = findPackage(packages, superPackageName);
            superpackage.addSubpackage(pac);
            pac.setSuperpackage(superpackage);
        }
        packages.add(pac);
        return pac;
    }

    public void findSuperclass(Class c, List<Class> classes) {
        if (c.getNode().resolveBinding().getSuperclass().isFromSource()) {
            for (Class classe : classes) {
                if (classe.getName().equals(c.getNode().resolveBinding().getSuperclass().getBinaryName())) {
                    c.setSuperclass(classe);
                    break;
                }
            }
        }
    }

    public void findSubclasses(Class c, List<Class> classes) {
        for (Class classe : classes) {
            if (classe.hasSuperClass()) {
                if (classe.getSuperclass().equals(c)) {
                    c.addSubclass(classe);
                }
            }
        }
    }

    public void buildMetrics(List<Class> classes) {
        for (Class c : classes) {
            c.calculateMetrics();
            if (!c.isInterface()) {
                for (Method method : c.getMethods()) {
                    method.calculateMetrics(c);
                }
            }
        }
    }

    public void buildDesignProperties(Program program) {
        DesignPropertiesCalculator calculator = new DesignPropertiesCalculator();
        program.setDesignProperties(calculator.calculateDesignProperties(program));
    }

    public void buildQualityAttributes(Program program) {
        QualityAttributesCalculator calculator = new QualityAttributesCalculator();
        QualityAttributes qualityAttributes = calculator.calculateQualityAttributes(program.getDesignProperties());
        program.setQualityAttributes(qualityAttributes);
    }

}
