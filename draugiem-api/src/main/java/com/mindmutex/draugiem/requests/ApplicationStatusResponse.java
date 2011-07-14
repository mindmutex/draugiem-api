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

/**
 * Response handler for {@link ApplicationStatusRequest}.
 *
 * @author ivarsv
 */
public class ApplicationStatusResponse extends BaseHttpResponse {
	/**
	 * Extracts statistics value from raw data.
	 *
	 * @param key key name
	 * @return value
	 */
	private Long extractStat(String key) {
		Map<String, Object> status = getRawData("status");

		Object value = status.get(key);
		if (value instanceof Integer) {
			return ((Integer) value).longValue();
		}
		return (Long) value;
	}

	public Long getUsersIn24h() {
		return extractStat("users24");
	}

	public Long getUsers() {
		return extractStat("users");
	}

	public Long getUsersOnline() {
		return extractStat("online");
	}

	public Long getAppRequests() {
		return extractStat("app_rq");
	}

	public Long getActivities() {
		return extractStat("activities");
	}

	public Long getNotifications() {
		return extractStat("notifications");
	}
}
