package org.amse.marinaSokol.view.shapes;

import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IConnectionSchema;
import org.amse.marinaSokol.model.interfaces.schema.IShapeSchema;
import org.amse.marinaSokol.model.interfaces.schema.IUsualLayerSchema;
import org.amse.marinaSokol.view.Standard;

import java.awt.*;

public class Arrow implements IShape {
    private IConnectionSchema myConnectionSchema;  //стрелка

    public Arrow(IConnectionSchema connectionSchema) {
        myConnectionSchema = connectionSchema;
    }

    public IShapeSchema getShapeModel() {
        return myConnectionSchema;
    }

    public void drawSelectedShape(Graphics g) {
        drawArrow(g, Standard.LIGHT_RED);
    }

    public void drawShape(Graphics g) {
        if (myConnectionSchema.isDirect()) {
             drawArrow(g, Standard.DARK_GREEN);
        } else {
            drawArrow(g, Standard.DARK_YELLOW);
        }
    }

    private void drawArrow(Graphics g, Color color) {
        ILayerSchema dest = myConnectionSchema.getDestLayerSchema();
        IUsualLayerSchema source = myConnectionSchema.getSourceLayerSchema();
        g.setColor(color);
        int x1;
        int y1;
        int x2;
        int y2;
        double index =
                (double)(dest.getInputConnectionsSchema().indexOf(myConnectionSchema)+1) /
                        (dest.getInputConnectionsSchema().size() + 1);
        final int aStrike = 5;
        int xStrike;
        int yStrike;
        final double cosAlfa = Math.sqrt(2)/2;
        final double sinAlfa = Math.sqrt(2)/2;
        if ((source.getY() - dest.getY() - dest.getHeight()) > 0) {
            x1 = source.getX() + source.getWidth()/2;
            y1 = source.getY();
            x2 = dest.getX() + (int)(dest.getWidth()*index);
            y2 = dest.getY() + dest.getHeight();

        } else if ((dest.getY() - source.getY() - source.getHeight()) > 0) {
            x1 = source.getX() + source.getWidth()/2;
            y1 = source.getY() + source.getHeight();
            x2 = dest.getX() + (int)(dest.getWidth()*index);
            y2 = dest.getY();

        } else if (source.getX() <= dest.getX()) {
            x1 = source.getX() + source.getWidth();
            y1 = source.getY() + source.getHeight()/2;
            x2 = dest.getX();
            y2 = dest.getY() + (int)(dest.getHeight()*index);

        } else {
            x1 = source.getX();
            y1 = source.getY() + source.getHeight()/2;
            x2 = dest.getX() + dest.getWidth();
            y2 = dest.getY() + (int)(dest.getHeight()*index);
        }
        g.drawLine(x1, y1, x2, y2);

        double c = Math.hypot(Math.abs(x1 - x2), Math.abs(y1 - y2));

        double cosb = (x1 - x2)/c;
        double sinb = (y1 - y2)/c;
        yStrike = (int)(y2 + cosA_minus_B(cosAlfa, sinAlfa, cosb, sinb)*aStrike);
        xStrike = (int)(x2 + sinA_minus_B(cosAlfa, sinAlfa, cosb, sinb)*aStrike);

        g.drawLine(xStrike, yStrike, x2, y2);

        yStrike = (int)(y2 - cosA_plus_B(cosAlfa, sinAlfa, cosb, sinb)*aStrike);
        xStrike = (int)(x2 + sinA_plus_B(cosAlfa, sinAlfa, cosb, sinb)*aStrike);

        g.drawLine(xStrike, yStrike, x2, y2);

    }

    private double cosA_minus_B(double cosa, double sina, double cosb, double sinb) {
        return cosa*cosb + sina*sinb;
    }

    private double cosA_plus_B(double cosa, double sina, double cosb, double sinb) {
        return cosa*cosb - sina*sinb;
    }

    private double sinA_minus_B(double cosa, double sina, double cosb, double sinb) {
        return sina*cosb - cosa*sinb;
    }

    private double sinA_plus_B(double cosa, double sina, double cosb, double sinb) {
        return sina*cosb + cosa*sinb;
    }


    public boolean contains(int x, int y) {
        return (myConnectionSchema.proximity(x, y) <= 3);
    }
}
