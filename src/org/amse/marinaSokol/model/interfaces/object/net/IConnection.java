package org.amse.marinaSokol.model.interfaces.object.net;

public interface IConnection {

    /**
     * возвращает слой, из которого выходит соединение.
     * @return  слой, из которого выходит соединение.
     * */
    IUsualLayer getSourceLayer();

    /**
     * возвращает слой, в который входит соединение.
     * @return  слой, в который входит соединение.
     * */
    ILayer getDestLayer();

    /**
     * возвращает веса соединения
     * @return веса соединения
     * */
    double[][] getWeights();

    /**
     * устанавливает веса соединения
     * @param weights - новые веса соединения
     * */
    void setWeights(double[][] weights);

    /**
     * устанавливает веса соединения
     * @param i
     * @param j - новые веса соединения
     * */
    //void setWeight(int i, int j, double  w)
    /**
     * Вызвывается при изменении количества в одном из слоев,
     * участвующих в соединении. Создает новые веса в соответствие с новой
     * размерностью.Заполняет веса случайными числами.
     * */
    void createNewWeights();

    /**
     * Возвращает задержку соединения
     * @return задержка
     * */
    int getDelay();

    /**
     * Устанавливает задержку соединения
     * @param delay - задержка
     * */
    void setDelay(int delay);
}
