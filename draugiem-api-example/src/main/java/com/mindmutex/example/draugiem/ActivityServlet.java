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
package com.mindmutex.example.draugiem;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindmutex.draugiem.DraugiemContext;
import com.mindmutex.draugiem.DraugiemHttpClient;
import com.mindmutex.draugiem.requests.ActivityRequest;
import com.mindmutex.draugiem.requests.OKStatusResponse;

/** 
 * Example code to post activity to the user's profile.
 *  
 * @author ivarsv
 */
public class ActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 2912279191769750964L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String text = request.getParameter("text");
		if (text != null) {
			DraugiemHttpClient client = DraugiemContext.getHttpClient();
			OKStatusResponse status = 
				client.execute(new ActivityRequest(text));
			if (!status.isOK()) {
				throw new RuntimeException(status.getStatus());
			}
		}
		response.sendRedirect(request.getContextPath() +  "/Example");
	}

}
