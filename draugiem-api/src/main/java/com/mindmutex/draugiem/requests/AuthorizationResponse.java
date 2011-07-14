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
package com.mindmutex.draugiem.requests;

import java.util.Map;

import com.mindmutex.draugiem.BaseHttpResponse;
import com.mindmutex.draugiem.User;

/**
 * {@link AuthorizationRequest} response handler.
 *
 * @author ivarsv
 */
public class AuthorizationResponse extends BaseHttpResponse {
	/**
	 * Authenticated user data.
	 */
	private User user;

	public Integer getUserId() {
		return getRawData("uid");
	}

	public String getApiKey() {
		return getRawData("apikey");
	}

	public String getLanguage() {
		return getRawData("language");
	}

	public Integer getInviter() {
		return getRawData("inviter");
	}

	public String getInviterExtra() {
		return getRawData("invite_extra");
	}

	public User getUser() {
		return user;
	}

	@SuppressWarnings("unchecked") @Override
	protected void handleResponse(Map<String, Object> response) {
		super.handleResponse(response);
		if (isSuccessful()) {
			Map<String, Object> users = getRawData("users");
			for (String uid : users.keySet()) {

				user = new User(
					Long.valueOf(uid), (Map<String, Object>) users.get(uid));
			}
		}
	}
}
