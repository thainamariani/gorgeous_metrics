/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements.metrics;

import elements.metrics.Metrics;

/**
 *
 * @author thaina
 */
public class ClassMetrics extends Metrics {

    private int numberOfAncestors;
    private int numberOfSubclasses;
    private int numbeOfPrivateAttributes;
    private int numberOfProtectedAttributes;
    private int numberOfPublicAttributes;
    private int numberOfAttributes;
    private int numberOfCoupledClasses;
    private double cohesion;
    private int numberOfMethods;
    private int numberPublicMethods;
    private int numberUserDefinedAttributes;
    private int numberOfInheritedMethods;
    private int numberOfPolymorphicMethods;

    public ClassMetrics() {
    }

    public ClassMetrics(int numberOfAncestors, int numberOfSubclasses, int numberOfAttributes, int numberOfCoupledClasses, int numberOfMethods, int numberPublicMethods, int numberUserDefinedAttributes, int numberOfInheritedMethods, int numberOfPolymorphicMethods) {
        this.numberOfAncestors = numberOfAncestors;
        this.numberOfSubclasses = numberOfSubclasses;
        this.numberOfAttributes = numberOfAttributes;
        this.numberOfCoupledClasses = numberOfCoupledClasses;
        this.numberOfMethods = numberOfMethods;
        this.numberPublicMethods = numberPublicMethods;
        this.numberUserDefinedAttributes = numberUserDefinedAttributes;
        this.numberOfInheritedMethods = numberOfInheritedMethods;
        this.numberOfPolymorphicMethods = numberOfPolymorphicMethods;
    }

    public int getNumberOfAncestors() {
        return numberOfAncestors;
    }

    public void setNumberOfAncestors(int numberOfAncestors) {
        this.numberOfAncestors = numberOfAncestors;
    }

    public int getNumberOfSubclasses() {
        return numberOfSubclasses;
    }

    public void setNumberOfSubclasses(int numberOfSubclasses) {
        this.numberOfSubclasses = numberOfSubclasses;
    }

    public int getNumbeOfPrivateAttributes() {
        return numbeOfPrivateAttributes;
    }

    public void setNumbeOfPrivateAttributes(int numbeOfPrivateAttributes) {
        this.numbeOfPrivateAttributes = numbeOfPrivateAttributes;
    }

    public int getNumberOfProtectedAttributes() {
        return numberOfProtectedAttributes;
    }

    public void setNumberOfProtectedAttributes(int numberOfProtectedAttributes) {
        this.numberOfProtectedAttributes = numberOfProtectedAttributes;
    }

    public int getNumberOfPublicAttributes() {
        return numberOfPublicAttributes;
    }

    public void setNumberOfPublicAttributes(int numberOfPublicAttributes) {
        this.numberOfPublicAttributes = numberOfPublicAttributes;
    }

    public int getNumberOfCoupledClasses() {
        return numberOfCoupledClasses;
    }

    public void setNumberOfCoupledClasses(int numberOfCoupledClasses) {
        this.numberOfCoupledClasses = numberOfCoupledClasses;
    }

    public int getNumberOfMethods() {
        return numberOfMethods;
    }

    public void setNumberOfMethods(int numberOfMethods) {
        this.numberOfMethods = numberOfMethods;
    }

    public int getNumberPublicMethods() {
        return numberPublicMethods;
    }

    public void setNumberPublicMethods(int numberPublicMethods) {
        this.numberPublicMethods = numberPublicMethods;
    }

    public double getCohesion() {
        return cohesion;
    }

    public void setCohesion(double cohesion) {
        this.cohesion = cohesion;
    }

    public int getNumberUserDefinedAttributes() {
        return numberUserDefinedAttributes;
    }

    public void setNumberUserDefinedAttributes(int numberUserDefinedAttributes) {
        this.numberUserDefinedAttributes = numberUserDefinedAttributes;
    }

    public int getNumberOfPolymorphicMethods() {
        return numberOfPolymorphicMethods;
    }

    public void setNumberOfPolymorphicMethods(int numberOfPolymorphicMethods) {
        this.numberOfPolymorphicMethods = numberOfPolymorphicMethods;
    }

    public int getNumberOfInheritedMethods() {
        return numberOfInheritedMethods;
    }

    public void setNumberOfInheritedMethods(int numberOfInheritedMethods) {
        this.numberOfInheritedMethods = numberOfInheritedMethods;
    }

    public int getNumberOfAttributes() {
        return numberOfAttributes;
    }

    public void setNumberOfAttributes(int numberOfAttributes) {
        this.numberOfAttributes = numberOfAttributes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.numberOfAncestors;
        hash = 17 * hash + this.numberOfSubclasses;
        hash = 17 * hash + this.numbeOfPrivateAttributes;
        hash = 17 * hash + this.numberOfProtectedAttributes;
        hash = 17 * hash + this.numberOfPublicAttributes;
        hash = 17 * hash + this.numberOfCoupledClasses;
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.cohesion) ^ (Double.doubleToLongBits(this.cohesion) >>> 32));
        hash = 17 * hash + this.numberOfMethods;
        hash = 17 * hash + this.numberPublicMethods;
        hash = 17 * hash + this.numberUserDefinedAttributes;
        hash = 17 * hash + this.numberOfInheritedMethods;
        hash = 17 * hash + this.numberOfPolymorphicMethods;
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
        final ClassMetrics other = (ClassMetrics) obj;
        if (this.numberOfAncestors != other.numberOfAncestors) {
            return false;
        }
        if (this.numberOfSubclasses != other.numberOfSubclasses) {
            return false;
        }
        if (this.numbeOfPrivateAttributes != other.numbeOfPrivateAttributes) {
            return false;
        }
        if (this.numberOfProtectedAttributes != other.numberOfProtectedAttributes) {
            return false;
        }
        if (this.numberOfPublicAttributes != other.numberOfPublicAttributes) {
            return false;
        }
        if (this.numberOfCoupledClasses != other.numberOfCoupledClasses) {
            return false;
        }
        if (Double.doubleToLongBits(this.cohesion) != Double.doubleToLongBits(other.cohesion)) {
            return false;
        }
        if (this.numberOfMethods != other.numberOfMethods) {
            return false;
        }
        if (this.numberPublicMethods != other.numberPublicMethods) {
            return false;
        }
        if (this.numberUserDefinedAttributes != other.numberUserDefinedAttributes) {
            return false;
        }
        if (this.numberOfInheritedMethods != other.numberOfInheritedMethods) {
            return false;
        }
        if (this.numberOfPolymorphicMethods != other.numberOfPolymorphicMethods) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Metrics{" + "numberOfAncestors=" + numberOfAncestors + ", numberOfSubclasses=" + numberOfSubclasses + ", numbeOfPrivateAttributes=" + numbeOfPrivateAttributes + ", numberOfProtectedAttributes=" + numberOfProtectedAttributes + ", numberOfPublicAttributes=" + numberOfPublicAttributes + ", numberOfCoupledClasses=" + numberOfCoupledClasses + ", cohesion=" + cohesion + ", numberOfMethods=" + numberOfMethods + ", numberPublicMethods=" + numberPublicMethods + ", numberUserDefinedAttributes=" + numberUserDefinedAttributes + ", numberOfInheritedMethods=" + numberOfInheritedMethods + ", numberOfPolymorphicMethods=" + numberOfPolymorphicMethods + '}';
    }

}
