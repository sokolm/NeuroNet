package org.amse.marinaSokol.model.impl;

import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IUsualLayerSchema;
import org.amse.marinaSokol.model.interfaces.object.net.ILayer;
import org.amse.marinaSokol.model.interfaces.object.net.IUsualLayer;

class ConnectionModel implements IConnectionModel {
    //слой, из которого выходит связь
    private IUsualLayerModel mySource;
    //слой, в который входит связь
    private ILayerModel myDest;
    //веса соединяющие связь
    private double[][] myWeights;
    //задержка для этой связи
    private int myDelay;
    //прямая связь
    private boolean myDirect;


    //методы IConnection
    public void setWeights(double [][]weights) {
        myWeights = weights;
    }

    public void createNewWeights() {
        myWeights =
                new double[mySource.getNeuronsNumber()][myDest.getNeuronsNumber()];
        for (int i = 0; i < mySource.getNeuronsNumber(); i++) {
            for (int j = 0; j < myDest.getNeuronsNumber(); j++) {
                 myWeights[i][j] = 0.1 - 2*Math.random()/10;//TODO Math.random();// ;
            }
        }
    }

    public double[][] getWeights() {
        return myWeights;
    }

    public IUsualLayer getSourceLayer() {
        return mySource;
    }

    public ILayer getDestLayer() {
        return myDest;
    }

    public int getDelay() {
        return myDelay;
    }

    public void setDelay(int delay) {
        myDelay = delay;
    }

    //методы схемы
    /**
     * @param source - начальный узел
     * @param dest - конечный узел
     * @param direct - указывает прямая или обратная стрелка(true - прямая)
     * если прямая то задержка по умолчанию бует равна 0
     * если обратная, то 1
     * */
    ConnectionModel(IUsualLayerSchema source, ILayerSchema dest, boolean direct) {
        mySource = (IUsualLayerModel)source;
        myDest = (ILayerModel)dest;
        myDirect = direct;
        if (myDirect) {
            myDelay = 0;
        } else {
            myDelay = 1;
        }
        myWeights = new double[mySource.getNeuronsNumber()][myDest.getNeuronsNumber()];
    }

    public boolean isDirect() {
        return myDirect;
    }

    public IUsualLayerSchema getSourceLayerSchema() {
        return mySource;
    }

    public ILayerSchema getDestLayerSchema() {
        return myDest;
    }

    public void setDestLayerSchema(ILayerSchema layerSchema) {
        myDest = (ILayerModel)layerSchema;
    }

    /**
     * Отсоединяет стрелку от начального и конечного узлов
     */
    void unbind() {
        ((UsualLayerModel)mySource).removeOutArrow(this);
        ((LayerModel)myDest).removeInArrow(this);
    }

    public int proximity(int x, int y) {
        int x1;
        int y1;
        int x2;
        int y2;
        double index = (double)(myDest.getInputConnectionsSchema().indexOf(this)+1) / (myDest.getInputConnectionsSchema().size() + 1); //+ myDest.getOutputConnectionsSchema().size());

        if ((mySource.getY() - myDest.getY() - myDest.getHeight()) > 0) {
            x1 = mySource.getX() + mySource.getWidth()/2;
            y1 = mySource.getY();
            x2 = myDest.getX() + (int)(myDest.getWidth()*index);
            y2 = myDest.getY() + myDest.getHeight();

        } else if ((myDest.getY() - mySource.getY() - mySource.getHeight()) > 0) {
            x1 = mySource.getX() + mySource.getWidth()/2;
            y1 = mySource.getY() + mySource.getHeight();
            x2 = myDest.getX() + (int)(myDest.getWidth()*index);
            y2 = myDest.getY();

        } else if (mySource.getX() < myDest.getX()) {
            x1 = mySource.getX() + mySource.getWidth();
            y1 = mySource.getY() + mySource.getHeight()/2;
            x2 = myDest.getX();
            y2 = myDest.getY() + (int)(myDest.getHeight()*index);
        } else {
            x1 = mySource.getX();
            y1 = mySource.getY() + mySource.getHeight()/2;
            x2 = myDest.getX() + myDest.getWidth();
            y2 = myDest.getY() + (int)(myDest.getHeight()*index);
        }

        if (x < Math.min(x1, x2) || x > Math.max(x1, x2) || y < Math.min(y1, y2) || y > Math.max(y1, y2)) {
            return Integer.MAX_VALUE;
        }

        double k = (double)(y1-y2)/(x1-x2);
        double b = y1 - k*x1;
        int ourY = (int)(k*x + b);
        return Math.abs(ourY - y);
    }


    public String toString() {
       StringBuffer sb = new StringBuffer();
        sb.append("The arrow description:\n");
        sb.append("delay: ").append(myDelay).append("\n");
        sb.append("type of the arrow direct: ").append(myDirect).append("\n");
        sb.append("the arrow source node:\n");
        sb.append("the source coordinates: ").append(mySource.getX()).append(" ").append(mySource.getY()).append("\n");
        sb.append("the arrow dest node:\n");
        sb.append("the dest coordinates: ").append(myDest.getX()).append(" ").append(myDest.getY()).append("\n");
        return sb.toString();
    }
}
