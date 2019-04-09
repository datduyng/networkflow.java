package com.networkflow.apputils;

public class Utils {

	/**
	 * get files extension of a files path
	 * thanks to: https://stackoverflow.com/questions/3571223/how-do-i-get-the-file-extension-of-a-file-in-java
	 * @return
	 */
	public static String getFileExtension(String fileName) {
		String extension = "";

		int i = fileName.lastIndexOf('.');
		if (i >= 0) {
		    extension = fileName.substring(i+1);
		}
		return extension;
	}
}
