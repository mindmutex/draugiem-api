package com.mindmutex.draugiem.filters;

import java.io.IOException;
import java.util.Properties;

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
import com.mindmutex.draugiem.requests.OKStatusResponse;
import com.mindmutex.draugiem.requests.SessionCheckRequest;

/**
 * Filter to check if current authenticated user session is expired or needs
 * validating by calling session hash validate function.
 *
 * @author ivarsv
 */
public class AuthenticationTimeoutFilter extends AbstractInnerFilter {
	/**
	 * {@link Logger}.
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Timeout value the current authenticated session must exceed.
	 * This variable is initialised from the configuration file.
	 */
	private Long sessionTimeout;

	@Override
	public boolean init(Properties configuration) {
		boolean status = super.init(configuration);
		sessionTimeout = Long.valueOf(
			getProperty("session.timeout")) * 1000;
		return status;
	}
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain innerChain)
		throws IOException, ServletException {

		HttpSession httpSession = request.getSession();

		String attributeName = Constants.REQUEST_AUTH_ATTRIBUTE;
		SessionData data = (SessionData) httpSession.getAttribute(attributeName);
		if (data != null) {
			if (System.currentTimeMillis() - data.getLastModifiedInMillis() > sessionTimeout) {
				// session check request for validation
				DraugiemHttpClient client = DraugiemHttpClientFactory.getHttpClient(getConfiguration());
				client.setApiKey(data.getSessionKey());

				OKStatusResponse status = client.execute(new SessionCheckRequest(data.getSessionHash()));
				if (logger.isDebugEnabled()) {
					logger.debug("Checking session {} and got response {}", data.getSessionHash(), status.getStatus());
				}

				if (status.isOK()) {
					data.updateLastModified();
					httpSession.setAttribute(attributeName, data);
				} else {
					httpSession.removeAttribute(attributeName);
				}
			}
		}
		innerChain.doFilter(request, response);
	}
}
