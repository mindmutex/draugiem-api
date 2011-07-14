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
 * Request to add notification to user's home screen.
 * Must subscribe to this service.
 *
 * @author ivarsv
 */
public class NotificationRequest extends BaseHttpRequest {
	public NotificationRequest(String text) {
		this(null, text, null, null);
	}

	public NotificationRequest(String text, String link) {
		this(null, text, link, null);
	}

	public NotificationRequest(String prefix, String text, String link, Long creator) {
		addParam("action", "add_notification");
		if (text.length() > 100
				|| (prefix != null && prefix.length() > 50)
				|| (link !=  null && link.length() > 100)) {
			throw new IllegalArgumentException("exceeded constraints: prefix < 50, link and text < 100");
		}
		addParam("text", text);

		addOptionalParam("prefix", prefix);
		addOptionalParam("link", link);
		addOptionalParam("creator", creator);
	}

	@Override
	public Class<? extends BaseHttpResponse> getResponseHandler() {
		return OKStatusResponse.class;
	}
}
