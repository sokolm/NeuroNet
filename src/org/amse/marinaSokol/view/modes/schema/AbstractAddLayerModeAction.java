package org.amse.marinaSokol.view.modes.schema;

import java.awt.event.MouseEvent;
import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.modes.AbstractModeAction;
import org.amse.marinaSokol.model.interfaces.schema.IUsualLayerSchema;
import org.amse.marinaSokol.view.shapes.Block;

abstract class AbstractAddLayerModeAction extends AbstractModeAction {
    //offest from the left top corner of the focus layer
    private int myOffsetXLayer = 0;
    private int myOffsetYLayer = 0;
    private Block myFocus;

    public AbstractAddLayerModeAction(View view) {
        super(view);
    }

    abstract protected Block createNewLayer(int x, int y);

    public void mousePressed(MouseEvent e) {
		myFocus = createNewLayer(e.getX(), e.getY());
        IUsualLayerSchema focusLayerSchema = (IUsualLayerSchema)myFocus.getShapeModel();
        updateToolBar();
        if (focusLayerSchema != null) {
            myOffsetXLayer = e.getX() - focusLayerSchema.getX();
            myOffsetYLayer = e.getY() - focusLayerSchema.getY();
        }
        diagram().selectShape(myFocus);
    }

    public void mouseReleased(MouseEvent e) {
        diagram().unselectShape(myFocus);
    }

    public void mouseDragged(MouseEvent e) {
        if (myFocus != null) {
            ((IUsualLayerSchema)myFocus.getShapeModel()).moveTo(e.getX() - myOffsetXLayer, e.getY() - myOffsetYLayer);
        }
    }
}
