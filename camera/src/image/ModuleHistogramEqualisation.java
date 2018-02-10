package image;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import module.Module;
import pipeline.PipelineExecutionException;

public class ModuleHistogramEqualisation extends Module<Mat> {

	public ModuleHistogramEqualisation(Module<Mat> predecessor) {
		super(predecessor);
	}

	@Override
	public Mat process() throws PipelineExecutionException {
		Mat image = (Mat) _predecessor.process();
		Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
		if ((image.type() == (((0) & ((1 << 3) - 1)) + (((1) - 1) << 3))))
			Imgproc.equalizeHist(image, image);

		return image;
	}

}
