package org.amse.marinaSokol.model.impl;

import org.amse.marinaSokol.model.interfaces.object.net.*;
import org.amse.marinaSokol.model.interfaces.object.INeuroNetVisitor;
import org.amse.marinaSokol.model.interfaces.schema.*;
import java.util.*;

public class NeuroNetModel implements INeuroNetSchema {
    private List<IUsualLayerModel>  myLayers;
    private INeuroNet myNeuroNet;

    public NeuroNetModel() {
        myLayers = new LinkedList<IUsualLayerModel>();
        myNeuroNet = null;
    }

    public INeuroNet createNeuroNet() {
        myNeuroNet = new NeuroNetAdapter();
        return myNeuroNet;
    }

    public void deleteNeuroNet() {
        myNeuroNet = null;
    }

    public INeuroNet getNeuroNet() {
        return myNeuroNet;
    }

    /**
     * »мплементаци€ класс схемы
     * */

    public ILayerSchema addLayerSchema(int x, int y, int width, int height) {
        ILayerSchema layerSchema = new LayerModel(x, y, width, height);
        myLayers.add((IUsualLayerModel)layerSchema);
        return layerSchema;
    }

    public IInputLayerSchema addInputLayerSchema(int x, int y, int width, int height) {
        IInputLayerSchema inputLayer = new InputLayerModel(x, y, width, height);

        myLayers.add((IUsualLayerModel)inputLayer);

        return inputLayer;
    }

    public IOutputLayerSchema addOutputLayerSchema(int x, int y, int width, int height) {
        IOutputLayerSchema out = new OutputLayerModel(x, y, width, height);
        myLayers.add((IUsualLayerModel)out);
        return out;
    }


    @SuppressWarnings({"SuspiciousMethodCalls"})
    public IUsualLayerSchema focusLayerSchema(int x, int y) {
        ListIterator<IUsualLayerModel> it = myLayers.listIterator(myLayers.size());
        while (it.hasPrevious()) {
            IUsualLayerModel layerSchema = it.previous();
            if (layerSchema.containsPoint(x, y)) {
                myLayers.remove(layerSchema);
                myLayers.add(layerSchema);
                return layerSchema;
            }
        }
        return null;
    }

    public IUsualLayerSchema getLayerSchema(int x, int y) {
        ListIterator it = myLayers.listIterator(myLayers.size());
        while (it.hasPrevious()) {
            IUsualLayerSchema layerSchema = (UsualLayerModel)it.previous();
            if (layerSchema.containsPoint(x, y)) {
                return layerSchema;
            }
        }
        return null;
    }

    public void removeLayerSchema(IUsualLayerSchema layerSchema ) {
        //todo как соласуютс€ разные типы
        ((UsualLayerModel)layerSchema).unbindAllArrows();
        //noinspection SuspiciousMethodCalls
        myLayers.remove(layerSchema);
    }

    public List<? extends IUsualLayerSchema> getLayersSchema() {
        return Collections.unmodifiableList(myLayers);
    }

    public IConnectionSchema addDirectConnectionSchema(IUsualLayerSchema source, IUsualLayerSchema dest) {
        if (canAddDirectConnectionSchema(source) &&
            canAddDirectConnectionSchema(source, dest)) {
            return ((UsualLayerModel)source).connect((ILayerSchema)dest, true);
        }
        return null;
    }

    public boolean canAddDirectConnectionSchema(IUsualLayerSchema source) {
        return true;
    }

    public boolean canAddDirectConnectionSchema(IUsualLayerSchema source, IUsualLayerSchema dest) {
        return !(checkAttainability(source, dest) || (dest instanceof IInputLayerSchema));
    }

    public boolean canAddBackConnectionSchema(IUsualLayerSchema source) {
        return !(source instanceof IInputLayerSchema);
    }

    public boolean canAddBackConnectionSchema(IUsualLayerSchema source, IUsualLayerSchema dest) {
        return (!(dest instanceof IInputLayerSchema) && !(source instanceof IInputLayerSchema));
    }


    public IConnectionSchema addBackConnectionSchema(IUsualLayerSchema source, IUsualLayerSchema dest) {
        if (canAddBackConnectionSchema(source) && canAddBackConnectionSchema(source, dest)) {
            return ((UsualLayerModel)source).connect((ILayerSchema)dest, false);
        }
        return null;
    }

    public void removeConnectionSchema(IConnectionSchema connectionSchema) {
        ((ConnectionModel)connectionSchema).unbind();
    }

