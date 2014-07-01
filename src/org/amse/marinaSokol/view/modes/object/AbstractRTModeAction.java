package org.amse.marinaSokol.view.modes.object;

import org.amse.marinaSokol.model.interfaces.object.net.IInputLayer;
import org.amse.marinaSokol.model.interfaces.object.net.IOutputLayer;
import org.amse.marinaSokol.view.shapes.IShape;
import org.amse.marinaSokol.view.IModelParameters;
import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.modes.AbstractModeAction;
import org.amse.marinaSokol.view.parameters.TeachParametersPanel;

import java.awt.event.MouseEvent;


public abstract class AbstractRTModeAction extends AbstractModeAction {
    protected IShape myFocusShape;
    protected IShape myPressShape;
    protected IModelParameters myCurrentModelParameters;

    public AbstractRTModeAction(View view) {
        super(view);
    }

    public boolean isEnabled() {
        return true;
    }
    
    void setMode() {
        //myCurrentModelParameters.setMode(true);
    }

    public void mousePressed(MouseEvent e) {
        IShape focusShape = diagram().focusShape(e.getX(), e.getY());
        if ((focusShape != null) &&
           (myPressShape != focusShape) &&
           ((focusShape.getShapeModel() instanceof IInputLayer) ||
           (focusShape.getShapeModel() instanceof IOutputLayer))) {
            diagram().unselectShape(myPressShape);
            ((TeachParametersPanel)myCurrentModelParameters).unselectLabels();
            diagram().selectShape(focusShape);
            myPressShape = focusShape;
            myCurrentModelParameters.setCurrentShape(myPressShape);
        } else {
            if (focusShape == null) {
                diagram().unselectShape(myPressShape);
                myPressShape = focusShape;
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        IShape focusShape = diagram().getShape(e.getX(), e.getY());
        if ((focusShape != null) &&
           (myFocusShape != focusShape) &&
           (((focusShape.getShapeModel() instanceof IInputLayer) ||
           (focusShape.getShapeModel() instanceof IOutputLayer)) &&
           (myPressShape == null || myFocusShape != myPressShape))) {

            if(myFocusShape != myPressShape) {
                diagram().unselectShape(myFocusShape);
            }
            diagram().selectShape(focusShape);
            myFocusShape = focusShape;
        } else if (focusShape == null) {
            if(myFocusShape != myPressShape) {
                diagram().unselectShape(myFocusShape);
            }
            myFocusShape = focusShape;
        }
    }
}
