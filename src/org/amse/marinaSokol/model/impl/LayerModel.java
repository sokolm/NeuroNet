package org.amse.marinaSokol.model.impl;

import org.amse.marinaSokol.model.interfaces.object.net.IActivationFunctor;
import org.amse.marinaSokol.model.interfaces.object.net.IConnection;
import org.amse.marinaSokol.model.impl.object.activationFunctions.*;
import org.amse.marinaSokol.model.interfaces.schema.IConnectionSchema;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import org.amse.marinaSokol.model.interfaces.object.net.ActivationFunctorType;

public class LayerModel extends UsualLayerModel implements ILayerModel {
    private List<IConnectionModel> myInputConnections;

    private IActivationFunctor myFunctor;


    /**
     * @param x - координата(абсциса) верхнего левого угла сло€
     * @param y - координата(ордината) верхнего левого угла сло€
     * @param width - ширина сло€
     * @param height - высота сло€
     * по умолчанию у сло€ 0 нейронов и ф-€ активации null(Ќе задана)
     * */
    LayerModel(int x, int y, int width, int height) {
        super(x, y, width, height);
        myInputConnections = new LinkedList<IConnectionModel>();
        myFunctor = new ActivationFunctorLinear();
    }

    public List<? extends IConnectionSchema> getInputConnectionsSchema() {
        return Collections.unmodifiableList(myInputConnections);
    }

    IConnectionSchema addInArrow(IConnectionSchema connectionSchema) {
        myInputConnections.add((IConnectionModel)connectionSchema);
        return connectionSchema;
    }

    @SuppressWarnings({"SuspiciousMethodCalls"})
    void removeInArrow(IConnectionSchema connectionSchema) {
        myInputConnections.remove(connectionSchema);
    }

    /**
     * This method connect this layerSchema(source) with another layerSchema(dest)
     * with arrow
     * @param layerSchema - слой, в который присоедин€ем стрелку
     * @param direct - тип стрелки, если true , то пр€ма€, иначе обратна€
     * @return arrow - возвращет стрелку, если соединение сделать нельз€, то null
     */
    IConnectionSchema connect(ILayerSchema layerSchema, boolean direct) {
        IConnectionSchema connectionSchema = new ConnectionModel(this, layerSchema, direct);
        addOutArrow(connectionSchema);
        ((LayerModel)layerSchema).addInArrow(connectionSchema);
        return connectionSchema;
    }

    /**
     * ќтсоедин€ет все вход€щие и выход€щие стрелки от сло€
     */
    void unbindAllArrows() {
        for (IConnectionSchema a: myInputConnections) {
            ((UsualLayerModel)a.getSourceLayerSchema()).removeOutArrow(a);
        }
        super.unbindAllArrows();
    }


    public void addInputConnection(IConnection newInput) {
        myInputConnections.add((IConnectionModel)newInput);
    }

    public List<? extends IConnection> getInputConnections() {
        return Collections.unmodifiableList(myInputConnections);
    }

    public IActivationFunctor getActivation() {
        return myFunctor;
    }

    public void setActivation(ActivationFunctorType type) {
    	switch (type) {    	
    	case LINEAR :
    		myFunctor = new ActivationFunctorLinear();
    		break;
    	case LOGISTIC :
       		myFunctor = new ActivationFunctorLogistic();
    		break; 
    	case NULL:
    		myFunctor = null;
    		break;
    	}
    }

    /**
     * This method evaluate factivation from sum
     *@return output from thia layer after function activation
     * ѕередаем сюда сумму на нейронах, и получаем результат ф-ии активации
     * */
    public double[] getActivationResult(double[] neuronSum) {
        double[] outputs = new double[getNeuronsNumber()];
        for (int i = 0; i < getNeuronsNumber(); i++) {
           outputs[i] = getActivation().getFunction(neuronSum[i]);
        }
        return outputs;
    }

    public void setNeuronsNumber(int number) {
        super.setNeuronsNumber(number);
        for (IConnection connection: myInputConnections) {
            connection.createNewWeights();
        }
    }


    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());

        sb.append("input arrows:\n");
        for (IConnectionSchema a : myInputConnections) {
            sb.append("the input arrow:\n");
            sb.append(a);
            sb.append("\n");
        }

        sb.append("Activation function: ").append(myFunctor.getNameFunction()).append("\n");

        return sb.toString();
    }
}
