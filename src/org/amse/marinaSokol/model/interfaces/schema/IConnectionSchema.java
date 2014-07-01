package org.amse.marinaSokol.model.interfaces.schema;

/**
 * Makes a description of an arrow
 * @author Sokol Marina
 * */
public interface IConnectionSchema extends IShapeSchema {

    /**
     * возвращает слой, из которого выходит стрелка.
     * @return  слой, из которого выходит стрелка.
     * */
    IUsualLayerSchema getSourceLayerSchema();

    /**
     * возвращает слой, в который входит стрелка.
     * @return слой, в который входит стрелка.
     * */
    ILayerSchema getDestLayerSchema();

    /**
     * Устанавливает слой, в который входит стрелка.
     * @param layerSchema  - слой в который будет входить стрелка.
     * */
    void setDestLayerSchema(ILayerSchema layerSchema);

    /**
     *@return Возвразает true, если это стрелка прямая и false иначе.
     * */
    boolean isDirect();

    /**
     * Возвращает рассояние между точкой и соединением.
     * @param x  - координата точки
     * @param y  - координата точки
     * @return близость точки (x, y) к линии
     * */
    int proximity(int x, int y);
}
