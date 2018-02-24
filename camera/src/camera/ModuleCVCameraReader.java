package camera;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import enums.ExecutionStatus;
import module.Module;
import pipeline.PipelineExecutionException;

public class ModuleCVCameraReader extends Module<Mat> {

	private static VideoCapture _webcam;
	Mat _frame = new Mat();

	public ModuleCVCameraReader(Module<Mat> successor) {
		super(successor);
		if (_webcam == null)
			_webcam = new VideoCapture(0);
	}

	@Override
	public Mat process() throws PipelineExecutionException {
		assert (_webcam.isOpened());
		if (!_webcam.read(_frame)) {
			throw new PipelineExecutionException(ExecutionStatus.restart, "Webcam image is still empty.");
		}
		if (_predecessor != null) {
			return endProcess((Mat) _predecessor.process());
		} else {
			return endProcess(_frame);
		}
	}

	public boolean isReady() {
		return _webcam.isOpened();
	}

	public void closeCamera() {
		_webcam.release();
	}
}
