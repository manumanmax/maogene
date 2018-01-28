package enums;

public enum FileUtils {
	FILE_SEPARATOR("\\");

	private String name = "";

	FileUtils(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
