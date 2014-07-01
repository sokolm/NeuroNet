package org.amse.marinaSokol.view;

import org.amse.marinaSokol.view.parameters.*;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class ParametersPanel extends JComponent {
    private JPanel myParamLayerPanel;
    private JPanel myParamArrowPanel;
    private JPanel myParamTeachPanel;

    ParametersPanel(JFrame frame, View view) {
        myParamLayerPanel = new LayerParametersPanel(view);
        myParamArrowPanel = new ConnectionParametersPanel(view);
        myParamTeachPanel = new TeachParametersPanel(frame, view);

        setLayout(new FlowLayout());
        add(myParamLayerPanel);
        add(myParamArrowPanel);
        add(myParamTeachPanel);

        myParamLayerPanel.setVisible(false);
        myParamArrowPanel.setVisible(false);
        myParamTeachPanel.setVisible(false);
    }

    public JPanel getParamLayerPanel() {
        return myParamLayerPanel;
    }

    public JPanel getParamArrowPanel() {
        return myParamArrowPanel;
    }

    public JPanel getParamTeachPanel() {
        return myParamTeachPanel;
    }
    
    public void showParamLayerPanel() {
        hideAll();
    	myParamLayerPanel.setVisible(true);
    }
    
    public void showParamArrowPanel() {
        hideAll();
        myParamArrowPanel.setVisible(true);
    } 

    public void showParamTeachPanel() {
        hideAll();
    	myParamTeachPanel.setVisible(true);    	       	
    }
    
    public void hideAll() {
       	myParamLayerPanel.setVisible(false);
        myParamArrowPanel.setVisible(false);
        myParamTeachPanel.setVisible(false);
    }
}
