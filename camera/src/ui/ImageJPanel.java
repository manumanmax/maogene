package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImageJPanel extends JPanel {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 5002279834197730624L;

	/** Image contained in the panel */
	private static BufferedImage _image;

	public ImageJPanel(BufferedImage image) {
		super();
		_image = image;
		setSize(image.getHeight(null), image.getWidth(null));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(_image, 0, 0, null);
	}

	public void replaceImage(BufferedImage image) {
		_image = image;
		paintComponent(this.getGraphics());
	}

}
