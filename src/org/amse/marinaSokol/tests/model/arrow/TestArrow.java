package org.amse.marinaSokol.tests.model.arrow;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IConnectionSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;


public class TestArrow extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);
        IConnectionSchema connectionSchema = netSchema.addDirectConnectionSchema(source, dest);
        assertEquals(connectionSchema.getSourceLayerSchema(), source);
        assertEquals(connectionSchema.getDestLayerSchema(), dest);
        //Utils.writeResult(connectionSchema.toString(), "tests/schema/connection/testDelay1.txt");
        Utils.compare(connectionSchema.toString(), "tests/schema/connection/testDelay1.txt");

    }

    public void test2() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);
        ILayerSchema dest2 = netSchema.addLayerSchema(60, 80, 20, 20);
        IConnectionSchema connectionSchema = netSchema.addDirectConnectionSchema(source, dest);
        connectionSchema.setDestLayerSchema(dest2);
        assertEquals(connectionSchema.getSourceLayerSchema(), source);
        assertEquals(connectionSchema.getDestLayerSchema(), dest2);
        //Utils.writeResult(connectionSchema.toString(), "tests/schema/connection/testDelay2.txt");
        Utils.compare(connectionSchema.toString(), "tests/schema/connection/testDelay2.txt");
    }

    public void test3() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 20, 30 , 40);
        ILayerSchema dest = netSchema.addLayerSchema(20, 40, 10, 10);
        IConnectionSchema connectionSchema = netSchema.addDirectConnectionSchema(source, dest);
        assertEquals(connectionSchema.isDirect(), true);
        IConnectionSchema arrowback = netSchema.addBackConnectionSchema(source, dest);
        assertEquals(arrowback.isDirect(), false);
        //Utils.writeResult(connectionSchema.toString(), "tests/schema/connection/testIsDirect3.txt");
        Utils.compare(connectionSchema.toString(), "tests/schema/connection/testIsDirect3.txt");
    }

    public void test4() {
        StringBuffer sb = new StringBuffer();
        INeuroNetSchema netSchema = new NeuroNetModel();

        ILayerSchema source = netSchema.addLayerSchema(10, 10, 10 , 10);
        ILayerSchema dest = netSchema.addLayerSchema(40, 10, 10, 10);

        IConnectionSchema connectionSchema1 = netSchema.addDirectConnectionSchema(source, dest);
        sb.append("point(0, 0)=").append(connectionSchema1.proximity(0, 0));
        sb.append("point(10, 20)=").append(connectionSchema1.proximity(20, 10));
        sb.append("point(10, 40)=").append(connectionSchema1.proximity(21, 15));
        sb.append("point(10, 45)=").append(connectionSchema1.proximity(10, 45));
        sb.append("point(10, 15)=").append(connectionSchema1.proximity(10, 15));
        sb.append("point(15, 35)=").append(connectionSchema1.proximity(15, 35));
        assertEquals((connectionSchema1.proximity(0,0) <= 3), false);
        IConnectionSchema arrowback = netSchema.addBackConnectionSchema(source, dest);
        assertEquals(arrowback.isDirect(), false);
        //Utils.writeResult(sb.toString(), "tests/schema/connection/testProximity.txt");
        Utils.compare(sb.toString(), "tests/schema/connection/testProximity.txt");

    }
}
