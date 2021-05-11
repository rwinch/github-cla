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
package io.pivotal.cla.webdriver.pages.admin;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.StringUtils;

import com.google.common.base.Predicate;

import io.pivotal.cla.webdriver.pages.BasePage;

import java.util.function.Function;

public class AdminLinkClaPage extends BasePage {
	WebElement submit;

	@FindBy(id = "repositories")
	WebElement repositories;

	WebElement claName;

	@FindBy(id = "contributing-adoc")
	WebElement contributingAdoc;

	@FindBy(id = "contributing-md")
	WebElement contributingMd;

	@FindBy(id = "migrate-submit")
	WebElement migrateSubmit;

	public AdminLinkClaPage(WebDriver driver) {
		super(driver);
	}

	public AdminLinkClaPage migrate() {
		migrateSubmit.click();
		return PageFactory.initElements(getDriver(), getClass());
	}

	public String contributingMd() {
		try {
			return contributingMd.getText();
		}catch(NoSuchElementException missing) {
			return null;
		}
	}

	public String contributingAdoc() {
		try {
			return contributingAdoc.getText();
		}catch(NoSuchElementException missing) {
			return null;
		}
	}

	public SelectAssert assertClaName() {
		return assertSelect(claName);
	}

	public SelectAssert assertRepositories() {
		return assertSelect(repositories);
	}

	public <T extends BasePage> T link(String repositoryName, String licenseName, Class<T> page) {
		Select cla = new Select(claName);
		Select repositoriesSelect = new Select(repositories);

		if(StringUtils.hasText(repositoryName)) {
			repositoriesSelect.selectByVisibleText(repositoryName);
		}
		if(StringUtils.hasText(licenseName)) {
			cla.selectByVisibleText(licenseName);
		}
		submit.click();
		return PageFactory.initElements(getDriver(), page);
	}

	public void waitUntilAt() {
		waitUntilAt(() -> assertAt());
	}

	public void assertAt() {
		assertThat(getDriver().getTitle()).endsWith("Link to CLA");
	}

	public static String url() {
		return "/admin/cla/link";
	}

	public static AdminLinkClaPage to(WebDriver driver) {
		get(driver, url());
		return PageFactory.initElements(driver, AdminLinkClaPage.class);
	}
}
