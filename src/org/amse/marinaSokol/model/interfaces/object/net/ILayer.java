package org.amse.marinaSokol.model.interfaces.object.net;

import java.util.List;

public interface ILayer extends IUsualLayer {

    /**
     * Добавляет соединение, входящее в наш слой.
     * @param newInput - новое соединение
     * */
    void addInputConnection(IConnection newInput);

    /**
     * Возвращает список всех входящих в этот слой соединений
     * @return  список всех входящих в этот слой соединений
     * */
    List<? extends IConnection> getInputConnections();

    /**
     * Возвращает функцию активации слоя
     * @return функция активации
     * */
    IActivationFunctor getActivation();

    /**
     * Устанавливает функцию активации слою
     * @param type - функция активации
     * */
    void setActivation(ActivationFunctorType type);

    /**
     * Считает выход на нейронах после функции активации
     * @param neuronSum - входная сумма на нейронах
     * @return выход на нейронах
     * */
    double[] getActivationResult(double[] neuronSum);
}

