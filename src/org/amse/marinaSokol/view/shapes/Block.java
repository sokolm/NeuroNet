package org.amse.marinaSokol.view.shapes;


import org.amse.marinaSokol.model.interfaces.schema.*;
import org.amse.marinaSokol.view.Standard;

import java.awt.*;

public class Block implements IShape {
    private IUsualLayerSchema myLayerSchema;  // слой

    public Block(IUsualLayerSchema layerSchema) {
        myLayerSchema = layerSchema;
    }

    public IShapeSchema getShapeModel() {
        return myLayerSchema;
    }

    public void drawSelectedShape(Graphics g) {
        if (myLayerSchema == null)
            return;
        drawShape(g, Standard.LIGHT_RED);

        g.setColor(Standard.LIGHT_RED);
        g.fillRect(myLayerSchema.getX() + myLayerSchema.getWidth() - Standard.RESIZE_SQUARE /2,
                myLayerSchema.getY() + myLayerSchema.getHeight() - Standard.RESIZE_SQUARE /2,
                Standard.RESIZE_SQUARE, Standard.RESIZE_SQUARE);

        g.setColor(Color.BLACK);
        g.drawRect(myLayerSchema.getX() + myLayerSchema.getWidth() - Standard.RESIZE_SQUARE /2,
                myLayerSchema.getY() + myLayerSchema.getHeight() - Standard.RESIZE_SQUARE /2,
                Standard.RESIZE_SQUARE, Standard.RESIZE_SQUARE);

    }

    private void drawShape(Graphics g, Color color) {
        g.setColor(color);
        g.fillRect(myLayerSchema.getX(), myLayerSchema.getY(), myLayerSchema.getWidth(), myLayerSchema.getHeight());

        g.setColor(Color.BLACK);
        g.drawRect(myLayerSchema.getX(), myLayerSchema.getY(), myLayerSchema.getWidth(), myLayerSchema.getHeight());
    }

    public void drawShape(Graphics g) {
        if (myLayerSchema instanceof IInputLayerSchema) {
            drawShape(g, Standard.LIGHT_ORANGE);
        } else if (myLayerSchema instanceof IOutputLayerSchema) {
            drawShape(g, Standard.LIGHT_BLUE); //голубой
        } else {
            drawShape(g, Standard.LIGHT_GREEN);//зеленый
        }
        final int id = myLayerSchema.getId();
        if (id != 0)
        g.drawString(" " + id, myLayerSchema.getX(), myLayerSchema.getY());
    }

    public boolean contains(int x, int y) {
        return myLayerSchema.containsPoint(x, y);
    }
}
