package org.amse.marinaSokol.model.interfaces.object.user;

import org.amse.marinaSokol.model.interfaces.object.net.ILayer;
import org.amse.marinaSokol.model.interfaces.object.net.IUsualLayer;
import org.amse.marinaSokol.model.interfaces.object.net.IInputLayer;

import java.util.Map;

public interface IUser<T> {
    void run(Map<IInputLayer, T> inputLayerData);
    void setNumPatterns(int numberOfPatterns);
    Map<ILayer, T> getOutputData();
}
