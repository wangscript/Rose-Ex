package com.renren.fenqi.application.web.util;


/**
 * @author Dongyu.cai
 * 只是代替system.out.print
 */
public class LogUtil {

	public static Object print(Object content){
		System.out.println(content);
		return content;
	}
	
	public static String print(String content){
		System.out.println(content);
		return content;
	}
	
	public static String printParams(Object ...objs){
		StringBuffer buf = new StringBuffer();
		buf.append("参数:| ");
		for(Object obj:objs){
			buf.append(obj+" | ");
		}
		System.out.println(buf.toString());
		return buf.toString();
	}
	
}
