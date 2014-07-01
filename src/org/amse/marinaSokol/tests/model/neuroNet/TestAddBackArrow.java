package org.amse.marinaSokol.tests.model.neuroNet;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IConnectionSchema;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.interfaces.schema.IInputLayerSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;

public class TestAddBackArrow extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);

        IConnectionSchema connectionSchema = netSchema.addBackConnectionSchema(source, dest);
        //Utils.writeResult(connectionSchema.toString(), "tests/schema/neuroNet/testAddBackArrow1.txt");
        Utils.compare(connectionSchema.toString(), "tests/schema/neuroNet/testAddBackArrow1.txt");
    }

    public void test2() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);
        netSchema.addDirectConnectionSchema(source, dest);
        netSchema.addBackConnectionSchema(source, dest);
        netSchema.addBackConnectionSchema(dest, source);
        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testAddBackArrow2.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testAddBackArrow2.txt");
    }

    public void test3() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        IInputLayerSchema source = netSchema.addInputLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);
        netSchema.addDirectConnectionSchema(source, dest);
        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testAddBackArrow3.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testAddBackArrow3.txt");
    }
}
