package org.amse.marinaSokol.model.impl.object.activationFunctions;

import org.amse.marinaSokol.model.interfaces.object.net.IActivationFunctor;


public class ActivationFunctorLinear implements IActivationFunctor {

    public ActivationFunctorLinear() {}

    public double getFunction(double x) {
        return x;
    }

    public double getDerivation(double x) {
        return 1;
    }

    public String getNameFunction() {
        return "linear";
    }
}
