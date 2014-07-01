package org.amse.marinaSokol.model.interfaces.object.net;

import java.util.List;


public interface IUsualLayer {
    /**
     * ¬озвращает количество нейронов в слое
     * @return количество нейронов
     * */
    int getNeuronsNumber();

    /**
     * ”станавливает количество нейронов в слое.
     * “ак же просбегаетс€ по всем соединени€м,
     * в котрых учавствовал этот слой и устанавливает новую размерность весов.
     * ¬еса задаютс€ малыми случайными числами.
     * @param number - количество нейронов
     * */
    void setNeuronsNumber(int number);   //количество нейронов в слое


    /**
     * ƒобавл€ет соединение, выход€щее из нашего сло€.
     * @param newOutput - новое соединение
     * */
    void addOutputConnection(IConnection newOutput);

    /**
     * ¬озвращает список всех выход€щих из этого сло€ соединений
     * @return  список всех выход€щих из этого сло€ соединений
     * */
    List<? extends IConnection> getOutputConnections();

}
