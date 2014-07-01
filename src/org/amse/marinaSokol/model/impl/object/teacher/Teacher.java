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
    //����� ��������� ��������
    private final int myNumPatterns;
    //��� ����
    private INeuroNet myNet;

    //�������� ��������
    private double SPEED = 0.3;
    //���������� �����
    private int STEPS = 600;

    //������ ��� ��������, �� ���� ���� �� ������ ������ ��� ��������
    private Map<IUsualLayer, LayerData> myLayerData;
    //���� ���������� �� ������ �������������� ��� ����������
    private Map<IConnection, ConnectionData> myConnectionData;
    //���� �������� ���� �� �������������� ������
    private Map<IUsualLayer, InputLayerData> myInputLayerData;
    private Map<ILayer, InputLayerData> myOutputLayerData;

    /**
     * @param net - ����, ������� ����� �������
     * @param numPatterns - ����� �������� �� ������� ����� �������
     * */
    public Teacher(INeuroNet net, int numPatterns) {
        myNumPatterns = numPatterns;
        myNet = net;
    }

    /**
     * @param speed - �������� ��������
     * */
    public void setSPEED(double speed) {
        SPEED = speed;
    }

    public Map<ILayer, InputLayerData> getOutputData() {
        return myOutputLayerData;
    }

    /**
     * @param steps - ����� ����� � ����� �����
     * */
    public void setSTEPS(int steps) {
        STEPS = steps;
    }

    /**
     * � ���� �-�� ��������� ���� ��� ����� � ���������� �� �������������� ������
     * ����� ��������� direct � back ����������(visitor)
     * � �������������� ������� ������ ���� �������� � �����
     * ������ ����, � ������� ��� ���������� ��� ��������� �����
     * @param inputLayerData - ���� � ������� ���������, ��� ����� � ����
     * � ��������� ������ �� ���
     * */
    public void teach(Map<IUsualLayer, InputLayerData> inputLayerData) {

        myInputLayerData = inputLayerData;

        myLayerData  = new HashMap<IUsualLayer, LayerData>();
        myConnectionData = new HashMap<IConnection, ConnectionData>();
        myOutputLayerData = new HashMap<ILayer, InputLayerData>();

        //������ ����
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
     * ������� ��� ���� ����� �����
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
     * ����������� �� ���� ����� ������ ������ ��� ��������
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
     * ����� visitor ��� ������� ������� ��� ��������
     * */
    private class DirectWalker implements INeuroNetVisitor {
        private int myCurM;

        public DirectWalker(){
            this.myCurM = 0;
        }

        /**
         * @param curM - ������� �������� �������
         * */
        void setCurrentM(int curM) {
            this.myCurM = curM;
        }

        /**
         * @param layer - ���� � �������� ���� �����
         * ���������� �� ��� �� �������� �� ������,
         *  � �� ��� �� ������ ���� ��������,
         * ��������� ������ ������� �������, ���� � ����
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
         * @param layer - ������ ���� ����, ����� �������� �������
         * ��������� ������� �������� �� ��������� � �����
         * � �������� ������� ��� ��������� ����������
         * */
        public void visitLayer(ILayer layer) {
            myLayerData.get(layer).myOutputHistory.set(myCurM, layer.getActivationResult(myLayerData.get(layer).myNeurons));
            myLayerData.get(layer).myNeuronHistory.set(myCurM, myLayerData.get(layer).myNeurons);

            for (int i = 0; i < layer.getNeuronsNumber(); i++) {
                myLayerData.get(layer).myNeurons[i] = 0;
            }
        }

        /**
         * @param layer - ������ ������� ����(�� ��������, ������� �������� �������)
         * ������������� � ��� ������� ��������������� ��������� ����, ������� ����� ����� �� ����
         * */
        public void visitInputLayer(IInputLayer layer) {
            myLayerData.get(layer).myOutputHistory.set(myCurM, myInputLayerData.get(layer).getPatternTrainingData().get(myCurM));
        }

        /**
         * @param connection - ���������� ����� ������
         * ����� ���� �� source ���� � ������ ��������
         * �������� ���� �� ���� ����� ����������, ���������,
         * ��������� ��� � ��������(��� ���������� ����� �� ������ ������ � dest �����)
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
     * ����� visitor ��� ��������� ������� ��� ��������
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
     * ������ ����
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
     * ����� � ��������������� ������� ��� ����������
     */
    private class ConnectionData {
        //��� ����� �������� �� ����� �� ���� ����
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
     * ����� � ��������������� ������� ��� ����
     * */
    private class LayerData {
        //���� ���� ��������
        double myError;
        //������� ���� �������� �� ���������
        NonExpandingQuery myNeuronHistory;
        //������� ������� ���� �������� ����� ���������
        NonExpandingQuery myOutputHistory;
        //������� ������ �� ������ ������� � ����
        NonExpandingQuery myErrorE;
        //��� ���������� ����� ��������
        double[] myNeurons;


        //��� ����� ���� ������� ����� � ����
        double[] myDeltaIn;
        //������� ����� � ���� ����
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
