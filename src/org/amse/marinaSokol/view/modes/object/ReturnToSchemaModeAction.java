package org.amse.marinaSokol.view.modes.object;

import org.amse.marinaSokol.view.modes.AbstractModeAction;
import org.amse.marinaSokol.view.modes.SwitcherModes;
import org.amse.marinaSokol.view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ReturnToSchemaModeAction extends AbstractModeAction implements SwitcherModes {

    public ReturnToSchemaModeAction(View view) {
        super(view);
    }

    public String getMode() {
        return "Return to schema";
    }

    public boolean isEnabled() {
        return true;
    }

    public String getToolTip() {
        return "Вернуться в режим рисования";
    }

    public String getIcon() {
        return "icon/obj/gotoSchema.png";
    }

    public void actionPerformed(ActionEvent e){
        super.actionPerformed(e);
        //вывести сообещение о сохранении обьекта
        int res = offeredSaveObject();
        if (res == JOptionPane.CANCEL_OPTION) {
            showObjectToolBar();
            return;
        }
        showSchemaToolBar();
        setSaveNet(false);        
        netSchema().deleteNeuroNet();
        setDefaultFile(null);
    }
}
