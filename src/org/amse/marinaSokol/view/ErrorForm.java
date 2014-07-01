package org.amse.marinaSokol.view;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;

public class ErrorForm {
	private static final String myWarning =
		"Лучше закрыть приложение";
	private static final String myPath =
		"Описание ошибки в \\gr.log";
	private static final String myFile = "gr.log";

	/**
	 * Создает новую форму
	 * @param mf владелец
	 * @param e причина ошибки
	 * @param description описание ошибки
	 * @param warning  если true то посоветовать закрыть  приложение
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
				"Ошибка", JOptionPane.ERROR_MESSAGE);
	}
}
