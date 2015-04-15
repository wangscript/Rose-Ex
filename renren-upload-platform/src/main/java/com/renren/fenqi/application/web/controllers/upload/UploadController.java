package com.renren.fenqi.application.web.controllers.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.web.multipart.MultipartFile;

import com.renren.fenqi.application.web.compoment.model.FileDefinition;
import com.renren.fenqi.application.web.controllers.constant.Constant;
import com.renren.fenqi.application.web.util.CollectionUtil;
import com.renren.fenqi.application.web.util.FileUtil;
import com.renren.fenqi.application.web.util.StringUtil;

@Path("")
public class UploadController {
	
	public static void main(String[] args) {
		/*String fileName = "c:\\/\\/合同审核.doc";
		int index = fileName.lastIndexOf("\\");
		if(index >= 0){
			fileName = fileName.substring(index+1);
		}
		index = fileName.lastIndexOf("/");
		if(index >= 0){
			fileName = fileName.substring(index+1);
		}
		System.out.println(fileName);*/
		

		Pattern ip = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
		Matcher m = ip.matcher(" 	20086594714集127.1.1.1体户口迁入申请.doc_127.0.0.1_2");
		while(m.find()){
			System.out.println(m.group());
		}
	}
	
	@Get("index")
	public String index(Invocation inv){
		File dir = new File(Constant.UPLOAD_PATH);
		File[] files = dir.listFiles();
		ArrayList<FileDefinition> list = new ArrayList<FileDefinition>();
		if(!CollectionUtil.isEmpty(files)){
			for(File file:files){
				FileDefinition df = new FileDefinition();
				df.setFileName(file.getName());
				df.setPath(Constant.UPLOAD_PATH);
				df.setModifyTime(new Date(file.lastModified()));
				list.add(df);
			}
		}
		inv.addModel("files", list);
		return "index.jsp";
	}
	
	@Post("")
	public String upload(Invocation inv,@Param("file")MultipartFile file){
		//fileName = fileName_ip_index	如：某某合同.doc_192.168.1.1_1
		String fileName = file.getOriginalFilename();
		if(StringUtil.isBlankTrim(fileName)){
			return "@[error]no file name";
		}
		
		int index = fileName.lastIndexOf("\\");
		if(index >= 0){
			fileName = fileName.substring(index+1);
		}
		index = fileName.lastIndexOf("/");
		if(index >= 0){
			fileName = fileName.substring(index+1);
		}
		String ip = inv.getRequest().getRemoteAddr();
		fileName = fileName+"_"+ip+"_";
		int tryTimes = 1;//尝试保存10次，如果都出现重名，就返回失败
		File localFile = null;
		Exception error = null;
		for(;tryTimes<10;tryTimes++){
			StringBuffer buf = new StringBuffer();
			String absulate = buf.append(Constant.UPLOAD_PATH).append(fileName).append(tryTimes).toString();
			if(FileUtil.fileExists(buf.toString())){
				continue;
			}
			synchronized (absulate) {
				if(FileUtil.fileExists(buf.toString())){
					continue;
				}
				localFile = new File(buf.toString());
				try {
					FileOutputStream writer = new FileOutputStream(localFile);
					InputStream reader = file.getInputStream();
					byte[] data = new byte[1024];
					int len = reader.read(data, 0, data.length);
					while(len > 0){
						writer.write(data, 0, len);
						len = reader.read(data, 0, data.length);
					}
					reader.close();
					writer.close();
					error = null;
					break;
				} catch (IOException e) {
					e.printStackTrace();
					error = e;
				}
			}
		}
		
		if(error != null){
			return "@[error]"+error.getMessage();
		}

		
		if(localFile == null){
			return "@[error]file name exists";
		}
		
		return "@"+localFile.getName();
	}
}
