package org.amse.marinaSokol.view.parameters;

import org.amse.marinaSokol.view.*;
import org.amse.marinaSokol.view.shapes.IShape;
import org.amse.marinaSokol.model.interfaces.schema.*;
import org.amse.marinaSokol.model.interfaces.object.net.IInputLayer;
import org.amse.marinaSokol.model.interfaces.object.net.IOutputLayer;
import org.amse.marinaSokol.model.interfaces.object.net.ILayer;
import org.amse.marinaSokol.model.interfaces.object.net.IUsualLayer;
import org.amse.marinaSokol.model.interfaces.object.teacher.ITeacher;
import org.amse.marinaSokol.model.interfaces.object.user.IUser;
import org.amse.marinaSokol.model.impl.object.InputLayerData;
import org.amse.marinaSokol.model.impl.object.user.User;
import org.amse.marinaSokol.model.impl.object.teacher.Teacher;
import org.amse.marinaSokol.writer.InputLayerDataWriter;
import org.amse.marinaSokol.reader.InputLayerDataReader;

import javax.swing.*;
import java.util.Map;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import static java.lang.Double.*;
import static java.lang.Math.*;

@SuppressWarnings("serial")
public class TeachParametersPanel extends AbstractModelParameters {
    private IUsualLayerSchema myCurrentLayerSchema;

    private INeuroNetSchema myNet;
    private boolean myIsTeach = true;


    private JLabel mySpeedLabel;
    private JTextField mySpeedTextField;
    private JTextField myNumEpochTextField;

    private JPanel myFilesPanel;  //панель в которой располагаются файлы

    private JPanel myRightOutputPanel;

    private File myFile;
    private File myCurrentDirectory;
    private JFrame myMainFrame;
    private Map<IUsualLayerSchema, JLabel> myInputLayerLabelMap;
    private Map <IUsualLayerSchema, JTextField> myInputLayerTextMap;
    private Map<ILayerSchema, JLabel> myRightOutputLayerLabelMap;
    private Map <ILayerSchema, JTextField> myRightOutputLayerTextMap;
    private Map<ILayerSchema, JLabel> myOutputLayerLabelMap;
    private Map <ILayerSchema, JTextField> myOutputLayerTextMap;

    private Map<JButton, JTextField> myButtonTextMap;

    private JPanel myMainPanel;
    private JPanel mySetUpPanel1;

    private JButton myRunButton; // запускает действие

    public TeachParametersPanel(JFrame frame, View view) {
        super(view);
        myMainFrame = frame;

        mySpeedLabel = new JLabel("Скорость обучения: ");
        mySpeedTextField = new JTextField("0.1");

        JLabel myNumEpochLabel = new JLabel("Число эпох:");
        myNumEpochTextField = new JTextField("10");


        JPanel panelSpeed = new JPanel(new GridLayout(0, 2));
        panelSpeed.add(mySpeedLabel);
        panelSpeed.add(mySpeedTextField);

        JPanel panelEpoch = new JPanel(new GridLayout(0, 2));
        panelEpoch.add(myNumEpochLabel);
        panelEpoch.add(myNumEpochTextField);

        JPanel panelError = new JPanel(new GridLayout(0, 2));
        myRunButton = new JButton("запуск");

        JPanel contentPane = BoxLayoutUtils.createHorizontalPanel();
        myMainPanel = new JPanel(new BorderLayout());//BoxLayoutUtils.createVerticalPanel();
        mySetUpPanel1 = new JPanel(new BorderLayout());//BoxLayoutUtils.createVerticalPanel();
        JPanel setUpPanel2 = new JPanel(new BorderLayout());//BoxLayoutUtils.createVerticalPanel();
        JPanel setUpPanel3 = new JPanel(new BorderLayout());//BoxLayoutUtils.createVerticalPanel();

        mySetUpPanel1.add(new JPanel(new FlowLayout()).add(panelSpeed), BorderLayout.NORTH);
        mySetUpPanel1.add(setUpPanel2, BorderLayout.SOUTH);

        setUpPanel2.add(new JPanel(new FlowLayout()).add(panelEpoch), BorderLayout.NORTH);
        setUpPanel2.add(setUpPanel3, BorderLayout.SOUTH);

        setUpPanel3.add(new JPanel(new FlowLayout()).add(panelError), BorderLayout.NORTH);
        myFilesPanel = new JPanel();

        myMainPanel.add(mySetUpPanel1, BorderLayout.NORTH);
        myMainPanel.add(myFilesPanel, BorderLayout.SOUTH);
        contentPane.add(myMainPanel);
        myMainPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        myRunButton.setAlignmentY(Component.TOP_ALIGNMENT);
        myRunButton.addActionListener(new ButtonListener());
        contentPane.add(myRunButton);
        add(contentPane);
    }
    /**
     * @param isTeach - если true, то режим учителя, иначе - пользователя
     * */
    public void setMode(boolean isTeach) {
        mySetUpPanel1.setVisible(isTeach);
        myRightOutputPanel.setVisible(isTeach);
        myIsTeach = isTeach;
    }

