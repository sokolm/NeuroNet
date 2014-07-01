package org.amse.marinaSokol.tests.model.neuroNet;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.interfaces.schema.IUsualLayerSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;

public class TestLayers extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();
        StringBuffer sb = new StringBuffer();

        for (IUsualLayerSchema iSchema : netSchema.getLayersSchema()) {
            sb.append(iSchema);
        }
        //Utils.writeResult(sb.toString(), "tests/schema/neuroNet/testLayers1.txt");
        Utils.compare(sb.toString(), "tests/schema/neuroNet/testLayers1.txt");
    }

    public void test2() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        netSchema.addLayerSchema(10, 20, 30 , 40);
        netSchema.addLayerSchema(10, 30, 30, 40);
        netSchema.addLayerSchema(10, 40, 50, 60);
        netSchema.addLayerSchema(20, 50, 10, 10);
        StringBuffer sb = new StringBuffer();

        for (IUsualLayerSchema iSchema : netSchema.getLayersSchema()) {
            sb.append(iSchema);
        }
        //Utils.writeResult(sb.toString(), "tests/schema/neuroNet/testLayers2.txt");
        Utils.compare(sb.toString(), "tests/schema/neuroNet/testLayers2.txt");
    }
}
