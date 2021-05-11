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

import java.util.Collections;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import io.pivotal.cla.data.User;
import io.pivotal.cla.security.Login.UserAuthentication;

public class WithAdminUserFactory implements WithSecurityContextFactory<WithAdminUser> {

	@Override
	public SecurityContext createSecurityContext(WithAdminUser user) {
		User adminUser = create();
		UserAuthentication auth = new UserAuthentication(adminUser);
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(auth);
		return context;
	}

	public static User create() {
		User user = new User();
		user.setAccessToken("mocked_access_token");
		user.setAvatarUrl("https://avatars.githubusercontent.com/u/362503?v=3");
		user.setEmails(Collections.singleton("rob@pivotal.io"));
		user.setGitHubLogin("rwinch");
		user.setAdmin(true);
		return user;
	}
}