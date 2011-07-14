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

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * Common functionality for all {@link DraugiemHttpClient} requests.
 *
 * @author ivarsv
 */
public class BaseHttpRequest {
	/**
	 * Value pairs provided to HTTP Get connection as query string arguments.
	 */
	private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

	/**
	 * Defines response handler that should be used to handle this request response.
	 * <p>
	 *
	 * The response handler converts raw response data
	 * into development friendly method calls.
	 *
	 * @return extended instance of {@link BaseHttpResponse}
	 */
	public Class<? extends BaseHttpResponse> getResponseHandler() {
		return BaseHttpResponse.class;
	}

	/**
	 * Adds an optional parameter to {@link #nameValuePairs}. This
	 * parameter is added only if not null.
	 *
	 * @param name parameter name
	 * @param value parameter value
	 */
	protected void addOptionalParam(String name, Object value) {
		if (value != null) {
			addParam(name, String.valueOf(value));
		}
	}

	protected void addParam(String name, String value) {
		nameValuePairs.add(new BasicNameValuePair(name, value));
	}

	protected boolean removeParam(String name) {
		for (NameValuePair pair : nameValuePairs) {
			if (pair.getName().equals(name)) {
				nameValuePairs.remove(pair);
				return true;
			}
		}
		return false;
	}

	protected List<NameValuePair> getParams() {
		return nameValuePairs;
	}
}
