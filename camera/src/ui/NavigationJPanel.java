package ui;

import java.awt.Button;

import javax.swing.JFileChooser;
import javax.swing.JSplitPane;

import controller.CaptureButtonMouseListener;
import controller.FolderSelectionActionListener;

public class NavigationJPanel extends JSplitPane {
	private static final long serialVersionUID = 1561083375065326722L;
	private static Button _captureButton = new Button("Capture");
	private static JFileChooser _fileChooser = new JFileChooser();

	public NavigationJPanel(DatasetJFrame datasetJFrame) {
		super();
		_captureButton.addMouseListener(new CaptureButtonMouseListener(datasetJFrame));
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setDividerLocation(100);
		setLeftComponent(_captureButton);
		_fileChooser.setCurrentDirectory(new java.io.File("."));
		_fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		_fileChooser.addActionListener(new FolderSelectionActionListener(datasetJFrame));
		setRightComponent(_fileChooser);
	}

}
