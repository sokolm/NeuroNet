package org.amse.marinaSokol.tests.model.layer;

import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;
import junit.framework.TestCase;


public class TestResize extends TestCase {
    private static void checkResize(StringBuffer sb, ILayerSchema layerSchema, int width, int height) {
        layerSchema.resize(width, height);
        if ((layerSchema.getHeight() == height) && (layerSchema.getWidth() == width)) {
            sb.append("OK\n");
        } else {
            sb.append("Error: width = ").append(width).append(" height = ").append(height);
        }
    }

    public void test1() {
        INeuroNetSchema net = new NeuroNetModel();

        ILayerSchema layerSchema = net.addLayerSchema(10, 20, 10, 20);
        StringBuffer sb = new StringBuffer();
        sb.append(layerSchema);
        checkResize(sb, layerSchema, 50, 60);
        sb.append(layerSchema);
        //Utils.writeResult(sb.toString(), "tests/schema/layer/testResize.txt");
        Utils.compare(sb.toString(), "tests/schema/layer/testResize.txt");
    }
}
