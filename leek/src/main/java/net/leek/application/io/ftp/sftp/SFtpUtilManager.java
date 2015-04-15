package net.leek.application.io.ftp.sftp;

import java.io.InputStream;
import java.util.List;

import net.leek.application.io.ftp.bean.FileTransferProgressInfo;
import net.leek.application.io.ftp.bean.FtpOrder;
import net.leek.application.io.ftp.intrfes.IFtpOrderPersistent;

/**
 * @author CaiDongyu
 * SFtp工具的管理类
 * 访问级别友好
 * 通过此类来使用SFtpUtil
 * 2014/4/20
 */
public class SFtpUtilManager {
	private SFtpUtil sftpUtil;
	
	public SFtpUtilManager() {}
	
	/**
	 * 获取sftp工具实例
	 * 安全的ftp工具
	 * @return
	 */
	public SFtpUtil getSFtpUtil(){
		if(sftpUtil == null){
			sftpUtil = new SFtpUtil();
		}
		return sftpUtil;
	}
	
	
	/**
	 * 单次上传
	 * @param server
	 * @param port
	 * @param user
	 * @param password
	 * @param local
	 * @param remote
	 * @param timeout
	 * @param info
	 * @throws Exception
	 */
	FtpOrder uploadOnce(String server, int port, String user, String password, FtpOrder order, int timeout, FileTransferProgressInfo info, IFtpOrderPersistent persist) throws Exception {
		getSFtpUtil();
		sftpUtil.connectServer(server, port, user, password,timeout);
		try {
			order = sftpUtil.upload(order, info, persist);
			sftpUtil.closeServer();
		} catch (Exception e) {
			sftpUtil.closeServer();
			throw new Exception(e);
		}
		return order;
	}
	
	/**
	 * 单次上传
	 * @param server
	 * @param port
	 * @param user
	 * @param password
	 * @param local
	 * @param remote
	 * @param timeout
	 * @param info
	 * @throws Exception
	 */
	public void uploadOnce(String server, int port, String user, String password, InputStream local, String remote, int timeout, FileTransferProgressInfo info) throws Exception {
		getSFtpUtil();
		sftpUtil.connectServer(server, port, user, password,timeout);
		try {
			sftpUtil.upload(local, remote,info);
			sftpUtil.closeServer();
		} catch (Exception e) {
			sftpUtil.closeServer();
			throw new Exception(e);
		}
	}
	
	/**
	 * 批量上传
	 * @param server
	 * @param port
	 * @param user
	 * @param password
	 * @param orders
	 * @param timeout
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List<String> uploadBatch(String server, int port, String user, String password, List<FtpOrder> orders, int timeout, FileTransferProgressInfo info, IFtpOrderPersistent persist) throws Exception{
		getSFtpUtil();
		sftpUtil.connectServer(server, port, user, password,timeout);
		List<String> failedMsgList = null;
		try {
			failedMsgList = sftpUtil.uploadBatch(orders, info, persist);
			sftpUtil.closeServer();
		} catch (Exception e) {
			sftpUtil.closeServer();
			throw new Exception(e);
		}
		return failedMsgList;
	}
	
	/**
	 * 单次下载
	 * @param server
	 * @param port
	 * @param user
	 * @param password
	 * @param remote
	 * @param local
	 * @param timeout
	 * @param info
	 * @throws Exception
	 */
	public void download(String server, int port, String user, String password, FtpOrder order, int timeout, FileTransferProgressInfo info) throws Exception {
		getSFtpUtil();
		sftpUtil.connectServer(server, port, user, password,timeout);
		try {
			sftpUtil.download(order, info);
			sftpUtil.closeServer();
		} catch (Exception e) {
			sftpUtil.closeServer();
			throw new Exception(e);
		}
	}
	
	/**
	 * 批量下载
	 * @param server
	 * @param port
	 * @param user
	 * @param password
	 * @param orders
	 * @param timeout
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List<String> downloadBatch(String server, int port, String user, String password, List<FtpOrder> orders, int timeout, FileTransferProgressInfo info) throws Exception{
		getSFtpUtil();
		sftpUtil.connectServer(server, port, user, password,timeout);
		List<String>  failedMsgList = null;
		try {
			failedMsgList = sftpUtil.downloadBatch(orders,info);
			sftpUtil.closeServer();
		} catch (Exception e) {
			sftpUtil.closeServer();
			throw new Exception(e);
		}
		return failedMsgList;
	}
	
	
}
