package net.leek.application.io.ftp.ftp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

/**
 * @author caidongyu
 * 仅仅支持jdk 1.6,因为1.7中FtpClient类大改
 * ftp远程工具类
 * 访问级别：友好
 * 对外的话，需要通过FtpUtilManager来获取实例
 * 2014/4/20 (改)
 */
class FtpUtil {

	private FtpClient ftpClient;

	/**
	 * connectServer 连接ftp服务器
	 * 
	 * @param password
	 *            密码
	 * @param user
	 *            登陆用户
	 * @param server
	 *            服务器地址
	 */
	public void connectServer(String server, int port, String user,String password) throws Exception {
		// server：FTP服务器的IP地址；user:登录FTP服务器的用户名
		// password：登录FTP服务器的用户名的口令；path：FTP服务器上的路径
		ftpClient = new FtpClient(server, port);
		ftpClient.login(user, password);
		// 用2进制上传、下载
		ftpClient.binary();
	}

	/**
	 * upload 上传文件
	 * 
	 * @throws java.lang.Exception
	 * @return -1 文件不存在 -2 文件内容为空 >0 成功上传，返回文件的大小
	 * @param local
	 *            上传的文件
	 * @param remote
	 *            上传后的新文件名
	 */
	public long upload(String local, String remote) throws Exception {
		long result = 0;
		TelnetOutputStream os = null;
		FileInputStream is = null;
		try {
			java.io.File file_in = new java.io.File(local);
			if (!file_in.exists())
				return -1;
			if (file_in.length() == 0)
				return -2;
			os = ftpClient.put(remote);
			result = file_in.length();
			is = new FileInputStream(file_in);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
		} finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		}
		return result;
	}

	
	/**
	 * 获取下载文件的输入流
	 * @param remote	远程文件
	 * @return
	 * @throws Exception
	 */
	public TelnetInputStream getInputStream(String remote) throws Exception {
		try {
			return ftpClient.get(remote);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 下载文件
	 * @param remote	下载到本地的文件，如果是文件名，需要先调用changeDir来打开目录
	 * @param local	需要下载的源文件名(含路径)
	 * @return
	 * @throws Exception
	 */
	public long download(String remote, String local) throws Exception {
		long result = 0;
		TelnetInputStream is = null;
		FileOutputStream os = null;
		try {
			is = ftpClient.get(local);
			java.io.File outfile = new java.io.File(remote);
			os = new FileOutputStream(outfile);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
				result = result + c;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		}
		return result;
	}
	
	/**
	 * 取得某个目录下的所有文件列表
	 * @param remoteDir 远程目录
	 */
	public List<String> getFileList(String remoteDir) {
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader dis = new BufferedReader(new InputStreamReader(ftpClient.nameList(remoteDir)));
			String filename = "";
			while ((filename = dis.readLine()) != null) {
				list.add(filename);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * closeServer 断开与ftp服务器的链接
	 * 
	 * @throws java.io.IOException
	 */
	public void closeServer() throws IOException {
		try {
			if (ftpClient != null) {
				ftpClient.closeServer();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 改变访问目录
	 * @param remoteDir	远程路径
	 * @return
	 */
	public boolean changeDir(String remoteDir){
		try {
			ftpClient.cd(remoteDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public static void main(String[] args) throws Exception {
		FtpUtil ftp = new FtpUtil();
		try {
			// 连接ftp服务器
			ftp.connectServer("132.42.43.170", 22, "root", "set@123");
//			ftp.changeDir("/cattsoft_ftp");
			// /** 上传文件到 info2 文件夹下 */
			// System.out.println("filesize:"+ftp.upload("f:/download/Install.exe")+"字节");
			/** 取得info2文件夹下的所有文件列表,并下载到 E盘下 */
//			ftp.download("CDBJKDWAL010011307160000000.011.MD5", "E:/"+ "test.txt");
			 List<String> list = ftp.getFileList("/home");
			 for (int i=0;i<list.size();i++)
			 {
			 String filename = (String)list.get(i);
			 System.out.println(filename);
			 // ftp.download(filename,"E:/"+filename);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ftp.closeServer();
		}
	}

}
