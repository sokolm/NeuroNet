package org.amse.marinaSokol.view.parameters;

import org.amse.marinaSokol.view.shapes.IShape;
import org.amse.marinaSokol.view.AbstractModelParameters;
import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.model.interfaces.object.net.IConnection;
import org.amse.marinaSokol.model.interfaces.schema.IConnectionSchema;
import org.amse.marinaSokol.exception.MyException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.Formatter;
import java.util.Locale;

@SuppressWarnings("serial")
public class ConnectionParametersPanel extends AbstractModelParameters {
    private IConnection myCurrentConnection;
    private JLabel myDelayLabel;
    private JTextField myDelayText;
    private JTable myWeightsTable;
    private JPanel myPanelMain;

    public ConnectionParametersPanel(View view) {
        super(view);
        myDelayLabel = new JLabel("задержка:");
        myDelayText = new JTextField();

        myDelayText.setPreferredSize(new Dimension(50, 20));
        JPanel panelDelay = new JPanel();
        panelDelay.add(myDelayLabel);
        panelDelay.add(myDelayText);

        myPanelMain = BoxLayoutUtils.createVerticalPanel();
        

        myPanelMain.add(panelDelay);
        myPanelMain.add(new JLabel("веса:"));
        add(myPanelMain);
    }

    public void setCurrentShape(IShape currentShape) {
        if (currentShape != null) {
            myCurrentConnection = (IConnection)currentShape.getShapeModel();
        } else {
            myCurrentConnection = null;
        }
    }

    public void setShapeParameters() {
        myDelayText.setText("" + myCurrentConnection.getDelay());
        if (myWeightsTable != null) {
		    myPanelMain.remove(myWeightsTable);
		}
        final int width = myCurrentConnection.getSourceLayer().getNeuronsNumber();
        final int height = myCurrentConnection.getDestLayer().getNeuronsNumber();
        myWeightsTable = new JTable(new MyTableModel(width, height));
        myPanelMain.add(myWeightsTable);
        final boolean isSave = getSaveNet();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //w.setAttribute("value", new Formatter().format("%.3e", new Double(a.getWeights()[i][j])).toString());
                myWeightsTable.setValueAt(new Formatter(Locale.US).format("%.3f", new Double(myCurrentConnection.getWeights()[i][j])).toString(), i, j);
                //myWeightsTable.setValueAt(Double.toString(myCurrentConnection.getWeights()[i][j]), i, j);
            }
        }
        setSaveNet(isSave);
		revalidate();
    }

    public void saveShapeParameters() {
        if (myCurrentConnection == null)
            return;
        //noinspection EmptyCatchBlock
        try{
            int newDelay = Integer.parseInt(myDelayText.getText());
            if ((((IConnectionSchema)myCurrentConnection).isDirect() && (newDelay < 0)) ||
                    (!((IConnectionSchema)myCurrentConnection).isDirect() && (newDelay < 1))) {
                throw new MyException("Неправильно введена задержка соединения");
            }
            if (newDelay != myCurrentConnection.getDelay()) {
                myCurrentConnection.setDelay(newDelay);
                setSaveNet(false);
            }
        } catch (MyException e){

        }
        catch (RuntimeException e) {

        }

        final int width = myCurrentConnection.getSourceLayer().getNeuronsNumber();
        final int height = myCurrentConnection.getDestLayer().getNeuronsNumber();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //noinspection EmptyCatchBlock
                try {
                    myCurrentConnection.getWeights()[i][j] = Double.parseDouble(myWeightsTable.getValueAt(i, j).toString());
                } catch (RuntimeException e) {}
            }
        }
    }

    public void setEnabled(boolean enable) {
        myDelayLabel.setEnabled(enable);
        myDelayText.setEnabled(enable);
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
        myDelayLabel.setEnabled(visible);
        myDelayText.setEnabled(visible);
        if (myWeightsTable != null) {
		    myWeightsTable.setVisible(visible);
	    }
        myPanelMain.setVisible(visible);
    }

    public void setMode(boolean isTeach){}

    private class MyTableModel extends AbstractTableModel {

        private String[] columnNames;
        private Object[][] data;

        MyTableModel(int w, int h) {
            columnNames = new String[h];
            data = new Object[w][h];
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
                return true;
        }

        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
            ConnectionParametersPanel.super.setSaveNet(false);
        }
    }

}
