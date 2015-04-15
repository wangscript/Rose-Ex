package net.leek.application.io.ftp;

import net.leek.application.io.ftp.ftp.FtpUtilManager;
import net.leek.application.io.ftp.sftp.SFtpUtilManager;

/**
 * @author CaiDongyu
 * @since 2014/4/20
 * Ftp管理类
 * 用来获取ftp或sftp的工具管理器
 * 
 * @see  Ftp不支持批量上传下载
 * @see  SFtp支持单次上传下载、批量上传下载,速度比ftp慢
 */
public class FtpManager {

	private static FtpUtilManager ftpManager;
	private static SFtpUtilManager sftpManager;
	
	/**
	 * 普通的Ftp
	 * @return
	 */
	public static FtpUtilManager getFtpUtilManager(){
		if(ftpManager == null){
			ftpManager = new FtpUtilManager();
		}
		return ftpManager;
	}
	/**
	 * 安全的Ftp
	 * @return
	 */
	public static SFtpUtilManager getSFtpUtilManager(){
		if(sftpManager == null){
			sftpManager = new SFtpUtilManager();
		}
		return sftpManager;
	}
}
