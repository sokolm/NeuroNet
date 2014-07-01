package org.amse.marinaSokol.model.interfaces.object.net;

public interface IActivationFunctor {

    /**
     * Функция активации
     * @param x - число, поданное на функцию активации
     * @return выход нейрона
     * */
    double getFunction(double x);

    /**
     * производная функции активации
     * @param x - число, поданное на функцию
     * @return выход
     * */
    double getDerivation(double x);

    /**
     * Имя функции активации
     * @return имя функции активации
     * */
    String getNameFunction();
}
