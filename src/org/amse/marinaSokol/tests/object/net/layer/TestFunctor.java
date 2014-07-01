package org.amse.marinaSokol.tests.object.net.layer;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.interfaces.object.net.ActivationFunctorType;
import org.amse.marinaSokol.model.interfaces.object.net.INeuroNet;
import org.amse.marinaSokol.model.interfaces.object.net.ILayer;
import org.amse.marinaSokol.model.interfaces.object.net.IUsualLayer;
import org.amse.marinaSokol.model.impl.NeuroNetModel;

public class TestFunctor extends TestCase {
    public void test1() {
        INeuroNetSchema netSchema = new NeuroNetModel();

        netSchema.addLayerSchema(10, 20, 30 , 40);
        netSchema.createNeuroNet();
        INeuroNet net = netSchema.getNeuroNet();
        IUsualLayer layer = net.getLayers().get(0);
        ((ILayer)layer).setActivation(ActivationFunctorType.LINEAR);
        assertEquals(((ILayer)layer).getActivation().getNameFunction(), "linear");
        ((ILayer)layer).setActivation(ActivationFunctorType.LOGISTIC);
        assertEquals(((ILayer)layer).getActivation().getNameFunction(), "logistic");
    }
}
