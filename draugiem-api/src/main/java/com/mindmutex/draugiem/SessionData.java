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

import java.io.Serializable;

/**
 * POJO to store authenticated user and provided HTTP get
 * parameters required for user session validation.
 *
 * This object instance is stored in session after authentication.
 *
 * @author ivarsv
 */
public class SessionData implements Serializable {
	private static final long serialVersionUID = 7607864556642678921L;

	/**
	 * Authentication code provided by remote server.
	 */
	private String authCode;

	/**
	 * Authentication user session API key.
	 */
	private String sessionKey;

	/**
	 * Authenticated user language if any (otherwise defaults to "LV").
	 */
	private String language;

	/**
	 * Session hash provided by remote server used to check session validity.
	 */
	private String sessionHash;

	/**
	 * Authenticated user.
	 */
	private User user;

	/**
	 * Time in milliseconds used to check when authenticated
	 * session has been last validated.
	 */
	private Long lastModified;

	public SessionData(String authCode,
		String sessionKey, String sessionHash, String language) {
		this.authCode = authCode;
		this.sessionKey = sessionKey;
		this.sessionHash = sessionHash;

		this.language = language == null ? "lv" : language;
		updateLastModified();
	}

	public String getAuthCode() {
		return authCode;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public String getLanguage() {
		return language;
	}

	public String getSessionHash() {
		return sessionHash;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void updateLastModified() {
		lastModified = System.currentTimeMillis();
	}

	public Long getLastModifiedInMillis() {
		return lastModified;
	}
}
