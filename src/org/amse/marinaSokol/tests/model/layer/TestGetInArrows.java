package org.amse.marinaSokol.tests.model.layer;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IConnectionSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;


public class TestGetInArrows extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema dest = netSchema.addLayerSchema(50, 60, 10, 20);
        ILayerSchema source1 = netSchema.addLayerSchema(10, 20, 30, 40);
        ILayerSchema source2 = netSchema.addLayerSchema(10, 20, 30, 40);

        netSchema.addDirectConnectionSchema(source1, dest);
        netSchema.addDirectConnectionSchema(source2, dest);
        netSchema.addDirectConnectionSchema(source1, dest);

        StringBuffer sb = new StringBuffer();

        for (IConnectionSchema a: dest.getInputConnectionsSchema()) {
            sb.append(a);
        }
        //Utils.writeResult(sb.toString(), "tests/schema/layer/testGetInArrows1.txt");
        Utils.compare(sb.toString(), "tests/schema/layer/testGetInArrows1.txt");
    }

    public void test2() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(50, 60, 10, 20);
        ILayerSchema dest1 = netSchema.addLayerSchema(10, 20, 30, 40);

        netSchema.addDirectConnectionSchema(source, dest1);
        StringBuffer sb = new StringBuffer();
        try {
            source.getInputConnectionsSchema().add(null);
            sb.append("Error: we add connectionSchema to unmodifauble collection!");
        } catch (Exception e) {
            sb.append("OK:exception, we try to add 1 connectionSchema to unmodifauble collection");
        }
        //Utils.writeResult(sb.toString(), "tests/schema/layer/testGetInArrows2.txt");
        Utils.compare(sb.toString(), "tests/schema/layer/testGetInArrows2.txt");
    }
}
