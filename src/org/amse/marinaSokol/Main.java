package org.amse.marinaSokol;

import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;
import org.amse.marinaSokol.view.MainWindow;

public class Main {
    public static void main(String[] args){
        INeuroNetSchema netSchema = new NeuroNetModel();
        new MainWindow(netSchema);
    }
}
