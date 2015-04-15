package com.renren.fenqi.application.web.util;

import java.util.ArrayList;

public class HtmlUtil {
	private ArrayList<Html> htmlStack = new ArrayList<Html>();
	
	
	private class Html{
		public String fronTag;//比如:<p>
		public StringBuffer htmlOrText=new StringBuffer();//比如: 123 或者 </b>
		public String backTag;//比如:</p>
		
	}
	
	public HtmlUtil pushTag(String tag,String attr){
		Html html = new Html();
		attr = attr==null?"":" "+attr.trim();
		html.fronTag = "<"+tag+attr+">";
		html.backTag = "</"+tag+">";
		htmlStack.add(html);
		return this;
	}
	
	public HtmlUtil pushHtmlOrText(String content){
		if(htmlStack.size() > 0){
			Html currentHtml = htmlStack.get(htmlStack.size()-1);
			currentHtml.htmlOrText.append(content);
		}else{
			Html firstHtml = new Html();
			firstHtml.htmlOrText.append(content);
			htmlStack.add(firstHtml);
		}
		return this;
	}
	
	@Override
	public String toString() {
		StringBuffer document = new StringBuffer();
		for(Html html:htmlStack){
			document.append(html.fronTag).append(html.htmlOrText);
		}
		for(int i=htmlStack.size()-1;i>=0;i--){
			document.append(htmlStack.get(i).backTag);
		}
		return document.toString();
	}
	
}
