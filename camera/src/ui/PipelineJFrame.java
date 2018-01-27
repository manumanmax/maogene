package ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PipelineJFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3671831581716719941L;

	public PipelineJFrame(JPanel panel) {
		super("Smile !");
		setPreferredSize(new Dimension(panel.getHeight(), panel.getWidth()));
		add(panel);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

}
