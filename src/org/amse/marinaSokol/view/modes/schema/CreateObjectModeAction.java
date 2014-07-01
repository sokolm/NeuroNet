package org.amse.marinaSokol.view.modes.schema;

import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.modes.SwitcherModes;
import org.amse.marinaSokol.view.modes.AbstractModeAction;
import org.amse.marinaSokol.model.interfaces.schema.IInputLayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IOutputLayerSchema;
import org.amse.marinaSokol.model.interfaces.schema.IUsualLayerSchema;

import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class CreateObjectModeAction extends AbstractModeAction implements SwitcherModes {

    public CreateObjectModeAction(View view) {
        super(view);
    }

    public String getMode() {
        return "create object";
    }

    public String getToolTip() {
        return "������� ������ ���� � ������� � ����� �������";
    }

    public String getIcon() {
        return "icon/createObject.png";
    }
    //���� ���� ���� �� 1 ���� � ����� ��������� ����� �����
    public boolean isEnabled() {
        IUsualLayerSchema input = null, output = null;
        for (IUsualLayerSchema layerSchema : netSchema().getLayersSchema()) {
            if (layerSchema instanceof IInputLayerSchema) {
                input = layerSchema;
            } else if (layerSchema instanceof IOutputLayerSchema) {
                output = layerSchema;
            }
            if ((input != null) && (output != null)) {
                return true;
            }
        }
        return false;
    }


    public void actionPerformed(ActionEvent e){
        super.actionPerformed(e);
        netSchema().topologicalSort();
        netSchema().createNeuroNet();
        updateToolBar();
        showObjectToolBar();
        setSaveNet(false);
        setDefaultFile(null);
    }
}
