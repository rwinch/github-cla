/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.pivotal.cla.service;

import io.pivotal.cla.OAuthClientCredentials;

/**
 * @author Rob Winch
 *
 */
public class AccessTokenRequest {

	private OAuthClientCredentials credentials;

	private OAuthAccessTokenParams oauthParams;

	/**
	 * @return the credentials
	 */
	public OAuthClientCredentials getCredentials() {
		return credentials;
	}

	/**
	 * @param credentials the credentials to set
	 */
	public void setCredentials(OAuthClientCredentials credentials) {
		this.credentials = credentials;
	}

	/**
	 * @return the oauthParams
	 */
	public OAuthAccessTokenParams getOauthParams() {
		return oauthParams;
	}

	/**
	 * @param oauthParams the oauthParams to set
	 */
	public void setOauthParams(OAuthAccessTokenParams oauthParams) {
		this.oauthParams = oauthParams;
	}
}