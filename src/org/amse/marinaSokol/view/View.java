package org.amse.marinaSokol.view;


import org.amse.marinaSokol.reader.ModelFileReader;
import org.amse.marinaSokol.view.modes.schema.*;
import org.amse.marinaSokol.writer.ModelFileWriter;
import org.amse.marinaSokol.model.impl.NeuroNetModel;
import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;

import javax.swing.filechooser.FileFilter;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.io.File;

public class View {
    private INeuroNetSchema myNeuroNetModel;
    private IModeAction myMode;
    private ShapeDiagram myShapeDiagram;

    private ParametersPanel myParametersPanel;
    private ToolBarsPanel myToolBarsPanel;

    private JSplitPane mySplitPane;
    private JFrame myMainFrame;

    private JMenuBar myMenuBar; 

    private boolean myIsSaveCurrentNeuroNet;
    private File myDefaultFile;
    private File myCurrentDirectory;

    View(INeuroNetSchema n, JFrame frame) {
        myNeuroNetModel = n;
        myMainFrame = frame;
        setMode(new NothingModeAction(this));

        myShapeDiagram = new ShapeDiagram(this);
        myShapeDiagram.setPreferredSize(new Dimension(Standard.MAXIMUM_BOARD_WIDTH, Standard.MAXIMUM_BOARD_HEIGHT));

        myParametersPanel = new ParametersPanel(frame, this);

        myToolBarsPanel = new ToolBarsPanel(this);

        mySplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        mySplitPane.setTopComponent(new JScrollPane(myShapeDiagram));

        mySplitPane.getTopComponent().setPreferredSize(new Dimension(300, 300));
        mySplitPane.setBottomComponent(new JScrollPane(myParametersPanel));

        updateToolBar();
        myMenuBar = new JMenuBar();
        myMenuBar.add(createFileMenu());
        myMenuBar.add(createHelpMenu());
        myIsSaveCurrentNeuroNet = true;

    }

    public void setDefaultFile(File file) {
       myDefaultFile = file;
    }

    public void setSaveNet(boolean isSave) {
        myIsSaveCurrentNeuroNet = isSave;
    }

    public boolean getSaveNet() {
        return myIsSaveCurrentNeuroNet;
    }

    public JFrame getMainFrame() {
        return myMainFrame;
    }

    public void setMode(IModeAction mode) {
        myMode = mode;
    }

    public IModeAction getMode() {
        return myMode;
    }

    public INeuroNetSchema getNeuroNetSchema() {
        return myNeuroNetModel;
    }

    public ParametersPanel getParametersPanel() {
        return myParametersPanel;
    }

    public ShapeDiagram getShapeDiagram() {
        return myShapeDiagram;
    }

    public void updateToolBar() {
        //myIsSaveCurrentNeuroNet = false;
        myToolBarsPanel.updateCurrentToolBar();
    }

    public void setNewNeuroNetSchema(INeuroNetSchema net) {
        myNeuroNetModel = net;
        mySplitPane.remove(myShapeDiagram);
        myShapeDiagram = new ShapeDiagram(View.this);
        myShapeDiagram.setPreferredSize(new Dimension(Standard.MAXIMUM_BOARD_WIDTH, Standard.MAXIMUM_BOARD_HEIGHT));

        mySplitPane.setTopComponent(new JScrollPane(myShapeDiagram));

        mySplitPane.getTopComponent().setPreferredSize(new Dimension(300, 300));

        myShapeDiagram.createSchapeDiagram(net);
    }
    
    public JSplitPane getSplitPane() {
        return mySplitPane;
    }

	public void showSchemaToolBar() {
		myMainFrame.remove(myToolBarsPanel.getObjectToolBar());
		myMainFrame.add(myToolBarsPanel.getSchemaToolBar(), BorderLayout.NORTH);
		myToolBarsPanel.getSchemaToolBar().unchecked();
        myMainFrame.invalidate();
		myMainFrame.validate();
		myMainFrame.repaint();
	}

	public void showObjectToolBar() {
		myMainFrame.remove(myToolBarsPanel.getSchemaToolBar());
		myMainFrame.add(myToolBarsPanel.getObjectToolBar(), BorderLayout.NORTH);
        myToolBarsPanel.getObjectToolBar().unchecked();
		myMainFrame.invalidate();
		myMainFrame.validate();
		myMainFrame.repaint();
	}
	
