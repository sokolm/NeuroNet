package org.amse.marinaSokol.view.shapes;

import org.amse.marinaSokol.model.interfaces.schema.IShapeSchema;

import java.awt.*;


public interface IShape {
    void drawSelectedShape(Graphics g);
    void drawShape(Graphics g);
    IShapeSchema getShapeModel();
}
