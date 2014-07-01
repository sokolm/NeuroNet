package org.amse.marinaSokol.tests.reader;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.writer.ModelFileWriter;
import org.amse.marinaSokol.tests.Utils;
import org.amse.marinaSokol.reader.ModelFileReader;
import org.amse.marinaSokol.exception.MyException;
import org.xml.sax.SAXParseException;

import java.io.File;

public class TestModelReader extends TestCase {
    private boolean writeDiagram(INeuroNetSchema d, String f) {
        try {
            ModelFileWriter fw = new ModelFileWriter(d);
            fw.write(f);
        } catch (Throwable e) {
            return false;
        }
        return true;
    }

    private INeuroNetSchema readDiagram(File file) {
        ModelFileReader fr;
        INeuroNetSchema d = new NeuroNetModel();
        try {
            fr = new ModelFileReader(file);
            fr.read(d);
        } catch (MyException e) {
            return null;
        }
        catch (SAXParseException e) {
            return null;
        }
        catch (Exception e) {
            return null;
        }
        catch (Error e) {
            return null;
        }
        catch (Throwable w) {
            return null;

        }
        return d;
    }

    public void test1() {
        File file = new File("tests/reader/rightEmptyModel.txt");
        INeuroNetSchema schema = readDiagram(file);
        writeDiagram(schema, "tests/reader/testEmptyModel.txt");
        //writeDiagram(schema, "tests/reader/rightEmptyModel.txt");
        Utils.compareFile("tests/reader/rightEmptyModel.txt", "tests/reader/testEmptyModel.txt");
    }

    public void test2() {
        File file = new File("tests/reader/rightComplexNetObjectModel.txt");
        INeuroNetSchema schema = readDiagram(file);
        writeDiagram(schema, "tests/reader/testComplexNetObjectModel.txt");
        writeDiagram(schema, "tests/reader/rightComplexNetObjectModel.txt");
        Utils.compareFile("tests/reader/rightComplexNetObjectModel.txt", "tests/reader/testComplexNetObjectModel.txt");

    }

    public void test3() {
        File file = new File("tests/reader/test.txt");
        assertEquals(null, readDiagram(file));
    }

    public void test4() {
        File file = new File("tests/reader/testIncorrectLayerObject.txt");
        assertEquals(null, readDiagram(file));

    }

    public void test5() {
        File file = new File("tests/reader/testIncorrectConnectionObject.txt");
        assertEquals(null, readDiagram(file));

    }

    public void test6() {
        File file = new File("tests/reader/testAnotherFile.txt");
        assertEquals(null, readDiagram(file));        
    }

    public void test7() {
        File file = new File("tests/reader/testAnotherXMLFile.txt");
        assertEquals(null, readDiagram(file));        
    }

}
