package ui;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

public interface ImageSaver {
	public BufferedImage getImage();

	public Path getFolder();

	void setFolder(Path path);
}
