package org.amse.marinaSokol.view.modes.schema;

import java.awt.event.MouseEvent;
import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.modes.AbstractModeAction;
import org.amse.marinaSokol.view.shapes.IShape;

@SuppressWarnings("serial")
public class DeleteModeAction extends AbstractModeAction {
    private IShape myUnderMouseShape;

    public DeleteModeAction(View view) {
        super(view);
    }

    public String getMode() {
        return "Delete layer and connection";
    }

    public boolean isEnabled() {
        return netSchema().getLayersSchema().size() >= 1;
    }

    public String getToolTip() {
        return "Удалить слой или соединение";
    }

    public String getIcon() {
        return "icon/delete.png";
    }

    public void mousePressed(MouseEvent e) {
        IShape focusShape = diagram().focusShape(e.getX(), e.getY());
        if (focusShape != null) {
            diagram().removeShape(focusShape);
            diagram().unselectShape(focusShape);
            setSaveNet(false);
            updateToolBar();
        }
    }

    public void mouseMoved(MouseEvent e) {
        IShape underMouseShape = diagram().getShape(e.getX(), e.getY());
        if (underMouseShape != myUnderMouseShape) {
            diagram().unselectShape(myUnderMouseShape);
            myUnderMouseShape = underMouseShape;
            diagram().selectShape(myUnderMouseShape);
        }
    }

}

