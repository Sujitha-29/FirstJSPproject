package com.chainsys.jspproject.commonutil;

public class HTMLHelper 
{
	public static String getHTMLTemplate(String title,String body)
	{
		String htmloutput="<html><head><title>"+title+"</title></head><body>";
		htmloutput +="<div>"+body+"</div></body></html>";
		return  htmloutput;
	}

}
