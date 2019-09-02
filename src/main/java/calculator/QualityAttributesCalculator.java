/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import elements.metrics.DesignProperties;
import elements.metrics.QualityAttributes;

/**
 *
 * @author Thainá Mariani
 */
public class QualityAttributesCalculator {

    public QualityAttributesCalculator() {
    }

    public QualityAttributes calculateQualityAttributes(DesignProperties designProperties) {
        double designSize = designProperties.getDesignSize();
        double hierarchies = designProperties.getHierarchies();
        double abstraction = designProperties.getAbstraction();
        double encapsulation = designProperties.getEncapsulation();
        double coupling = designProperties.getCoupling();
        double cohesion = designProperties.getCohesion();
        double composition = designProperties.getComposition();
        double inheritance = designProperties.getInheritance();
        double polymorphism = designProperties.getPolymorphism();
        double messaging = designProperties.getMessaging();
        double complexity = designProperties.getComplexity();

        double effectiveness = calculateEffectiveness(abstraction, encapsulation, composition, inheritance, polymorphism);
        double extensibility = calculateExtensibility(abstraction, coupling, inheritance, polymorphism);
        double flexibility = calculateFlexibility(encapsulation, coupling, composition, polymorphism);
        double functionality = calculateFunctionality(cohesion, polymorphism, messaging, designSize, hierarchies);
        double reusability = calculateReusability(coupling, cohesion, messaging, designSize);
        double understandability = calculateUnderstandability(abstraction, encapsulation, coupling, cohesion, polymorphism, complexity, designSize);

        QualityAttributes qualityAttributes = new QualityAttributes(reusability, flexibility, understandability, functionality, extensibility, effectiveness);
        return qualityAttributes;
    }

    public double calculateReusability(double coupling, double cohesion, double messaging, double designSize) {
        return -0.25 * coupling + 0.25 * cohesion + 0.5 * messaging + 0.5 * designSize;
    }

    public double calculateFlexibility(double encapsulation, double coupling, double composition, double polymorphism) {
        return 0.25 * encapsulation - 0.25 * coupling + 0.5 * composition + 0.5 * polymorphism;
    }

    public double calculateUnderstandability(double abstraction, double encapsulation, double coupling, double cohesion, double polymorphism, double complexity, double designSize) {
        return -0.33 * abstraction + 0.33 * encapsulation - 0.33 * coupling + 0.33 * cohesion - 0.33 * polymorphism - 0.33 * complexity - 0.33 * designSize;
    }

    public double calculateFunctionality(double cohesion, double polymorphism, double messaging, double designSize, double hierarchies) {
        return 0.12 * cohesion + 0.22 * polymorphism + 0.22 * messaging + 0.22 * designSize + 0.22 * hierarchies;
    }

    public double calculateExtensibility(double abstraction, double coupling, double inheritance, double polymorphism) {
        return 0.5 * abstraction - 0.5 * coupling + 0.5 * inheritance + 0.5 * polymorphism;
    }

    public double calculateEffectiveness(double abstraction, double encapsulation, double composition, double inheritance, double polymorphism) {
        return 0.2 * abstraction + 0.2 * encapsulation + 0.2 * composition + 0.2 * inheritance + 0.2 * polymorphism;
    }

}
