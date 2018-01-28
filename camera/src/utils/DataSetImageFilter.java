package utils;

import java.io.File;
import java.io.FilenameFilter;

public class DataSetImageFilter implements FilenameFilter {
	private String _name;

	public DataSetImageFilter(String name) {
		_name = name;
	}

	@Override
	public boolean accept(File dir, String current) {
		return current.toLowerCase().contains(_name);
	}

}
