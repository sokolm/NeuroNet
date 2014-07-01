package org.amse.marinaSokol.view.parameters;

import org.amse.marinaSokol.view.shapes.IShape;
import org.amse.marinaSokol.view.AbstractModelParameters;
import org.amse.marinaSokol.view.View;
import org.amse.marinaSokol.model.interfaces.object.net.*;
import org.amse.marinaSokol.exception.MyException;

import javax.swing.*;
import java.util.LinkedList;
import java.awt.*;

@SuppressWarnings("serial")
public class LayerParametersPanel extends AbstractModelParameters {
    private IUsualLayer myCurrentLayer;
    private JComboBox myComboBoxFA;
    private JTextField myNumNeuronsText;
    private JLabel myNumNeuronsLabel;
    private JLabel myFuncActivationLabel;

    public LayerParametersPanel(View view) {
        super(view);
        myNumNeuronsLabel = new JLabel("Число нейронов: ");
        myNumNeuronsText = new JTextField(3);
        myFuncActivationLabel = new JLabel("Функция активации: ");

        GridLayout gridLayout = new GridLayout(0, 2);
        setLayout(gridLayout);

        add(myNumNeuronsLabel);
        add(myNumNeuronsText);
        add(myFuncActivationLabel);

        java.util.List<String> nameFunction = new LinkedList<String>();

        for (ActivationFunctorType type : ActivationFunctorType.values()) {
            nameFunction.add(type.toString().toLowerCase());
        }
        nameFunction.remove(ActivationFunctorType.NULL.toString().toLowerCase());
        myComboBoxFA = new JComboBox(nameFunction.toArray());
        add(myComboBoxFA);
        myComboBoxFA.setSelectedItem(null);
    }

    public void setCurrentShape(IShape currentShape) {
        if (currentShape != null) {
            myCurrentLayer = (IUsualLayer)currentShape.getShapeModel();
        } else {
            myCurrentLayer = null;
        }
    }

    public void setShapeParameters() {
        if (!(myCurrentLayer instanceof IInputLayer)) {
            myComboBoxFA.setVisible(true);
            myFuncActivationLabel.setVisible(true);
        } else {
            myComboBoxFA.setVisible(false);
            myFuncActivationLabel.setVisible(false);
        }
        myNumNeuronsText.setText("" + myCurrentLayer.getNeuronsNumber());
        if (!(myCurrentLayer instanceof IInputLayer)) {
            myComboBoxFA.setSelectedItem(((ILayer)myCurrentLayer).getActivation().getNameFunction());//myMapFunctionName.get(layer.getActivation()));
        } else {
            myComboBoxFA.setSelectedItem(null);
        }
    }

    public void saveShapeParameters() {
        if (myCurrentLayer == null)
           return;

        if (!(myCurrentLayer instanceof IInputLayer)) {
            String stringValue = myComboBoxFA.getSelectedItem().toString();
            if (!stringValue.equals(((ILayer)myCurrentLayer).getActivation().getNameFunction())) {
                ((ILayer)myCurrentLayer).setActivation(ActivationFunctorType.valueOf(stringValue.toUpperCase()));
                setSaveNet(false);
            }
        }
        //noinspection EmptyCatchBlock
        try {
            final Integer num = Integer.parseInt(myNumNeuronsText.getText());
            if (num <= 0) {
                throw new MyException("Неправильно введено число нейронов в слое");
            }
            if (num != myCurrentLayer.getNeuronsNumber()) {
                myCurrentLayer.setNeuronsNumber(num);
                setSaveNet(false);
            }
        } catch (MyException e) {}
        catch (RuntimeException e) {}
    }

    public void setEnabled(boolean enable) {
        myComboBoxFA.setEnabled(enable);
        myNumNeuronsText.setEnabled(enable);
        myNumNeuronsLabel.setEnabled(enable);
        myFuncActivationLabel.setEnabled(enable);
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
        myComboBoxFA.setEnabled(visible);
        myNumNeuronsText.setEnabled(visible);
        myNumNeuronsLabel.setEnabled(visible);
        myFuncActivationLabel.setEnabled(visible);
    }


    public void setMode(boolean isTeach){}
}
