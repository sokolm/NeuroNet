package org.amse.marinaSokol.tests.object.net.connection;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IConnectionSchema;
import org.amse.marinaSokol.model.interfaces.object.net.IConnection;
import org.amse.marinaSokol.model.impl.NeuroNetModel;

public class TestDelay extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);
        IConnectionSchema connectionSchema = netSchema.addDirectConnectionSchema(source, dest);
        netSchema.createNeuroNet();
        IConnection  conn = (IConnection)connectionSchema;
        conn.setDelay(5);
        assertEquals(5, conn.getDelay());
        conn.setDelay(10);
        assertEquals(10, conn.getDelay());
    }
}
