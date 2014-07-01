package org.amse.marinaSokol.model.impl.object.teacher;

import org.amse.marinaSokol.model.interfaces.object.net.*;
import org.amse.marinaSokol.model.impl.object.LazyNeuroNetVisitor;
import org.amse.marinaSokol.model.impl.object.InputLayerData;
import org.amse.marinaSokol.model.interfaces.object.INeuroNetVisitor;
import org.amse.marinaSokol.model.impl.utils.NonExpandingQuery;

import org.amse.marinaSokol.model.interfaces.object.teacher.ITeacher;

import java.util.HashMap;
import java.util.Map;

public class Teacher implements ITeacher {
    //число обучающих образцов
    private final int myNumPatterns;
    //моя сеть
    private INeuroNet myNet;

    //скорость обучения
    private double SPEED = 0.3;
    //количество шагов
    private int STEPS = 600;

    //данные для обучения, те мапа слоя на данные нужные для обучения
    private Map<IUsualLayer, LayerData> myLayerData;
    //мапа соединения на данные дополнительные для соединения
    private Map<IConnection, ConnectionData> myConnectionData;
    //мапа входного слоя на дополнительные данные
    private Map<IUsualLayer, InputLayerData> myInputLayerData;
    private Map<ILayer, InputLayerData> myOutputLayerData;

    /**
     * @param net - сеть, которую нужно обучить
     * @param numPatterns - число образцов на которых будем обучать
     * */
    public Teacher(INeuroNet net, int numPatterns) {
        myNumPatterns = numPatterns;
        myNet = net;
    }

    /**
     * @param speed - скорость обучения
     * */
    public void setSPEED(double speed) {
        SPEED = speed;
    }

    public Map<ILayer, InputLayerData> getOutputData() {
        return myOutputLayerData;
    }

    /**
     * @param steps - число шагов в одной эпохе
     * */
    public void setSTEPS(int steps) {
        STEPS = steps;
    }

    /**
     * В этой ф-ии создаются мапы для слоев и соединений на дополнительные данные
     * затем создаются direct и back поситители(visitor)
     * и осуществляется проходы вперед всех образцов и назад
     * меняем веса, и очищаем доп информацию для следующей эпохи
     * @param inputLayerData - мапа в которой храняться, все входы в сеть
     * и требуемые выходы из нее
     * */
    public void teach(Map<IUsualLayer, InputLayerData> inputLayerData) {

        myInputLayerData = inputLayerData;

        myLayerData  = new HashMap<IUsualLayer, LayerData>();
        myConnectionData = new HashMap<IConnection, ConnectionData>();
        myOutputLayerData = new HashMap<ILayer, InputLayerData>();

        //делает мапу
        init();

        DirectWalker direct = new DirectWalker();
        BackWalker back = new BackWalker();
        int i, y;
        for (i = 0; i < STEPS; i++) {
            for (y = 0; y < myNumPatterns; y++) {
                direct.setCurrentM(y);
                myNet.walkDirect(direct);
            }

            for (y = myNumPatterns - 1; y >= 0; y--) {
                back.setCurrentM(y);
                myNet.walkBack(back);
            }
            changeWeights();
            saveOut();
            clear();
        }
    }

    /**
     * очищает все слои после эпохи
     * */
    private void clear() {
        class FakeWalker extends LazyNeuroNetVisitor {
            public void visitInputLayer(IInputLayer layer) {
                 myLayerData.get(layer).clear();
            }
            public void visitLayer(ILayer layer) {
                 myLayerData.get(layer).clear();
            }
            public void visitConnection(IConnection connection) {
                 myConnectionData.get(connection).clear();
            }
        }

        myNet.walkDirect(new FakeWalker());
    }

    /**
     * myOutputLayerData
     * */
    public void saveOut() {
        class FakeWalker extends LazyNeuroNetVisitor {
             public void visitOutputLayer(IOutputLayer layer) {
                 myOutputLayerData.put(layer, new InputLayerData(myLayerData.get(layer).myOutputHistory));
             }
         }
         myNet.walkDirect(new FakeWalker());
    }
    
