/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import elements.metrics.ClassMetrics;
import elements.Class;
import elements.metrics.DesignProperties;
import elements.Program;
import java.util.List;

/**
 *
 * @author Thainá Mariani
 */
public class DesignPropertiesCalculator {

    public DesignPropertiesCalculator() {
    }

    public DesignProperties calculateDesignProperties(Program program) {
        List<Class> classes = program.getClasses();
        List<Class> interfaces = program.getInterfaces();
        return calculateDesignProperties(classes, interfaces);
    }

    public DesignProperties calculateDesignProperties(List<Class> classes, List<Class> interfaces) {
        double designSize = calculateDesignSizeClasses(classes, interfaces);
        double hierarchies = calculateNumberHierarchies(classes);
        double abstraction = calculateAverageNumberAncestors(classes);
        double encapsulation = calculateAverageDataAcessMetric(classes);
        double coupling = calculateAverageDirectClassCoupling(classes, interfaces);
        double cohesion = calculateAverageCohesionAmongMethods(classes, interfaces);
        double composition = calculateAverageMeasureAggregation(classes);
        double inheritance = calculateAverageMeasureOfFunctionAbstraction(classes);
        double polymorphism = calculateAverageNumberPolymorphicMethods(classes);
        double messaging = calculateClassInterfaceSize(classes);
        double complexity = calculateAverageNumberMethods(classes, interfaces);
        DesignProperties designProperties = new DesignProperties(designSize, hierarchies, abstraction, encapsulation, coupling, cohesion, composition, inheritance, polymorphism, messaging, complexity);
        return designProperties;
    }

    ///Design Size in Classes (DSC): calculate number of classes and interfaces
    //related to design property Design Size
    public double calculateDesignSizeClasses(List<Class> classes, List<Class> interfaces) {
        return classes.size() + interfaces.size();
    }

    //Number of Hierarchies (NOH): calculate number of hierarchies by considering only root classes
    //related to design property Hierarchies
    public double calculateNumberHierarchies(List<Class> classes) {
        int numberHierarchies = 0;
        for (Class classe : classes) {
            if ((classe.getMetrics().getNumberOfSubclasses() > 0) && (classe.getMetrics().getNumberOfAncestors() == 0)) {
                numberHierarchies++;
            }
        }
        return numberHierarchies;
    }

    //Average Number of Ancestors (ANA): calculate number of ancestors (average of classes)
    //related to design property Abstraction
    public double calculateAverageNumberAncestors(List<Class> classes) {
        double countAncestors = 0;
        for (Class classe : classes) {
            countAncestors += classe.getMetrics().getNumberOfAncestors();
        }
        return countAncestors / (double) classes.size();
    }

    //Average Data Acess Metric (DAM): calculate the ratio of the number of private/protected attributes to the total number of attributes declared in a class (average of classes)
    //related to design property Encapsulation
    public double calculateAverageDataAcessMetric(List<Class> classes) {
        double countRatio = 0;
        for (Class classe : classes) {
            ClassMetrics metrics = classe.getMetrics();
            if (metrics.getNumberOfAttributes() == 0) {
                countRatio += 0;
            } else {
                countRatio += (metrics.getNumbeOfPrivateAttributes() + metrics.getNumberOfProtectedAttributes()) / metrics.getNumberOfAttributes();
            }
        }
        return countRatio / (double) classes.size();
    }

    //Average Direct Class Coupling (DCC): calculate the number of classes that a class is direclty related to
    //related to design property Coupling
    public double calculateAverageDirectClassCoupling(List<Class> classes, List<Class> interfaces) {
        double countCoupledClasses = 0;

        for (Class classe : classes) {
            countCoupledClasses += classe.getMetrics().getNumberOfCoupledClasses();
        }

        for (Class interfac : interfaces) {
            countCoupledClasses += interfac.getMetrics().getNumberOfCoupledClasses();
        }

        return countCoupledClasses / (double) (classes.size() + interfaces.size());
    }

    //Average Cohesion Among Methods in Class (CAM): calculate the relatedness among methods of a class
    //related to design property Cohesion
    public double calculateAverageCohesionAmongMethods(List<Class> classes, List<Class> interfaces) {
        double countCohesion = 0;

        for (Class classe : classes) {
            countCohesion += classe.getMetrics().getCohesion();
        }

        for (Class interfac : interfaces) {
            countCohesion += interfac.getMetrics().getCohesion();
        }

        return countCohesion / (double) (classes.size() + interfaces.size());
    }

    //Average of Measure of Aggregation (MFA): Number of classes declarations whose types are user defined classes
    //related to design property Composition
    public double calculateAverageMeasureAggregation(List<Class> classes) {
        double numberUserDefinedAttributes = 0;
        for (Class classe : classes) {
            numberUserDefinedAttributes += classe.getMetrics().getNumberUserDefinedAttributes();
        }

        return numberUserDefinedAttributes / (double) classes.size();
    }

    //Average Measure of Functional Abstraction (MFA): Ration of the number of methods inherited by a class to the total number of the methods of the class
    //related to design property Inheritance
    public double calculateAverageMeasureOfFunctionAbstraction(List<Class> classes) {
        double ratio = 0;
        for (Class classe : classes) {
            double numberMethods = classe.getMethods().size();
            if (numberMethods > 0) {
                ratio += (double) classe.getMetrics().getNumberOfInheritedMethods() / (double) numberMethods;
            }
        }
        return ratio / (double) classes.size();
    }

    //Average Number of Polymorphic Methods (NOP): methods that can have polymorphic behaviour
    //related to the design property Polymorphism
    public double calculateAverageNumberPolymorphicMethods(List<Class> classes) {
        double numberPolymorphicMethods = 0;
        for (Class classe : classes) {
            numberPolymorphicMethods += classe.getMetrics().getNumberOfPolymorphicMethods();
        }

        return numberPolymorphicMethods / (double) classes.size();
    }

    //Class Interface Size (CIS): average of number of public methods in a class
    //related to the design property Messaging
    public double calculateClassInterfaceSize(List<Class> classes) {
        double numberPublicMethods = 0;
        for (Class classe : classes) {
            numberPublicMethods += classe.getMetrics().getNumberPublicMethods();
        }
        return numberPublicMethods / (double) classes.size();
    }

    //Average Number of Methods (NOM): average of number of methods in a class
    //related to the design property Complexity
    public double calculateAverageNumberMethods(List<Class> classes, List<Class> interfaces) {
        double numberMethods = 0;
        for (Class classe : classes) {
            numberMethods += classe.getMetrics().getNumberOfMethods();
        }
        for (Class aInterface : interfaces) {
            numberMethods += aInterface.getMetrics().getNumberOfMethods();
        }
        return numberMethods / (double) (classes.size() + interfaces.size());
    }

}
