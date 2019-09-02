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
public class MethodMetrics extends Metrics {

    private int numberParameters;
    private int numberParameterTypes;
    private int numberUserDefinedParameterTypes;
    private int numberStatements;

    public MethodMetrics() {
    }

    public MethodMetrics(int numberParameters, int numberParameterTypes, int numberUserDefinedParameterTypes, int numberStatements) {
        this.numberParameters = numberParameters;
        this.numberParameterTypes = numberParameterTypes;
        this.numberUserDefinedParameterTypes = numberUserDefinedParameterTypes;
        this.numberStatements = numberStatements;
    }

    public int getNumberParameters() {
        return numberParameters;
    }

    public void setNumberParameters(int numberParameters) {
        this.numberParameters = numberParameters;
    }

    public int getNumberParameterTypes() {
        return numberParameterTypes;
    }

    public void setNumberParameterTypes(int numberParameterTypes) {
        this.numberParameterTypes = numberParameterTypes;
    }

    public int getNumberUserDefinedParameterTypes() {
        return numberUserDefinedParameterTypes;
    }

    public void setNumberUserDefinedParameterTypes(int numberUserDefinedParameterTypes) {
        this.numberUserDefinedParameterTypes = numberUserDefinedParameterTypes;
    }

    public int getNumberStatements() {
        return numberStatements;
    }

    public void setNumberStatements(int numberStatements) {
        this.numberStatements = numberStatements;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.numberParameters;
        hash = 79 * hash + this.numberParameterTypes;
        hash = 79 * hash + this.numberUserDefinedParameterTypes;
        hash = 79 * hash + this.numberStatements;
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
        final MethodMetrics other = (MethodMetrics) obj;
        if (this.numberParameters != other.numberParameters) {
            return false;
        }
        if (this.numberParameterTypes != other.numberParameterTypes) {
            return false;
        }
        if (this.numberUserDefinedParameterTypes != other.numberUserDefinedParameterTypes) {
            return false;
        }
        if (this.numberStatements != other.numberStatements) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Metrics{" + "numberParameters=" + numberParameters + ", numberParameterTypes=" + numberParameterTypes + ", numberUserDefinedParameterTypes=" + numberUserDefinedParameterTypes + ", numberStatements=" + numberStatements + '}';
    }

}
