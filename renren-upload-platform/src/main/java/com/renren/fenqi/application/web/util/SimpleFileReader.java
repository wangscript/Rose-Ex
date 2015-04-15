package com.renren.fenqi.application.web.util;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class SimpleFileReader {
	
	public  static String[] splitByFlag(MultipartFile file,String flag) throws IOException{
		byte[] data = file.getBytes();
		return new String(data).split(flag);
	}
}
