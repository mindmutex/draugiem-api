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
 * Request to check friendship between to users. Defaults to authenticated user
 * If the other user id is not provided.
 *
 * @author ivarsv
 */
public class FriendshipRequest extends BaseHttpRequest {
	public FriendshipRequest(Long userId, Long otherUserID) {
		addParam("action", "check_friendship");
		addParam("uid", String.valueOf(userId));
		if (otherUserID != null) {
			addParam("uid2", String.valueOf(otherUserID));
		}
	}

	public FriendshipRequest(Long userId) {
		this(userId, null);
	}

	@Override
	public Class<? extends BaseHttpResponse> getResponseHandler() {
		return OKStatusResponse.class;
	}
}

















