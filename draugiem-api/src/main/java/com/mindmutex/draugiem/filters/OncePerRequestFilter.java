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
package com.mindmutex.draugiem.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link Filter} implementation that executes once per request.
 *
 * <p>
 * Supports only {@link HttpServletRequest} request
 * and {@link HttpServletResponse} response.
 * <p>
 *
 * The filter stores a attribute in session that is used as check
 * to confirm filter already has been executed.
 *
 * @author ivarsv
 */
public abstract class OncePerRequestFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
		throws IOException, ServletException {

		if (!(request instanceof HttpServletRequest)
				|| !(response instanceof HttpServletResponse)) {
			throw new ServletException("OncePerRequestFilter supports only HttpServletRequest and HttpServletResponse");
		}

		String name = getAttributeName();
		if (request.getAttribute(name) != null) {
			filterChain.doFilter(request, response);
		} else {
			request.setAttribute(name, Boolean.TRUE);
			try {
				doInternalFilter((HttpServletRequest) request, (HttpServletResponse) response, filterChain);
			} finally {
				request.removeAttribute(name);
			}
		}
	}

	protected String getAttributeName() {
		return getClass().getName() + ".FILTER";
	}

	public abstract void doInternalFilter(HttpServletRequest request,
		HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException;
}
