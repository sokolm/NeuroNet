package org.amse.marinaSokol.model.interfaces.object;

import org.amse.marinaSokol.model.interfaces.object.net.*;

public interface INeuroNetVisitor {
    void visitInputLayer(IInputLayer layer);
    void visitOutputLayer(IOutputLayer layer);
    void visitLayer(ILayer layer);
    void visitConnection(IConnection connection);
}
