package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutionException;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import camera.ModuleCVCameraReader;
import face.ModuleFaceExtractor;
import image.ModuleCropImage;
import image.ModuleHistogramEqualisation;
import pipeline.Pipeline;
import pipeline.PipelineExecutionException;
import ui.DatasetJFrame;
import utils.CVUtils;
import utils.MaoLogger;

public class Start {

	public static void main(String[] args) throws PipelineExecutionException, InterruptedException, ExecutionException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		ModuleCVCameraReader mCVcr = new ModuleCVCameraReader(null);
		ModuleHistogramEqualisation mhe = new ModuleHistogramEqualisation(mCVcr);
		ModuleFaceExtractor mfe = new ModuleFaceExtractor(mhe);
		ModuleCropImage mci = new ModuleCropImage(mfe, mhe);

		DatasetJFrame frame = new DatasetJFrame(CVUtils.matToBufferedImage(mCVcr.process()));
		addAdvancedCloseListener(frame, mCVcr);
		processLoop(mci, frame);
		mCVcr.closeCamera();
	}

	private static void processLoop(module.Module<Mat> module, DatasetJFrame frame)
			throws InterruptedException, ExecutionException {
		while (frame.isDisplayable()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				MaoLogger._logger.debug("Interruption during sleep.");
			}
			Pipeline<Mat> pipeline = new Pipeline<Mat>(module);
			pipeline.run();

			frame.replaceImage(CVUtils.matToBufferedImage(pipeline.getOutput()));
		}
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
