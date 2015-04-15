package net.leek.application.io.ftp.bean;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author CaiDongyu
 * @see 记录文件传输过程中，快递货物(文件)的信息
 * Ftp、Upload等
 * 支持同步
 */
public class FileTransferProgressInfo {
	public Lock locker = new ReentrantLock(true);//公平的锁
	public long fileSize;//文件大小
	public long transfered = 0;//已经传输的数据量
	public boolean isEnd = false;//是否传送结束
	public FtpOrder order = new FtpOrder();//快递单
	public int timeout = 10;//超时，默认10次未响应就超时
}
