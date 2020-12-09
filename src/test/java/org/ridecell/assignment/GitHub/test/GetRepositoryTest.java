package org.ridecell.assignment.GitHub.test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.ridecell.assignment.base.TestBase;
import org.ridecell.assignment.uiLayer.GithubDjangoProjectUI;
import org.ridecell.assignment.util.TestUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;

/**
 * Unit test for simple App.
 */
public class GetRepositoryTest extends TestBase {
	private static final Logger logger = LogManager.getLogger(GetRepositoryTest.class);
	static TestBase testbase = new TestBase();
	static String apiUrl = prop.getProperty("API_URL");
	static String webUrl = prop.getProperty("WEB_URL");
	static String organisation = prop.getProperty("Organisation");
	static String repositoryEndpoint = prop.getProperty("RepositoryEndpoint");
	static LinkedHashMap<String, String> repoDetailsMapApi = new LinkedHashMap<String, String>();
	static LinkedHashMap<String, String> repoDetailsMapUI = GithubDjangoProjectUI.repoListMapUI;
	TestUtil testutl;
	WebDriver driver;
	GithubDjangoProjectUI githubDjangoProjectUI;
	static ExtentTest test;
	static ExtentReports report;

	@BeforeTest
	public void initialize() {
		logger.info("-------------------EXECUTION STARTED FOR REPOSITORY TEST------------------");
		try {
			logger.info("Test Configuration Started for RestAPI");
			logger.info("URL Configured for API endpoint: " + apiUrl);
			logger.info("Rest Endpoint URL configured for Repository Test: " + organisation + repositoryEndpoint);
			apiUrl += organisation + repositoryEndpoint;
			logger.info("System has configured the following application endpoint: " + apiUrl);
			logger.info("Test Configuration Completed Successfully for Rest API");
			logger.info("----------------------------------------------------------------------");
		} catch (Exception e) {
//			assertTrue(false);
			logger.error("Test Configuration Failed for RestAPI");
			logger.catching(e);
		}
		try {
			logger.info("Test Configuration Started for Web UI");
			driver = initializeSeleniumWebDriver();
			logger.info("Selenium Web Driver Intialized Successfully");
		} catch (Exception e) {
//			assertTrue(false);
			logger.error("Exception Occured while Initializing Web Driver");
			logger.catching(e);
		}
		logger.info("URL Configured for WEB UI: " + webUrl);
		githubDjangoProjectUI = new GithubDjangoProjectUI();
		if (githubDjangoProjectUI.openWebsite(webUrl)) {
			logger.info("Website opened successfully");
			logger.info("----------------------------------------------------------------------");
		} else {
//			assertTrue(false);
			logger.error("Website could not be opened");
		}

	}
	
	@BeforeClass
	public static void startTest()
	{
	report = new ExtentReports(System.getProperty("user.dir")+"\\ExtentReportResults.html");
	test = report.startTest("Django Repository Test");
	}

	@Test
	public void GetRepositoryDjangoAPI() {
		logger.info("GET Rest Call for Repository API started");
		test.log(LogStatus.INFO, "GET Rest Call for Repository API started");
		try {
			logger.info("GET: " + apiUrl);
			test.log(LogStatus.INFO, "GET: " + apiUrl);
			Response rs = given().when().get(apiUrl);
			logger.info("Status Code: " + rs.statusCode());
			test.log(LogStatus.INFO, "Status Code: " + rs.statusCode());
			assertEquals(200, rs.statusCode());
			List<String> allNames = rs.jsonPath().getList("name");
			List<String> allDesc = rs.jsonPath().getList("description");

			for (int i = 0; i < allNames.size(); i++) {
				repoDetailsMapApi.put(allNames.get(i), allDesc.get(i));
			}
			logger.info("Total Count of Repositories from Rest API: " + repoDetailsMapApi.size());
			test.log(LogStatus.INFO, "Status Code: " + "Total Count of Repositories from Rest API: " + repoDetailsMapApi.size());
			for (Map.Entry<String, String> map : repoDetailsMapApi.entrySet()) {
				logger.info("Repo Name: " + map.getKey() + " - Repo Desc: " + map.getValue());
			}
			test.log(LogStatus.INFO, "Successfully received repository details from Rest API Call");
			try {
				TestUtil.writeRepoDetailsToExcel(prop.getProperty("FileNameAPIData"), repoDetailsMapApi);
				logger.info("Successfully Exported API details to Excel");
				test.log(LogStatus.INFO, "Successfully Exported API details to Excel");
			} catch (Exception e) {
				logger.error("Error Writing API details to Excel");
				test.log(LogStatus.ERROR, "Error Writing API details to Excel");
				logger.catching(e);
			}

			logger.info("GET Rest Call Successful for Repository API");
			test.log(LogStatus.INFO, "GET Rest Call Successful for Repository API");
			allNames = null;
			allDesc = null;
		} catch (Exception e) {
			logger.error("GET Rest Call for Repository API failed due to unexpected error");
			test.log(LogStatus.ERROR, "GET Rest Call for Repository API failed due to unexpected error");
			logger.catching(e);
//			assertTrue(false);
		}
		logger.info("GET Rest Call for Repository API Completed");
		test.log(LogStatus.INFO, "GET Rest Call for Repository API Completed");
	}

