package enums;

public enum FileExt {
	JPG(".jpg");

	private String name = "";

	FileExt(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
