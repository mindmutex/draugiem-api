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

import java.util.Map;

/**
 * Common functionality for all response handlers. By default handles any
 * errors and stores the raw data in instance.
 *
 * @author ivarsv
 */
public class BaseHttpResponse {
	/**
	 * Is successful or failure. Checks if error is defined as key.
	 */
	private boolean successful;

	/**
	 * Description of the error if any.
	 */
	private String errorDescription;

	/**
	 * Error code if request has not been successful.
	 */
	private int errorCode;

	/**
	 * Raw response data.
	 */
	private Map<String, Object> rawData;


	public boolean isSuccessful() {
		return successful;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public Map<String, Object> getRawData() {
		return rawData;
	}

	@SuppressWarnings("unchecked")
	public <T> T getRawData(String key) {
		return (T) rawData.get(key);
	}

	/**
	 * Check if any errors are present in response and if positive store
	 * error code and description. The successful state is changed to false.
	 *
	 * @param response response from request
	 */
	@SuppressWarnings("unchecked")
	protected void handleResponse(Map<String, Object> response) {
		Map<String, Object> error = (Map<String, Object>) response.get("error");
		if (error != null) {
			errorDescription = (String) error.get("description");
			errorCode = (Integer) error.get("code");
		}
		successful = (error == null);
		rawData = response;
	}
}

