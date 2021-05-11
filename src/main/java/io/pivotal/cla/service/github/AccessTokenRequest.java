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
package io.pivotal.cla.service.github;

import io.pivotal.cla.config.OAuthClientCredentials;

/**
 * @author Rob Winch
 */
public class AccessTokenRequest {
	private OAuthClientCredentials credentials;
	private OAuthAccessTokenParams oauthParams;

	public AccessTokenRequest() {
	}

	public OAuthClientCredentials getCredentials() {
		return this.credentials;
	}

	public OAuthAccessTokenParams getOauthParams() {
		return this.oauthParams;
	}

	public void setCredentials(final OAuthClientCredentials credentials) {
		this.credentials = credentials;
	}

	public void setOauthParams(final OAuthAccessTokenParams oauthParams) {
		this.oauthParams = oauthParams;
	}

	@java.lang.Override
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof AccessTokenRequest)) return false;
		final AccessTokenRequest other = (AccessTokenRequest) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$credentials = this.getCredentials();
		final java.lang.Object other$credentials = other.getCredentials();
		if (this$credentials == null ? other$credentials != null : !this$credentials.equals(other$credentials)) return false;
		final java.lang.Object this$oauthParams = this.getOauthParams();
		final java.lang.Object other$oauthParams = other.getOauthParams();
		if (this$oauthParams == null ? other$oauthParams != null : !this$oauthParams.equals(other$oauthParams)) return false;
		return true;
	}

	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof AccessTokenRequest;
	}

	@java.lang.Override
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $credentials = this.getCredentials();
		result = result * PRIME + ($credentials == null ? 43 : $credentials.hashCode());
		final java.lang.Object $oauthParams = this.getOauthParams();
		result = result * PRIME + ($oauthParams == null ? 43 : $oauthParams.hashCode());
		return result;
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "AccessTokenRequest(credentials=" + this.getCredentials() + ", oauthParams=" + this.getOauthParams() + ")";
	}
}
