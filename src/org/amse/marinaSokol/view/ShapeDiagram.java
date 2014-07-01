package org.amse.marinaSokol.view;

import org.amse.marinaSokol.model.interfaces.schema.*;
import org.amse.marinaSokol.view.shapes.Block;
import org.amse.marinaSokol.view.shapes.IShape;
import org.amse.marinaSokol.view.shapes.Arrow;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class ShapeDiagram extends JPanel {
    private View myView;
    private HashSet<IShape> mySelectedShapes;
    private List<Arrow>  myArrows;
    private List<Block> myBlocks;
    private HashMap<IConnectionSchema, Arrow> myMapArrows;

    ShapeDiagram(View view) {
        myView = view;

        myArrows = new LinkedList<Arrow>();
        myBlocks = new LinkedList<Block>();
        myMapArrows = new HashMap<IConnectionSchema, Arrow>();
        mySelectedShapes = new HashSet<IShape>();
        MouseInputAdapter mouseInput = new ShapeDiagramMouseInput();
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        setFocusable(true);
        setSize(this.getWidth(),this.getHeight());
    }

    public void createSchapeDiagram(INeuroNetSchema net) {
        myArrows = new LinkedList<Arrow>();
        myBlocks = new LinkedList<Block>();
        myMapArrows = new HashMap<IConnectionSchema, Arrow>();
        mySelectedShapes = new HashSet<IShape>();
        Block block;
        Arrow arrow;
        for (IUsualLayerSchema layer : net.getLayersSchema()) {
            block = new Block(layer);
            myBlocks.add(block);
            if (!(layer instanceof IInputLayerSchema)) {
                for (IConnectionSchema connection: ((ILayerSchema)layer).getInputConnectionsSchema()) {//layer.getOutputConnectionsSchema()) {
                    arrow = new Arrow(connection);
                    myMapArrows.put(connection, arrow);
                    myArrows.add(arrow);
                }
            }
        }
    }
    
    public Map<IConnectionSchema, Arrow> getMapArrows() {
        return Collections.unmodifiableMap(myMapArrows);
    }

    public Arrow addDirectArrow(IUsualLayerSchema source, IUsualLayerSchema dest) {
        IConnectionSchema connectionSchema = myView.getNeuroNetSchema().addDirectConnectionSchema(source, dest);
        Arrow arrow = new Arrow(connectionSchema);
        myMapArrows.put(connectionSchema, arrow);
        myArrows.add(arrow);
        return arrow;
    }

    public Arrow addBackArrow(ILayerSchema source, ILayerSchema dest) {
        IConnectionSchema connectionSchema = myView.getNeuroNetSchema().addBackConnectionSchema(source, dest);
        Arrow arrow = new Arrow(connectionSchema);
        myMapArrows.put(connectionSchema, arrow);
        myArrows.add(arrow);
        return arrow;
    }

    /**
     * Эта функция выравнивает стрелки. Для каждой входной стрелки
     * она задает точку слоя в которую будет входить стрелка.
     * Вызывает при добавлении стрелки в слой dest,
     * для каждой входной стрелки в него.
     *
     * @param x - координата по оси х
     * @param y - координата по оси y
     * @param width - ширина
     * @param height - высота
     * @return Block
     * */

    public Block addBlock(int x, int y, int width, int height) {
        Block block = new Block(myView.getNeuroNetSchema().addLayerSchema(x, y, width, height));
        myBlocks.add(block);
        return block;
    }

    public Block addInputBlock(int x, int y, int width, int height) {
        Block block = new Block(myView.getNeuroNetSchema().addInputLayerSchema(x, y, width, height));
        myBlocks.add(block);
        return block;
    }

    public Block addOutputBlock(int x, int y, int width, int height) {
        Block block = new Block(myView.getNeuroNetSchema().addOutputLayerSchema(x, y, width, height));
        myBlocks.add(block);
        return block;
    }

    @SuppressWarnings({"SuspiciousMethodCalls"})
    public IShape removeShape(IShape shape) {
        if (shape.getShapeModel() instanceof IUsualLayerSchema) {
            IUsualLayerSchema layerSchema = (IUsualLayerSchema)shape.getShapeModel();
            if (!(layerSchema instanceof IInputLayerSchema)) {
                for (IConnectionSchema connectionSchema : ((ILayerSchema)layerSchema).getInputConnectionsSchema()) {
                    myArrows.remove(myMapArrows.get(connectionSchema));
                    myMapArrows.remove(connectionSchema);
                }
            }
            for (IConnectionSchema connectionSchema : layerSchema.getOutputConnectionsSchema()) {
                myArrows.remove(myMapArrows.get(connectionSchema));
                myMapArrows.remove(connectionSchema);
            }
            myBlocks.remove(shape);
        } else {
            myArrows.remove(shape);
            myMapArrows.remove(shape.getShapeModel());
        }
        myView.getNeuroNetSchema().removeShape(shape.getShapeModel());
        return shape;
    }

    void drawArrows(Graphics g) {
        for(Arrow arrow : myArrows){
            if (mySelectedShapes.contains(arrow)) {
                arrow.drawSelectedShape(g);
            } else {
                arrow.drawShape(g);
            }
        }
        g.setColor(Color.BLACK);
    }

    void drawBlocks(Graphics g) {
        for (Block block: myBlocks) {
            if (mySelectedShapes.contains(block)) {
                block.drawSelectedShape(g);
            } else {
                block.drawShape(g);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(),this.getHeight());

        drawBlocks(g);
        drawArrows(g);
        myView.getMode().drawFakeElements(g);
   }

    public IShape focusShape(int x, int y) {
        ListIterator<Arrow> itArrow = myArrows.listIterator(myArrows.size());
        while (itArrow.hasPrevious()) {
            Arrow arrow = itArrow.previous();
            if (arrow.contains(x, y)) {
                myArrows.remove(arrow);
                myArrows.add(arrow);
                return arrow;
            }
        }

        ListIterator<Block> itBlock = myBlocks.listIterator(myBlocks.size());
        while (itBlock.hasPrevious()) {
            Block block = itBlock.previous();
            if (block.contains(x, y)) {
                myBlocks.remove(block);
                myBlocks.add(block);
                return block;
            }
        }

        return null;
    }

    public IShape getShape(int x, int y) {
        ListIterator<Arrow> itArrow = myArrows.listIterator(myArrows.size());
        while (itArrow.hasPrevious()) {
            Arrow arrow = itArrow.previous();
            if (arrow.contains(x, y)) {
                return arrow;
            }
        }

        ListIterator<Block> itBlock = myBlocks.listIterator(myBlocks.size());
        while (itBlock.hasPrevious()) {
            Block block = itBlock.previous();
            if (block.contains(x, y)) {
                return block;
            }
        }

        return null;
    }


    public Block focusBlock(int x, int y) {
        ListIterator<Block> itBlock = myBlocks.listIterator(myBlocks.size());
        while (itBlock.hasPrevious()) {
            Block block = itBlock.previous();
            if (block.contains(x, y)) {
                myBlocks.remove(block);
                myBlocks.add(block);
                return block;
            }
        }
        return null;
    }

    public Block getBlock(int x, int y) {
        ListIterator<Block> itBlock = myBlocks.listIterator(myBlocks.size());
        while (itBlock.hasPrevious()) {
            Block block = itBlock.previous();
            if (block.contains(x, y)) {
                return block;
            }
        }
        return null;
    }

    public void selectShape(IShape shape) {
        if (shape != null)
            mySelectedShapes.add(shape);
    }

    public void unselectShape(IShape shape) {
        if (shape != null)
            mySelectedShapes.remove(shape);
    }

    private class ShapeDiagramMouseInput extends MouseInputAdapter {

        public void mousePressed(MouseEvent e) {
            if (e.getX() < 0 && e.getY() < 0) {
                return;
            }
            myView.getMode().mousePressed(e);
            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            myView.getMode().mouseReleased(e);
            repaint();
        }

        public void mouseMoved(MouseEvent e) {
            if ((e.getX() < 0) && (e.getY() < 0)) {
                return;
            }
            myView.getMode().mouseMoved(e);
            repaint();
        }

        public void mouseDragged(MouseEvent e) {
            if ((e.getX() <= 0) || (e.getY() <= 0) || (e.getX() >=  ShapeDiagram.this.getWidth()) || (e.getY() >=  ShapeDiagram.this.getHeight())) {
                return;
            }
            myView.getMode().mouseDragged(e);
            repaint();
        }
    }
}
