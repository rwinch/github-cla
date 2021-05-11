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
package io.pivotal.cla.mvc;

import io.pivotal.cla.data.ContributorLicenseAgreement;
import io.pivotal.cla.data.User;
import io.pivotal.cla.data.repository.ContributorLicenseAgreementRepository;
import io.pivotal.cla.service.ClaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.Map;

@Controller
public class ClaController {
	@Autowired
	private ContributorLicenseAgreementRepository clas;
	@Autowired
	private ClaService claService;

	@GetMapping("/sign/{claName}")
	public String signIndex(@AuthenticationPrincipal User user, @ModelAttribute ClaRequest claRequest, Map<String, Object> model) throws Exception {
		String claName = claRequest.getClaName();
		Integer pullRequestId = claRequest.getPullRequestId();
		String repositoryId = claRequest.getRepositoryId();
		ContributorLicenseAgreement cla = clas.findByNameAndPrimaryTrue(claName);
		if (cla == null) {
			throw new ResourceNotFoundException();
		}
		boolean signed = user != null && claService.hasSigned(user, claName);
		model.put("repositoryId", repositoryId);
		model.put("pullRequestId", pullRequestId);
		model.put("signed", signed);
		model.put("claName", claName);
		return "index";
	}

	@GetMapping("/view/{claName}")
	public String viewIndex(@ModelAttribute ClaRequest claRequest, Map<String, Object> model) throws Exception {
		return signIndex(null, claRequest, model);
	}
}
