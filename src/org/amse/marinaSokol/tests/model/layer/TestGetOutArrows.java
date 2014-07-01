package org.amse.marinaSokol.tests.model.layer;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.interfaces.schema.IConnectionSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;


public class TestGetOutArrows extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(50, 60, 10, 20);
        ILayerSchema dest1 = netSchema.addLayerSchema(10, 20, 30, 40);
        ILayerSchema dest2 = netSchema.addLayerSchema(10, 20, 30, 40);

        netSchema.addDirectConnectionSchema(source, dest1);
        netSchema.addDirectConnectionSchema(source, dest2);
        netSchema.addDirectConnectionSchema(source, dest1);

        StringBuffer sb = new StringBuffer();

        for (IConnectionSchema a: source.getOutputConnectionsSchema()) {
            sb.append(a);
        }
        //Utils.writeResult(sb.toString(), "tests/schema/layer/testGetOutArrows1.txt");
        Utils.compare(sb.toString(), "tests/schema/layer/testGetOutArrows1.txt");
    }

    public void test2() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(50, 60, 10, 20);
        ILayerSchema dest1 = netSchema.addLayerSchema(10, 20, 30, 40);

        netSchema.addDirectConnectionSchema(source, dest1);
        StringBuffer sb = new StringBuffer();
        try {
            source.getOutputConnectionsSchema().add(null);
            sb.append("Error: we add connectionSchema to unmodifauble collection!");
        } catch (Exception e) {
            sb.append("OK:exception, we try to add 1 connectionSchema to unmodifauble collection");
        }
        //Utils.writeResult(sb.toString(), "tests/schema/layer/testGetOutArrows2.txt");
        Utils.compare(sb.toString(), "tests/schema/layer/testGetOutArrows2.txt");
    }
}
