package org.amse.marinaSokol.view;

import org.amse.marinaSokol.view.modes.SwitcherModes;

import javax.swing.*;
import java.util.*;

@SuppressWarnings("serial")
class MyJButton extends JButton {
	MyJButton(Action action) {
		super(action);
	}

	void removeRollover() {
		if (model != null) {
			model.setRollover(false);
		}
	}
}

class ToolBar extends JToolBar {
    private ButtonGroup myButtonGroup;
    private JToggleButton myNothingButton;
	private List<AbstractButton> myAllButtons;

    ToolBar(List<IModeAction> actions){
		setFloatable(false);
        myAllButtons = new LinkedList<AbstractButton>();
        myButtonGroup = new ButtonGroup();
        for (IModeAction action : actions) {
            if (action == null) {
                addSeparator();
            } else if (action instanceof SwitcherModes) {
                functionAddButton(action);
            } else {
                functionAddToogleButton(action);
            }    
        }
        myNothingButton = new JToggleButton();
        myNothingButton.setEnabled(false);
        myNothingButton.setVisible(false);
        myButtonGroup.add(myNothingButton);
    }

    private void functionAddToogleButton(IModeAction mode) {
        JToggleButton button = new JToggleButton(mode);
				myAllButtons.add(button);
        myButtonGroup.add(button);
        button.setFocusPainted(false);
        add(button);
    }

    private void functionAddButton(IModeAction mode) {
        JButton button = new MyJButton(mode);
		myAllButtons.add(button);
        myButtonGroup.add(button);
        button.setFocusPainted(false);        
        add(button);
    }

    public void unchecked() {
		for (AbstractButton button : myAllButtons) {
			if (button instanceof MyJButton) {
			    ((MyJButton)button).removeRollover();
		    }
		}
        myNothingButton.setSelected(true);
    }
}
