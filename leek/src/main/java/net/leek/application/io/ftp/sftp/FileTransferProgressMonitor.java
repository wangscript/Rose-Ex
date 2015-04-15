package net.leek.application.io.ftp.sftp;

import net.leek.application.io.ftp.bean.FileTransferProgressInfo;

import com.jcraft.jsch.SftpProgressMonitor;

/**
 * @author CaiDongyu
 * @see 传输文件的进度监控
 * 依赖jsch 0.1.51
 */
public class FileTransferProgressMonitor implements SftpProgressMonitor{
	FileTransferProgressInfo progressInfo;
	
	public FileTransferProgressMonitor(FileTransferProgressInfo progressInfo) {
		this.progressInfo = progressInfo;
	}
	
	@Override
	public boolean count(long count) {
		progressInfo.locker.lock();
		progressInfo.transfered = progressInfo.transfered+count;
//		System.out.println(progressInfo.transfered);
		progressInfo.locker.unlock();
		return true;
	}
	

	@Override
	public void end() {
		//单个的完成不做处理，留在了外边去处理
	}

	@Override
	public void init(int op, String src, String dest, long max) {
			progressInfo.locker.lock();
			progressInfo.order.setLocal(src);
			progressInfo.order.setRemote(dest);
			progressInfo.fileSize = max;
			progressInfo.transfered = 0;
			progressInfo.locker.unlock();
	}
}
