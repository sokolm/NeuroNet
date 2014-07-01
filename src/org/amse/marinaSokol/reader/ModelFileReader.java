package org.amse.marinaSokol.reader;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.interfaces.schema.IUsualLayerSchema;
import org.amse.marinaSokol.model.interfaces.object.net.*;
import org.amse.marinaSokol.exception.MyException;

import java.io.File;
import java.io.IOException;


public class ModelFileReader {
    private INeuroNetSchema mySchema;
    private Document myDocument;
    private int myNumLayers;
    private int myNumConnections;

    public ModelFileReader(File file) throws SAXException,
        ParserConfigurationException, IOException {
        DocumentBuilder db = getBuilder();
        db.setErrorHandler(new ErrorHandler() {
                    public void warning(SAXParseException exception) throws SAXException {
                    }

                    public void error(SAXParseException exception) throws SAXException {
                    }

                    public void fatalError(SAXParseException exception) throws SAXException {
                    }
                } );
        myDocument = db.parse(file);
    }

    public void read(INeuroNetSchema model) throws MyException {
        mySchema = model;
        buildDiagram(myDocument.getDocumentElement());
    }

    private DocumentBuilder getBuilder()
        throws SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory =
            DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(false);
        return factory.newDocumentBuilder();
    }

    private void buildDiagram(Element root) throws MyException{
        NodeList children = root.getChildNodes();
        boolean schema = false;
        boolean object = false;
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element) {
                Element childElement = (Element)child;
                if (child.getNodeName().equals("schema") && !schema) {
                    buildSchema(childElement);
                    schema = true;
                } else {
                    if (child.getNodeName().equals("object") && !object) {
                        mySchema.createNeuroNet();
                        buildObject(childElement);
                        object = true;
                    } else {
                        if (schema && object) {
                            throw new MyException("лишний тег в руте");
                        } else {
                            throw new MyException("неопознанный тег в руте");
                        }
                    }
                }
            }
        }
    }

    private void buildSchema(Element nodeSchema) throws MyException{
        Node n;
        NodeList nodes;

        nodes = nodeSchema.getElementsByTagName("layer");
        myNumLayers = nodes.getLength();

        for (int i = 0; i < nodes.getLength(); i++) {
            n = nodes.item(i);
            addLayerSchema(n);
        }

        nodes = nodeSchema.getElementsByTagName("connection");
        myNumConnections = nodes.getLength();

        for (int i = 0; i < nodes.getLength(); i++) {
            n = nodes.item(i);
            addArrowSchema(n);
        }
    }

    private void buildObject(Element nodeObject) throws MyException {
        Node n;
        NodeList nodes;

        nodes = nodeObject.getElementsByTagName("layer");
        if (nodes.getLength() != myNumLayers) {
            throw new MyException("обьект поврежден: число слоев не корректно");
        }

        for (int i = 0; i < nodes.getLength(); i++) {
            n = nodes.item(i);
            addLayerObject(n);
        }
        nodes = nodeObject.getElementsByTagName("connection");

        if (nodes.getLength() != myNumConnections) {
            throw new MyException("обьект поврежден: число соединений не корректно");
        }

        for (int i = 0; i < nodes.getLength(); i++) {
            n = nodes.item(i);
            addArrowObject(n);
        }
    }

    private void addLayerSchema(Node n) throws MyException {
        NamedNodeMap attrs = n.getAttributes();
        String type = attrs.getNamedItem("type").getNodeValue();
        int x = new Integer(
                attrs.getNamedItem("x").getNodeValue());
        int y = new Integer(
                attrs.getNamedItem("y").getNodeValue());
        int w = new Integer(
                attrs.getNamedItem("width").getNodeValue());
        int h = new Integer(
                attrs.getNamedItem("height").getNodeValue());
        int id = new Integer(
                attrs.getNamedItem("id").getNodeValue());
        IUsualLayerSchema cn = null;
        if (type.equals("ordinary"))
            cn = mySchema.addLayerSchema(x, y, w, h);
        if (type.equals("begin"))
            cn = mySchema.addInputLayerSchema(x, y, w, h);
        if (type.equals("end"))
            cn = mySchema.addOutputLayerSchema(x, y, w, h);
        cn.setId(id);
        if (cn == null)
            throw new MyException("ERROR : layer of wrong type.\n");
    }

    private void addArrowSchema(Node n) {
        NamedNodeMap attrs = n.getAttributes();
        int source = new Integer(
                attrs.getNamedItem("source").getNodeValue());
        int destination = new Integer(
                attrs.getNamedItem("destination").getNodeValue());
        boolean direct = (Boolean.valueOf(attrs.getNamedItem("direct").getNodeValue()));
        if (direct)
            mySchema.addDirectConnectionSchema(mySchema.getLayersSchema().get(source),
                    mySchema.getLayersSchema().get(destination));
        else {
            mySchema.addBackConnectionSchema(mySchema.getLayersSchema().get(source),
                    mySchema.getLayersSchema().get(destination));
        }
    }

    private void addLayerObject(Node n) {
        NamedNodeMap attrs = n.getAttributes();
        int numNeurons = new Integer(
                attrs.getNamedItem("numNeurons").getNodeValue());
        String activation = attrs.getNamedItem("functor").getNodeValue();
        int id = new Integer(attrs.getNamedItem("id").getNodeValue());

        IUsualLayer layer = mySchema.getNeuroNet().getLayers().get(id);
        layer.setNeuronsNumber(numNeurons);
        if ("".equals(activation)) {
        } else {
            ((ILayer)layer).setActivation(ActivationFunctorType.valueOf(activation.toUpperCase()));
        }
    }


    private void addArrowObject(Node n) throws MyException {
        NamedNodeMap attrs = n.getAttributes();
        int source = new Integer(
                attrs.getNamedItem("source").getNodeValue());
        int destination = new Integer(
                attrs.getNamedItem("destination").getNodeValue());
		int delay = new Integer(
                attrs.getNamedItem("delay").getNodeValue());


        IUsualLayer sourceLayer = mySchema.getNeuroNet().getLayers().get(source);
        IUsualLayer destLayer = mySchema.getNeuroNet().getLayers().get(destination);//getOutputConnections().get()
        double[][] weights = new double[sourceLayer.getNeuronsNumber()][destLayer.getNeuronsNumber()];


        NodeList nodeList = n.getChildNodes();
        Node node = null;
        for (int i = 0; i < n.getChildNodes().getLength(); i++) {
            if (nodeList.item(i) instanceof Element) {
                node = nodeList.item(i);
            }
        }

        NodeList rowList = node.getChildNodes();
        NodeList wList;
        NamedNodeMap attrsWeight;
        int countI = 0;
        int countJ;
        for (int i = 0; i < rowList.getLength(); i++) {
            wList = rowList.item(i).getChildNodes();
            if (wList instanceof Element) {
                countJ = 0;
                for (int j = 0; j < wList.getLength(); j++) {
                    if (wList.item(j) instanceof Element) {
                        attrsWeight = wList.item(j).getAttributes();
                        weights[countI][countJ] = new Double(
                                attrsWeight.getNamedItem("value").getNodeValue());
                        countJ++;
                    }
                }
                countI++;
            }
        }

        for (IConnection conn: sourceLayer.getOutputConnections()) {
            if (conn.getDestLayer() == destLayer) {
                conn.setDelay(delay);
                conn.setWeights(weights);
            }
        }

    }
}
