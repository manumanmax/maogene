package main;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;

import org.opencv.core.Core;

import camera.ModuleCVCameraReader;
import face.ModuleFaceExtractor;
import pipeline.Pipeline;
import pipeline.PipelineExecutionException;
import ui.DatasetJFrame;
import utils.MaoLogger;

public class Start {

	public static void main(String[] args) throws PipelineExecutionException, InterruptedException, ExecutionException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// ModuleCameraReader mdr = new ModuleCameraReader(null);// mfe
		ModuleFaceExtractor mfe = new ModuleFaceExtractor(null);
		ModuleCVCameraReader mCVdr = new ModuleCVCameraReader(mfe);// mfe

		@SuppressWarnings("unused")
		DatasetJFrame frame = new DatasetJFrame(mCVdr.process(null));
		while (frame.isDisplayable()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				MaoLogger._logger.debug("Interruption during sleep.");
			}
			Pipeline<BufferedImage> pipeline = new Pipeline<BufferedImage>(mCVdr);
			pipeline.run();
			frame.replaceImage(pipeline.getOutput());
		}

	}
}
