package org.amse.marinaSokol.view.modes.schema;

import org.amse.marinaSokol.model.interfaces.schema.IUsualLayerSchema;
import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.view.modes.AbstractModeAction;
import org.amse.marinaSokol.view.shapes.Block;
import org.amse.marinaSokol.view.shapes.Arrow;

import java.awt.event.MouseEvent;
import java.awt.*;

abstract class AbstractAddArrowModeAction extends AbstractModeAction {
    protected Block myFocusLayer;
    private Block mySourceLayer;
    private int myXNewArrow;
    private int myYNewArrow;
    private Block myUnderMouseShape;

    public AbstractAddArrowModeAction(View view) {
        super(view);
    }

    abstract protected Arrow createNewLine(IUsualLayerSchema source, IUsualLayerSchema dest);
    abstract protected boolean canAddArrow(IUsualLayerSchema source);
    abstract protected boolean canAddArrow(IUsualLayerSchema source, IUsualLayerSchema dest);

    public void mousePressed(MouseEvent e) {
        myFocusLayer = diagram().focusBlock(e.getX(), e.getY());
        if (myFocusLayer != null && canAddArrow((IUsualLayerSchema)myFocusLayer.getShapeModel())) {
            diagram().selectShape(myFocusLayer);
            mySourceLayer = myFocusLayer;
            myXNewArrow = e.getX();
            myYNewArrow = e.getY();
        }  else {
            myFocusLayer = null;
        }
    }

    public void mouseReleased(MouseEvent e) {
        myFocusLayer = diagram().focusBlock(e.getX(), e.getY());
        if ((myFocusLayer != null) && (mySourceLayer != null) && (canAddArrow((IUsualLayerSchema)mySourceLayer.getShapeModel(), (IUsualLayerSchema)myFocusLayer.getShapeModel()))) {
            createNewLine((IUsualLayerSchema)mySourceLayer.getShapeModel(), (IUsualLayerSchema)myFocusLayer.getShapeModel());
            diagram().unselectShape(myFocusLayer);
            myFocusLayer = null;
            updateToolBar();
        }
        diagram().unselectShape(mySourceLayer);
        mySourceLayer = null;
    }

    public void mouseDragged(MouseEvent e) {
        if (mySourceLayer != null) {
            Block underMouseLayer = diagram().getBlock(e.getX(), e.getY());
            if ((underMouseLayer != myUnderMouseShape) || (underMouseLayer == null)) {
                if (myUnderMouseShape != mySourceLayer) {
                    diagram().unselectShape(myUnderMouseShape);
                }

                if (underMouseLayer != null && !canAddArrow((IUsualLayerSchema)mySourceLayer.getShapeModel(), (IUsualLayerSchema)underMouseLayer.getShapeModel())) {
                    underMouseLayer = null;
                }

                myUnderMouseShape = underMouseLayer;

                if (underMouseLayer == null) {
                    myXNewArrow = e.getX();
                    myYNewArrow = e.getY();
                } else {
                    diagram().selectShape(myUnderMouseShape);
                    IUsualLayerSchema layerSchema  = (IUsualLayerSchema)myUnderMouseShape.getShapeModel();
                    myXNewArrow = layerSchema.getX();
                    myYNewArrow = layerSchema.getY() + layerSchema.getHeight()/2;
                }
            }
        }
    }

    public void drawFakeElements(Graphics graphics) {
        if (mySourceLayer != null) {
            IUsualLayerSchema source = (IUsualLayerSchema)mySourceLayer.getShapeModel();
            graphics.drawLine(source.getX() + source.getWidth(), source.getY() + source.getHeight()/2, myXNewArrow, myYNewArrow);
        }
    }

    public void mouseMoved(MouseEvent e) {
        Block underMouseShape = diagram().getBlock(e.getX(), e.getY());
        if (underMouseShape != myUnderMouseShape && underMouseShape != null &&
                underMouseShape.getShapeModel()  instanceof IUsualLayerSchema) {
            diagram().unselectShape(myUnderMouseShape);
            if (canAddArrow((IUsualLayerSchema)underMouseShape.getShapeModel())) {
                myUnderMouseShape = underMouseShape;
                diagram().selectShape(myUnderMouseShape);                
            }
        } else if (underMouseShape == null) {
            diagram().unselectShape(myUnderMouseShape);
            myUnderMouseShape = underMouseShape;
        }
    }

}
