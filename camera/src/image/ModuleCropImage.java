package image;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import module.Module;
import pipeline.PipelineExecutionException;

public class ModuleCropImage extends Module<Mat> {
	private Module<Mat> _imageProcessor;

	public ModuleCropImage(Module<List<Rect>> predecessor, Module<Mat> imageProcessor)
			throws PipelineExecutionException {
		super(predecessor);
		_imageProcessor = imageProcessor;
	}

	@Override
	public Mat process() throws PipelineExecutionException {
		Mat image = _imageProcessor.process();
		@SuppressWarnings("unchecked")
		List<Rect> rects = (List<Rect>) _predecessor.process();
		if (rects.isEmpty()) {
			return image;
		}
		return new Mat(image, rects.get(0));
	}

}
