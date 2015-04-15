package net.leek.application.io.ftp.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import net.leek.application.collection.CollectionUtil;
import net.leek.application.io.file.FileUtil;
import net.leek.application.io.ftp.FtpManager;
import net.leek.application.io.ftp.bean.FileTransferProgressInfo;
import net.leek.application.io.ftp.bean.FtpOrder;
import net.leek.application.io.ftp.intrfes.IFtpOrderPersistent;
import net.leek.application.text.StringUtil;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * @author CaiDongyu
 * @see SFTP工具类，安全的FTP工具，使用JSch包 0.1.51版
 * @since 2014/4/19
 * 访问级别：友好
 * 对外的话，需要通过FtpUtilManager来获取实例
 */
public class SFtpUtil {

    private Session session = null;
    private List<ChannelSftp> channelList = null;
	
	
	/**
	 * 关闭SFTP通道
	 * @throws IOException
	 */
	public void closeServer() throws Exception {
		if (!CollectionUtil.isEmpty(channelList)) {
			for(ChannelSftp channel:channelList){
				channel.quit();
				channel.disconnect();
			}
        }
        if (session != null) {
            session.disconnect();
        }
	}

	/**
	 * 连接SFTP通道
	 * @param server	host地址
	 * @param port		端口
	 * @param user		ftp用户名
	 * @param password	密码
	 * @throws Exception
	 */
	public void connectServer(String server, int port, String user,String password,int timeout) throws Exception {
		if(session == null || !session.isConnected()){
			JSch jsch = new JSch(); // 创建JSch对象
			session = jsch.getSession(user, server, port); // 根据用户名，主机ip，端口获取一个Session对象
			session.setPassword(password); // 设置密码
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config); // 为Session对象设置properties
			if(timeout > 0){
				session.setTimeout(timeout); // 设置timeout时间
			}
			session.connect(); // 通过Session建立链接
			channelList = new ArrayList<ChannelSftp>();
		}
	}

	/**
	 * 下载文件
	 * @param remote	远程文件
	 * @param local  本地文件
	 * @throws Exception	
	 */
	public void download(FtpOrder order, FileTransferProgressInfo info) throws Exception {
		if(info != null){
			FileTransferProgressMonitor monitor = new FileTransferProgressMonitor(info);
			createChannel().get(order.getRemote(), order.getLocal(), monitor);
		}else{
			//Vector<LsEntry>,虽然反正会被类型擦除，但实际上，元素泛型是LsEntry
			Vector<?> ls = createChannel().ls(order.getRemote());
			if (ls.size() > 0)
				createChannel().get(order.getRemote(), order.getLocal());
			else
				throw new Exception("远程文件remote不存在:" + order.getRemote());
		}
	}
	
	/**
	 * 批量下载
	 * @param orders Ftp快递单列表
	 * @return
	 */
	public List<String> downloadBatch(List<FtpOrder> orders,FileTransferProgressInfo info){
		List<String> errorMsgList = new ArrayList<String>();
		for(FtpOrder order:orders){
			try {
				if(!StringUtil.isBlank(order.getRemote())){
					if(!StringUtil.isBlank(order.getLocal())){
						 download(order, info);
					}else{
						throw new Exception("本地文件路径为空");
					}
				}else{
					throw new Exception("远程文件路径为空");
				}
			} catch (Exception e) {
				errorMsgList.add(e.getMessage());
			}
			
		}
		return errorMsgList;
	}
	

	/**
	 * 获取上传时远程文件的输入流
	 * @param remote	远程文件
	 * @return
	 * @throws Exception
	 */
	public OutputStream getOutPutStream(String remote)throws Exception {
		return createChannel().put(remote);
	}
	
	private ChannelSftp createChannel()throws Exception {
		ChannelSftp channel = (ChannelSftp) session.openChannel("sftp"); // 打开SFTP通道
		channel.connect(); // 建立SFTP通道的连接
		channelList.add(channel);
		return channel;
	}
	
	/**
	 * 获取下载文件的输出流
	 * @param remote	远程文件
	 * @return
	 * @throws Exception
	 */
	public InputStream getInputStream(String remote) throws Exception {
		return createChannel().get(remote);
	}

	/**
	 * 单个上传
	 * @param local	本地文件名(含路径)
	 * @param remote	目标文件名(含路径)
	 * @param info	监控信息的存放处，传空就不监控
	 * @throws Exception
	 */
	public FtpOrder upload(FtpOrder order, FileTransferProgressInfo info, IFtpOrderPersistent persist) throws Exception {
		if (FileUtil.fileExit(order.getLocal())) {
			if(info != null){
				FileTransferProgressMonitor monitor = new FileTransferProgressMonitor(info);
				createChannel().put(order.getLocal(), order.getRemote(), monitor,ChannelSftp.OVERWRITE);
			}else{
				createChannel().put(order.getLocal(), order.getRemote(),ChannelSftp.OVERWRITE);
			}
			if (persist != null) {
				persist.persist(order);
			}
			return order;
		}else{
			throw new Exception("local文件不存在："+order.getLocal());
		}
	}
	
	/**
	 * 单个上传
	 * @param local	本地文件名(含路径)
	 * @param remote	目标文件名(含路径)
	 * @param info	监控信息的存放处，传空就不监控
	 * @throws Exception
	 */
	public void upload(InputStream local, String remote, FileTransferProgressInfo info) throws Exception {
		if(local != null){
			if(info != null){
				FileTransferProgressMonitor monitor = new FileTransferProgressMonitor(info);
				createChannel().put(local, remote, monitor,ChannelSftp.OVERWRITE);
			}else{
				createChannel().put(local, remote,ChannelSftp.OVERWRITE);
			}
		}else{
			throw new Exception("local文件不存在："+local);
		}
	}
	
	/**
	 * 批量上传
	 * @param orders Ftp快递单列表
	 * @return
	 */
	public List<String> uploadBatch(List<FtpOrder> orders, FileTransferProgressInfo info, IFtpOrderPersistent persist){
		List<String> errorMsgList = new ArrayList<String>();
		for(FtpOrder order:orders){
			try {
				if(!StringUtil.isBlank(order.getRemote())){
					if(!StringUtil.isBlank(order.getLocal())){
						order = upload(order, info, persist);
					}else{
						throw new Exception("本地文件路径为空");
					}
				}else{
					throw new Exception("远程文件路径为空");
				}
			} catch (Exception e) {
				errorMsgList.add(e.getMessage());
			}
			
		}
		return errorMsgList;
	}

	public static void main(String[] args) {
		try {
			final String server = "132.42.43.170";
			final int port = 22;
			final String user = "root";
			final String password = "set@123";
//			final String local = "C:\\Users\\Administrator\\Desktop\\jvm\\apache-ant-1.9.2-bin.tar.gz";
			final InputStream local = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\jvm\\apache-ant-1.9.2-bin.tar.gz"));
			final String remote = "/home/apache-ant-1.9.2-bin.tar.gz";
			final int timeout = 0;
			 
//			代码段1
//			mng.uploadOnce(server, port, user, password, local, remote, timeout);
//			mng.download(server, port, user, password, remote, local, timeout);
			
//			代码段2
//			final List<FtpOrder> orders = new ArrayList<FtpOrder>();
//			orders.add(new FtpOrder(remote, local));
//			orders.add(new FtpOrder(remote+".back", local));
			final FileTransferProgressInfo info  = new FileTransferProgressInfo();
			new Thread(new Runnable() {
				@Override
				public void run() {
					SFtpUtilManager mng= FtpManager.getSFtpUtilManager();
					try {
						mng.uploadOnce(server, port, user, password, local, remote, timeout, info);
//						代码段3
//						mng.uploadBatch(server, port, user, password, orders, timeout,info);
						info.locker.lock();
						info.isEnd = true;
						info.locker.unlock();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					boolean goon = true;
					while(goon){
						info.locker.lock();
						System.out.println(info.order.getLocal()+" # "+info.order.getRemote()+" # "+info.fileSize+" # "+info.transfered);
						if(info.isEnd){
							System.out.println("end");
							goon = false;
						}
						info.locker.unlock();
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
