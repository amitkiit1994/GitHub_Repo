GitHub Repository Automation
This is a Test Automation project that compares names and description of all repositories in django project from UI with respect to API.

Configuration Tools
The project is configured with following tools: Java, Maven, Selenium, Rest Assured io, Testng, Log4j and Extent Reports.

System Requirements
The jar requires following base versions: Maven - 3.6.3, Java - 1.8.0_261

Please install java and maven if not installed. Also add the system environment variables JAVA_HOME and MAVEN_HOME and set it with your installed bin folder location.

Installation
Open Git bash terminal
git clone https://github.com/amitkiit1994/RideCell_Project.git
Open Eclipse or IntellJ
Use Import Project Wizard to import Git Project
Run the pom.xml in the project directory
Detailed Information
This a typical Maven free style project having following :

ExtentReportResults.html
Automation report of last execution

pom.xml
This is to maintain all the dependencies

testng.xml
Configuring test classes

execdir
Actual Data from Runtime in excel file

src/main/java
base - TestBase
This class has the following methods:

Constructor
Intilializes the properties file from the resources package

initializeSeleniumWebDriver
Initializes the selenium driver and returns the driver

pageObjects
This package has the pages as a page object model pattern to store various element locators for locating UI elements

resources
This package has the following utilities:

chromedriver.exe
chromedriver to set system property to automate with chrome browser

config.properties
All the config parameters that can be modified at runtime, ex: API endpoint, web url, file name to store data, mail recipient to receive the automation results

log4j2.xml
Logging configuration details

logs
Log file for every execution

uiLayer
The business logic to automate the UI

util
This package has Test utility class having following methods:

sendMail()
To send report of the automation to the mail recipient

writeRepoDetailsToExcel
To write the API results and UI Results data to excel

src/test/java
This package has the test files that is recognized by testng runner while running pom.xml file

Execution Details
On Successful execution of the project, User shall receive a mail of the report and all the details of execution. Note: The config.properties file must be configured correctly with proper email id and password
