package org.amse.marinaSokol.tests.object.net.neuroNet;

import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IOutputLayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IInputLayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.interfaces.object.net.INeuroNet;
import org.amse.marinaSokol.tests.Utils;
import junit.framework.TestCase;

public class TestCreateNeuroNet extends TestCase {
    public void test1() {
        INeuroNetSchema net = new NeuroNetModel();
        IInputLayerSchema inputLayer = net.addInputLayerSchema(10, 10 , 10, 10);
        ILayerSchema layerSchema = net.addLayerSchema(20, 20, 10, 10);
        IOutputLayerSchema out = net.addOutputLayerSchema(40, 40, 10, 10);
        net.addDirectConnectionSchema(inputLayer, layerSchema);
        net.addDirectConnectionSchema(layerSchema, out);

        INeuroNet netobj = net.createNeuroNet();
        assertEquals(netobj, net.getNeuroNet());
        
        //Utils.writeResult(net.toString(), "tests/schema/neuroNet/testCreateNeuroNet1.txt");
        Utils.compare(net.toString(), "tests/schema/neuroNet/testCreateNeuroNet1.txt");       
    }
}