    /**
     * прикрепляет ко всем слоям данные нужные для обучения
     * */
    private void init() {
        class FakeWalker extends LazyNeuroNetVisitor {
            public void visitInputLayer(IInputLayer layer) {
                 myLayerData.put(layer, new LayerData(layer));
            }

            public void visitLayer(ILayer layer) {
                 myLayerData.put(layer, new LayerData(layer));
            }

            public void visitConnection(IConnection connection) {
                 myConnectionData.put(connection, new ConnectionData(connection));
            }
        }

        myNet.walkDirect(new FakeWalker());
    }

    /**
     * класс visitor для прямого прохода при обучении
     * */
    private class DirectWalker implements INeuroNetVisitor {
        private int myCurM;

        public DirectWalker(){
            this.myCurM = 0;
        }

        /**
         * @param curM - текущее значение образца
         * */
        void setCurrentM(int curM) {
            this.myCurM = curM;
        }

        /**
         * @param layer - слой у которого есть выход
         * сравниваем то что мы получили на выходе,
         *  и то что мы должны были получить,
         * вычисляем ошибку каждого нейрона, слоя и сети
         * */
        public void visitOutputLayer(IOutputLayer layer) {
            double[] errorE = new double[layer.getNeuronsNumber()];
            double c = 0;

            for(int i = 0; i < errorE.length; i++) {
                errorE[i] =
                        myInputLayerData.get(layer).getPatternTrainingData().get(myCurM)[i] -
                            myLayerData.get(layer).myOutputHistory.get(myCurM)[i];
                c += errorE[i] * errorE[i];
            }

            myLayerData.get(layer).myErrorE.set(myCurM, errorE);
            myLayerData.get(layer).myError += 0.5 * c;
        }

        /**
         * @param layer - каждый слой сети, кроме фейковых входных
         * сохраняем историю нейронов до активации и после
         * и обнуляем нейроны для следующих вычислений
         * */
        public void visitLayer(ILayer layer) {
            myLayerData.get(layer).myOutputHistory.set(myCurM, layer.getActivationResult(myLayerData.get(layer).myNeurons));
            myLayerData.get(layer).myNeuronHistory.set(myCurM, myLayerData.get(layer).myNeurons);

            for (int i = 0; i < layer.getNeuronsNumber(); i++) {
                myLayerData.get(layer).myNeurons[i] = 0;
            }
        }

        /**
         * @param layer - только входные слои(те фейковые, которые являются входами)
         * устанавливаем в его истории соответствующий следующий вход, который будет подан на сеть
         * */
        public void visitInputLayer(IInputLayer layer) {
            myLayerData.get(layer).myOutputHistory.set(myCurM, myInputLayerData.get(layer).getPatternTrainingData().get(myCurM));
        }

        /**
         * @param connection - соединение между слоями
         * берем вход от source слоя с учетом задержки
         * умножаем вход на веса этого соединения, суммируем,
         * сохраняем это в нейронах(еще добавяться суммы от других связей с dest слоем)
         * */
        public void visitConnection(IConnection connection) {
            double[] neurons = myLayerData.get(connection.getDestLayer()).myNeurons;//connection.getDestLayer().getNeurons();
            double[] inputs;

            if ((myCurM - connection.getDelay()) < 0)
                return;
            inputs = myLayerData.get(connection.getSourceLayer()).myOutputHistory.get(myCurM - connection.getDelay());

            for (int i = 0; i < neurons.length; i++) {
               for (int y = 0; y < inputs.length; y++) {
                    neurons[i] += inputs[y] * connection.getWeights()[y][i];
               }
            }

            myConnectionData.get(connection).myInputHistory.set(myCurM, inputs);
        }
     }

    /**
     * класс visitor для обратного прохода при обучении
     * */
    private class BackWalker implements INeuroNetVisitor {
        private int myCurM;

        public BackWalker() {
            myCurM = 0;
        }

        public BackWalker(int curM) {
            myCurM = curM;
        }

        void setCurrentM(int curM) {
            myCurM = curM;
        }

        public void visitOutputLayer(IOutputLayer layer) {
            double in[] = myLayerData.get(layer).myDeltaIn;
            double errors[] = myLayerData.get(layer).myErrorE.get(myCurM);

            for(int i = 0; i < in.length; i++) {
                in[i] += errors[i];
            }
        }

