package org.amse.marinaSokol.view.modes.schema;

import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.Standard;
import org.amse.marinaSokol.view.shapes.Block;

@SuppressWarnings("serial")
public class AddInputLayerModeAction extends AbstractAddLayerModeAction {

    public AddInputLayerModeAction(View view) {
        super(view);
    }

    public String getMode() {
        return "Add input";
    }

    public boolean isEnabled() {
        return true;
    }

    public String getToolTip() {
        return "Добавить входной слой";
    }

    public String getIcon() {
        return "icon/inputLayer.png";
    }


    protected Block createNewLayer(int x, int y) {
        setSaveNet(false);
        return diagram().addInputBlock(x, y, Standard.WIDTH_LAYER, Standard.HEIGHT_LAYER);
    }
}
