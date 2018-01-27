package main;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;

import org.opencv.core.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import camera.ModuleCVCameraReader;
import face.ModuleFaceExtractor;
import pipeline.Pipeline;
import pipeline.PipelineExecutionException;
import ui.DatasetJFrame;
import ui.DatasetJPanel;

public class Start {
	final static Logger _logger = LoggerFactory.getLogger(Start.class);

	public static void main(String[] args) throws PipelineExecutionException, InterruptedException, ExecutionException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// ModuleCameraReader mdr = new ModuleCameraReader(null);// mfe
		ModuleFaceExtractor mfe = new ModuleFaceExtractor(null);
		ModuleCVCameraReader mCVdr = new ModuleCVCameraReader(mfe);// mfe

		DatasetJPanel pipePanel = new DatasetJPanel(mCVdr.process(null));
		@SuppressWarnings("unused")
		DatasetJFrame frame = new DatasetJFrame(pipePanel);
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				_logger.debug("Interruption during sleep.");
			}
			Pipeline<BufferedImage> pipeline = new Pipeline<BufferedImage>(mCVdr);
			pipeline.run();
			pipePanel.replaceImage(pipeline.getOutput());
		}

	}
}
