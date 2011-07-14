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
import java.util.Map;

/**
 * User POJO provides friendly setter and getter methods that extract
 * data from raw HTTP response.
 *
 * @author ivarsv
 */
public class User implements Serializable {
	private static final long serialVersionUID = -7760048500696030185L;

	/**
	 * User unique identifier.
	 */
	private Long uid;

	/**
	 * Raw data provided by HTTP response.
	 */
	private Map<String, Object> data;

	public User(Long uid, Map<String, Object> data) {
		this.uid = uid;
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	private <T> T getDataValue(String name) {
		return (T) data.get(name);
	}

	public Long getId() {
		return uid;
	}

	public String getName() {
		return getDataValue("name");
	}

	public String getSurname() {
		return getDataValue("surname");
	}

	public String getNick() {
		return getDataValue("nick");
	}

	public String getPlace() {
		return getDataValue("place");
	}

	public boolean isAdult() {
		return (Integer) getDataValue("adult") == 1;
	}

	public boolean isMale() {
		return ((String) getDataValue("sex")).equalsIgnoreCase("M");
	}

	public int getAge() {
		Object value = getDataValue("age");
		if (value instanceof Boolean) {
			return 0;
		}
		return (Integer) value;
	}

	public String getIcon() {
		return getImage().replaceAll("/sm_", "/i_");
	}

	public String getImage() {
		return getDataValue("img");
	}

	public String getMediumImage() {
		return getImage().replaceAll("/sm_", "/m_");
	}

	public String getLargeImage() {
		return getImage().replaceAll("/sm_", "/l_");
	}
}
