package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import ui.ImageContainer;
import utils.MaoLogger;

public class CaptureButtonMouseListener implements MouseListener {

	private Path _folder;
	private ImageContainer _imageContainer;

	public CaptureButtonMouseListener(Path folder, ImageContainer container) {
		_folder = folder;
		_imageContainer = container;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		String storagePath = _folder.toString() + "\\image.jpg";
		File outputfile = new File(storagePath);
		try {
			ImageIO.write(_imageContainer.getImage(), "jpg", outputfile);
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
