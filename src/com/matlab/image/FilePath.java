package com.matlab.image;

import java.io.File;

/**
 * ����ģʽ  �ļ�·����Ŵ˴�
 * @author Administrator
 *
 */
public class FilePath {
	
	private static FilePath Instance = null;
	
	public static FilePath getInstance() {
		if(Instance == null){
			Instance = new FilePath();
		}
		return Instance;
	}
	
	private FilePath() {
	}
	
	private String uripath = "C:/004.png";
	private String uripath_new = "C:/My_new.png";
	private File file = new File("C:/004.png");
	private File file_new = new File("C:/My_new.png");
	public String getUripath() {
		return uripath;
	}
	public void setUripath(String uripath) {
		this.uripath = uripath;
	}
	public String getUripath_new() {
		return uripath_new;
	}
	public void setUripath_new(String uripath_new) {
		this.uripath_new = uripath_new;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public File getFile_new() {
		return file_new;
	}
	public void setFile_new(File file_new) {
		this.file_new = file_new;
	}
}
