package org.ridecell.assignment.uiLayer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.ridecell.assignment.base.TestBase;
import org.ridecell.assignment.pageObjects.GithubDjangoProjectPage;

public class GithubDjangoProjectUI extends TestBase {
	GithubDjangoProjectPage githubDjangoProjectPage = new GithubDjangoProjectPage(driver);

	public boolean openWebsite(String url) {

		try {
			if (url != null) {
				driver.get(url);
				driver.manage().window().maximize();
				if (githubDjangoProjectPage.homeLink().isDisplayed()) {
					return true;
				}
			}
			return false;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public static String countOfRepo = "";

	public boolean countOfRepos() {
		try {
			if (githubDjangoProjectPage.repoCount().isDisplayed()) {
				countOfRepo = githubDjangoProjectPage.repoCount().getText();
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public static LinkedHashMap<String, String> repoListMapUI = new LinkedHashMap<String, String>();

	public boolean getAllRepositoriesNameDesc() {
		List<WebElement> listOfNames = githubDjangoProjectPage.repositoryNamesList();
		String xpathRunTimeDesc = "(//a[contains(@itemprop,'name') and contains(@itemprop,'Repository')]/parent::h3)";
		try {
			for (int i = 0; i < listOfNames.size(); i++) {
				try {
					repoListMapUI.put(listOfNames.get(i).getText(),
							driver.findElement(By.xpath(xpathRunTimeDesc + "[" + (i + 1) + "]/following-sibling::p"))
									.getText());
				} catch (NoSuchElementException e) {
					// TODO: handle exception
					repoListMapUI.put(listOfNames.get(i).getText(), null);
					continue;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		if (repoListMapUI != null) {
			return true;
		}
		return false;
	}
}
