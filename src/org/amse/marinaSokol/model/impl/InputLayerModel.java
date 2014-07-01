package org.amse.marinaSokol.model.impl;

public class InputLayerModel  extends UsualLayerModel implements IInputLayerModel {
    private String myFileNameInput;

    InputLayerModel(int x, int y, int width, int height) {
        super(x, y, width, height);
        myFileNameInput = "";
    }

    public void setFileName(String fileName) {
        myFileNameInput = fileName;
    }

    public String getFileName() {
        return myFileNameInput;
    }

    public String toString() {
        return "the input layer:" + super.toString();
    }

}
