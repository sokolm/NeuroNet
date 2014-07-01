package org.amse.marinaSokol.model.impl.object.user;

import org.amse.marinaSokol.model.interfaces.object.user.IUser;
import org.amse.marinaSokol.model.interfaces.object.net.*;
import org.amse.marinaSokol.model.impl.object.LazyNeuroNetVisitor;
import org.amse.marinaSokol.model.impl.object.InputLayerData;
import org.amse.marinaSokol.model.interfaces.object.INeuroNetVisitor;
import org.amse.marinaSokol.model.impl.utils.NonExpandingQuery;

import java.util.HashMap;
import java.util.Map;

public class User implements IUser<InputLayerData> {
    private Map<IUsualLayer, LayerDataForUser> myLayerData;
    private Map<IInputLayer, InputLayerData> myInputLayerData;
    private Map<ILayer, InputLayerData> myOutputLayerData;
    private INeuroNet myNet;
    private int myNumPatterns;

    public User(INeuroNet net, int time){
        myNet = net;
        myNumPatterns = time;
    }

    public void setNumPatterns(int numberOfPatterns){
        myNumPatterns = numberOfPatterns;
    }

    public Map<ILayer, InputLayerData> getOutputData() {
        return myOutputLayerData;
    }

    public void run(Map<IInputLayer, InputLayerData> inputLayerData){
        myInputLayerData = inputLayerData;

        myLayerData  = new HashMap<IUsualLayer, LayerDataForUser>();
        myOutputLayerData = new HashMap<ILayer, InputLayerData>();
        init();
        DirectWalker direct = new DirectWalker();
        for (int y = 0; y < myNumPatterns; y++) {
            direct.setCurrentM(y);
            myNet.walkDirect(direct);
            //System.out.println("User OK");
        }
        saveOut();
    }

    public void init(){
        class FakeWalker extends LazyNeuroNetVisitor {
            public void visitInputLayer(IInputLayer layer) {
                 myLayerData.put(layer, new LayerDataForUser(layer));
            }

            public void visitLayer(ILayer layer) {
                 myLayerData.put(layer, new LayerDataForUser(layer));
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
         * */
        public void visitOutputLayer(IOutputLayer layer) {
        }

        /**
         * @param layer - ������ ���� ����, ����� �������� �������
         * ��������� ������� �������� �� ��������� � �����
         * � �������� ������� ��� ��������� ����������
         * */
        public void visitLayer(ILayer layer) {
            myLayerData.get(layer).myOutputHistory.set(myCurM, layer.getActivationResult(myLayerData.get(layer).myNeurons));

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
        }
     }

    /**
     * ����� � ��������������� ������� ��� ����
     * */
    private class LayerDataForUser {
        //������� ������� ���� �������� ����� ���������
        NonExpandingQuery myOutputHistory;
        //������� ������ �� ������ ������� � ����
        double[] myNeurons;

        public LayerDataForUser() {}

        public LayerDataForUser(IUsualLayer ILayer) {
            int numberOfNeurons = ILayer.getNeuronsNumber();

            myOutputHistory = new NonExpandingQuery(myNumPatterns, numberOfNeurons);

            myNeurons = new double[numberOfNeurons];
        }

        public void clear() {
            myOutputHistory.clear();
       }
    }
}
