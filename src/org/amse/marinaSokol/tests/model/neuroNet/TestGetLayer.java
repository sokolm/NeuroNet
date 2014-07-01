package org.amse.marinaSokol.tests.model.neuroNet;

import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;
import junit.framework.TestCase;

public class TestGetLayer extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();
        netSchema.addLayerSchema(50, 60, 10, 20);
        netSchema.addLayerSchema(10, 20, 30, 40);
        ILayerSchema layerSchema = netSchema.addLayerSchema(10, 20, 10, 10);
        netSchema.addLayerSchema(60, 40, 90, 30);
        StringBuffer sb = new StringBuffer();
        sb.append(layerSchema.toString());
        if (layerSchema == netSchema.getLayerSchema(10, 20)) {
            sb.append("OK: we get the same last layerSchema");
        } else {
            sb.append("Error: we get another layerSchema");
        }
        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testGetLayer1.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testGetLayer1.txt");
    }

    public void test2() {
        INeuroNetSchema netSchema = new NeuroNetModel();
        netSchema.addLayerSchema(50, 60, 10, 20);
        netSchema.addLayerSchema(10, 20, 30, 40);
        netSchema.addLayerSchema(10, 20, 10, 10);
        netSchema.addLayerSchema(60, 40, 90, 30);
        StringBuffer sb = new StringBuffer();
        if (null == netSchema.getLayerSchema(1000, 2000)) {
            sb.append("OK: netSchema dosen't contain this layer");
        } else {
            sb.append("Error: we get another layer");
        }
        //Utils.writeResult(netSchema.toString(), "tests/schema/neuroNet/testGetLayer2.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testGetLayer2.txt");
    }
//проверяет на последовательность слоев, после focusLayer
    public void test3() {
        INeuroNetSchema netSchema = new NeuroNetModel();
        netSchema.addLayerSchema(50, 60, 10, 20);
        netSchema.addLayerSchema(10, 20, 30, 40);
        netSchema.addLayerSchema(10, 20, 10, 10);
        netSchema.addLayerSchema(60, 40, 90, 30);
        StringBuffer sb = new StringBuffer();
        sb.append(netSchema.toString());
        netSchema.getLayerSchema(10, 20);
        sb.append(netSchema.toString());
        //Utils.writeResult(sb.toString(), "tests/schema/neuroNet/testGetLayer3.txt");
        Utils.compare(netSchema.toString(), "tests/schema/neuroNet/testGetLayer3.txt");
    }
}
