package org.amse.marinaSokol.tests.model.neuroNet;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IConnectionSchema;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;


public class TestRemoveArrow extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);

        IConnectionSchema connectionSchema = netSchema.addDirectConnectionSchema(source, dest);
        netSchema.removeConnectionSchema(connectionSchema);
        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testRemoveArrow1.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testRemoveArrow1.txt");
    }

    public void test2() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest1 = netSchema.addLayerSchema(20, 40, 10, 10);
        ILayerSchema dest2 = netSchema.addLayerSchema(30, 50, 10, 10);

        IConnectionSchema connectionSchema = netSchema.addDirectConnectionSchema(source, dest1);
        netSchema.addDirectConnectionSchema(source, dest2);
        netSchema.removeConnectionSchema(connectionSchema);
        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testRemoveArrow2.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testRemoveArrow2.txt");
    }
}
