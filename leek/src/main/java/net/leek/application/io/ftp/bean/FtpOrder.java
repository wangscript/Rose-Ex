package net.leek.application.io.ftp.bean;

/**
 * @author CaiDongyu
 * Ftp订单类
 * 注：就是快递单差不多的，记录远程文件、本地文件等一些参数
 * 这样做：
 * 一是规避了再批量Ftp上传下载时候，使用传统Map存放路径会导致相同key被覆盖
 * 二是可以扩充订单信息，比Map只有key和value记录更多东西
 * 2014/4/20
 */
public class FtpOrder {
	private String number;
	private String goods;
	private String goodsName;
	private String remote;
	private String local;
	
	public FtpOrder() {}
	
	public FtpOrder(String remote,String local) {
		this.remote = remote;
		this.local = local;
	}
	public void copyOrder(FtpOrder order){
	
		this.number = order.number;
		this.remote = order.remote;
		this.local = order.local;
	}
	public String getNumber(){
		return this.number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	public String getRemote() {
		return this.remote;
	}
	public void setRemote(String remote) {
		this.remote = remote;
	}
	public String getLocal() {
		return this.local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getGoodsName() {
		return this.goodsName;
	}
	
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public String getGoods() {
		return this.goods;
	}
	
	public void setGoods(String goods) {
		this.goods = goods;
	}
}
