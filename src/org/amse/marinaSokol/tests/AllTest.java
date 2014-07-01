package org.amse.marinaSokol.tests;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.amse.marinaSokol.tests.model.arrow.TestArrow;
import org.amse.marinaSokol.tests.model.layer.*;
import org.amse.marinaSokol.tests.model.neuroNet.*;
import org.amse.marinaSokol.tests.object.net.neuroNet.TestCreateNeuroNet;
import org.amse.marinaSokol.tests.object.net.layer.TestFunctor;
import org.amse.marinaSokol.tests.object.net.layer.TestNumNeurons;
import org.amse.marinaSokol.tests.writer.TestModelWriter;
import org.amse.marinaSokol.tests.writer.TestInputLayerDataWriter;
import org.amse.marinaSokol.tests.reader.TestModelReader;
import org.amse.marinaSokol.tests.reader.TestInputLayerDataReader;

public class AllTest {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestArrow.class);

        suite.addTestSuite(TestContainsPoint.class);
        suite.addTestSuite(TestGetInArrows.class);
        suite.addTestSuite(TestGetOutArrows.class);
        suite.addTestSuite(TestMoveTo.class);
        suite.addTestSuite(TestResize.class);
        suite.addTestSuite(TestParam.class);
        suite.addTestSuite(TestNumNeurons.class);
        suite.addTestSuite(TestFunctor.class);

        suite.addTestSuite(TestAddArrow.class);
        suite.addTestSuite(TestAddLayer.class);
        suite.addTestSuite(TestFocusLayer.class);
        suite.addTestSuite(TestGetLayer.class);
        suite.addTestSuite(TestLayers.class);
        suite.addTestSuite(TestRemoveArrow.class);
        suite.addTestSuite(TestRemoveLayer.class);
        suite.addTestSuite(TestAddInput.class);
        suite.addTestSuite(TestAddOut.class);
        suite.addTestSuite(TestAddBackArrow.class);
        suite.addTestSuite(TestCreateNeuroNet.class);
        suite.addTestSuite(TestCanAddBackArrow.class);
        suite.addTestSuite(TestCanAddDirectArrow.class);
        suite.addTestSuite(TestTopologicalSort.class);

        suite.addTestSuite(TestModelWriter.class);
        suite.addTestSuite(TestInputLayerDataWriter.class);                   

        suite.addTestSuite(TestModelReader.class);
        suite.addTestSuite(TestInputLayerDataReader.class);


        return suite;
    }
}
