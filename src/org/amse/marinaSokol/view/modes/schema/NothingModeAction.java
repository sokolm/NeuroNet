package org.amse.marinaSokol.view.modes.schema;

import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.modes.AbstractModeAction;

@SuppressWarnings("serial")
public class NothingModeAction extends AbstractModeAction {

    public NothingModeAction(View view) {
        super(view);
    }

    public String getMode() {
        return "Nothing";
    }

    public boolean isEnabled() {
        return true;
    }

    public String getToolTip() {
        return "ничего";
    }

    public String getIcon() {
        return null;
    }

}