    /**
     * проверка достижимости из сло€ source до сло€ dest(те на цикл)
     * @return true - если достижимы, и будет цикл, иначе false
     * @param source слой, из которого выходит стрелка
     * @param dest слой, в который входит стрелка*/
    public boolean checkAttainability(IUsualLayerSchema source, IUsualLayerSchema dest) {
        LinkedList<IUsualLayerModel> listSort = new LinkedList<IUsualLayerModel>();
        Map<IUsualLayerSchema, Integer> mapLayer = new HashMap<IUsualLayerSchema, Integer>();
        for (IUsualLayerSchema layerSchema : myLayers) {
             mapLayer.put(layerSchema, 0);
        }
        findInHeightVisitLayer(dest, mapLayer, listSort);
        //noinspection SuspiciousMethodCalls
        return listSort.contains(source);
    }

    public void removeShape(IShapeSchema shapeSchema) {
        if (shapeSchema instanceof IUsualLayerSchema) {
            removeLayerSchema((IUsualLayerSchema)shapeSchema);
        } else {
            this.removeConnectionSchema((IConnectionSchema)shapeSchema);
        }
    }

    /**
     * топологическа€ сортировка слоев
     * */
    public void topologicalSort() {
        LinkedList<IUsualLayerModel> listSort = new LinkedList<IUsualLayerModel>();
        findInHeight(listSort);
        myLayers = listSort;
        int i = 0;
        for (IUsualLayerSchema layer: myLayers) {
            i++;
            layer.setId(i);
        }
    }

    /**
     * јлгоритм поиска в глубину
     * 0- white  - еще не помеченна€
     * 1- grey   - помеченна€
     * 2- black  - обработанна€
     * @param listSort - список сортированных слоев
     * */
    private void findInHeight(LinkedList<IUsualLayerModel> listSort) {
        Map<IUsualLayerSchema, Integer> mapLayer = new HashMap<IUsualLayerSchema, Integer>();
        for (IUsualLayerModel layerModel : myLayers) {
            mapLayer.put(layerModel, 0);
        }
        for(IUsualLayerModel layerModel : myLayers) {
            if (mapLayer.get(layerModel) == 0) {
                findInHeightVisitLayer(layerModel, mapLayer, listSort);
            }
        }
    }

    private void findInHeightVisitLayer(IUsualLayerSchema layerSchema, Map<IUsualLayerSchema, Integer> mapLayer, LinkedList<IUsualLayerModel> listSort) {
        mapLayer.put(layerSchema, 1);
        for (IConnectionSchema connectionModel : layerSchema.getOutputConnectionsSchema()) {
            if (mapLayer.get(connectionModel.getDestLayerSchema()) == 0 && connectionModel.isDirect()) {
                findInHeightVisitLayer(connectionModel.getDestLayerSchema(), mapLayer, listSort);
            }
        }
        mapLayer.put(layerSchema, 2);
        listSort.addFirst((IUsualLayerModel)layerSchema);
    }


    /**
     * ѕриватный класс обьекта
     *
     * */
    private class NeuroNetAdapter implements INeuroNet {
        public List<? extends IUsualLayer> getLayers() {
            return myLayers;
        }

        public  NeuroNetAdapter() {
        }

        public void walkDirect(INeuroNetVisitor visitor) {
            for (IUsualLayer layer : myLayers) {
                if (layer instanceof IInputLayer) {
                    visitor.visitInputLayer((IInputLayer)layer);
                } else {
                    for (IConnection a : ((ILayer)layer).getInputConnections()) {
                        visitor.visitConnection(a);
                    }
                    visitor.visitLayer((ILayer)layer);
                    if (layer instanceof IOutputLayer) {
                        visitor.visitOutputLayer((IOutputLayer)layer);
                    }
                }
            }
        }

        public void walkBack(INeuroNetVisitor visitor) {
           ListIterator<? extends IUsualLayer> it = myLayers.listIterator(myLayers.size());
            while (it.hasPrevious()) {
                IUsualLayer layer = it.previous();
                if (layer instanceof IOutputLayer) {
                    visitor.visitOutputLayer((IOutputLayer)layer);
                }
                for (IConnection conn: layer.getOutputConnections()) {
                    visitor.visitConnection(conn);
                }
                if (!(layer instanceof IInputLayer))
                    visitor.visitLayer((ILayer)layer);
            }
        }
    }


    public String toString() {
       StringBuffer sb = new StringBuffer();
        sb.append("The neuroNet description:\n");
        for (IUsualLayerModel ilayer : myLayers) {
            sb.append(ilayer).append("\n");
        }
        return sb.toString();
    }
}
