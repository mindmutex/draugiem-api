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
package com.mindmutex.draugiem;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mindmutex.draugiem.exceptions.DraugiemException;
import com.mindmutex.draugiem.filters.InnerFilter;
import com.mindmutex.draugiem.filters.InnerFilterChain;
import com.mindmutex.draugiem.filters.OncePerRequestFilter;

/**
 * Web application filter that would load draugiem.properties
 * configuration file and execute the internal filter chain which
 * would do something like authentication, authentication timeout
 * and session to context.
 *
 * The filters are defined in configuration file. If none is specified
 * the defaults are assumed {@link InnerFilterChain#createDefault(Properties)}.
 *
 * The default filter chain is chained even if the inner chain fails.
 *
 * Example:
 * <pre>
 *   <filter>
 *   	<filter-name>DraugiemHttpFilter</filter-name>
 *   	<filter-class>com.mindmutex.draugiem.DraugiemHttpFilter</filter-class>
 *   	<init-param>
 *   		<param-name>configPath</param-name>
 *   		<param-value>META-INF/draugiem.properties</param-value>
 *   </init-param>
 *   </filter>
 *
 *   <filter-mapping>
 *   	<filter-name>DraugiemHttpFilter</filter-name>
 *   	<url-pattern>/*</url-pattern>
 *   </filter-mapping>
 * </pre>
 *
 * @author ivarsv
 */
public class DraugiemHttpFilter extends OncePerRequestFilter {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Default draugiem.properties configuration file path.
	 */
	public static String DEFAULT_CONFIG_PATH = "draugiem.properties";

	/**
	 * Configuration options provided through the properties file.
	 */
	private Properties configuration = null;

	/**
	 * Inner filter chain containing draugiem.lv
	 * library specific filters.
	 */
	private InnerFilterChain innerChain = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String configPath = filterConfig.getInitParameter("configPath");
		if (configPath == null) {
			configPath = DEFAULT_CONFIG_PATH;
		}

		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(configPath);
		if (inputStream == null) {
			throw new ServletException(
				"unable to load configuration file: " + configPath);
		}
		configuration = new Properties();
		try {
			configuration.load(inputStream);
		} catch (IOException ex) {
			throw new ServletException(ex);
		}
		configureFilter();

		logger.info("Initialised");
	}

	/**
	 * Configure the filter before the internal
	 * filter chain is executed.
	 */
	private void configureFilter() {
		// creates inner filter chain
		String filters = configuration.getProperty("application.filters");
		if (filters == null || filters.equalsIgnoreCase("default")) {
			innerChain = InnerFilterChain.createDefault(configuration);
		} else {
			innerChain = new InnerFilterChain(configuration);
			String packageName = getClass().getPackage().getName();

			String[] classNames = filters.split(",");
			for (String className : classNames) {
				className = StringUtils.trim(className);
				if (className.startsWith(".")) {
					className = packageName + className;
				}
				try {
					Class<?> clazz = Class.forName(className);

					Object filter = clazz.newInstance();
					if (!(filter instanceof InnerFilter)) {
						throw new DraugiemException(
							"application.filters must implement inner filter interface");
					}
					logger.info("Adding Filter: {}", className);

					innerChain.addInnerFilter((InnerFilter) filter);
				} catch (ClassNotFoundException ex) {
					throw new DraugiemException(ex);
				} catch (InstantiationException ex) {
					throw new DraugiemException(ex);
				} catch (IllegalAccessException ex) {
					throw new DraugiemException(ex);
				}
			}
		}
	}

	@Override
	public void doInternalFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
		throws IOException, ServletException {

		try {
			innerChain.reset();
			innerChain.doFilter(request, response);

			filterChain.doFilter(request, response);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}
}

