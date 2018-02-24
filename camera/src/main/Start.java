package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutionException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import camera.ModuleCVCameraReader;
import enums.ExecutionStatus;
import face.ModuleFaceExtractor;
import image.ModuleCropImage;
import image.ModuleFaceAlignment;
import pipeline.PipelineExecutionException;
import ui.DatasetJFrame;
import utils.CVUtils;

public class Start {
	public final static Logger _logger = LoggerFactory.getLogger(Start.class);

	public static void main(String[] args) throws PipelineExecutionException, InterruptedException, ExecutionException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		ModuleCVCameraReader mCVcr = new ModuleCVCameraReader(null);
		// ModuleHistogramEqualisation mhe = new ModuleHistogramEqualisation(mCVcr);
		ModuleFaceExtractor mfe = new ModuleFaceExtractor(mCVcr);
		ModuleCropImage mci = new ModuleCropImage(mfe, mCVcr);
		ModuleFaceAlignment mfa = new ModuleFaceAlignment(mci);
		DatasetJFrame frame = new DatasetJFrame(CVUtils.matToBufferedImage(mCVcr.process()));
		addAdvancedCloseListener(frame, mCVcr);
		processLoop(mCVcr, mfa, frame);
		mCVcr.closeCamera();
	}

	private static void processLoop(module.Module<Mat> module, ModuleFaceAlignment mfa, DatasetJFrame frame)
			throws InterruptedException, ExecutionException {
		while (frame.isDisplayable()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				_logger.debug("Interruption during sleep.");
			}

			double teta;
			try {
				teta = mfa.process();
				Mat output = (Mat) mfa.initModule().getOutput();
				output = CVUtils.rotate_bounds(new Point(output.cols() / 2, output.rows() / 2), output, teta);
				frame.replaceImage(CVUtils.matToBufferedImage(output));
			} catch (PipelineExecutionException e) {
				_logger.debug("Loading...");
				if (e._status != ExecutionStatus.stop) {
					frame.replaceImage(CVUtils.matToBufferedImage((Mat) mfa.initModule().getOutput()));
				}
			}
		}
	}

	private static void addAdvancedCloseListener(DatasetJFrame frame, ModuleCVCameraReader mCVcr) {
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Closing camera.");
				try {
					mCVcr.closeCamera();
				} catch (Exception e1) {
					_logger.debug("Couldn't close the camera", e1);
				}

				System.exit(0);
			}
		});
	}
}
