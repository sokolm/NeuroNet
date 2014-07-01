package org.amse.marinaSokol.tests.writer;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.impl.object.InputLayerData;
import org.amse.marinaSokol.writer.InputLayerDataWriter;
import org.amse.marinaSokol.view.ErrorForm;
import org.amse.marinaSokol.tests.Utils;

public class TestInputLayerDataWriter extends TestCase {
    private boolean writeDataFile(InputLayerData data, String f) {
        try {
            InputLayerDataWriter fw = new InputLayerDataWriter(data);
            fw.write(f);
        } catch (Exception e) {
            new ErrorForm( null, e,
                        "Ошибка во время записи данных" , false);
            return false;
        }
        return true;
    }

    public void test1() {
        double[][] array = new double[3][5];
        InputLayerData schema = new InputLayerData(array);
        writeDataFile(schema, "tests/writer/testSimpleData.txt");
        writeDataFile(schema, "tests/writer/rightSimpleData.txt");
        Utils.compareFile("tests/writer/rightSimpleData.txt", "tests/writer/testSimpleData.txt");
    }

    public void test2() {
        double[][] array = new double[2][5];

        for (double[] anArray : array) {
            for (int j = 0; j < anArray.length; j++) {
                anArray[j] = 76;
            }
        }

        InputLayerData schema = new InputLayerData(array);
        writeDataFile(schema, "tests/writer/testSimpleData2.txt");
        writeDataFile(schema, "tests/writer/rightSimpleData2.txt");
        Utils.compareFile("tests/writer/rightSimpleData2.txt", "tests/writer/testSimpleData2.txt");
    }

    public void test3() {
        double[][] array = new double[6][5];
        for (double[] anArray : array) {
            for (int j = 0; j < anArray.length; j++) {
                anArray[j] = j;
            }
        }
        InputLayerData schema = new InputLayerData(array);
        //System.out.println(schema.getPatternTrainingData().getXY(1,4));
        writeDataFile(schema, "tests/writer/testSimpleData3.txt");
        writeDataFile(schema, "tests/writer/rightSimpleData3.txt");
        Utils.compareFile("tests/writer/rightSimpleData3.txt", "tests/writer/testSimpleData3.txt");
    }

}
