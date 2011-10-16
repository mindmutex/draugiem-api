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
package com.mindmutex.draugiem.support;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindmutex.draugiem.filters.OncePerRequestFilter;

/** 
 * Filter to fix cookie problems in IE and Safari Web Browsers. 
 * 
 * @author ivarsv
 */
public class CookieFixFilter extends OncePerRequestFilter {

	@SuppressWarnings("unchecked") @Override
	public void doInternalFilter(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
		String userAgent = request.getHeader("user-agent");
		if (userAgent.contains("MSIE")) {
			response.setHeader("P3P", 
				"CP=\"IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT\"");
			filterChain.doFilter(request, response);
		} else {
			if (userAgent.contains("Safari") && request.getParameter("dr_auth_code") != null 
					&& request.getParameter("dr_cookie_fix") == null) { 
				
				StringBuffer buffer = new StringBuffer(); 
				buffer.append("<html><head><title>Cookie fix</title></head><body>");
				buffer.append("<form name=\"cookieFix\" method=\"get\" action=\"\">");
				buffer.append("<input type=\"hidden\" name=\"dr_cookie_fix\" value=\"1\" />");
				
				Map<String, String> parameterMap = request.getParameterMap(); 
				for (String name : parameterMap.keySet()) { 
					buffer.append(String.format(
							" <input type=\"hidden\" name=\"%s\" value=\"%s\" />", 
						name, parameterMap.get(name)));
				}
				
				buffer.append("	<noscript><input type=\"submit\" value=\"Continue\" /></noscript>");
				buffer.append("	</form>");
				buffer.append("<script type=\"text/javascript\">document.cookieFix.submit();</script>");
				buffer.append("</body></html>");
				
				response.getWriter().write(buffer.toString());
			}
		}
	}
}
