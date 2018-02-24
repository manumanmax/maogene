package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import enums.FileExt;
import enums.FileUtils;
import ui.ImageSaver;
import utils.DataSetImageFilter;

public class CaptureButtonMouseListener implements MouseListener {
	public final static Logger _logger = LoggerFactory.getLogger(CaptureButtonMouseListener.class);
	private static ImageSaver _container;

	public CaptureButtonMouseListener(ImageSaver container) {
		_container = container;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// folder prepare
		Path folderPath = _container.getFolder();
		File folder = folderPath.toFile();
		if (!folder.exists()) {
			if (!folder.mkdir()) {
				JOptionPane.showMessageDialog(_container.getComponent(),
						"Coudn't create the folder " + folder.getPath());
			}
		}
		// get next number
		String[] files = folder.list(new DataSetImageFilter(folder.getName()));

		StringBuilder storagePath = new StringBuilder().append(folderPath.toString()).append(FileUtils.FILE_SEPARATOR)
				.append(folder.getName()).append(files.length).append(FileExt.JPG);
		File outputfile = new File(storagePath.toString());
		try {
			ImageIO.write(_container.getImage(), "jpg", outputfile);
		} catch (IOException e) {
			_logger.debug("Fail to store the image in " + storagePath);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
