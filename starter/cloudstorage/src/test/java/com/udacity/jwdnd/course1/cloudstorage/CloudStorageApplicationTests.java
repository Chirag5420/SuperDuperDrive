package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private static String username;
	private static String password;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@BeforeEach
	public void beforeEach() {
	}

	@AfterAll
	public static void afterAll() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void testAccessWithoutLogin(){
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	@Order(2)
	public void testAccessWithLogin() {
		username = "Chirag2000";
		password = "password123";
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.createUser("Chirag", "Chhajlani", username, password);

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login(username,password);


		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		Assertions.assertEquals("Home", driver.getTitle());
		homePage.logout();

		Assertions.assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	@Order(3)
	public void testCreateNote() throws InterruptedException {
		String title = "title123";
		String description = "description123";

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login(username,password);

		//Wait Driver
		WebDriverWait wait = new WebDriverWait(driver, 5);
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		Assertions.assertEquals("Home", driver.getTitle());

		//open notes tab
		WebElement noteTab = homePage.getNotesTab();
		wait.until(ExpectedConditions.elementToBeClickable(noteTab)).click();

		//add new note
		wait.until(ExpectedConditions.elementToBeClickable( By.id("addNoteButton") )).click();
		wait.until(ExpectedConditions.elementToBeClickable( By.id("note-title") )).sendKeys(title);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("note-description") )).sendKeys(description);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("saveButton") )).click();

		//check result page to home page flow
		Assertions.assertEquals("http://localhost:" + port + "/note", driver.getCurrentUrl());
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("successResult-to-home-link") )).click();
		Thread.sleep(1000);
		Assertions.assertEquals("http://localhost:" + port + "/home", driver.getCurrentUrl());

		//check if the inputted note is consistent
		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(noteTab)).click();
		String displayedTitle = wait.until(ExpectedConditions.elementToBeClickable(By.id("homeNoteTitle"))).getText();
		String displayedDescription = wait.until(ExpectedConditions.elementToBeClickable(By.id("homeNoteDescription"))).getText();
		Assertions.assertEquals(title, displayedTitle);
		Assertions.assertEquals(description, displayedDescription);
	}

	@Test
	@Order(4)
	public void testEditNote() throws InterruptedException {
		String editedTitle = "title321";
		String editedDescription = "description321";

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login(username,password);

		//Wait Driver
		WebDriverWait wait = new WebDriverWait(driver, 5);
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		Assertions.assertEquals("Home", driver.getTitle());

		//open notes tab
		WebElement noteTab = homePage.getNotesTab();
		wait.until(ExpectedConditions.elementToBeClickable(noteTab)).click();

		//edit existing note
		wait.until(ExpectedConditions.elementToBeClickable(By.id("noteEditButton"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(editedTitle);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(editedDescription);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("saveButton"))).click();

		//check result page to home page flow
		Assertions.assertEquals("http://localhost:" + port + "/note", driver.getCurrentUrl());
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("successResult-to-home-link") )).click();
		Thread.sleep(1000);
		Assertions.assertEquals("http://localhost:" + port + "/home", driver.getCurrentUrl());

		//check if the inputted note is consistent
		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(noteTab)).click();
		String displayedTitle = wait.until(ExpectedConditions.elementToBeClickable(By.id("homeNoteTitle"))).getText();
		String displayedDescription = wait.until(ExpectedConditions.elementToBeClickable(By.id("homeNoteDescription"))).getText();
		Assertions.assertEquals(editedTitle, displayedTitle);
		Assertions.assertEquals(editedDescription, displayedDescription);
	}

	@Test
	@Order(5)
	public void testDeleteNote() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login(username,password);

		//Wait Driver
		WebDriverWait wait = new WebDriverWait(driver, 5);
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		Assertions.assertEquals("Home", driver.getTitle());

		//open notes tab
		WebElement noteTab = homePage.getNotesTab();
		wait.until(ExpectedConditions.elementToBeClickable(noteTab)).click();

		//delete note
		wait.until(ExpectedConditions.elementToBeClickable(By.id("noteDeleteButton"))).click();

		//check result page to home page flow
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("successResult-to-home-link") )).click();
		Thread.sleep(1000);
		Assertions.assertEquals("http://localhost:" + port + "/home", driver.getCurrentUrl());

		//check if note is deleted
		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(noteTab)).click();

		Assertions.assertThrows(NoSuchElementException.class, () -> homePage.getHomeNoteTitle().getText());
		Assertions.assertThrows(NoSuchElementException.class, () -> homePage.getHomeNoteDescription().getText());
	}

	@Test
	@Order(6)
	public void testCreateCredential() throws InterruptedException {
		String urlC = "test.com";
		String usernameC = "userTest";
		String passwordC = "passTest";

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login(username,password);

		//Wait Driver
		WebDriverWait waitDriver = new WebDriverWait(driver, 5);
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		Assertions.assertEquals("Home", driver.getTitle());

		//open credential tab
		WebElement credentialTab = homePage.getCredentialsTab();
		waitDriver.until(ExpectedConditions.elementToBeClickable(credentialTab)).click();

		//add new credential
		waitDriver.until(ExpectedConditions.elementToBeClickable( By.id("addCredentialButton") )).click();
		waitDriver.until(ExpectedConditions.elementToBeClickable( By.id("credential-url") )).sendKeys(urlC);
		waitDriver.until(ExpectedConditions.elementToBeClickable( By.id("credential-username") )).sendKeys(usernameC);
		waitDriver.until(ExpectedConditions.elementToBeClickable( By.id("credential-password") )).sendKeys(passwordC);
		waitDriver.until(ExpectedConditions.elementToBeClickable( By.id("saveCredentialChanges") )).click();

		//check result page to home page flow
		Assertions.assertEquals("http://localhost:" + port + "/credential", driver.getCurrentUrl());
		Thread.sleep(2000);
		waitDriver.until(ExpectedConditions.elementToBeClickable( By.id("successResult-to-home-link") )).click();
		Thread.sleep(1000);
		Assertions.assertEquals("http://localhost:" + port + "/home", driver.getCurrentUrl());

		//check if the inputted note is consistent
		driver.get("http://localhost:" + this.port + "/home");
		waitDriver.until(ExpectedConditions.elementToBeClickable(credentialTab)).click();
		String displayedUrl = waitDriver.until(ExpectedConditions.elementToBeClickable(By.id("homeCredentialUrl"))).getText();
		String displayedUsername = waitDriver.until(ExpectedConditions.elementToBeClickable(By.id("homeCredentialUsername"))).getText();
		String displayedPassword = waitDriver.until(ExpectedConditions.elementToBeClickable(By.id("homeCredentialPassword"))).getText();
		Assertions.assertEquals(urlC, displayedUrl);
		Assertions.assertEquals(usernameC, displayedUsername);
		Assertions.assertNotEquals(passwordC, displayedPassword);
	}

	@Test
	@Order(7)
	public void testEditCredential() throws InterruptedException {
		String editedUrlC = "google.com";
		String editedUsernameC = "userGoogle";
		String editedPasswordC = "passGoogle";

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login(username,password);

		//Wait Driver
		WebDriverWait waitDriver = new WebDriverWait(driver, 5);
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		Assertions.assertEquals("Home", driver.getTitle());

		//open credential tab
		WebElement credentialTab = homePage.getCredentialsTab();
		waitDriver.until(ExpectedConditions.elementToBeClickable(credentialTab)).click();

		//edit existing credential
		waitDriver.until(ExpectedConditions.elementToBeClickable(By.id("editCredentialButton"))).click();
		waitDriver.until(ExpectedConditions.elementToBeClickable( By.id("credential-url") )).clear();
		waitDriver.until(ExpectedConditions.elementToBeClickable( By.id("credential-username") )).clear();
		waitDriver.until(ExpectedConditions.elementToBeClickable( By.id("credential-password") )).clear();
		waitDriver.until(ExpectedConditions.elementToBeClickable( By.id("credential-url") )).sendKeys(editedUrlC);
		waitDriver.until(ExpectedConditions.elementToBeClickable( By.id("credential-username") )).sendKeys(editedUsernameC);
		waitDriver.until(ExpectedConditions.elementToBeClickable( By.id("credential-password") )).sendKeys(editedPasswordC);
		waitDriver.until(ExpectedConditions.elementToBeClickable(By.id("saveCredentialChanges"))).click();

		//check result page to home page flow
		Assertions.assertEquals("http://localhost:" + port + "/credential", driver.getCurrentUrl());
		Thread.sleep(2000);
		waitDriver.until(ExpectedConditions.elementToBeClickable( By.id("successResult-to-home-link") )).click();
		Thread.sleep(1000);
		Assertions.assertEquals("http://localhost:" + port + "/home", driver.getCurrentUrl());

		//check if the inputted note is consistent
		driver.get("http://localhost:" + this.port + "/home");
		waitDriver.until(ExpectedConditions.elementToBeClickable(credentialTab)).click();
		String displayedUrl = waitDriver.until(ExpectedConditions.elementToBeClickable(By.id("homeCredentialUrl"))).getText();
		String displayedUsername = waitDriver.until(ExpectedConditions.elementToBeClickable(By.id("homeCredentialUsername"))).getText();
		String displayedPassword = waitDriver.until(ExpectedConditions.elementToBeClickable(By.id("homeCredentialPassword"))).getText();
		Assertions.assertEquals(editedUrlC, displayedUrl);
		Assertions.assertEquals(editedUsernameC, displayedUsername);
		Assertions.assertNotEquals(editedPasswordC, displayedPassword);
	}

	@Test
	@Order(8)
	public void testDeleteCredential() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login(username,password);

		//Wait Driver
		WebDriverWait waitDriver = new WebDriverWait(driver, 5);
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		Assertions.assertEquals("Home", driver.getTitle());

		//open credential tab
		WebElement credentialTab = homePage.getCredentialsTab();
		waitDriver.until(ExpectedConditions.elementToBeClickable(credentialTab)).click();

		//delete credential
		driver.get("http://localhost:" + this.port + "/home");
		waitDriver.until(ExpectedConditions.elementToBeClickable(credentialTab)).click();
		waitDriver.until(ExpectedConditions.elementToBeClickable(By.id("deleteCredentialButton"))).click();

		//check result page to home page flow
		Thread.sleep(2000);
		waitDriver.until(ExpectedConditions.elementToBeClickable( By.id("successResult-to-home-link") )).click();
		Thread.sleep(1000);
		Assertions.assertEquals("http://localhost:" + port + "/home", driver.getCurrentUrl());

		//check if credential is deleted
		driver.get("http://localhost:" + this.port + "/home");
		waitDriver.until(ExpectedConditions.elementToBeClickable(credentialTab)).click();

		Assertions.assertThrows(NoSuchElementException.class, () -> homePage.getHomeCredentialUrl().getText());
		Assertions.assertThrows(NoSuchElementException.class, () -> homePage.getHomeCredentialUsername().getText());
		Assertions.assertThrows(NoSuchElementException.class, () -> homePage.getHomeCredentialPassword().getText());
	}
}