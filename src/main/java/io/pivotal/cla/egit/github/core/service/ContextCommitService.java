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

import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_REPOS;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_STATUSES;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.egit.github.core.CommitStatus;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PagedRequest;
import org.eclipse.egit.github.core.service.CommitService;
import com.google.gson.reflect.TypeToken;
import io.pivotal.cla.egit.github.core.ContextCommitStatus;

public class ContextCommitService extends CommitService {
	public ContextCommitService() {
	}

	public ContextCommitService(GitHubClient client) {
		super(client);
	}

	/**
	 * Create status for commit SHA-1
	 *
	 * @param repository
	 * @param sha
	 * @param status
	 * @return created status
	 * @throws IOException
	 */
	public CommitStatus createStatus(IRepositoryIdProvider repository, String sha, ContextCommitStatus status) {
		String id = getId(repository);
		if (sha == null) throw new IllegalArgumentException("SHA-1 cannot be null"); //$NON-NLS-1$
		if (sha.length() == 0) throw new IllegalArgumentException("SHA-1 cannot be empty"); //$NON-NLS-1$
		if (status == null) throw new IllegalArgumentException("Status cannot be null"); //$NON-NLS-1$
		Map<String, String> params = new HashMap<String, String>(3, 1);
		if (status.getState() != null) params.put("state", status.getState());
		if (status.getTargetUrl() != null) params.put("target_url", status.getTargetUrl());
		if (status.getDescription() != null) params.put("description", status.getDescription());
		if (status.getContext() != null) params.put("context", status.getContext());
		StringBuilder uri = new StringBuilder(SEGMENT_REPOS);
		uri.append('/').append(id);
		uri.append(SEGMENT_STATUSES);
		uri.append('/').append(sha);
		try {
			return client.post(uri.toString(), params, CommitStatus.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get statuses for commit SHA-1
	 *
	 * @param repository
	 * @param sha
	 * @return list of statuses
	 * @throws IOException
	 */
	public List<ContextCommitStatus> getContextStatuses(IRepositoryIdProvider repository, String sha) {
		String id = getId(repository);
		if (sha == null) throw new IllegalArgumentException("SHA-1 cannot be null"); //$NON-NLS-1$
		if (sha.length() == 0) throw new IllegalArgumentException("SHA-1 cannot be empty"); //$NON-NLS-1$
		StringBuilder uri = new StringBuilder(SEGMENT_REPOS);
		uri.append('/').append(id);
		uri.append(SEGMENT_STATUSES);
		uri.append('/').append(sha);
		PagedRequest<ContextCommitStatus> request = createPagedRequest();
		request.setType(new TypeToken<List<ContextCommitStatus>>() {}.getType());
		request.setUri(uri);
		try {
			return getAll(request);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
