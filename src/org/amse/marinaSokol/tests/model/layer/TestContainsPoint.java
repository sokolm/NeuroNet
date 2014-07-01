package org.amse.marinaSokol.tests.model.layer;

import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;
import junit.framework.TestCase;

public class TestContainsPoint extends TestCase {
    static private void checkPoint(StringBuffer test, ILayerSchema layerSchema, int x, int y, boolean isContains) {
        if (layerSchema.containsPoint(x, y) == isContains) {
            test.append("OK\n");
        } else {
            test.append("Errow in Point: x = ").append(x).append(" y = ").append(y).append("\n");
        }
    }

    public void test1() {
        INeuroNetSchema net = new NeuroNetModel();
        ILayerSchema layerSchema = net.addLayerSchema(10, 20, 10, 20);
        StringBuffer sb = new StringBuffer();
        sb.append(net.toString());

        checkPoint(sb, layerSchema, 10, 20, true);
        checkPoint(sb, layerSchema, 9, 19, false);
        checkPoint(sb, layerSchema, 9, 20, false);
        checkPoint(sb, layerSchema, 10, 19, false);
        checkPoint(sb, layerSchema, 22, 42, false);
        checkPoint(sb, layerSchema, 15, 30, true);
        checkPoint(sb, layerSchema, 41, 10, false);
        checkPoint(sb, layerSchema, 40, 10, true);
        checkPoint(sb, layerSchema, 20, 40, true);
        checkPoint(sb, layerSchema, 21, 41, false);
        checkPoint(sb, layerSchema, 21, 40, false);
        checkPoint(sb, layerSchema, 20, 41, false);

        //Utils.writeResult(sb.toString(), "tests/schema/layer/testContainsPoint.txt");
        Utils.compare(sb.toString(), "tests/schema/layer/testContainsPoint.txt");
    }
}
