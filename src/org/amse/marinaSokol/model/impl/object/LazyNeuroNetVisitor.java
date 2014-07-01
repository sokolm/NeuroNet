package org.amse.marinaSokol.model.impl.object;

import org.amse.marinaSokol.model.interfaces.object.net.ILayer;
import org.amse.marinaSokol.model.interfaces.object.net.IConnection;
import org.amse.marinaSokol.model.interfaces.object.net.IInputLayer;
import org.amse.marinaSokol.model.interfaces.object.net.IOutputLayer;
import org.amse.marinaSokol.model.interfaces.object.INeuroNetVisitor;


public abstract class LazyNeuroNetVisitor implements INeuroNetVisitor {

    public void visitInputLayer(IInputLayer layer) {
    }

    public void visitOutputLayer(IOutputLayer layer) {
    }

    public void visitLayer(ILayer layer) {
    }

    public void visitConnection(IConnection connection) {
    }
}
