package org.amse.marinaSokol.view;

import java.awt.event.*;
import java.awt.*;
import javax.swing.Action;

public interface IModeAction extends MouseMotionListener, MouseListener, Action {	
    String getMode();
    void exchangeMode();
    String getIcon();
    String getToolTip();
    void drawFakeElements(Graphics graphics);
}
