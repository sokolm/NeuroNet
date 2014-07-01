package org.amse.marinaSokol.model.interfaces.schema;

import java.util.List;

public interface IUsualLayerSchema extends IShapeSchema {

    /**
     * @return координату(абсциссу) левого верхнего угла слоя
     */
    int getX();

    /**
     * @return координату(ординату) левого верхнего угла слоя
     */
    int getY();

    /**
     * @return ширину слоя
     */
    int getWidth();

    /**
     * @return высоту слоя
     */
    int getHeight();

    /**
     * перемещает слой к точке  (x, y)
     * @param x - координаты по оси x
     * @param y - координата по оси y
     */
    void moveTo(int x, int y);

    /**
     * Устанавливает новые размеры слоя
     * @param width  - желаемая ширина слоя
     * @param height - желаемая высота слоя
     */
    void resize(int width, int height);

    /**
     * @return Возвращает true если точка (х, у) содержится в слое и false иначе
     * @param x - координата точки по по оси х
     * @param y - координата точки по оси y
     */
    boolean containsPoint(int x, int y);

    /**
     * @return возвращает список стрелок, выходящих из слоя
     */
    List<? extends IConnectionSchema> getOutputConnectionsSchema();

    /**
     * устанавливает номер слоя после топологической сортировки
     * @param id - номер слоя
     * */
    void setId(int id);

    /**
     * Возвращает номер слоя после топологической сортировки
     * @return id - номер слоя
     * */
    int getId();

}
