package org.amse.marinaSokol.tests.writer;

import junit.framework.TestCase;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.interfaces.schema.IInputLayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IOutputLayerSchema;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.tests.Utils;
import org.amse.marinaSokol.writer.ModelFileWriter;


public class TestModelWriter extends TestCase {
    private boolean writeDiagram(INeuroNetSchema d, String f) {
        try {
            ModelFileWriter fw = new ModelFileWriter(d);
            fw.write(f);
        } catch (Exception e) {
            System.out.println("Error, file не записан");
            return false;
        }
        return true;
    }

    //пустую схему
    public void test1() {
        INeuroNetSchema schema = new NeuroNetModel();
        writeDiagram(schema, "tests/writer/testEmptyModel.txt");
        //writeDiagram(schema, "tests/writer/rightEmptyModel.txt");
        Utils.compareFile("tests/writer/rightEmptyModel.txt", "tests/writer/testEmptyModel.txt");
    }

    //записать 1 слой
    public void test2() {
        INeuroNetSchema schema = new NeuroNetModel();
        schema.addLayerSchema(10, 40, 20, 30);
        writeDiagram(schema, "tests/writer/test1LayerModel.txt");
        //writeDiagram(schema, "tests/writer/right1LayerModel.txt");
        Utils.compareFile("tests/writer/right1LayerModel.txt", "tests/writer/test1LayerModel.txt");
    }

    //записать сеть из 1входа, выхода, и слоя соелин прямыми связями
    public void test3() {
        INeuroNetSchema schema = new NeuroNetModel();
        IInputLayerSchema input = schema.addInputLayerSchema(10, 10, 10, 10);
        ILayerSchema layer = schema.addLayerSchema(20, 20, 20, 20);
        IOutputLayerSchema output = schema.addOutputLayerSchema(30, 30, 30, 30);
        schema.addDirectConnectionSchema(input, layer);
        schema.addDirectConnectionSchema(layer, output);
        writeDiagram(schema, "tests/writer/testSimpleNetModel.txt");
        //writeDiagram(schema, "tests/writer/rightSimpleNetModel.txt");
        Utils.compareFile("tests/writer/rightSimpleNetModel.txt", "tests/writer/testSimpleNetModel.txt");
    }

    //записать сеть из 1входа, выхода, и слоя соелин прямыми связями
    //и создать сеть
    public void test4() {
        INeuroNetSchema schema = new NeuroNetModel();
        IInputLayerSchema input = schema.addInputLayerSchema(10, 10, 10, 10);
        ILayerSchema layer = schema.addLayerSchema(20, 20, 20, 20);
        IOutputLayerSchema output = schema.addOutputLayerSchema(30, 30, 30, 30);
        schema.addDirectConnectionSchema(input, layer);
        schema.addDirectConnectionSchema(layer, output);
        schema.createNeuroNet();
        writeDiagram(schema, "tests/writer/testSimpleNetObjectModel.txt");
        writeDiagram(schema, "tests/writer/rightSimpleNetObjectModel.txt");
        Utils.compareFile("tests/writer/rightSimpleNetObjectModel.txt", "tests/writer/testSimpleNetObjectModel.txt");
    }

    //записать сложную структуру сети
    //и создать сеть
    public void test5() {
        INeuroNetSchema schema = new NeuroNetModel();
        IInputLayerSchema input = schema.addInputLayerSchema(10, 10, 10, 10);
        ILayerSchema layer1 = schema.addLayerSchema(20, 20, 20, 20);
        ILayerSchema layer2 = schema.addLayerSchema(20, 20, 20, 20);
        ILayerSchema layer3 = schema.addLayerSchema(20, 20, 20, 20);

        IOutputLayerSchema output = schema.addOutputLayerSchema(30, 30, 30, 30);
        schema.addDirectConnectionSchema(input, layer1);
        schema.addDirectConnectionSchema(input, layer2);
        schema.addDirectConnectionSchema(layer1, layer2);
        schema.addDirectConnectionSchema(layer2, layer3);
        schema.addDirectConnectionSchema(layer3, output);

        schema.addBackConnectionSchema(layer3, layer3);
        schema.addBackConnectionSchema(layer3, layer1);

        schema.createNeuroNet();
        writeDiagram(schema, "tests/writer/testComplexNetObjectModel.txt");
        writeDiagram(schema, "tests/writer/rightComplexNetObjectModel.txt");

        Utils.compareFile("tests/writer/rightComplexNetObjectModel.txt", "tests/writer/testComplexNetObjectModel.txt");
    }
}
