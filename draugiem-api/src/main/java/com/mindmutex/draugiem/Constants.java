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
 * Library constants.
 *
 * @author ivarsv
 */
public class Constants {
	/**
	 * HTTP request authorisation code parameter provided
	 * by draugiem.lv API server.
	 */
	public static String HTTP_GET_AUTH_CODE = "dr_auth_code";

	/**
	 * HTTP request session hash parameter provided by
	 * draugiem.lv API server. Used to validate authorisation
	 * after authentication.
	 */
	public static String HTTP_GET_SESSION_HASH = "session_hash";

	/**
	 * HTTP request user language parameter. If none is specified
	 * assume "LV" as the default.
	 */
	public static String HTTP_GET_LANGUAGE = "language";

	/**
	 * Attribute name in session which will contain
	 * {@link SessionData} instance.
	 */
	public static String REQUEST_AUTH_ATTRIBUTE = "__draugiem_authentication";
}
