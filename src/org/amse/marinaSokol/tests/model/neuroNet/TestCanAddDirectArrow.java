package org.amse.marinaSokol.tests.model.neuroNet;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.interfaces.schema.IInputLayerSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;

public class TestCanAddDirectArrow extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);

        assertEquals(netSchema.canAddDirectConnectionSchema(source), true);
        assertEquals(netSchema.canAddDirectConnectionSchema(source, dest), true);
    }

    public void test2() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        IInputLayerSchema source = netSchema.addInputLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);

        assertEquals(netSchema.canAddDirectConnectionSchema(source), true);
        assertEquals(netSchema.canAddDirectConnectionSchema(source, dest), true);
        assertEquals(netSchema.canAddDirectConnectionSchema(dest, source), false);
    }

    public void test3() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);
        netSchema.addDirectConnectionSchema(source, dest);
        assertEquals(netSchema.canAddDirectConnectionSchema(dest, source), false);
    }

    public void test4() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);
        ILayerSchema dest2 = netSchema.addLayerSchema(20, 30, 10, 30);
        netSchema.addDirectConnectionSchema(source, dest);
        netSchema.addDirectConnectionSchema(dest, dest2);
        assertEquals(netSchema.canAddDirectConnectionSchema(dest2, dest), false);

    }
}
