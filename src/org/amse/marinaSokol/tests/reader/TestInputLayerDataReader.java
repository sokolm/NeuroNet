package org.amse.marinaSokol.tests.reader;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.impl.object.InputLayerData;
import org.amse.marinaSokol.reader.InputLayerDataReader;
import org.amse.marinaSokol.writer.InputLayerDataWriter;
import org.amse.marinaSokol.tests.Utils;

import java.io.File;

public class TestInputLayerDataReader extends TestCase {
    private InputLayerData readDataFile(String file) {
        InputLayerDataReader fr;
        InputLayerData data;
        try {
            fr = new InputLayerDataReader(file);
            data = new  InputLayerData(fr.read());
        } catch (Exception e) {
            return null;
        }
        return data;
    }
    
    private boolean writeDataFile(InputLayerData data, String f) {
        try {
            InputLayerDataWriter fw = new InputLayerDataWriter(data);
            fw.write(f);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void test1() {
        File file = new File("tests/reader/data/rightSimpleData.txt");
        InputLayerData data2 = readDataFile(file.getPath());
        writeDataFile(data2, "tests/reader/data/testSimpleData1.txt");
        writeDataFile(data2, "tests/reader/data/rightSimpleData1.txt");
        Utils.compareFile("tests/reader/data/rightSimpleData1.txt", "tests/reader/data/testSimpleData1.txt");
    }

    public void test2() {
        File file = new File("tests/reader/data/rightSimpleData2.txt");
        InputLayerData data2 = readDataFile(file.getPath());
        assertEquals(null, data2);
    }

    public void test3() {
          File file = new File("tests/reader/data/incorrectSimpleData3.txt");
          assertEquals(null, readDataFile(file.getPath()));
    }
}
