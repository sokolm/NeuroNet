package org.amse.marinaSokol.view.modes.object;

import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.IModelParameters;
import org.amse.marinaSokol.view.modes.AbstractModeAction;
import org.amse.marinaSokol.view.shapes.IShape;
import org.amse.marinaSokol.model.interfaces.object.net.IUsualLayer;
//import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;

import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class SetUpParamShapeModeAction extends AbstractModeAction {
    private IShape myPressShape;
    private IModelParameters myCurrentModelParameters;
    private IShape myUnderMouseShape;

    public SetUpParamShapeModeAction(View view) {
        super(view);
    }

    public String getToolTip() {
        return "Указать параметры слоев и соединений";
    }

    //При заходе в этот режим
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
    }

    // вызывается при смене режима  (выхода из режима)
    public void exchangeMode() {
        diagram().unselectShape(myPressShape);
        myPressShape = null;
        if (myCurrentModelParameters != null) {
            myCurrentModelParameters.saveShapeParameters();
            myCurrentModelParameters.setCurrentShape(null);
        }
        myCurrentModelParameters = null;
        parameters().hideAll();
    }

    public String getMode() {
        return "set up";
    }

    public String getIcon() {
        return "icon/obj/setUp.png";
    }


    public boolean isEnabled() {
        return true;//return (netSchema().getNeuroNetSchema() != null);//return (netSchema().getLayersSchema().size() >= 1);
    }

    public IShape getFocusShape() {
        return myPressShape;
    }

    public void mousePressed(MouseEvent e) {
        IShape focusShape = diagram().focusShape(e.getX(), e.getY());
        if (focusShape != null && myPressShape != focusShape) {
            if (myCurrentModelParameters != null) {
                myCurrentModelParameters.saveShapeParameters();
            }
            diagram().unselectShape(myPressShape);
            diagram().selectShape(focusShape);
            myPressShape = focusShape;
            if (myPressShape.getShapeModel() instanceof IUsualLayer) {
                parameters().showParamLayerPanel();
                myCurrentModelParameters = (IModelParameters)parameters().getParamLayerPanel();
            } else {
                parameters().showParamArrowPanel();
                myCurrentModelParameters = (IModelParameters)parameters().getParamArrowPanel();
            }
            myCurrentModelParameters.setCurrentShape(myPressShape);
            myCurrentModelParameters.setShapeParameters();
        }
    }

    public void mouseMoved(MouseEvent e) {
        IShape underMouseShape = diagram().getShape(e.getX(), e.getY());
        if (underMouseShape != myUnderMouseShape) {
            if (myUnderMouseShape != myPressShape) {
                diagram().unselectShape(myUnderMouseShape);
            }
            myUnderMouseShape = underMouseShape;
            diagram().selectShape(myUnderMouseShape);
        }
    }

}
