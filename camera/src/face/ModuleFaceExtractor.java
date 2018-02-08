package face;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

import enums.ExecutionStatus;
import module.Module;
import pipeline.PipelineExecutionException;

public class ModuleFaceExtractor extends Module<List<Rect>> {
	private Mat _image;

	public ModuleFaceExtractor(Module<Mat> predecessor) throws PipelineExecutionException {
		super(predecessor);
		_image = predecessor.process();
	}

	@Override
	public List<Rect> process() throws PipelineExecutionException {
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
			return new ArrayList<Rect>();
		}
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(_image, faceDetections);
		return faceDetections.toList();
	}

}