        public void visitLayer(ILayer layer) {
            double []deltaLayer = myLayerData.get(layer).myDeltaLayer.get(myCurM);
            double []neurons = myLayerData.get(layer).myNeuronHistory.get(myCurM);
            double in[] = myLayerData.get(layer).myDeltaIn;

            for (int i = 0; i < layer.getNeuronsNumber(); i++) {

                deltaLayer[i] =
                        layer.getActivation().getDerivation(neurons[i]) *
                        in[i];
            }

            for (int i = 0; i < layer.getNeuronsNumber(); i++) {
                 myLayerData.get(layer).myDeltaIn[i] = 0;
             }
        }

        public void visitInputLayer(IInputLayer ILayer) {

        }

        public void visitConnection(IConnection connection) {
             int delta = connection.getDelay();
             IUsualLayer l1 = connection.getSourceLayer();
             ILayer l2 = connection.getDestLayer();
             double []in = myLayerData.get(l1).myDeltaIn;

             if (delta + myCurM < myNumPatterns) {
                 for(int i = 0; i < in.length; i++) {
                     for(int j = 0; j < l2.getNeuronsNumber(); j++){
                           in[i] += connection.getWeights()[i][j] * myLayerData.get(l2).myDeltaLayer.get(myCurM + delta)[j];
                     }
                 }
             }
        }
    }

    /**
     * меняет веса
     * */
    private void changeWeights() {
        class FakeWalker extends LazyNeuroNetVisitor  {

            public void visitConnection(IConnection conn) {
                IUsualLayer layerFrom = conn.getSourceLayer();
                ILayer layerTo = conn.getDestLayer();
                for(int i = 0; i < layerTo.getNeuronsNumber(); i++){
                    for(int j = 0; j < layerFrom.getNeuronsNumber(); j++){
                        double sum = 0;
                        for(int k = 0; k < myNumPatterns; k++){
                             sum += myLayerData.get(layerTo).myDeltaLayer.get(k)[i] *
                                    myConnectionData.get(conn).myInputHistory.get(k)[j];
                        }
                        conn.getWeights()[j][i] += SPEED * sum;
                    }
                }
            }
        }
        myNet.walkDirect(new FakeWalker());
    }

    /**
     * класс с дополнительными данными для соединений
     */
    private class ConnectionData {
        //все входы поданные за эпоху на этот слой
        NonExpandingQuery myInputHistory;
        NonExpandingQuery myDeltaOut;

        public ConnectionData() {}

        public ConnectionData(int neuronsFrom, int neuronsTo) {
            myInputHistory = new  NonExpandingQuery(myNumPatterns, neuronsFrom);
            myDeltaOut = new  NonExpandingQuery(myNumPatterns, neuronsTo);
        }

        public ConnectionData(IConnection con) {
            this(con.getSourceLayer().getNeuronsNumber(),
            con.getDestLayer().getNeuronsNumber());
        }

        public void clear() {
            myInputHistory.clear();
            myDeltaOut.clear();
        }
    }

    /**
     * класс с дополнительными данными для слоя
     * */
    private class LayerData {
        //если слой выходной
        double myError;
        //история всех нейронов до активации
        NonExpandingQuery myNeuronHistory;
        //история выходов всех нейронов после активации
        NonExpandingQuery myOutputHistory;
        //история ошибок на каждом нейроне в слое
        NonExpandingQuery myErrorE;
        //для вычисления суммы нейронов
        double[] myNeurons;


        //это сумма всех входных дельт в слой
        double[] myDeltaIn;
        //история дельт в этом слое
        NonExpandingQuery myDeltaLayer;

        public LayerData() {}

        public LayerData(IUsualLayer ILayer) {
            int numberOfNeurons = ILayer.getNeuronsNumber();

            myNeuronHistory = new NonExpandingQuery(myNumPatterns, numberOfNeurons);
            myOutputHistory = new NonExpandingQuery(myNumPatterns, numberOfNeurons);
            myDeltaLayer = new NonExpandingQuery(myNumPatterns, numberOfNeurons);
            myErrorE = new NonExpandingQuery(myNumPatterns, numberOfNeurons);
            myDeltaIn = new double[numberOfNeurons];
            myError = 0;

            myNeurons = new double[numberOfNeurons];
        }

        public void clear() {
            myNeuronHistory.clear();
            myDeltaLayer.clear();
            myErrorE.clear();
            myError = 0;

            for (int i = 0; i < myDeltaIn.length; i++) {
                myDeltaIn[i] = 0;
            }
        }
    }
}
