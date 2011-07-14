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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mindmutex.draugiem.BaseHttpResponse;
import com.mindmutex.draugiem.User;

/**
 * Response handler for {@link UserDataRequest}.
 *
 * @author ivarsv
 */
public class UserDataResponse extends BaseHttpResponse {

	private List<User> users = new ArrayList<User>();

	@SuppressWarnings("unchecked") @Override
	public void handleResponse(Map<String, Object> response) {
		super.handleResponse(response);

		if (isSuccessful()) {
			Map<String, Object> usersData = getRawData("users");
			for (String uid : usersData.keySet()) {
				users.add(new User(Long.valueOf(uid), (Map<String, Object>) usersData.get(uid)));
			}
		}
	}

	public List<User> getUsers() {
		return users;
	}
}
