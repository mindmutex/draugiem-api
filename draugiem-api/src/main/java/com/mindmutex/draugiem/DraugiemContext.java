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

import java.util.Properties;

import com.mindmutex.draugiem.session.SessionHolder;
import com.mindmutex.draugiem.session.ThreadLocalSessionHolder;

/**
 * Provides an easy way to access session data, configuration
 * and HTTP client instance.
 * <p>
 *
 * This class can be used in Servlets or other classes, for example:
 *
 * <pre>
 * String name = DraugiemContext.getUser().getName();
 * ApplicationStatusResponse response
 * 	= DraugiemContext.getHttpClient().execute(new ApplicationStatusRequest());
 * if (response.isSuccessful()) {
 * 	logger.info("{} performed a successful application status request", name);
 * }
 * </pre>
 *
 * @author ivarsv
 */
public class DraugiemContext {
	/**
	 * Defines a way to access currently authenticated session. By default
	 * uses thread local variable.
	 */
	private SessionHolder sessionHolder = new ThreadLocalSessionHolder();

	/**
	 * Any configuration settings.
	 */
	private Properties configuration = new Properties();

	/**
	 * Private variable used to create a singleton.
	 */
	private static DraugiemContext instance;

	private static DraugiemContext getInstance() {
		if (instance == null) {
			instance = new DraugiemContext();
		}
		return instance;
	}

	public static SessionData getSession() {
		return getInstance().getSessionHolder().getSession();
	}

	public static User getUser() {
		return getSession().getUser();
	}

	public static void setSession(SessionData sessionData) {
		getInstance().getSessionHolder().setSession(sessionData);
	}

	public static String getProperty(String name) {
		return getInstance().getConfiguration().getProperty(name);
	}

	public static void setConfiguration(Properties configuration) {
		getInstance().getConfiguration().putAll(configuration);
	}

	public static DraugiemHttpClient getHttpClient() {
		DraugiemHttpClient client =
			DraugiemHttpClientFactory.getHttpClient(getInstance().getConfiguration());;
		client.setApiKey(getSession().getSessionKey());
		return client;
	}

	public void setSessionHolder(SessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;
	}

	public SessionHolder getSessionHolder() {
		return sessionHolder;
	}

	public Properties getConfiguration() {
		return configuration;
	}
}