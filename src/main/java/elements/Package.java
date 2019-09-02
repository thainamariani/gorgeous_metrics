/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thainá Mariani
 */
public class Package extends Element {

    private List<Class> classes;
    private List<Class> interfaces;
    private Package superpackage;
    private List<Package> subpackages;

    public Package() {
    }

    public Package(String name) {
        this.name = name;
        classes = new ArrayList<>();
        interfaces = new ArrayList<>();
        subpackages = new ArrayList<>();
        superpackage = new Package();
        superpackage.setName("");
    }

    public Package(String name, List<Class> classes, List<Class> interfaces, Package superpackage, List<Package> subpackages) {
        this.name = name;
        this.classes = classes;
        this.interfaces = interfaces;
        this.superpackage = superpackage;
        this.subpackages = subpackages;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    public Package getSuperpackage() {
        return superpackage;
    }

    public void setSuperpackage(Package superpackage) {
        this.superpackage = superpackage;
    }

    public List<Package> getSubpackages() {
        return subpackages;
    }

    public void setSubpackages(List<Package> subpackages) {
        this.subpackages = subpackages;
    }

    public List<Class> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<Class> interfaces) {
        this.interfaces = interfaces;
    }

    public boolean addClass(Class c) {
        if (c.isInterface()) {
            return interfaces.add(c);
        } else {
            return classes.add(c);
        }
    }

    public void addSubpackage(elements.Package subpackage) {
        subpackages.add(subpackage);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}
