/**
 * Copyright (C) 2011 by mindmutex.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mindmutex.draugiem.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.mindmutex.draugiem.DraugiemContext;

/** 
 * Renders JavaScript tags to complete integration with draugiem.lv.
 * 
 * @author ivarsv
 */
public class JavascriptTag extends TagSupport {
	/** 
	 * For serialisation. 
	 */
	private static final long serialVersionUID = -7835502332024883010L;
	
	/** 
	 * URL to the draugiem.js file that should be included in the layout. 
	 */
	public static String JS_URL = "http://ifrype.com/applications/external/draugiem.js";

	/** 
	 * HTML Container used for resizing.
	 */
	private String container;
	
	/** 
	 * Use different domain rather the one that is stored in session data.
	 */
	private String domain; 
	
	/** 
	 * Custom call back URL otherwise defaults  
	 */
	private String callbackUrl; 
	
	public void setContainer(String container) { 
		this.container = container; 
	}
	
	public void setDomain(String domain) { 
		this.domain = domain; 
	}
	
	public String getDomain() { 
		if (domain == null) {
			domain = DraugiemContext.getSession().getDomain(); 
		}
		return domain; 
	}
	
	public void setCallbackUrl(String callbackUrl) { 
		this.callbackUrl = callbackUrl; 
	}
	
	/** 
	 * Creates the default call back URL if none is 
	 * specified. The default is http://[host:port]/callback.html.
	 * 
	 * @return URL
	 */
	public String createCallbackUrlIfRequired() { 
		if (callbackUrl == null) {
			StringBuffer buffer = new StringBuffer();
			
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			
			buffer.append("http" + (request.isSecure() ? "s" : "") + "://");
			buffer.append(request.getServerName());
			
			int port = request.getServerPort();
			if (port != 80) {
				buffer.append(":" + port);
			}
			buffer.append(request.getContextPath());
			buffer.append("/callback.html");
			
			return buffer.toString();
		}
		return callbackUrl; 
	}
	
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter writer = pageContext.getOut(); 
			writer.println(String.format(
				"<script type=\"text/javascript\" src=\"%s\" charset=\"utf-8\"></script>", JS_URL));
			
			writer.println("<script type=\"text/javascript\">");
			if (container != null) { 
				writer.println(String.format("var draugiem_container='%s'", container));
			}
			writer.println(String.format("var draugiem_domain='%s'", getDomain()));
			writer.println(String.format("var draugiem_callback_url='%s'", createCallbackUrlIfRequired()));
			writer.println("</script>");
		} catch (IOException ex) {
			throw new JspException(ex);
		}
		return SKIP_BODY;
	}
}
