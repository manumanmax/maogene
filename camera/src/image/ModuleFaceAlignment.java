package image;

import java.io.File;
import java.net.URISyntaxException;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

import enums.ExecutionStatus;
import module.Module;
import pipeline.PipelineExecutionException;
import ui.Imshow;

public class ModuleFaceAlignment extends module.Module<Double> {
	private final int MAX_EYES = 2;
	private static final boolean DRAW = true;
	Mat _image;
	private static Imshow _display = new Imshow("croped image");

	public ModuleFaceAlignment(Module<Mat> predecessor) throws pipeline.PipelineExecutionException {
		super(predecessor);
	}

	@Override
	public Double process() throws PipelineExecutionException {
		_image = (Mat) _predecessor.process();
		if (_image == null) {
			throw new PipelineExecutionException(ExecutionStatus.stop,
					"Empty image not compatible with face extraction module");
		}
		CascadeClassifier eyesDetector = new CascadeClassifier();
		boolean res;
		try {
			res = eyesDetector.load(
					new File(ModuleFaceAlignment.class.getResource("/haarcascade_eye.xml").toURI()).getAbsolutePath());
		} catch (URISyntaxException e) {
			throw new PipelineExecutionException(ExecutionStatus.Continue, "Cannot find the cascade classifier");
		}
		if (!res) {
			return 0.0;
		}
		MatOfRect eyesDetections = new MatOfRect();
		eyesDetector.detectMultiScale(_image, eyesDetections);
		Rect[] eyesArray = eyesDetections.toArray();

		_display.showImage(_image);
		if (eyesArray.length < MAX_EYES) {
			return 0.0;
		}

		Point center1 = findCenter(eyesArray[0]);
		Point center2 = findCenter(eyesArray[1]);
		Point left = Math.min(center1.x, center2.x) == center1.x ? center1 : center2;
		Point right = left == center1 ? center2 : center1;

		double teta = Math.atan((right.y - left.y) / (right.x - left.x));
		if (teta > Math.PI / 4 || teta < -(Math.PI / 4)) {
			return 0.0;
		}
		return teta;
	}

	private Point findCenter(Rect eye) {
		return new Point((eye.x + eye.width / 2), (eye.y + eye.height / 2));
	}

}
