package org.amse.marinaSokol.view.modes.schema;

import org.amse.marinaSokol.view.Standard;
import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.shapes.Block;

@SuppressWarnings("serial")
public class AddLayerModeAction extends AbstractAddLayerModeAction {

    public AddLayerModeAction(View view) {
        super(view);
    }

    public String getMode() {
        return "Add Layer";
    }

    public boolean isEnabled() {
        return true;
    }

    public String getToolTip() {
        return "Добавить слой";
    }

    public String getIcon() {
        return "icon/layer.png";
    }


    protected Block createNewLayer(int x, int y) {
        setSaveNet(false);
        return diagram().addBlock(x, y, Standard.WIDTH_LAYER, Standard.HEIGHT_LAYER);
    }
}
