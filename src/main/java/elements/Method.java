/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import elements.metrics.MethodMetrics;
import calculator.MetricsCalculator;
import java.util.List;
import java.util.Objects;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

/**
 *
 * @author Thainá Mariani
 */

public class Method extends Element {
    
    private MethodDeclaration node;
    private List<Modifier> modifiers;
    private Type returnType;
    private List<SingleVariableDeclaration> parameters;
    private boolean constructor;

    public Method() {
    }

    public Method(MethodDeclaration node, String name, List<Modifier> modifiers, Type returnType, List<SingleVariableDeclaration> parameters, boolean constructor) {
        this.node = node;
        this.name = name;
        this.modifiers = modifiers;
        this.returnType = returnType;
        this.parameters = parameters;
        this.constructor = constructor;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public List<SingleVariableDeclaration> getParameters() {
        return parameters;
    }

    public void setParameters(List<SingleVariableDeclaration> parameters) {
        this.parameters = parameters;
    }

    public MethodDeclaration getNode() {
        return node;
    }

    public void setNode(MethodDeclaration node) {
        this.node = node;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<Modifier> modifiers) {
        this.modifiers = modifiers;
    }

    public boolean isConstructor() {
        return constructor;
    }

    public void setConstructor(boolean constructor) {
        this.constructor = constructor;
    }

    @Override
    public MethodMetrics getMetrics() {
        return (MethodMetrics) metrics;
    }

    //equals and hashcode are checked based on the name of the method, type of return, and number, order and type of parameters
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.name.hashCode();
        for (SingleVariableDeclaration parameter : this.parameters) {
            hash = 67 * hash + parameter.getType().toString().hashCode();
        }
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
        final Method other = (Method) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.parameters.size(), other.parameters.size())) {
            return false;
        }

        for (int i = 0; i < this.parameters.size(); i++) {
            if (!Objects.equals(this.parameters.get(i).getType().toString(), other.parameters.get(i).getType().toString())) {
                return false;
            }
        }
        return true;
    }

    public void calculateMetrics(Class c) {
        MetricsCalculator calculator = new MetricsCalculator();
        MethodMetrics methodMetrics = new MethodMetrics();
        methodMetrics.setNumberParameters(calculator.calculateNumberParameters(this));
        methodMetrics.setNumberParameterTypes(calculator.calculateNumberParameterTypes(this, c));
        methodMetrics.setNumberUserDefinedParameterTypes(calculator.calculateNumberUserDefinedParameterTypes(this));
        methodMetrics.setNumberStatements(calculator.calculateNumberStatements(this));

        this.metrics = methodMetrics;
    }

    @Override
    protected Method clone() throws CloneNotSupportedException {
        return (Method) super.clone();
    }

}
