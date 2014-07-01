package org.amse.marinaSokol.model.interfaces.schema;
import java.util.List;

/**
  * Makes a description of a layer
  * @author Sokol Marina
  */
public interface ILayerSchema extends IUsualLayerSchema {

    /**
     * @return возвращает список стрелок входящих в слой
     */
    List<? extends IConnectionSchema> getInputConnectionsSchema();
}
