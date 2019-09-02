/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import elements.metrics.ClassMetrics;
import java.util.ArrayList;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import java.util.List;
import calculator.MetricsCalculator;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import java.util.Objects;
import org.eclipse.jdt.core.dom.ImportDeclaration;

/**
 *
 * @author Thainá Mariani
 */

@JsonIgnoreType
public class Class extends Element{

    private TypeDeclaration node;
    private List<ImportDeclaration> imports;
    private List<Method> methods;
    private List<Method> constructors;
    private List<Field> fields;
    private Class superclass;
    private List<Class> subclasses;
    private boolean interfac;

    public Class() {
    }

    public Class(TypeDeclaration node, String name, List<ImportDeclaration> imports, List<Method> methods, List<Method> constructors, List<Field> fields, boolean interfac) {
        this.node = node;
        this.name = name;
        this.imports = imports;
        this.methods = methods;
        this.constructors = constructors;
        this.fields = fields;
        this.subclasses = new ArrayList<>();
        this.interfac = interfac;
    }

    public TypeDeclaration getNode() {
        return node;
    }

    public void setNode(TypeDeclaration node) {
        this.node = node;
    }

    public List<ImportDeclaration> getImports() {
        return imports;
    }

    public void setImports(List<ImportDeclaration> imports) {
        this.imports = imports;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public boolean addMethod(Method method) {
        return methods.add(method);
    }

    public boolean removeMethod(Method method) {
        return methods.remove(method);
    }

    public List<Method> getConstructors() {
        return constructors;
    }

    public void setConstructors(List<Method> constructors) {
        this.constructors = constructors;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Class getSuperclass() {
        return superclass;
    }

    public void setSuperclass(Class superclass) {
        this.superclass = superclass;
    }

    public List<Class> getSubclasses() {
        return subclasses;
    }

    public void setSubclasses(List<Class> subclasses) {
        this.subclasses = subclasses;
    }

    public boolean isInterface() {
        return interfac;
    }

    public void setInterface(boolean interfac) {
        this.interfac = interfac;
    }

    public boolean addSubclass(Class subclass) {
        return this.subclasses.add(subclass);
    }

    public boolean isSuperClass() {
        return !subclasses.isEmpty();
    }

    public boolean hasSuperClass() {
        return superclass != null;
    }

    @Override
    public ClassMetrics getMetrics() {
        return (ClassMetrics) metrics;
    }

    @Override
    public String toString() {
        String toString = "---------------------------------\n";
        toString += this.name + "\n";
        toString += "isInterface: " + isInterface() + "\n";
        toString += "imports:\n";
        for (ImportDeclaration aImport : imports) {
            toString += aImport.toString() + "\n";
        }
        toString += "\nconstructors: \n";
        for (Method method : this.constructors) {
            toString += method.getName() + "\n";
        }
        toString += "\nmethods: \n";
        for (Method method : this.methods) {
            toString += method.getName() + "\n";
        }

        toString += "\nfields: \n";
        for (Field field : this.fields) {
            toString += field.getName() + "\n";
        }

        if (superclass != null) {
            toString += "\nsuperclass: " + superclass.getName() + "\n";
        }

        toString += "\nsubclasses: \n";
        for (Class classes : this.subclasses) {
            toString += classes.getName() + "\n";
        }

        return toString;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        for (Method method : this.methods) {
            hash = 67 * hash + method.hashCode();
        }
        for (Method constructor : this.constructors) {
            hash = 67 * hash + constructor.hashCode();
        }
        for (Field field : this.fields) {
            hash = 67 * hash + field.hashCode();
        }
        hash = 67 * hash + (this.interfac ? 1 : 0);
        return hash;
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
        final Class other = (Class) obj;
        if (this.interfac != other.interfac) {
            return false;
        }
        if (!Objects.equals(this.methods, other.methods)) {
            return false;
        }
        if (!Objects.equals(this.constructors, other.constructors)) {
            return false;
        }
        if (!Objects.equals(this.fields, other.fields)) {
            return false;
        }
        return true;
    }

    public void calculateMetrics() {
        MetricsCalculator calculator = new MetricsCalculator();
        ClassMetrics classMetrics = new ClassMetrics();
        classMetrics.setNumberOfCoupledClasses(calculator.calculateNumberCoupledClasses(this));
        classMetrics.setCohesion(calculator.calculateCohesionClass(this));
        classMetrics.setNumberOfMethods(calculator.calculateNumberMethods(this));

        //metrics calculated only for classes that are not interfaces
        if (!isInterface()) {
            classMetrics.setNumberOfInheritedMethods(calculator.calculateNumberInheritedMethods(this));
            classMetrics.setNumberUserDefinedAttributes(calculator.calculateNumberUserDefinedAttributes(this));
            classMetrics.setNumbeOfPrivateAttributes(calculator.calculateNumberPrivateAttributes(this));
            classMetrics.setNumberOfAncestors(calculator.calculateNumberOfAncestors(this));
            classMetrics.setNumberOfPolymorphicMethods(calculator.calculateNumberOfPolymorphicMethods(this));
            classMetrics.setNumberOfProtectedAttributes(calculator.calculateNumberProtectedAttributes(this));
            classMetrics.setNumberOfPublicAttributes(calculator.calculateNumberPublicAttributes(this));
            classMetrics.setNumberOfSubclasses(calculator.calculateNumberOfSubclasses(this));
            classMetrics.setNumberPublicMethods(calculator.calculateNumberPublicMethods(this));
            classMetrics.setNumberOfAttributes(classMetrics.getNumbeOfPrivateAttributes() +
            classMetrics.getNumberOfProtectedAttributes() + classMetrics.getNumberOfPublicAttributes());
        }
        this.metrics = classMetrics;
    }

}
