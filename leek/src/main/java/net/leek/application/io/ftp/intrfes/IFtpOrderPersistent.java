package net.leek.application.io.ftp.intrfes;
import net.leek.application.io.ftp.bean.FtpOrder;

/**
 * @author CaiDongyu
 * @see Ftp快递单信息持久化接口
 * @since 2014/4/29
 */
public interface IFtpOrderPersistent {

	/**
	 * 持久化
	 * @throws Exception
	 */
	public void persist(FtpOrder order)throws Exception;
}
