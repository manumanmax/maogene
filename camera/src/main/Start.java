package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
		ModuleCVCameraReader mCVcr = new ModuleCVCameraReader(mfe);// mfe

		@SuppressWarnings("unused")
		DatasetJFrame frame = new DatasetJFrame(mCVcr.process(null));
		addAdvancedCloseListener(frame, mCVcr);
		while (frame.isDisplayable()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				MaoLogger._logger.debug("Interruption during sleep.");
			}
			Pipeline<BufferedImage> pipeline = new Pipeline<BufferedImage>(mCVcr);
			pipeline.run();

			frame.replaceImage(pipeline.getOutput());
		}
		mCVcr.closeCamera();
	}

	private static void addAdvancedCloseListener(DatasetJFrame frame, ModuleCVCameraReader mCVcr) {
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("\nClosing it.");
				try {
					mCVcr.closeCamera();
				} catch (Exception e1) {
					MaoLogger._logger.debug("Couldn't close the camera", e1);
				}

				System.exit(0);
			}
		});
	}
}