    public void setCurrentShape(IShape currentShape) {
        myCurrentLayerSchema = (IUsualLayerSchema)currentShape.getShapeModel();
        if (myCurrentLayerSchema instanceof IInputLayerSchema) {
            myInputLayerLabelMap.get(myCurrentLayerSchema).setForeground(Standard.LIGHT_RED);
        } else {
            myOutputLayerLabelMap.get(myCurrentLayerSchema).setForeground(Standard.LIGHT_RED);
            myRightOutputLayerLabelMap.get(myCurrentLayerSchema).setForeground(Standard.LIGHT_RED);
        }
    }

    public void setNet(INeuroNetSchema net) {
        myMainPanel.remove(myFilesPanel);

        myInputLayerLabelMap = new HashMap<IUsualLayerSchema, JLabel>();
        myRightOutputLayerLabelMap = new HashMap<ILayerSchema, JLabel>();
        myOutputLayerLabelMap = new HashMap<ILayerSchema, JLabel>();

        myInputLayerTextMap = new HashMap<IUsualLayerSchema, JTextField>() ;
        myRightOutputLayerTextMap = new HashMap<ILayerSchema, JTextField>() ;
        myOutputLayerTextMap = new HashMap<ILayerSchema, JTextField>() ;

        myButtonTextMap = new  HashMap<JButton, JTextField>();

        myNet = net;
        myFilesPanel = new JPanel();
        myFilesPanel.setLayout(new GridLayout(0,3));
        JPanel myInputPanel = new JPanel(new GridLayout(0, 1));
        myRightOutputPanel = new JPanel(new GridLayout(0,1));
        JPanel myOutputPanel = new JPanel(new GridLayout(0, 1));
        JPanel flowRight = new JPanel(new FlowLayout());
        flowRight.add(myRightOutputPanel);
        JPanel flowOut = new JPanel(new FlowLayout());
        flowOut.add(myOutputPanel);
        JPanel flowInput = new JPanel(new FlowLayout());
        flowInput.add(myInputPanel);
        myFilesPanel.add(flowInput);
        myFilesPanel.add(flowOut);
        myFilesPanel.add(flowRight);
        myMainPanel.add(myFilesPanel, BorderLayout.SOUTH);

        for (IUsualLayerSchema layer: myNet.getLayersSchema()) {
            if (layer instanceof IInputLayerSchema) {
                JPanel panel = BoxLayoutUtils.createHorizontalPanel();
                JLabel label = new JLabel("вход " + layer.getId() + " из:");
                JTextField textField = new JTextField(((IInputLayerSchema)layer).getFileName());
                JButton button = createChooserButton();
                textField.setPreferredSize(new Dimension(100, 10));
                textField.setText(((IInputLayerSchema)layer).getFileName());
                panel.add(textField);
                panel.add(button);
                myInputPanel.add(label);
                myInputPanel.add(panel);
                myInputLayerLabelMap.put(layer, label);
                myInputLayerTextMap.put(layer, textField);
                myButtonTextMap.put(button, textField);

            } else if (layer instanceof IOutputLayerSchema) {
                JPanel panelRight = BoxLayoutUtils.createHorizontalPanel();
                JLabel labelRight = new JLabel("требуемый выход " + layer.getId() + " из:");
                JTextField textFieldRight = new JTextField(((IOutputLayerSchema)layer).getFileNameRightOutput());
                textFieldRight.setPreferredSize(new Dimension(100, 10));
                textFieldRight.setText(((IOutputLayerSchema)layer).getFileNameRightOutput());
                {
                    JButton button = createChooserButton();
                    panelRight.add(textFieldRight);
                    panelRight.add(button);
                    myRightOutputLayerLabelMap.put((IOutputLayerSchema)layer, labelRight);
                    myButtonTextMap.put(button, textFieldRight);

                }
                myRightOutputLayerTextMap.put((IOutputLayerSchema)layer, textFieldRight);
                myRightOutputPanel.add(labelRight);
                myRightOutputPanel.add(panelRight);

                JPanel panel = BoxLayoutUtils.createHorizontalPanel();
                JLabel label = new JLabel("выход " + layer.getId() + " в :");
                JTextField textField = new JTextField(((IOutputLayerSchema)layer).getFileNameOutput());
                textField.setPreferredSize(new Dimension(100, 10));
                textField.setText(((IOutputLayerSchema)layer).getFileNameOutput());
                {
                    JButton button = createChooserButton();
                    panel.add(textField);
                    panel.add(button);
                    myOutputLayerLabelMap.put((IOutputLayerSchema)layer, label);
                    myButtonTextMap.put(button, textField);
                }
                myOutputLayerTextMap.put((IOutputLayerSchema)layer, textField);
                myOutputPanel.add(label);
                myOutputPanel.add(panel);
            }
        }

        myFilesPanel.revalidate();
        revalidate();
    }

