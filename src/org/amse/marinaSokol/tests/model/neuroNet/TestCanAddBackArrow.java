package org.amse.marinaSokol.tests.model.neuroNet;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IUsualLayerSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;

public class TestCanAddBackArrow extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);

        assertEquals(netSchema.canAddBackConnectionSchema(source), true);
        assertEquals(netSchema.canAddBackConnectionSchema(source, dest), true);
    }

    public void test2() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        IUsualLayerSchema source = netSchema.addInputLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);

        assertEquals(netSchema.canAddBackConnectionSchema(source), false);
        assertEquals(netSchema.canAddBackConnectionSchema(source, dest), false);
        assertEquals(netSchema.canAddBackConnectionSchema(dest, source), false);
    }

    public void test3() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);
        netSchema.addDirectConnectionSchema(source, dest);
        assertEquals(netSchema.canAddBackConnectionSchema(dest, source), true);
    }


}
