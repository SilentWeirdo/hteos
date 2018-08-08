package com.hteos.framework.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
/**
 * @author Mayi
 * @since 2010年7月16日
 * 用于处理下载文件时 response ContentType属性的类
 */
public class IconUtils {
	private static HashMap<String,String> contentTypes;
	
	/**
	 * 根据文件的名称读取该文件的内容类型
	 * @param fileName 文件名称，包含扩展名
	 * @return ContentType
	 */
	public static String getContentType(String fileName){
		if (contentTypes == null){
			initContentTypes();
		}
		String postfix = getFilePostfix(fileName);	//读取文件的扩展名
		String contentType = contentTypes.get(postfix);		//根据文件的扩展名读取内容类型
		if (contentType == null){	//如果文件内容类型是空，给默认值
			contentType = "application/octet-stream";
		}
		return contentType;
	}
	
	/**
	 * 初始化contentType变量
	 */
	private static void initContentTypes(){
		contentTypes = new HashMap<String,String>();
		contentTypes.put("abs", "audio/x-mpeg");
		contentTypes.put("ai", "application/postscript");
		contentTypes.put("aif", "audio/x-aiff");
		contentTypes.put("aifc", "audio/x-aiff");
		contentTypes.put("aiff", "audio/x-aiff");
		contentTypes.put("aim", "application/x-aim");
		contentTypes.put("art", "image/x-jg");
		contentTypes.put("asf", "video/x-ms-asf");
		contentTypes.put("asx", "video/x-ms-asf");
		contentTypes.put("au", "audio/basic");
		contentTypes.put("avi", "video/x-msvideo");
		contentTypes.put("avx", "video/x-rad-screenplay");
		contentTypes.put("bcpio", "application/x-bcpio");
		contentTypes.put("bin", "application/octet-stream");
		contentTypes.put("bmp", "image/bmp");
		contentTypes.put("cdf", "application/x-cdf");
		contentTypes.put("cer", "application/x-x509-ca-cert");
		contentTypes.put("class", "application/java");
		contentTypes.put("cpio", "application/x-cpio");
		contentTypes.put("csh", "application/x-csh");
		contentTypes.put("css", "text/css");
		contentTypes.put("dib", "image/bmp");
		contentTypes.put("dtd", "application/xml-dtd");
		contentTypes.put("dv", "video/x-dv");
		contentTypes.put("dvi", "application/x-dvi");
		contentTypes.put("eps", "application/postscript");
		contentTypes.put("exe", "application/octet-stream");
		contentTypes.put("gif", "image/gif");
		contentTypes.put("gz", "application/x-gzip");
		contentTypes.put("htc", "text/x-component");
		contentTypes.put("htm", "text/html");
		contentTypes.put("html", "text/html");
		contentTypes.put("jar", "application/java-archive");
		contentTypes.put("java", "text/plain");
		contentTypes.put("jnlp", "application/x-java-jnlp-file");
		contentTypes.put("jpe", "image/jpeg");
		contentTypes.put("jpeg", "image/jpeg");
		contentTypes.put("jpg", "image/jpeg");
		contentTypes.put("js", "text/javascript");
		contentTypes.put("jsf", "text/plain");
		contentTypes.put("jspf", "text/plain");
		contentTypes.put("mid", "audio/x-midi");
		contentTypes.put("midi", "audio/x-midi");
		contentTypes.put("mov", "video/quicktime");
		contentTypes.put("mp1", "audio/x-mpeg");
		contentTypes.put("mp2", "audio/x-mpeg");
		contentTypes.put("mp3", "audio/x-mpeg");
		contentTypes.put("mp4", "video/mp4");
		contentTypes.put("mpeg", "video/mpeg");
		contentTypes.put("mpg", "video/mpeg");
		contentTypes.put("ogg", "application/ogg");
		contentTypes.put("pdf", "application/pdf");
		contentTypes.put("png", "image/png");
		contentTypes.put("ps", "application/postscript");
		contentTypes.put("psd", "image/x-photoshop");
		contentTypes.put("qt", "video/quicktime");
		contentTypes.put("rdf", "application/rdf+xml");
		contentTypes.put("rm", "application/vnd.rn-realmedia");
		contentTypes.put("rtf", "application/rtf");
		contentTypes.put("swf", "application/x-shockwave-flash");
		contentTypes.put("txt", "text/plain");
		contentTypes.put("vxml", "application/voicexml+xml");
		contentTypes.put("xht", "application/xhtml+xml");
		contentTypes.put("xhtml", "application/xhtml+xml");
		contentTypes.put("xml", "application/xml");
		contentTypes.put("xsl", "application/xml");
		contentTypes.put("xslt", "application/xslt+xml");
		contentTypes.put("wav", "audio/x-wav");
		contentTypes.put("svg", "image/svg+xml");
		contentTypes.put("vsd", "application/x-visio");
		contentTypes.put("wmv", "video/x-ms-wmv");
		contentTypes.put("zip", "application/zip");
		contentTypes.put("xls", "application/vnd.ms-excel");
		contentTypes.put("ppt", "application/vnd.ms-powerpoint");
		contentTypes.put("doc", "application/vnd.ms-word");
		contentTypes.put("docx", "application/vnd.ms-word");
		contentTypes.put("xlsx", "application/vnd.ms-excel");
		contentTypes.put("pptx", "application/vnd.ms-powerpoint");
	}
	
	/**
	 * 读取文件的扩展名
	 * @param fileName 完整的文件名
	 * @return 文件的扩展名，如果没有扩展名，返回“”
	 */
	public static String getFilePostfix(String fileName){
        String result = "";
        if(fileName.contains("."))
            result = fileName.substring(fileName.lastIndexOf(".") + 1);
        return result;
    }
	
	public static String formatURL(String url){
		if(url != null && url.length() > 0){
			if(url.startsWith("url(")){
				url = url.substring(4);
			}
			if(url.endsWith(");")){
				url = url.substring(0,url.length() - 2);
			}else if(url.endsWith(")")){
				url = url.substring(0,url.length() - 1);
			}
		}
		return url;
	}
	
	public static String getFileNameFromURL(String url){
		if(url != null && url.length() > 0){
			if(url.lastIndexOf("/") > 0){
				url = url.substring(url.lastIndexOf("/") + 1,url.length());
			}
		}
		return url;
	}
	
	public static BufferedImage scale(String url ) throws Exception {
		BufferedImage input = ImageIO.read(new URL(url));
		int width = input.getWidth();
		int height = input.getHeight();
		int scale = 16;
		int scaleWidth = 0;
		int scaleHeight = 0;
		if(width > height){
			scaleHeight = (height * scale) / width;
			scaleWidth = 16;
		}else{
			scaleWidth = (width * scale) / height;
			scaleHeight = 16;
		}
		BufferedImage output = new BufferedImage(scaleWidth, scaleHeight,
				BufferedImage.TRANSLUCENT);
		Image image = input.getScaledInstance(
				output.getWidth(), output.getHeight(), output.getType());
		output.createGraphics().drawImage(image, null, null);
		return output;
	}
	
	public static void main(String[] args) {
		int i = 10 % 3;
		i ++ ;
		i = 10 / i;
		System.out.println(i);
	}
}



