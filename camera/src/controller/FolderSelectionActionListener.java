package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import ui.ImageSaver;

public class FolderSelectionActionListener implements ActionListener {
	private static ImageSaver _folderContainer;

	public FolderSelectionActionListener(ImageSaver folderContainer) {
		_folderContainer = folderContainer;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		JFileChooser theFileChooser = (JFileChooser) actionEvent.getSource();
		String command = actionEvent.getActionCommand();
		if (command.equals(JFileChooser.APPROVE_SELECTION)) {
			_folderContainer.setFolder(theFileChooser.getSelectedFile().toPath());
		}
	}

}
