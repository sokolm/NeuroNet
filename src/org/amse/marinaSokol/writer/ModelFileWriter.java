package org.amse.marinaSokol.writer;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.amse.marinaSokol.model.interfaces.schema.*;
import org.amse.marinaSokol.model.interfaces.object.net.*;
import org.amse.marinaSokol.exception.MyException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import java.util.List;
import java.util.Formatter;
import java.util.Locale;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.File;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class ModelFileWriter {
    private INeuroNetSchema mySchema;
    private Document myDocument;

    public ModelFileWriter(INeuroNetSchema diagram) throws
        ParserConfigurationException {
        mySchema = diagram;
        org.w3c.dom.Node myRoot = createDocument();
        myDocument.appendChild(myRoot);
    }

    private org.w3c.dom.Node createDocument()
        throws ParserConfigurationException {
        Document doc = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder().newDocument();
        myDocument = doc;
        Element root = doc.createElement("model");
        root.appendChild(writeSchema(doc));
        if (mySchema.getNeuroNet() != null) {
            root.appendChild(writeObject(doc));
        }
        return root;
    }

    private Element writeSchema(Document doc) {
        Element schemaElement = doc.createElement("schema");
        Element e;
        List<? extends IUsualLayerSchema> list = mySchema.getLayersSchema();
        for (IUsualLayerSchema n : list) {
            e = doc.createElement("layer");
            e.setAttribute("type", getTypeSchema(n));
            e.setAttribute("x", (new Integer(n.getX())).toString());
            e.setAttribute("y", (new Integer(n.getY())).toString());
            e.setAttribute("width", (new Integer(n.getWidth())).toString());
            e.setAttribute("height", (new Integer(n.getHeight())).toString());
            e.setAttribute("id", new Integer(n.getId()).toString());
            schemaElement.appendChild(e);
        }

        for (IUsualLayerSchema nn : list){
            for (IConnectionSchema a : nn.getOutputConnectionsSchema()) {
                if (a != null) {
                    e = doc.createElement("connection");
                    e.setAttribute("source",
                        (new Integer(list.indexOf(nn))).toString());
                    e.setAttribute("destination",
                        (new Integer(list.indexOf(a.getDestLayerSchema()))).toString());
                    e.setAttribute("direct", new Boolean(a.isDirect()).toString());
                    schemaElement.appendChild(e);
                }
            }
        }
        return schemaElement;
    }

    private Element writeObject(Document doc) {
        Element objectElement = doc.createElement("object");
        Element e;
        INeuroNet net = mySchema.getNeuroNet();
        List<? extends IUsualLayer> list = net.getLayers();
        for (IUsualLayer n : list) {
            e = doc.createElement("layer");
            e.setAttribute("type", getTypeObject(n));
            e.setAttribute("numNeurons", (new Integer(n.getNeuronsNumber())).toString());
            if (!(n instanceof IInputLayer)) {
                e.setAttribute("functor", ((ILayer)n).getActivation().getNameFunction());
            } else {
                e.setAttribute("functor", "");
            }
            e.setAttribute("id", new Integer(net.getLayers().indexOf(n)).toString());
            objectElement.appendChild(e);
        }
        Element weights;
        Element row;
        Element w;
        for (IUsualLayer nn : list){
            for (IConnection a : nn.getOutputConnections()) {
                if (a != null) {
                    e = doc.createElement("connection");
                    e.setAttribute("source",
                        (new Integer(list.indexOf(nn))).toString());
                    e.setAttribute("destination",
                        (new Integer(list.indexOf(a.getDestLayer()))).toString());
                    e.setAttribute("delay", new Integer(a.getDelay()).toString());

                    weights = doc.createElement("weights");
                    for (int i = 0; i < a.getWeights().length; i++) {
                        row = doc.createElement("row");
                        for (int j = 0; j < a.getWeights()[0].length; j++) {
                            w = doc.createElement("weight");
                            w.setAttribute("value", new Formatter(Locale.US).format("%.3f", new Double(a.getWeights()[i][j])).toString());
                            row.appendChild(w);
                        }
                        weights.appendChild(row);
                    }
                    e.appendChild(weights);
                    objectElement.appendChild(e);
                }
            }
        }
        return objectElement;
    }


    public void write(String filename) throws IOException, MyException, 
        TransformerException {
        new java.io.FileWriter(filename);
        OutputFormat format = new OutputFormat(myDocument);
        format.setIndenting(true);
        format.setEncoding("UTF-8");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(filename));
            XMLSerializer serializer = new XMLSerializer(outputStream, format);
            serializer.serialize(myDocument);
        } catch (FileNotFoundException e) {
            throw new MyException("Файл не найден. Имя файла: " + filename);
        } catch (IOException e) {
            throw new MyException("Ошибка при записи файла. Имя файла: " + filename);
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                //noinspection ThrowFromFinallyBlock
                throw new MyException("Ошибка при закрытии файла. Имя файла: " + filename);
            }
        }
    }

    private String getTypeSchema(IUsualLayerSchema n) {
        if (n instanceof IInputLayerSchema)
            return "begin";
        if (n instanceof IOutputLayerSchema)
            return "end";
        if (n instanceof ILayerSchema)
            return "ordinary";
        return "";
    }

    private String getTypeObject(IUsualLayer n) {
        if (n instanceof IInputLayer)
            return "begin";
        if (n instanceof IOutputLayer)
            return "end";
        if (n instanceof ILayer)
            return "ordinary";
        return "";
    }
}
