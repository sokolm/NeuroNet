package org.amse.marinaSokol.view.modes.schema;

import java.awt.event.MouseEvent;
import org.amse.marinaSokol.view.Standard;
import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.shapes.Block;
import org.amse.marinaSokol.model.interfaces.schema.IUsualLayerSchema;

@SuppressWarnings("serial")
public class ResizeLayerModeAction extends AbstractMouseMoveModeAction {

    private Block myFocusLayer;

    public ResizeLayerModeAction(View view) {
        super(view);
    }

    public String getMode() {
        return "Resize layer";
    }

    public String getToolTip() {
        return "Изменить размер слоя";
    }

    public String getIcon() {
        return "icon/resize.png";
    }

    public boolean isEnabled() {
        return (netSchema().getLayersSchema().size() >= 1);
    }

    public void mousePressed(MouseEvent e) {
        myFocusLayer = diagram().focusBlock(e.getX(), e.getY());
        diagram().selectShape(myFocusLayer);
    }

    public void mouseDragged(MouseEvent e) {
        if (myFocusLayer != null) {
            IUsualLayerSchema layerSchema = (IUsualLayerSchema)myFocusLayer.getShapeModel();
            int width = e.getX() - layerSchema.getX();
            int height = e.getY() - layerSchema.getY();
            if ((width > Standard.THE_SMALLEST_WIDTH_LAYER) && (height > Standard.THE_SMALLEST_HEIGHT_LAYER)){
                layerSchema.resize(width, height);
                setSaveNet(false);
            }
        }
    }
}
