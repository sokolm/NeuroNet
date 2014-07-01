package org.amse.marinaSokol.reader;

import org.amse.marinaSokol.exception.MyException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.IOException;
import java.io.File;

public class InputLayerDataReader {
    private Document myDocument;
    private double[][] myDataArray;

    public InputLayerDataReader(String file) throws SAXException,
        ParserConfigurationException, IOException {
        File f = new File(file);
        DocumentBuilder db = getBuilder();
        db.setErrorHandler(new ErrorHandler() {
                    public void warning(SAXParseException exception) throws SAXException {
                    }

                    public void error(SAXParseException exception) throws SAXException {
                    }

                    public void fatalError(SAXParseException exception) throws SAXException {
                    }
                } );

        myDocument = db.parse(f);
    }

    public double[][] read() throws MyException {
        buildData(myDocument.getDocumentElement());
        return myDataArray;
    }

    private DocumentBuilder getBuilder()
        throws SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory =
            DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(false);
        return factory.newDocumentBuilder();
    }

    private void buildData(Element root) throws MyException {
        NodeList children = root.getChildNodes();
        boolean size = false;
        int countTime = 0;
        int maxCountTime = 0;
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element) {
                Element childElement = (Element)child;
                if (child.getNodeName().equals("size") && !size) {
                    maxCountTime = setSize(childElement);
                    size = true;

                } else {
                    if (child.getNodeName().equals("time") && (countTime < maxCountTime) && size) {
                        fillInput(childElement, countTime);
                        countTime++;

                    } else {
                        if (!size) {
                            throw new MyException("нет тега size ");
                        } else if ((!(countTime < maxCountTime) && size)) {
                            throw new MyException("лишний тег в руте max=" + maxCountTime );
                        } else {
                            throw new MyException("неопознанный тег в руте");
                        }
                    }
                }
            }
        }
    }

    private int setSize(Node n) {
        NamedNodeMap attrs = n.getAttributes();
        int time = new Integer(
                attrs.getNamedItem("time").getNodeValue());
        int neuronsNumber = new Integer(
                attrs.getNamedItem("neuronsNumber").getNodeValue());

        myDataArray = new double[time][neuronsNumber];
        return time;

    }

    private void fillInput(Node n, int time) {
        NodeList nodes;

        nodes = n.getChildNodes();
        int count = 0;
        for (int i = 0; i < nodes.getLength(); i++) {
            Node child = nodes.item(i);
            if (child instanceof Element) {
                setData(child, time, count);
                count++;
            }
        }
    }

    private void setData(Node n, int time, int numberNeuron) {
        NamedNodeMap attrs = n.getAttributes();
        final double number = new Double(
                attrs.getNamedItem("number").getNodeValue());
        myDataArray[time][numberNeuron] = number;

    }
}
