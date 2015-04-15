package com.renren.fenqi.application.web.controllers.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import com.renren.fenqi.application.web.controllers.constant.Constant;

@Path("")
public class DownloadController {
	
	@Get("")
	public String download(Invocation inv,@Param("fileName")String fileName){
		try {
			inv.getResponse().reset();
			inv.getResponse().setContentType("application/x-download;charset=utf-8");
			inv.getResponse().setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes(),"iso-8859-1"));
			inv.getResponse().flushBuffer();
			OutputStream out = inv.getResponse().getOutputStream();
			File file = new File(Constant.DOWNLOAD_PATH+fileName);
			if(!file.exists()){
				file = new File(Constant.DOWNLOAD_ERROR);
			}
			InputStream reader = new FileInputStream(file);
			byte[] data = new byte[1024];
			int len = reader.read(data, 0, data.length);
			while(len > 0){
				out.write(data, 0, len);
				len = reader.read(data, 0, data.length);
			}
			reader.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				inv.getResponse().getOutputStream().flush();  
				inv.getResponse().getOutputStream().close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return "";
	}
	
	@Get("exists")
	public String exists(Invocation inv,@Param("fileName")String fileName){
		File file = new File(Constant.DOWNLOAD_PATH+fileName);
		if(!file.exists()){
			return "@0";//不存在
		}
		return "@1";//存在
	}
}
