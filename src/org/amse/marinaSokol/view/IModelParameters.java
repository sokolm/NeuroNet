package org.amse.marinaSokol.view;

import org.amse.marinaSokol.view.shapes.IShape;

public interface IModelParameters {
    void setCurrentShape(IShape currentShape);
    void setShapeParameters();
    void saveShapeParameters();
    void setVisible(boolean visible);
    void setEnabled(boolean enable);
    void setMode(boolean isTeach);
}
