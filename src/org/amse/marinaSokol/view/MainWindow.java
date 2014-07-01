package org.amse.marinaSokol.view;

import org.amse.marinaSokol.model.interfaces.schema.INeuroNetSchema;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
    public MainWindow(INeuroNetSchema netSchema){
        super("Редактор нейронных сетей");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(Standard.STANDARD_WINDOW_WIDTH, Standard.STANDARD_WINDOW_HEIGHT);
        JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());

        getContentPane().add(contentPanel);

        final View view = new View(netSchema, this);
        view.showSchemaToolBar();  
 
        view.getSplitPane().setAlignmentX(JComponent.LEFT_ALIGNMENT);

        contentPanel.add(view.getSplitPane(), BorderLayout.CENTER);

        setJMenuBar(view.getMenuBar());
        setVisible(true);
        UIManager.put("OptionPane.noButtonText", "нет");
        UIManager.put("OptionPane.yesButtonText", "да");
        UIManager.put("OptionPane.cancelButtonText", "отменить");
        

        addComponentListener(new ComponentAdapter() {
             public void componentResized(ComponentEvent event) {
                 int width = getWidth();
                 int height = getHeight();

                 if (width < Standard.MINIMUM_WINDOW_WIDTH) {
                     width = Standard.MINIMUM_WINDOW_WIDTH;
                 } else if (width > Standard.MAXIMUM_WINDOW_WIDTH) {
                     width = Standard.MAXIMUM_WINDOW_WIDTH;
                 }

                 if (height < Standard.MINIMUM_WINDOW_HEIGHT) {
                     height = Standard.MINIMUM_WINDOW_HEIGHT;
                 } else if (height > Standard.MAXIMUM_WINDOW_HEIGHT) {
                     height = Standard.MAXIMUM_WINDOW_HEIGHT;
                 }

                 setSize(width, height);
             }
        });

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                view.exit();
            }
        });

    }
}

