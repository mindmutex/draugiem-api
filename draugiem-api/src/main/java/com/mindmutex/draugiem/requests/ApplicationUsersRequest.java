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

import com.mindmutex.draugiem.BaseHttpRequest;
import com.mindmutex.draugiem.BaseHttpResponse;

/**
 * API app_friends or app_users request. The choice depends on
 * showFriends status in constructor.
 *
 * To return only IDs call the {@link #showOnlyIds()} before
 * executing request.
 *
 * @author ivarsv
 */
public class ApplicationUsersRequest extends BaseHttpRequest {
	public ApplicationUsersRequest(boolean showFriends) {
		addParam("action", showFriends ? "app_friends" : "app_users");
	}

	public ApplicationUsersRequest(boolean showFriends, int page, int limit) {
		this(showFriends);

		addParam("page", String.valueOf(page));
		addParam("limit", String.valueOf(limit));
	}

	public void showOnlyIds() {
		addParam("show", "ids");
	}

	@Override
	public Class<? extends BaseHttpResponse> getResponseHandler() {
		return ApplicationUsersResponse.class;
	}
}