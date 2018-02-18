package camera;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import enums.ExecutionStatus;
import module.Module;
import pipeline.PipelineExecutionException;
import utils.MaoLogger;

public class ModuleCVCameraReader extends Module<Mat> {

	private VideoCapture _webcam;
	Mat _frame = new Mat();

	public ModuleCVCameraReader(Module<Mat> successor) {
		super(successor);
		_webcam = new VideoCapture(0);
	}

	@Override
	public Mat process() throws PipelineExecutionException {
		assert (_webcam.isOpened());
		try {
			if (!_webcam.read(_frame)) {
				throw new PipelineExecutionException(ExecutionStatus.restart, "Webcam image is still empty.");
			}
			if (_predecessor != null) {
				return (Mat) _predecessor.process();
			} else {
				return _frame;
			}
		} catch (Exception e) {
			MaoLogger._logger.debug("Could'nt read the image from camera");
			return null;
		}
	}

	public boolean isReady() {
		return _webcam.isOpened();
	}

	public void closeCamera() {
		_webcam.release();
	}
}
