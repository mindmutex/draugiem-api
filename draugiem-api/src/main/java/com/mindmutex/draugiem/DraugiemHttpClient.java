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

/**
 * Interface for making HTTP API calls to draugiem.lv API Server.
 *
 * @author ivarsv
 */
public interface DraugiemHttpClient {
	/**
	 * Sets the application id that must never be shared with other people.
	 * @param applicationId application id
	 */
	void setApplicationId(String applicationId);

	/**
	 * Returns the application id.
	 * @return application id
	 */
	String getApplicationId();

	/**
	 * Sets the API key created when new session has been initialised
	 * @param apiKey API key
	 */
	void setApiKey(String apiKey);

	/**
	 * Returns the API key created per session.
	 * @return api key
	 */
	String getApiKey();

	/**
	 * Execute a new request and returns a response instance
	 * defined in request implementation.
	 * <p>
	 * Each request object overrides {@link BaseHttpRequest#getResponseHandler()} method
	 * to define which response handler should be used.
	 * <p>
	 * By default {@link BaseHttpResponse} is used.
	 *
	 * @param <T> expected return type that extends {@link BaseHttpResponse}
	 * @param request request to execute
	 *
	 * @return response instance
	 */
	public <T extends BaseHttpResponse> T execute(BaseHttpRequest request);

	/**
	 * Executes a request and returns specified response handler instance.
	 *
	 * @param <T> specified response handler type
	 * @param request request to execute
	 * @param responseHandler specified response handler class
	 *
	 * @return response handler instance
	 */
	public <T extends BaseHttpResponse> T execute(
		BaseHttpRequest request, Class<T> responseHandler);
}
