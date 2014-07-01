package org.amse.marinaSokol.view;

import javax.swing.*;

public abstract class AbstractModelParameters extends JPanel implements IModelParameters {
    private View myView;

    public AbstractModelParameters(View view) {
        myView = view;
    }

    public void setSaveNet(boolean isSave) {
        myView.setSaveNet(isSave);
    }

    public boolean getSaveNet() {
        return myView.getSaveNet();
    }
}
