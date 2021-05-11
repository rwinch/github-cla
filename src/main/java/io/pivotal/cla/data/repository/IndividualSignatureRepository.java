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
package io.pivotal.cla.data.repository;

import io.pivotal.cla.data.IndividualSignature;
import io.pivotal.cla.data.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface IndividualSignatureRepository extends CrudRepository<IndividualSignature, Long> {

	default IndividualSignature findOne(Long id) {
		return findById(id).orElse(null);
	}

	@Query("select s from IndividualSignature s where (s.cla.name = :#{#claName} or s.cla.name in (select distinct c.supersedingCla.name from ContributorLicenseAgreement c where c.name = :#{#claName})) and (s.gitHubLogin = :#{#u.gitHubLogin} or s.email in (:#{#u.emails.empty ? '' : #u.emails}))")
	List<IndividualSignature> findSignaturesFor(Pageable pageable, @Param("u") User user,  @Param("claName") String claName);

	@Query("select s from IndividualSignature s where (s.gitHubLogin = :#{#u.gitHubLogin} or s.email in (:#{#u.emails.empty ? '' : #u.emails}))")
	List<IndividualSignature> findSignaturesFor(Pageable pageable, @Param("u") User user);

	List<IndividualSignature> findByEmailIn(Set<String> email);
}
