package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import ui.ImageSaver;
import utils.MaoLogger;

public class CaptureButtonMouseListener implements MouseListener {

	private static ImageSaver _container;

	public CaptureButtonMouseListener(Path folder, ImageSaver container) {
		_container = container;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		String storagePath = _container.getFolder().toString() + "\\image.jpg";
		File outputfile = new File(storagePath);
		try {
			ImageIO.write(_container.getImage(), "jpg", outputfile);
		} catch (IOException e) {
			MaoLogger._logger.debug("Fail to store the image in " + storagePath);
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
