/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import elements.Class;
import elements.Field;
import elements.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

/**
 *
 * @author thaina
 */
public class MetricsCalculator {

    public MetricsCalculator() {
    }

    public int calculateNumberMethods(elements.Class c) {
        int numberOfMethods = 0;

        for (Method method : c.getMethods()) {
            if (!method.isConstructor()) {
                numberOfMethods++;
            }
        }
        return numberOfMethods;
    }

    public int calculateNumberFields(elements.Class c) {
        return c.getFields().size();
    }

    public int calculateNumberPrivateAttributes(elements.Class c) {
        int numberPrivateAttributes = 0;

        for (Field field : c.getFields()) {
            for (Modifier modifier : field.getModifiers()) {
                if (modifier.isPrivate()) {
                    numberPrivateAttributes++;
                }
            }
        }
        return numberPrivateAttributes;
    }

    public int calculateNumberProtectedAttributes(elements.Class c) {
        int numberProtectedAttributes = 0;
        for (Field field : c.getFields()) {
            for (Modifier modifier : field.getModifiers()) {
                if (modifier.isProtected()) {
                    numberProtectedAttributes++;
                }
            }
        }
        return numberProtectedAttributes;
    }

    public int calculateNumberPublicAttributes(elements.Class c) {
        int numberPublicAttributes = c.getFields().size();
        for (Field field : c.getFields()) {
            for (Modifier modifier : field.getModifiers()) {
                //isPublic() only returns true if the modifier public is explicit declared
                //by java rules the modifier is public when no other modifier is explicit declared
                //this is why this code descarts both private and protected modifiers instead of looking for public modifiers
                if (modifier.isPrivate() || modifier.isProtected()) {
                    numberPublicAttributes--;
                }
            }
        }
        return numberPublicAttributes;
    }

    public int calculateNumberPublicMethods(elements.Class c) {
        int numberPublicMethods = c.getMethods().size();
        for (Method method : c.getMethods()) {
            if (method.isConstructor()) {
                numberPublicMethods--;
            } else {
                for (Modifier modifier : method.getModifiers()) {
                    //isPublic() only returns true if the modifier public is explicit declared
                    //by java rules the modifier is public when no other modifier is explicit declared
                    //this is why this code descarts both private and protected modifiers instead of looking for public modifiers
                    if (modifier.isPrivate() || modifier.isProtected()) {
                        numberPublicMethods--;
                    }
                }
            }
        }
        return numberPublicMethods;
    }

    //it calculates the number of classes related to a class, based on attribute declarations and parameters passed by methods
    public int calculateNumberCoupledClasses(elements.Class c) {
        List<Type> classTypes = new ArrayList<>();

        for (Field field : c.getFields()) {
            classTypes.add(field.getNode().getType());
        }

        for (Method method : c.getMethods()) {
            List<SingleVariableDeclaration> parameters = method.getParameters();
            for (SingleVariableDeclaration parameter : parameters) {
                classTypes.add(parameter.getType());
            }
        }

        Set<String> differentUserDefinedTypes = getDifferentUserDefinedTypes(classTypes);
        if (c.getNode() != null) {
            differentUserDefinedTypes.removeIf(x -> x.equals(c.getNode().resolveBinding().getQualifiedName()));
        }
        return differentUserDefinedTypes.size();
    }

    //it calculates the cohesion of a class based on the existing parameters of the methods
    public double calculateCohesionClass(elements.Class c) {
        //list of types found in the parameters of all methods of the class
        List<Type> classTypes = new ArrayList<>();
        double numberMethodsTypes = 0;
        for (Method method : c.getMethods()) {
            //list of types found in the parameters of a method
            List<Type> methodTypes = new ArrayList<>();
            List<SingleVariableDeclaration> parameters = method.getParameters();
            for (SingleVariableDeclaration parameter : parameters) {
                methodTypes.add(parameter.getType());
                classTypes.add(parameter.getType());
            }
            numberMethodsTypes += getTypes(methodTypes, c).size();
        }
        double numberOfClassTypes = getTypes(classTypes, c).size();
        double numberOfMethods = c.getMethods().size();

        if ((numberOfClassTypes * numberOfMethods) == 0) {
            return 0;
        } else {
            //cohesion function
            return numberMethodsTypes / (numberOfClassTypes * numberOfMethods);
        }
    }

