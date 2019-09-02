/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements.metrics;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Thainá Mariani
 */
public class QualityAttributes {

    private double reusability;
    private double flexibility;
    private double understandability;
    private double functionality;
    private double extensibility;
    private double effectiveness;

    public QualityAttributes() {
    }

    public QualityAttributes(double reusability, double flexibility, double understandability, double functionality, double extensibility, double effectiveness) {
        this.reusability = reusability;
        this.flexibility = flexibility;
        this.understandability = understandability;
        this.functionality = functionality;
        this.extensibility = extensibility;
        this.effectiveness = effectiveness;
    }

    public double getReusability() {
        return reusability;
    }

    public void setReusability(double reusability) {
        this.reusability = reusability;
    }

    public double getFlexibility() {
        return flexibility;
    }

    public void setFlexibility(double flexibility) {
        this.flexibility = flexibility;
    }

    public double getUnderstandability() {
        return understandability;
    }

    public void setUnderstandability(double understandability) {
        this.understandability = understandability;
    }

    public double getFunctionality() {
        return functionality;
    }

    public void setFunctionality(double functionality) {
        this.functionality = functionality;
    }

    public double getExtensibility() {
        return extensibility;
    }

    public void setExtensibility(double extensibility) {
        this.extensibility = extensibility;
    }

    public double getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(double effectiveness) {
        this.effectiveness = effectiveness;
    }

    @JsonIgnore
    public double getSum() {
        return this.reusability
                + this.flexibility
                + this.understandability
                + this.functionality
                + this.extensibility
                + this.effectiveness;
    }

    @Override
    public String toString() {
        return "QualityAttributes{" + "reusability=" + reusability + ", flexibility=" + flexibility + ", understandability=" + understandability + ", functionality=" + functionality + ", extensibility=" + extensibility + ", effectiveness=" + effectiveness + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.reusability) ^ (Double.doubleToLongBits(this.reusability) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.flexibility) ^ (Double.doubleToLongBits(this.flexibility) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.understandability) ^ (Double.doubleToLongBits(this.understandability) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.functionality) ^ (Double.doubleToLongBits(this.functionality) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.extensibility) ^ (Double.doubleToLongBits(this.extensibility) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.effectiveness) ^ (Double.doubleToLongBits(this.effectiveness) >>> 32));
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
        final QualityAttributes other = (QualityAttributes) obj;
        if (Double.doubleToLongBits(this.reusability) != Double.doubleToLongBits(other.reusability)) {
            return false;
        }
        if (Double.doubleToLongBits(this.flexibility) != Double.doubleToLongBits(other.flexibility)) {
            return false;
        }
        if (Double.doubleToLongBits(this.understandability) != Double.doubleToLongBits(other.understandability)) {
            return false;
        }
        if (Double.doubleToLongBits(this.functionality) != Double.doubleToLongBits(other.functionality)) {
            return false;
        }
        if (Double.doubleToLongBits(this.extensibility) != Double.doubleToLongBits(other.extensibility)) {
            return false;
        }
        if (Double.doubleToLongBits(this.effectiveness) != Double.doubleToLongBits(other.effectiveness)) {
            return false;
        }
        return true;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
