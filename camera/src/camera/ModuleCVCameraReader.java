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
		// System.out.println("Write image to desktop");
		// CVUtils.storeImage(_frame, "C:\\Users\\MANU\\Desktop\\", "output", ".png");
		if (_successor != null) {
			return (BufferedImage) _successor.process(_frame);
		} else {
			return CVUtils.matToBufferedImage(_frame);// new BufferedImage(_frame.width(), _frame.height(),
			// BufferedImage.TYPE_BYTE_GRAY);
		}
	}

	public boolean isReady() {
		return _webcam.isOpened();
	}
}