    public Set<String> getDifferentUserDefinedTypes(List<Type> elementsTypes) {
        Set<String> differentElementTypes = new HashSet<>();
        differentElementTypes.addAll(getUserDefinedTypes(elementsTypes));
        return differentElementTypes;
    }

    //it returns a description of different types presented on a list of types, eg: "List<A>, A, B, int, int, double" returns types "List, A, B, int, and double"
    public Set<String> getTypes(List<Type> elementsTypes, Class c) {
        Set<String> types = new HashSet<>();
        for (Type elementType : elementsTypes) {
            types.add(getType(c, elementType));
            if (elementType.isParameterizedType()) {
                types.addAll(getParameterizedType(c, elementType));
            }
        }
        return types;
    }

    public String getType(Class c, Type elementType) {
        String name = "";
        String qualifiedName = "";
        if (elementType.isWildcardType()) {
            name = elementType.resolveBinding().getErasure().getName();
            qualifiedName = elementType.resolveBinding().getErasure().getQualifiedName();
        } else {
            name = elementType.resolveBinding().getTypeDeclaration().getName();
            qualifiedName = elementType.resolveBinding().getTypeDeclaration().getQualifiedName();
        }

        String path = findLibrary(c, name);
        if (elementType.resolveBinding().isPrimitive() || elementType.resolveBinding().isFromSource() || path.equals("")) {
            return qualifiedName;
        }
        return path + name;
    }

    public String findLibrary(Class c, String type) {
        for (ImportDeclaration aImport : c.getImports()) {
            String importName = aImport.getName().toString();
            int lastIndexOf = importName.lastIndexOf(".");
            String className = importName.substring(lastIndexOf + 1, importName.length());
            importName = importName.substring(0, lastIndexOf + 1);
            if (className.equals(type)) {
                return importName;
            }
        }
        return "";
    }

    //collects the type passed as parameters, eg: List<Integer>
    private Set<String> getParameterizedType(Class c, Type elementType) {
        Set<String> types = new HashSet<>();
        ParameterizedType type = (ParameterizedType) elementType;
        for (Iterator it = type.typeArguments().iterator(); it.hasNext();) {
            Type typeArgument = (Type) it.next();
            types.add(getType(c, typeArgument));
            if (typeArgument.isParameterizedType()) {
                types.addAll(getParameterizedType(c, typeArgument));
            }
        }
        return types;
    }

    //it returns a description of all the user-defined types presented on a list of types, eg: "List<A>, A, B, int, int, double" returns types "A, A and B"
    public List<String> getUserDefinedTypes(List<Type> elementsTypes) {
        List<String> userDefinedTypes = new ArrayList<>();
        for (Type elementType : elementsTypes) {
            if (elementType.resolveBinding().isFromSource()) {
                userDefinedTypes.add(elementType.resolveBinding().getTypeDeclaration().getQualifiedName());
            }
            //collects the user-defined types passed as parameters, eg: List<A>
            if (elementType.isParameterizedType()) {
                ParameterizedType type = (ParameterizedType) elementType;
                for (Iterator it = type.typeArguments().iterator(); it.hasNext();) {
                    Type typeArgument = (Type) it.next();
                    if (typeArgument.resolveBinding().isFromSource()) {
                        userDefinedTypes.add(typeArgument.resolveBinding().getQualifiedName());
                    } else if (typeArgument.isWildcardType()) {
                        ITypeBinding typeErasure = typeArgument.resolveBinding().getErasure();
                        if (typeErasure.isFromSource()) {
                            userDefinedTypes.add(typeErasure.getQualifiedName());
                        }
                    }
                }
            }
        }
        return userDefinedTypes;
    }

