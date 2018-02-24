package utils;

import java.awt.image.BufferedImage;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class CVUtils {
	/**
	 * Converts/writes a Mat into a BufferedImage.
	 * 
	 * @param matrix
	 *            Mat of type CV_8UC3 or CV_8UC1
	 * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY
	 */
	public static BufferedImage matToBufferedImage(Mat matrix) {
		BufferedImage bimg;
		if (matrix != null) {
			int cols = matrix.cols();
			int rows = matrix.rows();
			int elemSize = (int) matrix.elemSize();
			byte[] data = new byte[cols * rows * elemSize];
			int type;
			matrix.get(0, 0, data);
			switch (matrix.channels()) {
			case 1:
				type = BufferedImage.TYPE_BYTE_GRAY;
				break;
			case 3:
				type = BufferedImage.TYPE_3BYTE_BGR;
				// bgr to rgb
				byte b;
				for (int i = 0; i < data.length; i = i + 3) {
					b = data[i];
					data[i] = data[i + 2];
					data[i + 2] = b;
				}
				break;
			default:
				return null;
			}
			bimg = new BufferedImage(cols, rows, type);
			bimg.getRaster().setDataElements(0, 0, cols, rows, data);
		} else { // mat was null
			bimg = null;
		}
		return bimg;
	}

	public static void storeImage(Mat img, String folder, String name, String extension) {
		Imgcodecs.imwrite(folder + name + extension, img);

	}

	public static Mat rotate(Point center, Mat input, double radAngle) {
		Mat output = new Mat();
		int len = Math.max(input.cols(), input.rows());
		Mat rotationMatrix = Imgproc.getRotationMatrix2D(center, Math.toDegrees(radAngle), 1.0);
		Imgproc.warpAffine(input, output, rotationMatrix, new Size(len, len));

		return output;
	}

	// source Adrian Rosebrock from pyimagesearch
	public static Mat rotate_bounds(Point center, Mat input, double radAngle) {
		Size inSize = input.size();
		// int len = Math.max(input.cols(), input.rows());
		Mat rotationMatrix = Imgproc.getRotationMatrix2D(center, -Math.toDegrees(radAngle), 1.0);
		double cos = Math.abs(rotationMatrix.get(0, 0)[0]);
		double sin = Math.abs(rotationMatrix.get(0, 1)[0]);
		int nW = (int) ((inSize.height * sin) + (inSize.width * cos));
		int nH = (int) ((inSize.height * cos) + (inSize.width * sin));
		rotationMatrix.put(0, 2, rotationMatrix.get(0, 2)[0] + (nW / 2) - center.x);
		rotationMatrix.put(1, 2, rotationMatrix.get(1, 2)[0] + (nH / 2) - center.y);
		Mat output = new Mat(new Size(nW, nH), input.type());
		Imgproc.warpAffine(input, output, rotationMatrix, new Size(nW, nH));

		return output;
	}
}
