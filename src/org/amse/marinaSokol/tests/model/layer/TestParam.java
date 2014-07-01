package org.amse.marinaSokol.tests.model.layer;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;


public class TestParam extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema layerSchema = netSchema.addLayerSchema(10, 20, 30 , 40);
        assertEquals(layerSchema.getX(), 10);
        assertEquals(layerSchema.getY(), 20);
        assertEquals(layerSchema.getWidth(), 30);
        assertEquals(layerSchema.getHeight(), 40);
    }
}