    //if the element or one of its parameters is userdefined type
    public boolean isUserDefinedType(Type elementType) {
        if (elementType.resolveBinding().isFromSource()) {
            return true;
        }
        if (elementType.isParameterizedType()) {
            ParameterizedType type = (ParameterizedType) elementType;
            for (Iterator it = type.typeArguments().iterator(); it.hasNext();) {
                Type typeArgument = (Type) it.next();
                if (typeArgument.resolveBinding().isFromSource()) {
                    return true;
                } else if (typeArgument.isWildcardType()) {
                    ITypeBinding typeErasure = typeArgument.resolveBinding().getErasure();
                    if (typeErasure.isFromSource()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //it calculates the number of methods of a class that can exhibit polymorfic behavior
    //it excludes methods that can not be overrided, which are constructors, private methods, static methods, and final methods
    public int calculateNumberOfPolymorphicMethods(elements.Class c) {
        //check if the class is final
        if (c.getNode() != null) {
            List<Modifier> classModifiers = getModifiers(c.getNode());
            for (Modifier classModifier : classModifiers) {
                if (classModifier.isFinal()) {
                    return 0;
                }
            }
        }

        //if the class is not final its methods are analyzed
        int numberOfpolymorphicMethods = 0;
        for (Method method : c.getMethods()) {
            boolean allowOverride = true;

            if (method.isConstructor()) {
                allowOverride = false;
            } else {
                for (Modifier modifier : method.getModifiers()) {
                    if (modifier.isPrivate() || modifier.isStatic() || modifier.isFinal()) {
                        allowOverride = false;
                        break;
                    }
                }
            }

            if (allowOverride) {
                numberOfpolymorphicMethods++;
            }
        }
        return numberOfpolymorphicMethods;
    }

    public int calculateNumberUserDefinedAttributes(elements.Class c) {
        int numberUserDefinedAttributes = 0;
        for (Field field : c.getFields()) {
            if (isUserDefinedType(field.getNode().getType())) {
                numberUserDefinedAttributes++;
            }
        }
        return numberUserDefinedAttributes;
    }

    public int calculateNumberOfSubclasses(elements.Class c) {
        return c.getSubclasses().size();
    }

    public int calculateNumberOfAncestors(elements.Class c) {
        int numberOfAncestors = 0;
        if (c.hasSuperClass()) {
            numberOfAncestors = calculateNumberOfAncestors(c.getSuperclass(), numberOfAncestors + 1);
        }
        return numberOfAncestors;
    }

    public int calculateNumberOfAncestors(elements.Class c, int numberOfAncestors) {
        if (c.hasSuperClass()) {
            numberOfAncestors = calculateNumberOfAncestors(c.getSuperclass(), numberOfAncestors + 1);
        }
        return numberOfAncestors;
    }

    public int calculateNumberInheritedMethods(elements.Class c) {
        Set<Method> ancestorsMethods = new HashSet<>();
        getAncestorsMethods(c, ancestorsMethods);

        int numberInheritedMethods = 0;
        for (Method method : ancestorsMethods) {
            if (c.getMethods().contains(method)) {
                numberInheritedMethods++;
            }
        }

        return numberInheritedMethods;
    }

    public void getAncestorsMethods(elements.Class c, Set<Method> ancestorsMethods) {
        if (c.hasSuperClass()) {
            Class superclass = c.getSuperclass();
            ancestorsMethods.addAll(superclass.getMethods());
            getAncestorsMethods(superclass, ancestorsMethods);
        }
    }

    public List<Modifier> getModifiers(BodyDeclaration node) {
        List<Modifier> modifiers = new ArrayList<>();
        List<Object> objects = node.modifiers();
        for (Object object : objects) {
            if (object instanceof Modifier) {
                modifiers.add((Modifier) object);
            }
        }
        return modifiers;
    }

    public int calculateNumberParameters(Method method) {
        return method.getParameters().size();
    }

    public int calculateNumberParameterTypes(Method method, Class c) {
        List<Type> types = new ArrayList<>();

        List<SingleVariableDeclaration> parameters = method.getParameters();
        for (SingleVariableDeclaration parameter : parameters) {
            types.add(parameter.getType());
        }
        Set<String> methodTypes = getTypes(types, c);
        return methodTypes.size();
    }

    public int calculateNumberUserDefinedParameterTypes(Method method) {
        List<Type> types = new ArrayList<>();

        List<SingleVariableDeclaration> parameters = method.getParameters();
        for (SingleVariableDeclaration parameter : parameters) {
            types.add(parameter.getType());
        }
        Set<String> differentUserDefinedTypes = getDifferentUserDefinedTypes(types);
        return differentUserDefinedTypes.size();
    }

    public int calculateNumberStatements(Method method) {
        try {
            return method.getNode().getBody().statements().size();
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
