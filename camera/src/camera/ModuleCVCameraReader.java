package camera;

import java.awt.image.BufferedImage;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import enums.ExecutionStatus;
import module.Module;
import pipeline.PipelineExecutionException;
import utils.CVUtils;

public class ModuleCVCameraReader extends module.Module<BufferedImage> {

	private VideoCapture _webcam;
	Mat _frame = new Mat();

	public ModuleCVCameraReader(Module<BufferedImage> successor) {
		super(successor);
		_webcam = new VideoCapture(0);
	}

	@Override
	public <T> BufferedImage process(T previousOutput) throws PipelineExecutionException {
		assert (_webcam.isOpened());
		if (!_webcam.read(_frame)) {
			throw new PipelineExecutionException(ExecutionStatus.restart, "Webcam image is still empty.");
		}
		if (_successor != null) {
			return (BufferedImage) _successor.process(_frame);
		} else {
			return CVUtils.matToBufferedImage(_frame);
		}
	}

	public boolean isReady() {
		return _webcam.isOpened();
	}

	public void closeCamera() {
		_webcam.release();
	}
}
