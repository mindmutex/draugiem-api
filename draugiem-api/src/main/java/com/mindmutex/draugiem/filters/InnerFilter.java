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
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Defines inner filter methods that are required to
 * implement in every filter.
 *
 * @author ivarsv
 */
public interface InnerFilter {
	/**
	 * Initialise inner filter with configuration loaded by the
	 * inner filter chain.
	 *
	 * @param config
	 * @return
	 */
	boolean init(Properties config);

	/**
	 * Execute filter.
	 *
	 * @param request HTTP request
	 * @param response HTTP response
	 * @param innerChain inner filter chain
	 *
	 * @throws IOException when filter fails
	 * @throws ServletException when filter fails
	 */
	void doFilter(HttpServletRequest request, HttpServletResponse response,
		FilterChain innerChain) throws IOException, ServletException;
}
