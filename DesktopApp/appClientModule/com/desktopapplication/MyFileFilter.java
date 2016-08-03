package com.desktopapplication;

import java.io.File;
import java.io.FilenameFilter;

public class MyFileFilter implements FilenameFilter {
	
	@Override
	public boolean accept(File directory, String fileName) {
		if (fileName.endsWith(".feature")) {
            return true;
        }
        return false;
	}
}