package org.amse.marinaSokol.view.modes.schema;

import java.awt.event.MouseEvent;
import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.shapes.IShape;
import org.amse.marinaSokol.model.interfaces.schema.IUsualLayerSchema;

@SuppressWarnings("serial")
public class MoveLayerModeAction extends AbstractMouseMoveModeAction {
    //offest from the left top corner of the focus layer
    private int myOffsetXLayer = 0;
    private int myOffsetYLayer = 0;
    private IShape myFocusShape;

    public MoveLayerModeAction(View view) {
        super(view);
    }

    public String getMode() {
        return "Move Layer";
    }

    public String getToolTip() {
        return "Передвинуть слой";
    }

    public String getIcon() {
        return "icon/move.png";
    }

    public boolean isEnabled() {
        return netSchema().getLayersSchema().size() >= 1;
    }

    public void mousePressed(MouseEvent e) {        
        myFocusShape = diagram().focusShape(e.getX(), e.getY());
        if (myFocusShape != null) {

            if (!(myFocusShape.getShapeModel() instanceof IUsualLayerSchema)) {
                myFocusShape = null;
                return;
            }

            IUsualLayerSchema layerSchema = (IUsualLayerSchema)myFocusShape.getShapeModel();

            myOffsetXLayer = e.getX() - layerSchema.getX();
            myOffsetYLayer = e.getY() - layerSchema.getY();
        }
        diagram().selectShape(myFocusShape);
    }

    public void mouseReleased(MouseEvent e) {
        diagram().unselectShape(myFocusShape);
    }

    public void mouseDragged(MouseEvent e) {
        if (myFocusShape != null) {
            ((IUsualLayerSchema)myFocusShape.getShapeModel()).moveTo(e.getX() - myOffsetXLayer, e.getY() - myOffsetYLayer);
            setSaveNet(false);
        }
    }
}

