package ui;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

public interface ImageSaver {
	public BufferedImage getImage();

	public Path getFolder();

	public void setFolder(Path path);

	public Component getComponent();
}
