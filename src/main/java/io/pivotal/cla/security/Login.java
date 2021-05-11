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
package io.pivotal.cla.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import io.pivotal.cla.data.User;

public class Login {
	public static Authentication loginAs(User user) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		UserAuthentication authentication = new UserAuthentication(user);
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
		return authentication;
	}


	static class UserAuthentication implements Authentication {
		private static final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
		private static final List<GrantedAuthority> CLA_AUTHOR_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN", "ROLE_CLA_AUTHOR", "ACTUATOR");
		private static final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
		private static final long serialVersionUID = 4717809728702726728L;
		private final User user;

		public UserAuthentication(User user) {
			this.user = user;
		}

		@Override
		public String getName() {
			return String.valueOf(user.getGitHubLogin());
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			if (user.isAdmin()) {
				return user.isClaAuthor() ? CLA_AUTHOR_ROLES : ADMIN_ROLES;
			}
			return USER_ROLES;
		}

		@Override
		public Object getCredentials() {
			return null;
		}

		@Override
		public Object getDetails() {
			return null;
		}

		@Override
		public Object getPrincipal() {
			return user;
		}

		@Override
		public boolean isAuthenticated() {
			return true;
		}

		@Override
		public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
			throw new UnsupportedOperationException();
		}
	}
}
