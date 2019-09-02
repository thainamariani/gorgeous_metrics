/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import java.util.List;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Modifier;

/**
 *
 * @author thaina
 */

public class Field extends Element {

    private FieldDeclaration node;
    List<Modifier> modifiers;

    public Field() {
    }

    public Field(FieldDeclaration node, String name, List<Modifier> modifiers) {
        this.node = node;
        this.name = name;
        this.modifiers = modifiers;
    }

    public FieldDeclaration getNode() {
        return node;
    }

    public void setNode(FieldDeclaration node) {
        this.node = node;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<Modifier> modifiers) {
        this.modifiers = modifiers;
    }

    @Override
    protected Field clone() throws CloneNotSupportedException {
        return (Field) super.clone();
    }

}
