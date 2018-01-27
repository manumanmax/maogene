package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class PipelineJPanel extends JPanel {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 5002279834197730624L;
	BufferedImage _image;

	public PipelineJPanel(BufferedImage _image) {
		super();
		this._image = _image;
		setSize(_image.getHeight(null), _image.getWidth(null));
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
