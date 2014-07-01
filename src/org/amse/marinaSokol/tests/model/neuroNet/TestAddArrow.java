package org.amse.marinaSokol.tests.model.neuroNet;

import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.interfaces.schema.IConnectionSchema;
import org.amse.marinaSokol.model.interfaces.schema.IInputLayerSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;
import junit.framework.TestCase;

public class TestAddArrow extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);

        IConnectionSchema connectionSchema = netSchema.addDirectConnectionSchema(source, dest);
        //Utils.writeResult(connectionSchema.toString(), "tests/schema/neuroNet/testAddArrow1.txt");
        Utils.compare(connectionSchema.toString(), "tests/schema/neuroNet/testAddArrow1.txt");
    }

    public void test2() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest1 = netSchema.addLayerSchema(20, 40, 10, 10);
        ILayerSchema dest2 = netSchema.addLayerSchema(30, 50, 10, 10);

        netSchema.addDirectConnectionSchema(source, dest1);
        netSchema.addDirectConnectionSchema(source, dest2);

        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testAddArrow2.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testAddArrow2.txt");
    }
    //к входу приделать стрелку от слоя
    public void test3() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        IInputLayerSchema dest1 = netSchema.addInputLayerSchema(20, 40, 10, 10);

        netSchema.addDirectConnectionSchema(source, dest1);

        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testAddArrow3.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testAddArrow3.txt");
    }
    //сделать цикл
    public void test4() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema layer1 = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema layer2 = netSchema.addLayerSchema(20, 40, 10, 10);
        ILayerSchema layer3 = netSchema.addLayerSchema(10, 20, 50, 60);
        netSchema.addDirectConnectionSchema(layer1, layer2);
        netSchema.addDirectConnectionSchema(layer2, layer3);
        netSchema.addDirectConnectionSchema(layer3, layer1);

        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testAddArrow4.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testAddArrow4.txt");
    }
    //на цикл
    public void test5() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema layer1 = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema layer2 = netSchema.addLayerSchema(20, 40, 10, 10);
        ILayerSchema layer3 = netSchema.addLayerSchema(10, 20, 50, 60);

        ILayerSchema layer4 = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema layer5 = netSchema.addLayerSchema(20, 40, 10, 10);
        ILayerSchema layer6 = netSchema.addLayerSchema(10, 20, 50, 60);

        netSchema.addDirectConnectionSchema(layer1, layer2);
        netSchema.addDirectConnectionSchema(layer2, layer3);

        netSchema.addDirectConnectionSchema(layer4, layer5);
        netSchema.addDirectConnectionSchema(layer5, layer6);

        netSchema.addDirectConnectionSchema(layer3, layer4);

        netSchema.addDirectConnectionSchema(layer5, layer1);
        netSchema.addDirectConnectionSchema(layer1, layer4);

        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testAddArrow5.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testAddArrow5.txt");
    }
}
