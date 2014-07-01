package org.amse.marinaSokol.view.modes;

import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.model.interfaces.object.net.INeuroNet;
import org.amse.marinaSokol.view.*;
import org.amse.marinaSokol.Main;

import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.io.File;
import javax.swing.*;

public abstract class AbstractModeAction extends AbstractAction implements IModeAction {
    private View myView;

    protected void updateToolBar() {
        myView.updateToolBar();
    }

    public void setDefaultFile(File f) {
        myView.setDefaultFile(f);
    }

    public void drawFakeElements(Graphics graphics) {
    }

    public void setSaveNet(boolean isSave) {
        myView.setSaveNet(isSave);
    }

    public void exchangeMode() {
    }

    public void actionPerformed(ActionEvent e) {
        myView.getMode().exchangeMode();
        myView.setMode(this);
    }

    protected AbstractModeAction(View view) {
        myView = view;
		final String iconName = getIcon();
		final String toolTip = getToolTip();
        if (iconName != null) {
            putValue(SMALL_ICON, new ImageIcon(Main.class.getClassLoader().getResource(iconName)));
            putValue(SHORT_DESCRIPTION, toolTip);
        }
    }

    protected ShapeDiagram diagram() {
        return myView.getShapeDiagram();
    }

    protected INeuroNetSchema netSchema() {
        return myView.getNeuroNetSchema();
    }

    protected INeuroNet netObject() {
        return myView.getNeuroNetSchema().getNeuroNet();
    }

    protected ParametersPanel parameters() {
        return myView.getParametersPanel();
    }

    protected int offeredSaveObject() {
        return myView.offeredSaveObject();
    }
    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

		public void showSchemaToolBar() {
				myView.showSchemaToolBar();
		}

		public void showObjectToolBar() {
				myView.showObjectToolBar();
		}
}
