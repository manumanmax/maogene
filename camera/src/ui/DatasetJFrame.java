package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;

import javax.swing.JFrame;

public class DatasetJFrame extends JFrame implements ImageContainer {

	/**
	 * 
	 */
	/** Image contained in the panel */
	private static BufferedImage _image;
	private static final long serialVersionUID = 3671831581716719941L;
	private static ImageJPanel _imagePanel;
	private static NavigationJPanel _navPanel;

	public DatasetJFrame(BufferedImage image) {
		super("Smile !");
		_imagePanel = new ImageJPanel(image);
		_navPanel = new NavigationJPanel(Paths.get("C:\\Users\\MANU\\Desktop"), this);
		setLayout(new GridLayout(1, 2));
		setPreferredSize(new Dimension(_imagePanel.getHeight() + _navPanel.getHeight() + 10,
				_imagePanel.getWidth() + _navPanel.getWidth() + 10));
		add(_imagePanel);
		add(_navPanel);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public void replaceImage(BufferedImage image) {
		_image = image;
		_imagePanel.replaceImage(image);
	}

	@Override
	public BufferedImage getImage() {
		return _image;
	}

}
