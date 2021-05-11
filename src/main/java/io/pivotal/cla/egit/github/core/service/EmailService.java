/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.pivotal.cla.egit.github.core.service;

import static org.eclipse.egit.github.core.client.IGitHubConstants.AUTH_TOKEN;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_EMAILS;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_USER;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PagedRequest;
import org.eclipse.egit.github.core.service.GitHubService;
import org.eclipse.egit.github.core.util.EncodingUtils;
import com.google.gson.reflect.TypeToken;
import io.pivotal.cla.config.ClaOAuthConfig;
import io.pivotal.cla.egit.github.core.Email;

public class EmailService extends GitHubService {
	public List<Email> getEmails() {
		PagedRequest<Email> request = createPagedRequest();
		request.setUri(SEGMENT_USER + SEGMENT_EMAILS);
		request.setType(new TypeToken<List<Email>>() {}.getType());
		try {
			return getAll(request);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private EmailService(GitHubClient client) {
		super(client);
	}

	public static EmailService forOAuth(String token, ClaOAuthConfig config) {
		return new EmailService(new EmailGitHubClient(config.getGitHubApiHost(), config.getPort(), config.getScheme()).setOAuth2Token(token));
	}


	private static final class EmailGitHubClient extends GitHubClient {
		private String credentials;

		public EmailGitHubClient(String hostname, int port, String scheme) {
			super(hostname, port, scheme);
		}

		public GitHubClient setCredentials(String user, String password) {
			super.setCredentials(user, password);
			if (user != null && user.length() > 0 && password != null && password.length() > 0) {
				credentials =  //$NON-NLS-1$
				"Basic " + EncodingUtils.toBase64(user + ':' + password);
			} else {
				credentials = null;
			}
			return this;
		}

		@Override
		public GitHubClient setOAuth2Token(String token) {
			if (token != null && token.length() > 0) {
				credentials = AUTH_TOKEN + ' ' + token;
			} else {
				credentials = null;
			}
			return this;
		}

		@Override
		protected HttpURLConnection configureRequest(HttpURLConnection request) {
			if (credentials != null) request.setRequestProperty(HEADER_AUTHORIZATION, credentials);
			request.setRequestProperty(HEADER_USER_AGENT, USER_AGENT);
			return request;
		}
	}
}
