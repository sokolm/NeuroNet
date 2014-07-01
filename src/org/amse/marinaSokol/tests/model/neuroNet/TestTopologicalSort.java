package org.amse.marinaSokol.tests.model.neuroNet;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IUsualLayerSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;


public class TestTopologicalSort extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema layer1 = netSchema.addLayerSchema(11, 20, 30 , 40);
        ILayerSchema layer2 = netSchema.addLayerSchema(12, 20, 30 , 40);
        ILayerSchema layer3 = netSchema.addLayerSchema(13, 20, 30 , 40);
        netSchema.addDirectConnectionSchema(layer3, layer2);
        netSchema.addDirectConnectionSchema(layer2, layer1);

        netSchema.topologicalSort();
        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testTopologicalSort1.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testTopologicalSort1.txt");
    }

    public void test2() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema layer1 = netSchema.addLayerSchema(11, 20, 30 , 40);
        ILayerSchema layer2 = netSchema.addLayerSchema(12, 20, 30 , 40);
        ILayerSchema layer3 = netSchema.addLayerSchema(13, 20, 30 , 40);
        ILayerSchema layer4 = netSchema.addLayerSchema(14, 20, 30 , 40);
        ILayerSchema layer5 = netSchema.addLayerSchema(15, 20, 30 , 40);
        ILayerSchema layer6 = netSchema.addLayerSchema(16, 20, 30 , 40);

        netSchema.addDirectConnectionSchema(layer1, layer2);
        netSchema.addDirectConnectionSchema(layer2, layer3);

        netSchema.addDirectConnectionSchema(layer4, layer5);
        netSchema.addDirectConnectionSchema(layer5, layer6);
        netSchema.addDirectConnectionSchema(layer6, layer3);
        netSchema.addBackConnectionSchema(layer3, layer1);

        netSchema.topologicalSort();
        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testTopologicalSort2.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testTopologicalSort2.txt");
    }

    public void test3() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema layer1 = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema layer2 = netSchema.addLayerSchema(11, 20, 30 , 40);
        ILayerSchema layer3 = netSchema.addLayerSchema(12, 20, 30 , 40);
        ILayerSchema layer4 = netSchema.addLayerSchema(13, 20, 30 , 40);
        ILayerSchema layer5 = netSchema.addLayerSchema(14, 20, 30 , 40);
        ILayerSchema layer6 = netSchema.addLayerSchema(15, 20, 30 , 40);
        IUsualLayerSchema layer7 = netSchema.addInputLayerSchema(16, 20, 20, 20);
        IUsualLayerSchema layer8 = netSchema.addInputLayerSchema(17, 20, 20, 20);

        netSchema.addDirectConnectionSchema(layer1, layer2);
        netSchema.addDirectConnectionSchema(layer2, layer3);

        netSchema.addDirectConnectionSchema(layer4, layer5);
        netSchema.addDirectConnectionSchema(layer5, layer6);
        netSchema.addDirectConnectionSchema(layer6, layer3);
        netSchema.addBackConnectionSchema(layer3, layer1);
        netSchema.addDirectConnectionSchema(layer7, layer6);
        netSchema.addDirectConnectionSchema(layer8, layer3);

        netSchema.topologicalSort();
        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testTopologicalSort3.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testTopologicalSort3.txt");
    }

}
