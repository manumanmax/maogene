package face;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.net.URISyntaxException;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import enums.ExecutionStatus;
import module.Module;
import pipeline.PipelineExecutionException;
import utils.CVUtils;

public class ModuleFaceExtractor extends Module<BufferedImage> {
	Mat _image;

	public ModuleFaceExtractor(Module<?> successor) {
		super(successor);
	}

	@Override
	public <T> BufferedImage process(T previousOutput) throws PipelineExecutionException {
		if (previousOutput instanceof BufferedImage) {
			BufferedImage input = (BufferedImage) previousOutput;
			_image = new Mat(input.getHeight(), input.getWidth(), CvType.CV_8UC3);
			_image.put(0, 0, ((DataBufferByte) input.getRaster().getDataBuffer()).getData());
		} else if (previousOutput instanceof Mat) {
			_image = (Mat) previousOutput;
		} else {
			throw new PipelineExecutionException(ExecutionStatus.stop,
					"Image format not compatible with face extraction module");
		}
		if (_image == null) {
			throw new PipelineExecutionException(ExecutionStatus.stop,
					"Empty image not compatible with face extraction module");
		}
		CascadeClassifier faceDetector = new CascadeClassifier();
		boolean res;
		try {
			res = faceDetector
					.load(new File(ModuleFaceExtractor.class.getResource("/haarcascade_frontalface_alt.xml").toURI())
							.getAbsolutePath());
		} catch (URISyntaxException e) {
			throw new PipelineExecutionException(ExecutionStatus.Continue, "Cannot find the cascade classifier");
		}
		if (!res) {
			return CVUtils.matToBufferedImage(_image);
		}
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(_image, faceDetections);
		for (Rect rect : faceDetections.toArray()) {
			Imgproc.rectangle(_image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(0, 255, 0));
		}
		return CVUtils.matToBufferedImage(_image);
	}

}
