package org.amse.marinaSokol.model.interfaces.schema;
import org.amse.marinaSokol.model.interfaces.object.net.INeuroNet;

import java.util.List;

/**
  * Makes a description of a neuroNet
  * @author Sokol Marina
  */
public interface INeuroNetSchema {

    /**
     * Метод добавляет слой в сеть
     * @param x - коодината(абсцисса) верхнего левого угла
     * @param y - координата(ордината) верхнего лквого угла
     * @param width - ширина слоя
     * @param height - высота слоя
     * @return добавленный слой
     */
    ILayerSchema addLayerSchema(int x, int y, int width, int height);

    /**
     * Метод добавляет входной слой в сеть
     * @param x - коодината(абсцисса) верхнего левого угла
     * @param y - координата(ордината) верхнего лквого угла
     * @param width - ширина входного слоя
     * @param height - высота входного слоя
     * @return добавленный входной слой
     */
    IInputLayerSchema addInputLayerSchema(int x, int y, int width, int height);
    
    /**
     * Метод добавляет выходной слой в сеть
     * @param x - коодината(абсцисса) верхнего левого угла
     * @param y - координата(ордината) верхнего лквого угла
     * @param width - ширина выходного слоя
     * @param height - высота выходного слоя
     * @return добавленный выходной слой
     */
    IOutputLayerSchema addOutputLayerSchema(int x, int y, int width, int height);

    /**
     * Метод удаляет слой из сети
     * @param layerSchema - удаляемый слой
     */
    void removeLayerSchema(IUsualLayerSchema layerSchema);

    /**
     * Возвращает слой, которому принадлежит точка (x, y) и
     * перемещает его в конец списка.
     * Если таких слоев несколько, возвращает последний в списке.
     * Если таких слоев нет, возвращает null.
     * @param x - координаты точки
     * @param y  - координаты точки
     * @return слой, которому принадлежит точка (x, y)
     */
    IUsualLayerSchema focusLayerSchema(int x, int y);

    /**
     * Возвращает слой, которому принадлежит точка (x, y).
	 * Если таких слоев несколько, возвращает последний в списке.
	 * Если таких слоев нет, возвращает null.
     * @param x - координаты точки
     * @param y  - координаты точки
     * @return слой, которому принадлежит точка (x, y)
     */
    IUsualLayerSchema getLayerSchema(int x, int y);

    /**
     * @return список всех слоев в сети
     */
    List<? extends IUsualLayerSchema> getLayersSchema();

    /**
     * Добавляет прямую стрелку в слой от source слоя к dest слою.
     * Если такую стрелку добавит нельзя, то возвращает null.
     * @param source - слой, из которого выходит стрелка
     * @param dest - слой, куда входит стрелка
     * @return стрелку
     */
    IConnectionSchema addDirectConnectionSchema(IUsualLayerSchema source, IUsualLayerSchema dest);

    /**
     * @return Возвращает true если от слоя source можно провести прямую стрелку
     * иначе false
     *  @param source слой, от которого хотим провести прямую стрелку
     * */
    boolean canAddDirectConnectionSchema(IUsualLayerSchema source);

    /**
     * @return Возвращает true если к слою dest можно провести прямую стрелку
     * иначе false
     *  @param source - слой, от которого хотим провести прямую стрелку
     *  @param dest - слой, в который хотим провести прямую стрелку
     * */
    boolean canAddDirectConnectionSchema(IUsualLayerSchema source, IUsualLayerSchema dest);

    /**
     * @return Возвращает true если от слоя source можно провести обратную стрелку
     * иначе false
     * @param source - слой, из которого хотим провести обратную стрелку
     * */
    boolean canAddBackConnectionSchema(IUsualLayerSchema source);

    /**
     * @return Возвращает true если к слою dest можно провести обратную стрелку
     * иначе false
     * @param source - слой из которого хотим провести обратную стрелку
     * @param dest - слой в который хотим провести обратную стрелку
     * */
    boolean canAddBackConnectionSchema(IUsualLayerSchema source, IUsualLayerSchema dest);

    /**
     * Добавляет обратную стрелку в слой от source слоя к dest слою.
     * Если такую стрелку добавит нельзя, то возвращает null.
     * @param source - слой, из которого выходит стрелка
     * @param dest - слой, куда входит стрелка
     * @return стрелку
     */
    IConnectionSchema addBackConnectionSchema(IUsualLayerSchema source, IUsualLayerSchema dest);

    /**
     * Удаляет стрелку из сети
     * @param connectionSchema - удаляемая стрелка
     */
    void removeConnectionSchema(IConnectionSchema connectionSchema);

    /**
     * Сортирует слои в сети в топологическом порядке
     * */
    void topologicalSort();

    /**
     * Удаляет фигуру(слой или стрелку) из сети
     * @param shapeSchema - удаляемая фигура
     */
    void removeShape(IShapeSchema shapeSchema);

    /**
     * Создает обьект нейронной сети
     * @return обьект нейронной сети
     * */
    INeuroNet createNeuroNet();

    /**
     * Возвращает обьект нейронной сети
     * @return обьект нейронной сети
     * */
    INeuroNet getNeuroNet();

    /**
     * Удаляет обьект нейронной сети
     * */
    void deleteNeuroNet();
}
