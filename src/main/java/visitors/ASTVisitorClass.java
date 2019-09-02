/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitors;

import builder.ProgramBuilder;
import elements.Class;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 *
 * @author Thainá Mariani
 */
public class ASTVisitorClass extends ASTVisitor {

    private CompilationUnit unit;
    private List<elements.Class> classes;

    public ASTVisitorClass() {
    }

    public ASTVisitorClass(CompilationUnit unit) {
        this.unit = unit;
        classes = new ArrayList<>();
    }

    @Override
    public boolean visit(TypeDeclaration node) {
        if (node.resolveBinding().isClass() || node.resolveBinding().isInterface()) {
            ProgramBuilder programBuilder = new ProgramBuilder();
            List<ImportDeclaration> imports = new ArrayList<>();
            for (Object aImport : this.unit.imports()) {
                imports.add((ImportDeclaration) aImport);
            }

            Class c = programBuilder.buildClass(node, imports);
            classes.add(c);
            
        }
        return true;
    }

    public CompilationUnit getUnit() {
        return unit;
    }

    public void setUnit(CompilationUnit unit) {
        this.unit = unit;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

}
