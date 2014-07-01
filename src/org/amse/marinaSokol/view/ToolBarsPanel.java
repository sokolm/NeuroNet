package org.amse.marinaSokol.view;

import org.amse.marinaSokol.view.modes.schema.*;
import org.amse.marinaSokol.view.modes.object.*;

import java.util.List;
import java.util.ArrayList;

class ToolBarsPanel {
    private ToolBar mySchemaToolBar;
    private ToolBar myObjectToolBar;

    private List<IModeAction> myActionsSchema = new ArrayList<IModeAction>();
    private List<IModeAction> myActionsObject = new ArrayList<IModeAction>();


    ToolBarsPanel(View view) {
        myActionsSchema.add(new AddInputLayerModeAction(view));
        myActionsSchema.add(new AddLayerModeAction(view));
        myActionsSchema.add(new AddOutLayerModeAction(view));
        myActionsSchema.add(null);
        myActionsSchema.add(new AddDirectArrowModeAction(view));
        myActionsSchema.add(new AddBackArrowModeAction(view));
        myActionsSchema.add(null);
        myActionsSchema.add(new DeleteModeAction(view));
        myActionsSchema.add(new MoveLayerModeAction(view));
        myActionsSchema.add(new ResizeLayerModeAction(view));
        myActionsSchema.add(null);

        myActionsSchema.add(new CreateObjectModeAction(view));

        mySchemaToolBar = new ToolBar(myActionsSchema);

        myActionsObject.add(new SetUpParamShapeModeAction(view));
        myActionsObject.add(new RunModeAction(view));
        myActionsObject.add(new TeachModeAction(view));
        myActionsObject.add(new ReturnToSchemaModeAction(view));

        myObjectToolBar = new ToolBar(myActionsObject);
    }

	ToolBar getSchemaToolBar() {
		return mySchemaToolBar;
	}

	ToolBar getObjectToolBar() {
		return myObjectToolBar;
	}

    void updateCurrentToolBar() {
        for (IModeAction action : myActionsSchema) {
            if (action != null) {
                action.setEnabled(action.isEnabled());
            }
        }
        for (IModeAction action : myActionsObject) {
            if (action != null) {
                action.setEnabled(action.isEnabled());
            }
        }
    }
}
