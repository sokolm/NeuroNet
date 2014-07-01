package org.amse.marinaSokol.tests.model.neuroNet;

import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;
import junit.framework.TestCase;

public class TestAddInput extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        netSchema.addInputLayerSchema(10, 20, 30 , 40);

        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testAddInput1.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testAddInput1.txt");
    }

    public void test2() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        netSchema.addInputLayerSchema(10, 20, 30 , 40);
        netSchema.addInputLayerSchema(10, 20, 30, 40);
        netSchema.addLayerSchema(10, 20, 50, 60);
        netSchema.addInputLayerSchema(20, 30, 10, 10);

        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testAddInput2.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testAddInput2.txt");
    }
}
