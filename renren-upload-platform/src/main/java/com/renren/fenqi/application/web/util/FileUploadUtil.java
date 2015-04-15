package com.renren.fenqi.application.web.util;

import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dongyu.cai
 * 上传文件的工具类
 * 与renren-upload-platform工程交互
 */
public class FileUploadUtil {
	
	private static int TIME_OUT = 5000;
	private static final String UPLOAD_URL="http://platform.upload.com/upload";
	private static final String DOWNLOAD_URL="http://platform.upload.com/upload";
//	private static final String EXISTS_URL="http://platform.upload.com/upload/exists";
	
	
	public static String upload(MultipartFile file){
		try {
			Part[] parts = {
					new FilePart("file", new ByteArrayPartSource(file.getOriginalFilename(), file.getBytes())) 
				};
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT);
			PostMethod post = new PostMethod(UPLOAD_URL);
			post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
			String strBody =null;
			try {
				int status = client.executeMethod(post);
				byte[] body = post.getResponseBody();
				if (status == HttpStatus.SC_OK) {
					strBody = new String(body);
				} else {
					strBody=null;
				}
			} catch (Exception e) {
				strBody=null;
			}finally{  
				post.releaseConnection();  
	        }
			return strBody;
		} catch (Exception e) {
			return "[error]"+e.getMessage();
		}
	}
	
	/**下载
	 * 需要自己关闭InputStream
	 * @return
	 */
	public static InputStream download(String remoteFileName){
		HttpClient client = new HttpClient();  
		client.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT);
		GetMethod getDowload = new GetMethod(DOWNLOAD_URL);
		getDowload.getParams().setParameter("fileName", remoteFileName);
        try {  
            client.executeMethod(getDowload);  
            InputStream in = getDowload.getResponseBodyAsStream(); 
            //自己记得关闭in
            return in;
        }catch (Exception e){  
            e.printStackTrace();  
        }finally{
        	getDowload.releaseConnection();  
        }
		return null;
	}
}
