package org.amse.marinaSokol.view.modes.schema;

import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.Standard;
import org.amse.marinaSokol.view.shapes.Block;

@SuppressWarnings("serial")
public class AddOutLayerModeAction extends AbstractAddLayerModeAction {

    public AddOutLayerModeAction(View view) {
        super(view);
    }

    public String getMode() {
        return "Add out";
    }

    public boolean isEnabled() {
        return true;
    }

    public String getToolTip() {
        return "Добавить выходной слой";
    }

    public String getIcon() {
        return "icon/outputLayer.png";
    }

    protected Block createNewLayer(int x, int y) {
        setSaveNet(false);
        return diagram().addOutputBlock(x, y, Standard.WIDTH_LAYER, Standard.HEIGHT_LAYER);
    }
}
