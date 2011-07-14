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
import com.mindmutex.draugiem.requests.ApplicationStatusRequest;
import com.mindmutex.draugiem.requests.ApplicationStatusResponse;
import com.mindmutex.draugiem.requests.ApplicationUsersRequest;
import com.mindmutex.draugiem.requests.ApplicationUsersResponse;

/** 
 * Example code to test draugiem API implementation. 
 * 
 * @author ivarsv
 */
public class ExampleServlet extends HttpServlet {
	private static final long serialVersionUID = 6614901831367588486L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DraugiemHttpClient client = DraugiemContext.getHttpClient();
		request.setAttribute("user", DraugiemContext.getUser());

		ApplicationStatusResponse statusResponse = client.execute(new ApplicationStatusRequest());
		request.setAttribute("status", statusResponse);
		ApplicationUsersResponse usersResponse = 
			client.execute(new ApplicationUsersRequest(false));
		
		request.setAttribute("users", usersResponse.getUsers());
		request.getRequestDispatcher("/example.jsp").forward(request, response);
	}
}
