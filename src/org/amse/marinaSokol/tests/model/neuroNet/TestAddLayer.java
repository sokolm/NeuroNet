package org.amse.marinaSokol.tests.model.neuroNet;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;

public class TestAddLayer extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        netSchema.addLayerSchema(10, 20, 30 , 40);

        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testAddLayer1.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testAddLayer1.txt");
    }

    public void test2() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        netSchema.addLayerSchema(10, 20, 30 , 40);
        netSchema.addLayerSchema(10, 20, 30, 40);
        netSchema.addLayerSchema(10, 20, 50, 60);
        netSchema.addLayerSchema(20, 30, 10, 10);

        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testAddLayer2.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testAddLayer2.txt");
    }

    public void test3() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        netSchema.addLayerSchema(10, 20, 10, 10);
        StringBuffer sb = new StringBuffer();
        try {
            netSchema.getLayersSchema().add(null);
            sb.append("Error: we try add layer to unmodufauble collection");
        } catch(Exception e) {
             sb.append("OK: exception,we can't add layer to unmodefauble collection");
        }
        //Utils.writeResult(sb.toString(), "tests/schema/neuroNet/testAddLayer3.txt");
        Utils.compare(sb.toString(), "tests/schema/neuroNet/testAddLayer3.txt");
    }

    public void test4() {
    }
}
