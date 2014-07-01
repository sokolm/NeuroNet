package org.amse.marinaSokol.view;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;

public class ErrorForm {
	private static final String myWarning =
		"����� ������� ����������";
	private static final String myPath =
		"�������� ������ � \\gr.log";
	private static final String myFile = "gr.log";

	/**
	 * ������� ����� �����
	 * @param mf ��������
	 * @param e ������� ������
	 * @param description �������� ������
	 * @param warning  ���� true �� ������������ �������  ����������
	 */
	public ErrorForm(JFrame mf, Exception e,
			String description, boolean warning) {
		String message = description;
		if (e != null) {
			message += "\n" + myPath;
            //noinspection EmptyCatchBlock
            try {
				FileWriter fw =  new FileWriter(myFile);
				fw.write(e.toString());
				fw.close();
			} catch (IOException ioe) {}
		}
		if (warning)
			message += myWarning;
		JOptionPane.showMessageDialog(mf, message,
				"������", JOptionPane.ERROR_MESSAGE);
	}
}
