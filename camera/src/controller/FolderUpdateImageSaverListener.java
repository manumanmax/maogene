package controller;

import java.awt.Button;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFileChooser;

import ui.ImageSaver;

public class FolderUpdateImageSaverListener implements PropertyChangeListener {
	private static ImageSaver _folderContainer;
	private static Button _button;

	public FolderUpdateImageSaverListener(ImageSaver imageSaver, Button button) {
		_folderContainer = imageSaver;
		_button = button;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(event.getPropertyName())) {
			File folder = (File) event.getNewValue();
			_folderContainer.setFolder(folder.toPath());
			_button.setLabel("Capture in : " + folder.toString());
		}

	}

}
