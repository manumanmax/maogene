package ui;

import java.awt.Button;
import java.nio.file.Path;

import javax.swing.JPanel;

import controller.CaptureButtonMouseListener;

public class NavigationJPanel extends JPanel {
	private static final long serialVersionUID = 1561083375065326722L;
	private static Button _capture = new Button("Capture");

	public NavigationJPanel(Path folder, DatasetJFrame datasetJFrame) {
		super();
		_capture.addMouseListener(new CaptureButtonMouseListener(folder, datasetJFrame));
		add(_capture);
	}

}
