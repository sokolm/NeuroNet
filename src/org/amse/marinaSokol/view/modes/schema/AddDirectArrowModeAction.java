package org.amse.marinaSokol.view.modes.schema;

import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.shapes.Arrow;
import org.amse.marinaSokol.model.interfaces.schema.IInputLayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IUsualLayerSchema;

@SuppressWarnings("serial")
public class AddDirectArrowModeAction extends AbstractAddArrowModeAction {

    public AddDirectArrowModeAction(View view) {
        super(view);
    }

    public String getMode() {
        return "Add direct arrow";
    }

    public String getToolTip() {
        return "Добавить прямую стрелку";
    }

    public String getIcon() {
        return "icon/directArrow.png";
    }

    public boolean isEnabled() {
        if (netSchema().getLayersSchema().size() > 1) {
            for (IUsualLayerSchema layerSchema : netSchema().getLayersSchema()) {
                if (!(layerSchema instanceof IInputLayerSchema)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected Arrow createNewLine(IUsualLayerSchema source, IUsualLayerSchema dest) {
        setSaveNet(false);
        return diagram().addDirectArrow(source, dest);
    }

    protected boolean canAddArrow(IUsualLayerSchema source) {
        return netSchema().canAddDirectConnectionSchema(source);
    }

    protected boolean canAddArrow(IUsualLayerSchema source, IUsualLayerSchema dest) {
        return netSchema().canAddDirectConnectionSchema(source, dest);
    }
}