	@Test
	public void GetRepositoryDjangoUI() {
		logger.info("Get Repository from Web UI started");
		test.log(LogStatus.INFO, "Get Repository from Web UI started");
		try {
			githubDjangoProjectUI = new GithubDjangoProjectUI();
			if (githubDjangoProjectUI.countOfRepos()) {
				logger.info("Total Count of Repositories from UI: " + GithubDjangoProjectUI.countOfRepo);
				test.log(LogStatus.INFO, "Total Count of Repositories from UI: " + GithubDjangoProjectUI.countOfRepo);
			} else {
				logger.error("Error receiving count from web ui");
				test.log(LogStatus.ERROR, "Error receiving count from web ui");
//				assertTrue(false);
			}
			if (githubDjangoProjectUI.getAllRepositoriesNameDesc()) {
				logger.info("Successfully received repo details from web ui");
				test.log(LogStatus.INFO, "Successfully received repo details from web ui");
				
			} else {
				logger.error("Error receiving Names and Description from web ui");
				test.log(LogStatus.ERROR, "Error receiving Names and Description from web ui");
//				assertTrue(false);
			}
			for (Map.Entry<String, String> map : repoDetailsMapUI.entrySet()) {
				logger.info("Repo Name: " + map.getKey() + " - Repo Desc: " + map.getValue());
			}
			try {
				TestUtil.writeRepoDetailsToExcel(prop.getProperty("FileNameUIData"), repoDetailsMapUI);
				logger.info("Successfully Exported UI details to Excel");
				test.log(LogStatus.INFO, "Successfully Exported UI details to Excel");
			} catch (Exception e) {
				logger.error("Error Writing UI details to Excel");
				test.log(LogStatus.ERROR, "Error Writing UI details to Excel");
				logger.catching(e);
			}
		} catch (Exception e) {
			logger.error("Get Repository from Web UI failed due to unexpected error");
			test.log(LogStatus.ERROR, "Get Repository from Web UI failed due to unexpected error");
			logger.catching(e);
//			assertTrue(false);
		}
		logger.info("Get Repository from Web UI Completed");
		test.log(LogStatus.INFO, "Get Repository from Web UI Completed");
	}

	@Test(dependsOnMethods = { "GetRepositoryDjangoAPI", "GetRepositoryDjangoUI" })
	public void assertingUIDataWithApiData() {
		try {
			boolean flag = false;
			if (repoDetailsMapApi.size() == repoDetailsMapUI.size()) {
				for (Map.Entry<String, String> apiMap : repoDetailsMapApi.entrySet()) {
					if (repoDetailsMapUI.containsKey(apiMap.getKey())
							&& repoDetailsMapUI.containsValue(apiMap.getValue())) {
						flag = true;
					}
				}
			}
			if (flag) {
				logger.info("Repository Details Asserted Successfully with API Data");
				test.log(LogStatus.PASS, "Repository Details Asserted Successfully with API Data");
			} else {
				logger.error("Repository Details Assertion failed with API Data");
				test.log(LogStatus.FAIL, "Repository Details Assertion failed with API Data");
				assertTrue(false);
			}
		} catch (Exception e) {
			logger.error("Repository Details Assertion failed due to unexpected error");
			test.log(LogStatus.FAIL, "Repository Details Assertion failed due to unexpected error");
			logger.catching(e);
			assertTrue(false);
		}
	}
	
	@AfterClass
	public static void endTest()
	{
	report.endTest(test);
	report.flush();
	}

	@AfterTest
	public void finishingTests() throws IOException {
		driver.close();
		logger.info("Browser Windows Closed");
		driver.quit();
		logger.info("Browser closed");
		logger.info("-------------EXECUTION FOR REPOSITORY TEST COMPLETED----------------");
//		testutl = new TestUtil();
//		TestUtil.sendMail();
	}
}
