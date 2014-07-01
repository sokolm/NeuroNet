package org.amse.marinaSokol.view.modes.object;

import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.parameters.TeachParametersPanel;

import java.awt.event.ActionEvent;

public class TeachModeAction extends AbstractRTModeAction {

    public TeachModeAction(View view) {
        super(view);
    }

    //При заходе в этот режим
    public void actionPerformed(ActionEvent e){
        super.actionPerformed(e);

        parameters().showParamTeachPanel();
        myCurrentModelParameters = (TeachParametersPanel)parameters().getParamTeachPanel();
        ((TeachParametersPanel)myCurrentModelParameters).setNet(netSchema());
        myCurrentModelParameters.setMode(true);
    }

    // вызывается при смене режима  (выхода из режима)
    public void exchangeMode() {
        parameters().hideAll();
        myCurrentModelParameters.saveShapeParameters();
        diagram().unselectShape(myFocusShape);
        diagram().unselectShape(myPressShape);
        myFocusShape = null;
        myPressShape = null;
        diagram().repaint();
    }

    public boolean isEnabled() {
        return true;
    }

    public String getMode() {
        return "teach";
    }

    public String getToolTip() {
        return "Указать данные для обучения и обучить сеть";
    }

    public String getIcon() {
        return "icon/obj/teachObject.png";
    }

}
