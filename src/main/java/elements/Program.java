/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import elements.metrics.DesignProperties;
import elements.metrics.QualityAttributes;
import calculator.DesignPropertiesCalculator;
import calculator.QualityAttributesCalculator;
import com.rits.cloning.Cloner;
import java.util.List;
import java.util.Objects;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ChildListPropertyDescriptor;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Type;
import org.w3c.dom.NodeList;

/**
 *
 * @author thaina
 */
public class Program extends Element{

    private List<Class> classes;
    private List<Class> interfaces;
    private List<Package> packages;
    private DesignProperties designProperties;
    private QualityAttributes qualityAttributes;

    public Program() {
    }

    public Program(String name, List<Class> classes, List<Class> interfaces, List<Package> packages) {
        super.name = name;
        this.classes = classes;
        this.interfaces = interfaces;
        this.packages = packages;
    }

    public Program(String name, List<Class> classes, List<Class> interfaces, List<Package> packages, DesignProperties designProperties, QualityAttributes qualityAttributes) {
        super.name = name;
        this.classes = classes;
        this.interfaces = interfaces;
        this.packages = packages;
        this.designProperties = designProperties;
        this.qualityAttributes = qualityAttributes;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    public List<Class> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<Class> interfaces) {
        this.interfaces = interfaces;
    }

    public QualityAttributes getQualityAttributes() {
        return qualityAttributes;
    }

    public void setQualityAttributes(QualityAttributes qualityAttributes) {
        this.qualityAttributes = qualityAttributes;
    }

    public DesignProperties getDesignProperties() {
        return designProperties;
    }

    public void setDesignProperties(DesignProperties designProperties) {
        this.designProperties = designProperties;
    }

    public void updateQualityAttributes() {
        for (Class classe : classes) {
            classe.calculateMetrics();
        }

        DesignPropertiesCalculator designPropertiesCalculator = new DesignPropertiesCalculator();
        this.designProperties = designPropertiesCalculator.calculateDesignProperties(this);

        QualityAttributesCalculator qualityAttributesCalculator = new QualityAttributesCalculator();
        this.qualityAttributes = qualityAttributesCalculator.calculateQualityAttributes(this.designProperties);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.name.hashCode();
        for (Class classe : this.classes) {
            hash = 97 * hash + classe.hashCode();
        }

        for (Class aInterface : this.interfaces) {
            hash = 97 * hash + aInterface.hashCode();
        }

        for (Package aPackage : this.packages) {
            hash = 97 * hash + aPackage.hashCode();
        }

        hash = 97 * hash + this.designProperties.hashCode();
        hash = 97 * hash + this.qualityAttributes.hashCode();
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
        final Program other = (Program) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.classes, other.classes)) {
            return false;
        }
        if (!Objects.equals(this.interfaces, other.interfaces)) {
            return false;
        }
        if (!Objects.equals(this.packages, other.packages)) {
            return false;
        }
        if (!Objects.equals(this.designProperties, other.designProperties)) {
            return false;
        }
        if (!Objects.equals(this.qualityAttributes, other.qualityAttributes)) {
            return false;
        }
        return true;
    }

    @Override
    public Program clone() throws CloneNotSupportedException {
        Cloner cloner = new Cloner();
        cloner.dontCloneInstanceOf(ASTNode.class, Type.class, NodeList.class, Modifier.class, ChildListPropertyDescriptor.class);
        return cloner.deepClone(this);
    }
}