    public void setShapeParameters(){
    }

    public void saveShapeParameters() {
        for(IUsualLayerSchema layer : myNet.getLayersSchema()) {
            if (layer instanceof IInputLayer) {
                ((IInputLayerSchema)layer).setFileName(myInputLayerTextMap.get(layer).getText());
            } else if (layer instanceof IOutputLayer) {
                ((IOutputLayerSchema)layer).setFileNameOutput(myOutputLayerTextMap.get(layer).getText());
                ((IOutputLayerSchema)layer).setFileNameRightOutput(myRightOutputLayerTextMap.get(layer).getText());                                
            }
        }
    }

    public void unselectLabels() {
        Color black = mySpeedLabel.getForeground();
        JLabel labelInput = myInputLayerLabelMap.get(myCurrentLayerSchema);
        if (labelInput != null) {
            labelInput.setForeground(black);
        }

        //noinspection SuspiciousMethodCalls
        JLabel labelOutput = myOutputLayerLabelMap.get(myCurrentLayerSchema);
        if (labelOutput != null) {
            labelOutput.setForeground(black);
            myRightOutputLayerLabelMap.get(myCurrentLayerSchema).setForeground(black);
        }
    }

    public void setVisible(boolean visible){
        myMainPanel.remove(myFilesPanel);
        mySetUpPanel1.setVisible(visible);
        myRunButton.setVisible(visible);
        revalidate();
    }

    public void setEnabled(boolean enable){
    }

