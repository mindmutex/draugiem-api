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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mindmutex.draugiem.Constants;
import com.mindmutex.draugiem.DraugiemHttpClient;
import com.mindmutex.draugiem.DraugiemHttpClientFactory;
import com.mindmutex.draugiem.SessionData;
import com.mindmutex.draugiem.exceptions.DraugiemAuthenticationException;
import com.mindmutex.draugiem.requests.AuthorizationRequest;
import com.mindmutex.draugiem.requests.AuthorizationResponse;

/**
 * Filter authenticates current HTTP request.
 *
 * @author ivarsv
 */
public class AuthenticationFilter extends AbstractInnerFilter {
	/**
	 * {@link Logger}.
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain innerChain) throws IOException, ServletException {

		// check if authentication is required.
		if (!authenticationRequired(request)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Authentication not required");
			}
		} else {
			// create authorisation request and store in session
			DraugiemHttpClient client = DraugiemHttpClientFactory.getHttpClient(getConfiguration());

			String authorizationCode = request.getParameter(Constants.HTTP_GET_AUTH_CODE);
			logger.info("Authenticating {}", authorizationCode);

			AuthorizationResponse authResponse = client.execute(new AuthorizationRequest(authorizationCode));
			if (authResponse.isSuccessful()) {
				String sessionHash = request.getParameter(Constants.HTTP_GET_SESSION_HASH);
				String language = request.getParameter(Constants.HTTP_GET_LANGUAGE);

				SessionData sessionData = new SessionData(
					authorizationCode, authResponse.getApiKey(), sessionHash, language);
				sessionData.setUser(authResponse.getUser());

				request.getSession().setAttribute(Constants.REQUEST_AUTH_ATTRIBUTE, sessionData);

				logger.info("Authentication successful");
				if (logger.isDebugEnabled()) {
					logger.debug("SessionData: {}", sessionData);
				}
			} else {
				throw new DraugiemAuthenticationException(String.format("[%d] %s",
					authResponse.getErrorCode(), authResponse.getErrorDescription()));
			}
		}
		innerChain.doFilter(request, response);
	}

	/**
	 * Check whether the authentication is required.
	 *
	 * @param request request
	 * @return status
	 */
	protected boolean authenticationRequired(HttpServletRequest request) {
		HttpSession session = request.getSession();
		SessionData data = (SessionData) session.getAttribute(Constants.REQUEST_AUTH_ATTRIBUTE);

		String authCode = request.getParameter(Constants.HTTP_GET_AUTH_CODE);
		if (data == null && authCode == null) {
			throw new IllegalStateException(
				"expected " + Constants.HTTP_GET_AUTH_CODE + " in http request");
		}
		if (authCode != null
				&& (data == null || !data.getAuthCode().equals(authCode))) {
			return true;
		}
		return false;
	}

}
