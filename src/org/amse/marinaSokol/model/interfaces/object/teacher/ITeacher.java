package org.amse.marinaSokol.model.interfaces.object.teacher;

import org.amse.marinaSokol.model.interfaces.object.net.ILayer;
import org.amse.marinaSokol.model.interfaces.object.net.IUsualLayer;
import org.amse.marinaSokol.model.impl.object.InputLayerData;

import java.util.Map;

public interface ITeacher {
      void teach(Map<IUsualLayer, InputLayerData> inputLayerData);
      void setSPEED(double speed);
      void setSTEPS(int steps);
      Map<ILayer, InputLayerData> getOutputData();

}

