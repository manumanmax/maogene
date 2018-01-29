package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class DatasetJFrame extends JFrame implements ImageSaver {

	/** Image contained in the panel */
	private static BufferedImage _image;
	private static final long serialVersionUID = 3671831581716719941L;
	private static ImageJPanel _imagePanel;
	private static NavigationJPanel _navPanel;
	private static JSplitPane _mainSplitPane;
	private Path _folder;

	public DatasetJFrame(BufferedImage image) {
		super("Smile !");
		_folder = Paths.get(System.getProperty("user.dir"));
		_imagePanel = new ImageJPanel(image);
		_navPanel = new NavigationJPanel(this);
		_mainSplitPane = new JSplitPane();

		configureAndDispose();
	}

	private void configureAndDispose() {
		setPreferredSize(new Dimension(_imagePanel.getHeight() + _navPanel.getHeight() + 10,
				_imagePanel.getWidth() + _navPanel.getWidth() + 10));
		setLayout(new GridLayout(1, 2));
		add(_mainSplitPane);
		_mainSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		_mainSplitPane.setDividerLocation(_imagePanel.getWidth());
		_mainSplitPane.setLeftComponent(_imagePanel);
		_mainSplitPane.setRightComponent(_navPanel);
		setResizable(true);
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

	@Override
	public void setFolder(Path path) {
		_folder = path;
	}

	@Override
	public Path getFolder() {
		return _folder;
	}

	@Override
	public Component getComponent() {
		return this;
	}

}
