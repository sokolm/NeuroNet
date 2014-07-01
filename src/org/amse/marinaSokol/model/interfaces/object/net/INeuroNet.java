package org.amse.marinaSokol.model.interfaces.object.net;

import org.amse.marinaSokol.model.interfaces.object.INeuroNetVisitor;

import java.util.List;

public interface INeuroNet {
    /**
     * ¬озвращает слои сети
     * @return слои
     * */
    List<? extends IUsualLayer> getLayers();

    /**
     * ћетод позвол€ет пройтись по сети в пр€мом пор€дке
     * @param visitor - посетитель, который знает как обходить сеть
     * */
    void walkDirect(INeuroNetVisitor visitor);

    /**
     * ћетод позвол€ет пройтись по сети в обратном пор€дке
     * @param visitor - посетитель, который знает как обходить сеть
     * */
    void walkBack(INeuroNetVisitor visitor);
}
