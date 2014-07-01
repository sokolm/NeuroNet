package org.amse.marinaSokol.view.modes.schema;

import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.modes.AbstractModeAction;
import org.amse.marinaSokol.view.shapes.IShape;
import org.amse.marinaSokol.model.interfaces.schema.IUsualLayerSchema;

import java.awt.event.MouseEvent;

public abstract class AbstractMouseMoveModeAction extends AbstractModeAction {
    protected IShape myUnderMouseShape;

    public AbstractMouseMoveModeAction(View view) {
        super(view);
    }

    public void mouseMoved(MouseEvent e) {
        IShape underMouseShape = diagram().getShape(e.getX(), e.getY());
        if (underMouseShape != myUnderMouseShape && underMouseShape != null &&
                underMouseShape.getShapeModel()  instanceof IUsualLayerSchema) {
            diagram().unselectShape(myUnderMouseShape);
            myUnderMouseShape = underMouseShape;
            diagram().selectShape(myUnderMouseShape);
        } else if (underMouseShape == null) {
            diagram().unselectShape(myUnderMouseShape);
            myUnderMouseShape = underMouseShape;            
        }
    }
}
