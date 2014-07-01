package org.amse.marinaSokol.model.impl;

public class OutputLayerModel extends LayerModel implements IOutputLayerModel {
    private String myFileNameOutput;
    private String myFileNameRightOutput;

    OutputLayerModel(int x, int y, int width, int height) {
        super(x, y, width, height);
        myFileNameRightOutput = "";
    }
    

    public void setFileNameOutput(String fileName) {
        myFileNameOutput = fileName;
    }

    public String getFileNameOutput() {
        return myFileNameOutput;
    }

    public void setFileNameRightOutput(String fileName) {
        myFileNameRightOutput = fileName;
    }

    public String getFileNameRightOutput() {
        return myFileNameRightOutput;
    }

    public String toString() {
        return "the output layer:" + super.toString();
    }
}
