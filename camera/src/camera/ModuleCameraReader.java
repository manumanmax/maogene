package camera;

import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import enums.ExecutionStatus;
import module.Module;
import pipeline.PipelineExecutionException;

public class ModuleCameraReader extends module.Module<BufferedImage> {

	private Webcam _webcam;

	public ModuleCameraReader(Module<BufferedImage> successor) {
		super(successor);
		_webcam = Webcam.getDefault();
		_webcam.setViewSize(WebcamResolution.VGA.getSize());
		_webcam.open();
	}

	@Override
	public <T> BufferedImage process(T previousOutput) throws PipelineExecutionException {
		assert (_webcam != null);
		if (_webcam.getImage() == null) {
			throw new PipelineExecutionException(ExecutionStatus.restart, "Webcam image is still empty.");
		}
		if (_successor != null) {
			return (BufferedImage) _successor.process(_webcam.getImage());
		} else {
			return _webcam.getImage();
		}
	}

	public boolean isReady() {
		return _webcam.getImage() == null;
	}
}