	public JMenuBar getMenuBar() {
		return myMenuBar;
	}
	
    private JMenu createFileMenu() {
        JMenu file = new JMenu("Файл");
        final JMenuItem newSchema = new JMenuItem(new NewSchemaAction());
        final JMenuItem open = new JMenuItem(new OpenAction());
        final JMenuItem save = new JMenuItem(new SaveAction());
        final JMenuItem saveAs = new JMenuItem(new SaveAsAction());        
        final JMenuItem exit = new JMenuItem(new ExitAction());
        file.add(newSchema);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.addSeparator();
        file.add(exit);

        file.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (myIsSaveCurrentNeuroNet) {
                    save.setEnabled(false);
                } else {
                    save.setEnabled(true);
                }

            }
        });
        return file;
    }

    private class NewSchemaAction extends AbstractAction {
        NewSchemaAction() {
            putValue(NAME, "Создать");
        }

        public void actionPerformed(ActionEvent e){
            newSchema();
        }
    }

    public AbstractAction createExitAction() {
        return new ExitAction();
    }

    private class ExitAction extends AbstractAction {
        ExitAction() {
            putValue(NAME, "Выход");
        }

        public void actionPerformed(ActionEvent e){
             exit();
        }
    }

    private class OpenAction extends AbstractAction {

        OpenAction() {
            putValue(NAME, "Открыть...");
        }

        public void actionPerformed(ActionEvent e){
             open();
        }
    }

    private class SaveAction extends AbstractAction {
        SaveAction() {
            putValue(NAME, "Сохранить");
        }

        public void actionPerformed(ActionEvent e){
             save();
        }
    }

    private class SaveAsAction extends AbstractAction {
        SaveAsAction() {
            putValue(NAME, "Сохранить как...");
        }

        public void actionPerformed(ActionEvent e){
             saveAs();
        }
    }


    private boolean writeDiagram(INeuroNetSchema d, String f) {
        try {
            ModelFileWriter fw = new ModelFileWriter(d);
            fw.write(f);
        } catch (Exception e) {
            new ErrorForm(this.myMainFrame, e,
                        "Ошибка во время записи", false);            
            return false;
        }
        return true;
    }

    private INeuroNetSchema readDiagram(File file) {
        ModelFileReader fr;
        INeuroNetSchema d = new NeuroNetModel();
        try {
            fr = new ModelFileReader(file);
            fr.read(d);
        } catch (Exception e) {
            new ErrorForm(this.myMainFrame, e,
                        "Ошибка во время чтения", false);
            return null;
        }
        return d;
    }

    private void  createDiagram(INeuroNetSchema net) {
        if (net == null)
            return;
        setNewNeuroNetSchema(net);
        updateToolBar();
    }

    private int offeredSavePreviousSchema() {
        if (!myIsSaveCurrentNeuroNet) {
            int resSave = JOptionPane.showConfirmDialog(myMainFrame, "Хотите ли вы сохранить предыдущий файл?", "Вопрос", JOptionPane.YES_NO_CANCEL_OPTION);
            if (resSave == JOptionPane.YES_OPTION) {
                save();
            }
            return resSave;
        }
        return -1;
    }

    public int offeredSaveObject() {
        if (!myIsSaveCurrentNeuroNet) {
            int resSave = JOptionPane.showConfirmDialog(myMainFrame, "Хотите ли вы записать обьект?", "Вопрос", JOptionPane.YES_NO_CANCEL_OPTION);
            if (resSave == JOptionPane.YES_OPTION) {
                save();
            }
            showSchemaToolBar();
            return resSave;
        }
        return -1;
    }


    private void save() {
        if (myDefaultFile == null) {
            saveAs();
        } else {
            String path = myDefaultFile.getPath();
            writeDiagram(myNeuroNetModel, path);
        }
        myIsSaveCurrentNeuroNet = true;

    }

    private void saveAs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранение файла");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (myNeuroNetModel.getNeuroNet() == null) {
            fileChooser.addChoosableFileFilter(new TextFilesFilter(".nng", "Файлы рисунка сети (*.nng)"));
        } else {
            fileChooser.addChoosableFileFilter(new TextFilesFilter(".nno", "Файлы обьекта сети (*.nno)"));
        }

        if (myDefaultFile != null) {
            fileChooser.setSelectedFile(myDefaultFile);
        }

        if (myCurrentDirectory != null) {
            fileChooser.setCurrentDirectory(myCurrentDirectory);
        }

        int res = fileChooser.showSaveDialog(myMainFrame);
        File f = null;
        if (res == JFileChooser.APPROVE_OPTION) {
            f = fileChooser.getSelectedFile();
            String path = f.getPath();
            final String suffix = ((TextFilesFilter)fileChooser.getFileFilter()).getSuffix();
            if (!path.endsWith(suffix)) {
                path = path + suffix;
            }
            writeDiagram(myNeuroNetModel, path);
        }
        myIsSaveCurrentNeuroNet = true;
        myDefaultFile = f;
        myCurrentDirectory = fileChooser.getCurrentDirectory();
    }

    private void open() {
        INeuroNetSchema d = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберете каталог:");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.addChoosableFileFilter(new TextFilesFilter(".nng", "Файлы рисунка сети (*.nng)"));
        fileChooser.addChoosableFileFilter(new TextFilesFilter(".nno", "Файлы обьекта сети (*.nno)"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (myCurrentDirectory != null) {
            fileChooser.setCurrentDirectory(myCurrentDirectory);
        }
        int resOpen = fileChooser.showOpenDialog(myMainFrame);
        File file = null;
        if (resOpen == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            myCurrentDirectory = fileChooser.getCurrentDirectory();
            d = readDiagram(file);           
        }

        if (d == null) {
            return;
        }
        final int res = offeredSavePreviousSchema();
        if (res == JOptionPane.CANCEL_OPTION) {
            return;
        }


        if (d.getNeuroNet() == null) {
            showSchemaToolBar();
        } else {
            showObjectToolBar();
        }
        myParametersPanel.hideAll();        
        createDiagram(d);
        myIsSaveCurrentNeuroNet = true;
        myDefaultFile = file;
        myCurrentDirectory = fileChooser.getCurrentDirectory();

    }

    public void exit() {
        final int res = offeredSavePreviousSchema();
        if (res == JOptionPane.CANCEL_OPTION) {
            return;
        }
        System.exit(0);
    }

    private void newSchema() {
        final int res = offeredSavePreviousSchema();
        if (res == JOptionPane.CANCEL_OPTION) {
            return;
        }
        myNeuroNetModel = new NeuroNetModel();
        createDiagram(myNeuroNetModel);
        showSchemaToolBar();
        myParametersPanel.hideAll();
        myIsSaveCurrentNeuroNet = true;
        myDefaultFile = null;
    }

    private class TextFilesFilter extends FileFilter {
        String mySuffix;
        String myDescription;

        TextFilesFilter() {}

        TextFilesFilter(String suffix, String description) {
            mySuffix = suffix;
            myDescription = description;
        }

        public String getSuffix() {
            return mySuffix;
        }

        public boolean accept(java.io.File file) {
            return file.isDirectory() || (file.getName().endsWith(mySuffix));
        }

        public String getDescription() {
            return myDescription;
        }
    }
    



    private JMenu createHelpMenu() {
		JMenu help = new JMenu("Помощь");
		help.add(new JMenuItem(new AboutAction()));
		return help;
	}

	class AboutAction extends AbstractAction {
		private static final long serialVersionUID = 1;

		AboutAction() {
			putValue(NAME, "О программе...");
		}

		public void actionPerformed(ActionEvent e) {
			JDialog dialog = createDialog("О программе");
			dialog.setVisible(true);
		}
	}

	private JDialog createDialog(String title) {
		JDialog dialog = new JDialog(this.myMainFrame, title, true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setLayout(new FlowLayout());
		JLabel label = new JLabel(
				"<html><center><h2>Графический редактор нейронных сетей</h2>" +
				"<p>Программа для создания, обучения и использования" +
                "</p><p>рекуррентных нейронных сетей." +
				"</p><p>Сокол Марина, 2007</p>"
				+ "</center></html>");
		dialog.add(label);
		dialog.setBounds(250, 270, 400, 140);
		dialog.setResizable(false);
		return dialog;
	}
}
