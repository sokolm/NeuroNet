package org.amse.marinaSokol.tests.model.layer;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;


public class TestMoveTo extends TestCase {
    private static void checkMoveTo(StringBuffer test, ILayerSchema layerSchema, int x, int y) {
        layerSchema.moveTo(x, y);
        if ((layerSchema.getX() == x) && (layerSchema.getY() == y)) {
            test.append("OK\n");
        } else {
            test.append("Error in Point: x = ").append(x).append(" y = ").append(y).append('\n');
        }
    }

    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();
        ILayerSchema layerSchema = netSchema.addLayerSchema(10, 20, 30 , 40);
        StringBuffer test = new StringBuffer();
        test.append(layerSchema);
        checkMoveTo(test, layerSchema, 50, 60);
        checkMoveTo(test, layerSchema, 20, 30);
        //Utils.writeResult(test.toString(), "tests/schema/layer/testMoveTo.txt");
        Utils.compare(test.toString(), "tests/schema/layer/testMoveTo.txt");
    }
}
