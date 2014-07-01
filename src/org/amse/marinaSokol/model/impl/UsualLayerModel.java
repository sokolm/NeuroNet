package org.amse.marinaSokol.model.impl;

import org.amse.marinaSokol.model.interfaces.object.net.IConnection;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IConnectionSchema;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

public class UsualLayerModel implements IUsualLayerModel {
    private int myX;
    private int myY;
    private int myWidth;
    private int myHeight;

    private List<IConnectionModel> myOutputConnections;

    private int myId;


    private int myNumNeurons;


    /**
     * @param x - координата(абсциса) верхнего левого угла слоя
     * @param y - координата(ордината) верхнего левого угла слоя
     * @param width - ширина слоя
     * @param height - высота слоя
     * по умолчанию у слоя 0 нейронов и ф-я активации null(Не задана)
     * */
    UsualLayerModel(int x, int y, int width, int height) {
        myX = x;
        myY = y;
        myWidth = width;
        myHeight = height;
        myOutputConnections = new LinkedList<IConnectionModel>();
        myNumNeurons = 1;
    }

    public void setId(int id) {
        myId = id;
    }

    public int getId() {
        return myId;
    }


    public int getX() {
        return myX;
    }

    public int getY() {
        return myY;
    }

    public int getWidth() {
        return myWidth;
    }

    public int getHeight() {
        return myHeight;
    }

    public void moveTo(int x, int y) {
        this.myX = x;
        this.myY = y;
    }

    public void resize(int width, int height) {
        this.myWidth = width;
        this.myHeight = height;
    }

    public boolean containsPoint(int x, int y) {
        return (myX <= x) && (x <= (myX + myWidth)) && (myY <= y) && (y <= (myY + myHeight));
    }

    IConnectionSchema addOutArrow(IConnectionSchema connectionSchema) {
        myOutputConnections.add((IConnectionModel)connectionSchema);
        return connectionSchema;
    }

    @SuppressWarnings({"SuspiciousMethodCalls"})
    void removeOutArrow(IConnectionSchema connectionSchema) {
        myOutputConnections.remove(connectionSchema);
    }

    public List<? extends IConnectionSchema> getOutputConnectionsSchema() {
        return Collections.unmodifiableList(myOutputConnections);
    }

    /**
     * This method connect this layerSchema(source) with another layerSchema(dest)
     * with arrow
     * @param layerSchema - слой, в который присоединяем стрелку
     * @param direct - тип стрелки, если true , то прямая, иначе обратная
     * @return arrow - возвращет стрелку, если соединение сделать нельзя, то null
     */
    IConnectionSchema connect(ILayerSchema layerSchema, boolean direct) {
        IConnectionSchema connectionSchema = new ConnectionModel(this, layerSchema, direct);
        addOutArrow(connectionSchema);
        ((LayerModel)layerSchema).addInArrow(connectionSchema);
        return connectionSchema;
    }

    /**
     * Отсоединяет все входящие и выходящие стрелки от слоя
     */
    void unbindAllArrows() {
        for (IConnectionSchema a: myOutputConnections) {
            ((LayerModel)a.getDestLayerSchema()).removeInArrow(a);
        }
    }

    public void addOutputConnection(IConnection newOut) {
        myOutputConnections.add((IConnectionModel)newOut);
    }

    public List<? extends IConnection> getOutputConnections() {
        return Collections.unmodifiableList(myOutputConnections);
    }

    public int getNeuronsNumber() {
        return myNumNeurons;
    }

    public void setNeuronsNumber(int number) {
        myNumNeurons = number;

        for (IConnection connection: myOutputConnections) {
            connection.createNewWeights();
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("The layer description:\n");
        sb.append("Num neurons: ").append(myNumNeurons).append("\n");
        sb.append("id layer =").append(myId).append("\n");
        sb.append("the layer coordinates: ").append(myX).append(" ").append(myY).append("\n");
        sb.append("the layer size: ").append(myWidth).append(" ").append(myHeight).append("\n");

        sb.append("out arrows:\n");
        for (IConnectionSchema a : myOutputConnections) {
            sb.append("the out arrow:\n");
            sb.append(a);
            sb.append("\n");
        }

        return sb.toString();
    }
}
