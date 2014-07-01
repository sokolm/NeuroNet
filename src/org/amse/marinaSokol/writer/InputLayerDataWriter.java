package org.amse.marinaSokol.writer;

import org.amse.marinaSokol.model.impl.object.InputLayerData;
import org.amse.marinaSokol.exception.MyException;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Formatter;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class InputLayerDataWriter {
    private InputLayerData myData;
    private Document myDocument;

    public InputLayerDataWriter(InputLayerData data) throws
        ParserConfigurationException {
        myData = data;
        org.w3c.dom.Node myRoot = createDocument();
        myDocument.appendChild(myRoot);

    }

    private org.w3c.dom.Node createDocument()
        throws ParserConfigurationException {
        myDocument = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder().newDocument();
        Element root = myDocument.createElement("data");
        Element e;
        Element echild;
        e = myDocument.createElement("size");
        e.setAttribute("time", (new Integer(myData.getPatternTrainingData().getSizeY())).toString());
        e.setAttribute("neuronsNumber", (new Integer(myData.getPatternTrainingData().getSizeX())).toString());
        root.appendChild(e);

        for (int i = 0; i < myData.getPatternTrainingData().getSizeY(); i++){
            e = myDocument.createElement("time");
            for (int j = 0 ; j < myData.getPatternTrainingData().getSizeX(); j++) {
                echild = myDocument.createElement("neuron");
                echild.setAttribute("number", new Formatter(Locale.US).format("%.3f", new Double(myData.getPatternTrainingData().getXY(i, j))).toString());
                
                e.appendChild(echild);
            }
            root.appendChild(e);
        }
        return root;
    }

    public void write(String filename) throws IOException, MyException , TransformerException {
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
}
