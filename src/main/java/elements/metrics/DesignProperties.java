/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements.metrics;

/**
 *
 * @author Thainá Mariani
 */
public class DesignProperties {

    private double designSize;
    private double hierarchies;
    private double abstraction;
    private double encapsulation;
    private double coupling;
    private double cohesion;
    private double composition;
    private double inheritance;
    private double polymorphism;
    private double messaging;
    private double complexity;

    public DesignProperties() {
    }

    public DesignProperties(double designSize, double hierarchies, double abstraction, double encapsulation, double coupling, double cohesion, double composition, double inheritance, double polymorphism, double messaging, double complexity) {
        this.designSize = designSize;
        this.hierarchies = hierarchies;
        this.abstraction = abstraction;
        this.encapsulation = encapsulation;
        this.coupling = coupling;
        this.cohesion = cohesion;
        this.composition = composition;
        this.inheritance = inheritance;
        this.polymorphism = polymorphism;
        this.messaging = messaging;
        this.complexity = complexity;
    }

    public double getDesignSize() {
        return designSize;
    }

    public void setDesignSize(double designSize) {
        this.designSize = designSize;
    }

    public double getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(double hierarchies) {
        this.hierarchies = hierarchies;
    }

    public double getAbstraction() {
        return abstraction;
    }

    public void setAbstraction(double abstraction) {
        this.abstraction = abstraction;
    }

    public double getEncapsulation() {
        return encapsulation;
    }

    public void setEncapsulation(double encapsulation) {
        this.encapsulation = encapsulation;
    }

    public double getCoupling() {
        return coupling;
    }

    public void setCoupling(double coupling) {
        this.coupling = coupling;
    }

    public double getCohesion() {
        return cohesion;
    }

    public void setCohesion(double cohesion) {
        this.cohesion = cohesion;
    }

    public double getComposition() {
        return composition;
    }

    public void setComposition(double composition) {
        this.composition = composition;
    }

    public double getInheritance() {
        return inheritance;
    }

    public void setInheritance(double inheritance) {
        this.inheritance = inheritance;
    }

    public double getPolymorphism() {
        return polymorphism;
    }

    public void setPolymorphism(double polymorphism) {
        this.polymorphism = polymorphism;
    }

    public double getMessaging() {
        return messaging;
    }

    public void setMessaging(double messaging) {
        this.messaging = messaging;
    }

    public double getComplexity() {
        return complexity;
    }

    public void setComplexity(double complexity) {
        this.complexity = complexity;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.designSize) ^ (Double.doubleToLongBits(this.designSize) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.hierarchies) ^ (Double.doubleToLongBits(this.hierarchies) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.abstraction) ^ (Double.doubleToLongBits(this.abstraction) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.encapsulation) ^ (Double.doubleToLongBits(this.encapsulation) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.coupling) ^ (Double.doubleToLongBits(this.coupling) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.cohesion) ^ (Double.doubleToLongBits(this.cohesion) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.composition) ^ (Double.doubleToLongBits(this.composition) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.inheritance) ^ (Double.doubleToLongBits(this.inheritance) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.polymorphism) ^ (Double.doubleToLongBits(this.polymorphism) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.messaging) ^ (Double.doubleToLongBits(this.messaging) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.complexity) ^ (Double.doubleToLongBits(this.complexity) >>> 32));
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
        final DesignProperties other = (DesignProperties) obj;
        if (Double.doubleToLongBits(this.designSize) != Double.doubleToLongBits(other.designSize)) {
            return false;
        }
        if (Double.doubleToLongBits(this.hierarchies) != Double.doubleToLongBits(other.hierarchies)) {
            return false;
        }
        if (Double.doubleToLongBits(this.abstraction) != Double.doubleToLongBits(other.abstraction)) {
            return false;
        }
        if (Double.doubleToLongBits(this.encapsulation) != Double.doubleToLongBits(other.encapsulation)) {
            return false;
        }
        if (Double.doubleToLongBits(this.coupling) != Double.doubleToLongBits(other.coupling)) {
            return false;
        }
        if (Double.doubleToLongBits(this.cohesion) != Double.doubleToLongBits(other.cohesion)) {
            return false;
        }
        if (Double.doubleToLongBits(this.composition) != Double.doubleToLongBits(other.composition)) {
            return false;
        }
        if (Double.doubleToLongBits(this.inheritance) != Double.doubleToLongBits(other.inheritance)) {
            return false;
        }
        if (Double.doubleToLongBits(this.polymorphism) != Double.doubleToLongBits(other.polymorphism)) {
            return false;
        }
        if (Double.doubleToLongBits(this.messaging) != Double.doubleToLongBits(other.messaging)) {
            return false;
        }
        if (Double.doubleToLongBits(this.complexity) != Double.doubleToLongBits(other.complexity)) {
            return false;
        }
        return true;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
