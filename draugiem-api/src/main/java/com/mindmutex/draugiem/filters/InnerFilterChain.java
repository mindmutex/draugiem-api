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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;

/**
 * {@link FilterChain} implementation for {@link InnerFilter} instances.
 *
 * @author ivarsv
 */
public class InnerFilterChain implements FilterChain {

	/**
	 * {@link Logger}.
	 */
	private Log logger = LogFactory.getLog(getClass());

	/**
	 * List of {@link InnerFilter} instances.
	 */
	private List<InnerFilter> filters = new ArrayList<InnerFilter>();

	/**
	 * This instance iterator.
	 */
	private Iterator<InnerFilter> currentIterator = null;

	/**
	 * Configuration properties.
	 */
	private Properties configuration;

	/**
	 * Creates default {@link InnerFilter} instances.
	 *
	 * @see AuthenticationFilter
	 * @see AuthenticationTimeoutFilter
	 * @see SessionToContextFilter
	 *
	 * @param configuration configuration
	 * @return default filters
	 */
	public static InnerFilterChain createDefault(Properties configuration) {
		InnerFilterChain chain = new InnerFilterChain(configuration);
		chain.addInnerFilter(new AuthenticationTimeoutFilter());
		chain.addInnerFilter(new AuthenticationFilter());
		chain.addInnerFilter(new SessionToContextFilter());

		return chain;
	}

	public InnerFilterChain(Properties configuration) {
		this.configuration = configuration;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		doFilter((HttpServletRequest)  request, (HttpServletResponse) response);
	}

	/**
	 * Initialised {@link #currentIterator} if not initialised and executes the next
	 * filter in order.
	 *
	 * @param request HTTP request
	 * @param response HTTP response
	 *
	 * @throws IOException when something goes wrong within filter
	 * @throws ServletException when something goes wrong within filter
	 */
	private void doFilter(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (currentIterator == null) {
			currentIterator = filters.iterator();
		}
		if (currentIterator.hasNext()) {
			InnerFilter filter = currentIterator.next();
			if (logger.isDebugEnabled()) {
				logger.debug(
					"executing inner filter: " + filter.getClass().getName());
			}
			if (filter.init(configuration)) {
				filter.doFilter(request, response, this);
			}
		}
	}

	public void reset() {
		currentIterator = null;
	}

	public void addInnerFilter(InnerFilter filter) {
		filters.add(filter);
	}
}
