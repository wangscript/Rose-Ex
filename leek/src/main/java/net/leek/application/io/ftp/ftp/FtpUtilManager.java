package net.leek.application.io.ftp.ftp;


/**
 * @author CaiDongyu
 * Ftp工具的管理类
 * 访问级别：友好
 * 通过此类使用FtpUtil
 * 2014/4/20
 */
public class FtpUtilManager {
	
	private FtpUtil ftpUtil;
	
	public FtpUtilManager() {}
	
	
	/**
	 * 获取ftp工具实例
	 * @return
	 */
	public  FtpUtil getFtpUtil(){
		if(ftpUtil == null){
			ftpUtil = new FtpUtil();
		}
		return ftpUtil;
	}
	
	/**
	 * 单次的上传
	 * @param server
	 * @param port
	 * @param user
	 * @param password
	 * @param local
	 * @param remote
	 * @return
	 * @throws Exception
	 */
	public long uploadOnce(String server, int port, String user,String password,String local, String remote) throws Exception {
		getFtpUtil();
		ftpUtil.connectServer(server, port, user, password);
		long result = ftpUtil.upload(local, remote);
		ftpUtil.closeServer();
		return result;
	}
	
	/**
	 * 单次下载
	 * @param server
	 * @param port
	 * @param user
	 * @param password
	 * @param remote
	 * @param local
	 * @return
	 * @throws Exception
	 */
	public long download(String server, int port, String user,String password,String remote, String local) throws Exception {
		getFtpUtil();
		ftpUtil.connectServer(server, port, user, password);
		long result = ftpUtil.download(remote, local);
		ftpUtil.closeServer();
		return result;
	}
	
}
