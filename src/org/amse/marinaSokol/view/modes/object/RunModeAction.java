package org.amse.marinaSokol.view.modes.object;

import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.parameters.TeachParametersPanel;

import java.awt.event.ActionEvent;

public class RunModeAction extends AbstractRTModeAction {

    public RunModeAction(View view) {
        super(view);
    }

    public String getToolTip() {
        return "Указать входные данные и запустить сеть";
    }

    //При заходе в этот режим
    public void actionPerformed(ActionEvent e){
        super.actionPerformed(e);

        parameters().showParamTeachPanel();

        myCurrentModelParameters = (TeachParametersPanel)parameters().getParamTeachPanel();
        ((TeachParametersPanel)myCurrentModelParameters).setNet(netSchema());
        myCurrentModelParameters.setMode(false);
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

    public String getIcon() {
        return "icon/obj/runObject.png";
    }

}
