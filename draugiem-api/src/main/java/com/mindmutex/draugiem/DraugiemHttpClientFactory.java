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

import com.mindmutex.draugiem.exceptions.DraugiemException;

/**
 * Factory class for creating {@link DraugiemHttpClient} instances. The created
 * instance is cached and reused if requested the same type.
 *
 * Usage:
 * <pre>
 * DraugiemHttpClient client = DraugiemHttpClientFactory.getHttpClient(getCofiguration());
 * </pre>
 *
 * @author ivarsv
 */
public class DraugiemHttpClientFactory {

	private static DraugiemHttpClient cachedClient = null;

	/**
	 * Returns the {@link DraugiemHttpClient}.
	 *
	 * @param configuration configuration which will contain the class name to use
	 * @return instance
	 */
	public static DraugiemHttpClient getHttpClient(Properties configuration) {
		String className = configuration.getProperty("application.http_client");
		if (cachedClient != null && cachedClient.getClass().getName().equals(className)) {
			return cachedClient;
		}
		try {
			Class<?> clazz = Class.forName(className);
			Object instance = clazz.newInstance();
			if (!(instance instanceof DraugiemHttpClient)) {
				throw new DraugiemException(
					"defined client in configuration must implement DraugiemHttpClient");
			}
			cachedClient = (DraugiemHttpClient) instance;
			cachedClient.setApplicationId(configuration.getProperty("application.api_key"));

			return cachedClient;
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
	}
}
