package org.amse.marinaSokol.tests.model.neuroNet;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;


public class TestRemoveLayer extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema layerSchema = netSchema.addLayerSchema(10, 20, 30 , 40);
        netSchema.removeLayerSchema(layerSchema);

        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testRemoveLayer1.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testRemoveLayer1.txt");
    }

    public void test2() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema layer1 = netSchema.addLayerSchema(10, 20, 30 , 40);
        netSchema.addLayerSchema(10, 20, 30, 40);
        netSchema.addLayerSchema(10, 20, 50, 60);
        ILayerSchema layer2 = netSchema.addLayerSchema(20, 30, 10, 10);
        netSchema.removeLayerSchema(layer1);
        netSchema.removeLayerSchema(layer2);

        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testRemoveLayer2.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testRemoveLayer2.txt");
    }

    public void test3() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema layer1 = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema layer2 = netSchema.addLayerSchema(10, 20, 30, 40);
        netSchema.addDirectConnectionSchema(layer1, layer2);
        netSchema.addDirectConnectionSchema(layer2, layer1);
        netSchema.removeLayerSchema(layer1);

        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testRemoveLayer3.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testRemoveLayer3.txt");
    }

    public void test4() {
        INeuroNetSchema netSchema = new NeuroNetModel();
        ILayerSchema layer1 = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema layer2 = netSchema.addLayerSchema(10, 20, 30, 40);
        ILayerSchema layer3 = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema layer4 = netSchema.addLayerSchema(10, 20, 30, 40);

        netSchema.addDirectConnectionSchema(layer1, layer1);
        netSchema.addDirectConnectionSchema(layer1, layer2);
        netSchema.addDirectConnectionSchema(layer1, layer3);
        netSchema.addDirectConnectionSchema(layer1, layer4);
        netSchema.addDirectConnectionSchema(layer2, layer3);
        netSchema.removeLayerSchema(layer1);

        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testRemoveLayer4.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testRemoveLayer4.txt");
    }

    public void test5() {
        INeuroNetSchema netSchema = new NeuroNetModel();
        ILayerSchema layer1 = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema layer2 = netSchema.addLayerSchema(10, 20, 30, 40);
        ILayerSchema layer3 = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema layer4 = netSchema.addLayerSchema(10, 20, 30, 40);

        netSchema.addDirectConnectionSchema(layer1, layer1);
        netSchema.addDirectConnectionSchema(layer2, layer1);
        netSchema.addDirectConnectionSchema(layer3, layer1);
        netSchema.addDirectConnectionSchema(layer4, layer1);
        netSchema.addDirectConnectionSchema(layer1, layer2);
        netSchema.removeLayerSchema(layer1);

        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testRemoveLayer5.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testRemoveLayer5.txt");
    }

    public void test6() {

        INeuroNetSchema netSchema = new NeuroNetModel();
        ILayerSchema layer1 = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema layer2 = netSchema.addLayerSchema(10, 20, 30, 40);

        netSchema.addDirectConnectionSchema(layer1, layer1);
        netSchema.addDirectConnectionSchema(layer2, layer1);
        netSchema.addDirectConnectionSchema(layer2, layer2);
        netSchema.addBackConnectionSchema(layer1, layer1);
        assertEquals(layer2.getOutputConnectionsSchema().size(), 1);
        assertEquals(layer2.getInputConnectionsSchema().size(), 0);
        netSchema.removeLayerSchema(layer1);

        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testRemoveLayer6.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testRemoveLayer6.txt");
    }
}
