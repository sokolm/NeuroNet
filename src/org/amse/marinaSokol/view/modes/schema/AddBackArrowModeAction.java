package org.amse.marinaSokol.view.modes.schema;

import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.model.interfaces.schema.ILayerSchema;
import org.amse.marinaSokol.view.shapes.Arrow;
import org.amse.marinaSokol.model.interfaces.schema.IInputLayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IUsualLayerSchema;

@SuppressWarnings("serial")
public class AddBackArrowModeAction extends AbstractAddArrowModeAction {

    public AddBackArrowModeAction(View view) {
        super(view);
    }

    public String getMode() {
        return "Add back arrow";
    }

    public String getToolTip() {
        return "Добавить обратную стрелку";
    }

    public String getIcon() {
        return "icon/backArrow.png";
    }

    public boolean isEnabled() {
        if (netSchema().getLayersSchema().size() >= 1) {
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
        return diagram().addBackArrow((ILayerSchema)source, (ILayerSchema)dest);
    }

    protected boolean canAddArrow(IUsualLayerSchema source) {
        return netSchema().canAddBackConnectionSchema(source);
    }

    protected boolean canAddArrow(IUsualLayerSchema source, IUsualLayerSchema dest) {
        return netSchema().canAddBackConnectionSchema(source, dest);
    }
}
