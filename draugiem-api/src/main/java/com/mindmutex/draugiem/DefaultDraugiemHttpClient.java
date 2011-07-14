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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mindmutex.draugiem.exceptions.DraugiemException;

/**
 * Default {@link DraugiemHttpClient} implementation
 * that uses commons HTTP client.
 *
 * @author ivarsv
 */
public class DefaultDraugiemHttpClient implements DraugiemHttpClient {
	/**
	 * {@link Logger}.
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Application API key that should never be shared.
	 */
	private String applicationId;

	/**
	 * Authenticated user session API key provided after authentication.
	 */
	private String apiKey;

	/**
	 * Protocol used to connect to API server.
	 */
	public static String API_PROT = "http";

	/**
	 * API server domain name.
	 */
	public static String API_HOST = "api.draugiem.lv";


	@Override
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	@Override
	public String getApplicationId() {
		return applicationId;
	}

	@Override
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * Execute a new request and returns a response instance
	 * defined in request implementation.
	 * <p>
	 * Each request instance overrides {@link BaseHttpRequest#getResponseHandler()} method
	 * to define which response handler should be used.
	 * <p>
	 * By default {@link BaseHttpResponse} is used.
	 *
	 * @param <T> expected return type that extends {@link BaseHttpResponse}
	 * @param request request to execute
	 *
	 * @return response instance
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends BaseHttpResponse> T execute(BaseHttpRequest request) {
		return (T) execute(request, request.getResponseHandler());
	}

	/**
	 * Executes a request and returns specified response handler instance.
	 *
	 * @param <T> specified response handler type
	 * @param request request to execute
	 * @param responseHandler specified response handler class
	 *
	 * @return response handler instance
	 */
	@Override
	public <T extends BaseHttpResponse> T execute(
			BaseHttpRequest request, Class<T> responseHandler) {

		List<NameValuePair> arguments = new ArrayList<NameValuePair>();
		arguments.add(new BasicNameValuePair("app", getApplicationId()));
		if (apiKey != null) {
			arguments.add(new BasicNameValuePair("apikey", apiKey));
		}

		arguments.addAll(request.getParams());

		try {
			URI uri = createRequestURI(arguments);
			Map<String, Object> response = convertResponse(makeHTTPConnection(uri));
			if (response != null) {
				return handleResponse(responseHandler, response);
			}
		} catch (URISyntaxException ex) {
			throw new DraugiemException(ex);
		} catch (ClientProtocolException ex) {
			throw new DraugiemException(ex);
		} catch (IOException ex) {
			throw new DraugiemException(ex);
		}
		return null;
	}

	/**
	 * Create HTTP(s) connection and returns the response as input stream.
	 *
	 * @param uri URI
	 * @return input stream with response data
	 * @throws IOException when fails to execute
	 */
	protected InputStream makeHTTPConnection(URI uri) throws IOException {
		HttpClient client = new DefaultHttpClient();
		HttpResponse httpResponse = client.execute(new HttpGet(uri));

		return httpResponse.getEntity().getContent();
	}

	/**
	 * Converts returned response from HTTP request into an object map. By default
	 * as {@link #createRequestURI(List)} is using JSON this
	 * converts from JSON to object map.
	 *
	 * @param inputStream HTTP response input stream
	 * @return object map
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> convertResponse(InputStream inputStream)  {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(inputStream, HashMap.class);
		} catch (Exception ex) {
			logger.error("Failed to convert response to json:", ex);
		}
		return null;
	}

	/**
	 * Creates URI instance from {@link #API_PROT} and {@value #API_HOST} that will
	 * be used to create HTTP GET connection. By default uses JSON response type.
	 *
	 * @param arguments arguments to append to URI
	 * @return URI
	 *
	 * @throws URISyntaxException when fails to construct a valid URI
	 */
	protected URI createRequestURI(List<NameValuePair> arguments) throws URISyntaxException {
		String queryArgs = URLEncodedUtils.format(arguments, "UTF-8");
		return URIUtils.createURI(API_PROT, API_HOST, -1, "json", queryArgs, null);
	}

	/**
	 * Creates a new instance of response handler and handles the given object map response.
	 *
	 * @param <T> expected response handler type
	 * @param responseHandler response handler class
	 * @param response object map returned from {@link #convertResponse(InputStream)}
	 *
	 * @return response instance
	 */
	protected <T extends BaseHttpResponse> T handleResponse(
			Class<T> responseHandler, Map<String, Object> response) {
		T instance = null;
		try {
			instance = responseHandler.newInstance();
			instance.handleResponse(response);
		} catch (InstantiationException ex) {
			throw new DraugiemException(ex);
		} catch (IllegalAccessException ex) {
			throw new DraugiemException(ex);
		}
		return instance;
	}
}
