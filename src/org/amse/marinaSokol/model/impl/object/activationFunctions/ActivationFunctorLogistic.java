package org.amse.marinaSokol.model.impl.object.activationFunctions;

import org.amse.marinaSokol.model.interfaces.object.net.IActivationFunctor;

public class ActivationFunctorLogistic implements IActivationFunctor {

    public double getFunction(double x){
        return 1 / (1 + Math.exp(-x));
    }

    public double getDerivation(double x){
        return getFunction(x) * (1 - getFunction(x));
    }

    public String getNameFunction() {
        return "logistic";
    }
}