    private JButton createChooserButton() {
        final JButton chooserButton = new JButton("...");

        chooserButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Выберете каталог:");
                if (myCurrentDirectory != null) {
                    fileChooser.setCurrentDirectory(myCurrentDirectory);
                }
                int resOpen = fileChooser.showOpenDialog(myMainFrame);
                if (resOpen == JFileChooser.APPROVE_OPTION) {
                    myFile = fileChooser.getSelectedFile();
                    myCurrentDirectory = fileChooser.getCurrentDirectory();
                    String file = myFile.getPath();                    
                    myButtonTextMap.get(chooserButton).setText(file);
                }

            }
        });
        chooserButton.setText("...");
        return chooserButton;
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //созраняем все сделанные настройки
            saveShapeParameters();
            unselectLabels();
            if (myIsTeach) {
                teach();
            } else {
                use();
            }
        }
    }

    private void use() {
        int time = 0;
        int tmpTime;
        //делаем мапу для учителя, если где-то что-то не так. то показываем где
        Map<IInputLayer, InputLayerData> layerInputData = new HashMap<IInputLayer, InputLayerData>();

        for (IUsualLayerSchema layer: myNet.getLayersSchema()) {
            if (layer instanceof IInputLayerSchema) {
                IInputLayerSchema l = (IInputLayerSchema)layer;
                final InputLayerData data = readDataFile(l.getFileName());
                if (data != null) {
                    layerInputData.put((IInputLayer)l, data);
                    tmpTime = time;
                    time = data.getPatternTrainingData().getSizeY();
                    if (tmpTime != time && tmpTime != 0) {
                        time = min(tmpTime, time);
                        JOptionPane.showMessageDialog(myMainFrame, "Файлы данных сети разных размеров. В программе будет использован меньший размер.",
                                "Предупреждение", JOptionPane.WARNING_MESSAGE);                        
                    }
                } else {
                    myCurrentLayerSchema = layer;
                    myInputLayerLabelMap.get(layer).setForeground(Standard.LIGHT_RED);
                    return;
                }
            }
        }
        IUser<InputLayerData> user = new User(myNet.getNeuroNet(), time);
        user.run(layerInputData);
        Map<ILayer, InputLayerData> outputLayerData = user.getOutputData();
        write(outputLayerData);
        JOptionPane.showMessageDialog(myMainFrame, "Операция завершена!",
                                "Оповещение", JOptionPane.INFORMATION_MESSAGE);

    }

    private void teach() {
        int time = 0;
        int tmpTime;
        double speed;
       // double error;
        int numEpoch;
        try {
            //noinspection UnusedAssignment
            speed = parseDouble(mySpeedTextField.getText());
            //noinspection UnusedAssignment
            //error = parseDouble(myErrorTextField.getText());
            numEpoch = Integer.parseInt(myNumEpochTextField.getText());
        } catch (Exception ex) {
            new ErrorForm( myMainFrame, ex,
                       "Неправильно введены параметры учителя" , false);
            return;
        }
        //делаем мапу для учителя, если где-то что-то не так. то показываем где
        Map<IUsualLayer, InputLayerData> layerInputData = new HashMap<IUsualLayer, InputLayerData>();

        for (IUsualLayerSchema layer: myNet.getLayersSchema()) {
            if (layer instanceof IInputLayerSchema) {
                IInputLayerSchema l = (IInputLayerSchema)layer;
                final InputLayerData data = readDataFile(l.getFileName());
                if (data != null) {
                    layerInputData.put((IUsualLayer)l, data);
                    tmpTime = time;
                    time = data.getPatternTrainingData().getSizeY();
                    if (tmpTime != time && tmpTime != 0) {
                        //noinspection UnusedAssignment
                        time = min(tmpTime, time);
                        JOptionPane.showMessageDialog(myMainFrame, "Файлы данных сети разных размеров.",
                                "Предупреждение", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                } else {
                    myCurrentLayerSchema = layer;
                    myInputLayerLabelMap.get(layer).setForeground(Standard.LIGHT_RED);

                    return;
                }
            } else if (layer instanceof IOutputLayerSchema) {
                IOutputLayerSchema l = (IOutputLayerSchema)layer;
                final InputLayerData data = readDataFile(l.getFileNameRightOutput());
                if (data != null) {
                    layerInputData.put((IUsualLayer)l, data);
                    tmpTime = time;
                    time = data.getPatternTrainingData().getSizeY();
                    if (tmpTime != time && tmpTime != 0) {
                        //noinspection UnusedAssignment
                        time = min(tmpTime, time);
                        JOptionPane.showMessageDialog(myMainFrame, "Файлы данных сети разных размеров.",
                                "Предупреждение", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } else {
                    myCurrentLayerSchema = layer;
                    myRightOutputLayerLabelMap.get(layer).setForeground(Standard.LIGHT_RED);
                    return;
                }
            }
        }

        ITeacher teacher = new Teacher(myNet.getNeuroNet(), time);
        teacher.setSPEED(speed);
        teacher.setSTEPS(numEpoch);
        teacher.teach(layerInputData);
        Map<ILayer, InputLayerData> outputLayerData = teacher.getOutputData();
        setSaveNet(false);
        write(outputLayerData);
        JOptionPane.showMessageDialog(myMainFrame, "Операция завершена!",
                                "Оповещение", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean write(Map<ILayer, InputLayerData> myOutputLayerData) {
        for (IUsualLayerSchema layer: myNet.getLayersSchema()) {
            if (layer instanceof IOutputLayerSchema) {
                IOutputLayerSchema l = (IOutputLayerSchema)layer;
                if (!writeDataFile(myOutputLayerData.get(layer), l.getFileNameOutput())) {
                    myCurrentLayerSchema = layer;
                    myOutputLayerLabelMap.get(layer).setForeground(Standard.LIGHT_RED);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean writeDataFile(InputLayerData data, String f) {
        try {
            InputLayerDataWriter fw = new InputLayerDataWriter(data);
            fw.write(f);
        } catch (Exception e) {
            new ErrorForm( myMainFrame, e,
                        "Ошибка во время записи данных" , false);
            return false;
        }
        return true;
    }

    private InputLayerData readDataFile(String file) {
        InputLayerDataReader fr;
        InputLayerData data;
        try {
            fr = new InputLayerDataReader(file);
            data = new InputLayerData(fr.read());
        } catch (Exception e) {
            new ErrorForm(myMainFrame, e,
                        "Ошибка во время чтения данных слоя.", false);
            return null;
        }
        return data;
    }
}

