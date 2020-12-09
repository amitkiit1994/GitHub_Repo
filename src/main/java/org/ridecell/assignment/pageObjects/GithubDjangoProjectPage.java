package org.ridecell.assignment.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GithubDjangoProjectPage {
	public WebDriver driver;
	
	public GithubDjangoProjectPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public List<WebElement> repositoryNamesList() {
		return driver.findElements(By.xpath("//a[contains(@itemprop,'name') and contains(@itemprop,'Repository')]"));
	}
	public List<WebElement> repositoryDescList() {
		return driver.findElements(By.xpath("//a[contains(@itemprop,'name') and contains(@itemprop,'Repository')]/parent::h3/following-sibling::p"));
	}
	public WebElement homeLink() {
		return driver.findElement(By.xpath("//a[@href='https://github.com/']"));
	}
	public WebElement repoCount() {
		return driver.findElement(By.xpath("//span[contains(@class,'repository-count')]"));
//		
	}
	


}
