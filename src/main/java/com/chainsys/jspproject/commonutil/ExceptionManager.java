package com.chainsys.jspproject.commonutil;

import com.chainsys.jspproject.commonutil.HTMLHelper;
import com.chainsys.jspproject.commonutil.LogManager;

public class ExceptionManager
{
	public static String handleException(Exception e,String source,String message)
	{
		LogManager.logException(e, source);
		message +="Message: "+e.getMessage();
		String errorPage=HTMLHelper.getHTMLTemplate("ERROR", message);
		return errorPage;
	}

}
