package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private CloudStorageApplicationTests cloudStorageApplicationTests;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password){
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
        //Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
    }



    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password)
    {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

    }


    /**
     * Test Note is created and displayed
     */

    @Test
    public void testCreateCredential() {
        // Create a test account
        doMockSignUp("Create Credential","Test","CCRED","123");
        doLogIn("CCRED", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement navCredentialTab = driver.findElement(By.id("nav-credentials-tab"));
        navCredentialTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-show-credential-modal")));
        WebElement credentialModalBtn = driver.findElement(By.id("btn-show-credential-modal"));
        credentialModalBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement credentialUrl = driver.findElement(By.id("credential-url"));
        credentialUrl.click();
        credentialUrl.sendKeys("credentialUrl");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement credentialUsername = driver.findElement(By.id("credential-username"));
        credentialUsername.click();
        credentialUsername.sendKeys("credentialUsername");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement credentialPassword = driver.findElement(By.id("credential-password"));
        credentialPassword.click();
        credentialPassword.sendKeys("credentialPassword");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-submit-credential")));
        WebElement noteSubmit = driver.findElement(By.id("btn-submit-credential"));
        noteSubmit.click();

        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("Credential was successfully added!"));

        Assertions.assertTrue(driver.findElement(By.id("credentialTable")).getText().contains("credentialUrl"));

    }

    @Test
    public void testUpdateCredential() {
        // Create a test account
        doMockSignUp("Update Credential","Test","UCRED","123");
        doLogIn("UCRED", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement navCredentialTab = driver.findElement(By.id("nav-credentials-tab"));
        navCredentialTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-show-credential-modal")));
        WebElement credentialModalBtn = driver.findElement(By.id("btn-show-credential-modal"));
        credentialModalBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement credentialUrl = driver.findElement(By.id("credential-url"));
        credentialUrl.click();
        credentialUrl.sendKeys("credential2Url");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement credentialUsername = driver.findElement(By.id("credential-username"));
        credentialUsername.click();
        credentialUsername.sendKeys("credential2Username");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement credentialPassword = driver.findElement(By.id("credential-password"));
        credentialPassword.click();
        credentialPassword.sendKeys("credential2Password");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-submit-credential")));
        WebElement noteSubmit = driver.findElement(By.id("btn-submit-credential"));
        noteSubmit.click();

        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("Credential was successfully added!"));

        List<WebElement> credentialEditButtons = driver.findElements(By.cssSelector("button"));
        WebElement credentialEditButton = null;
        String onclick = null;
        for (WebElement element : credentialEditButtons) {
            if (element.getText().equals("Edit")){
                onclick = element.getAttribute("onclick");
                System.out.println(onclick);
                if (onclick.contains("credential2Username")) {
                    credentialEditButton = element;
                }
            }
        }
        credentialEditButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement credentialUpdateUrl = driver.findElement(By.id("credential-url"));
        credentialUpdateUrl.click();
        credentialUpdateUrl.sendKeys(" - Updated");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement credentialUpdateUsername = driver.findElement(By.id("credential-username"));
        credentialUpdateUsername.click();
        credentialUpdateUsername.sendKeys(" - Updated");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement credentialUpdatePassword = driver.findElement(By.id("credential-password"));
        credentialUpdatePassword.click();
        credentialUpdatePassword.sendKeys(" - Updated");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-submit-credential")));
        WebElement credentialUpdateSubmit = driver.findElement(By.id("btn-submit-credential"));
        credentialUpdateSubmit.click();

        List<WebElement> credentialEditButtons1 = driver.findElements(By.cssSelector("button"));
        for (WebElement element : credentialEditButtons1) {
            if (element.getText().equals("Edit")){
                onclick = element.getAttribute("onclick");
                System.out.println(onclick);
                if (onclick.contains("credential2Password - Updated")) {
                    break;
                }
            }
        }


        Assertions.assertTrue(onclick.contains("credential2Password - Updated"));

        Assertions.assertTrue(driver.findElement(By.id("credentialTable")).getText().contains("credential2Url - Updated"));
        Assertions.assertTrue(driver.findElement(By.id("credentialTable")).getText().contains("credential2Username - Updated"));

        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("Credential was successfully updated!"));

    }


    @Test
    public void testDeleteCredential() {
        // Create a test account
        doMockSignUp("Delete Credential","Test","DCRED","123");
        doLogIn("DCRED", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement navCredentialTab = driver.findElement(By.id("nav-credentials-tab"));
        navCredentialTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-show-credential-modal")));
        WebElement credentialModalBtn = driver.findElement(By.id("btn-show-credential-modal"));
        credentialModalBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement credentialUrl = driver.findElement(By.id("credential-url"));
        credentialUrl.click();
        credentialUrl.sendKeys("credential2Url");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement credentialUsername = driver.findElement(By.id("credential-username"));
        credentialUsername.click();
        credentialUsername.sendKeys("credential2Username");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement credentialPassword = driver.findElement(By.id("credential-password"));
        credentialPassword.click();
        credentialPassword.sendKeys("credential2Password");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-submit-credential")));
        WebElement noteSubmit = driver.findElement(By.id("btn-submit-credential"));
        noteSubmit.click();

        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("Credential was successfully added!"));

        List<WebElement> credentialEditButtons = driver.findElements(By.cssSelector("button"));
        WebElement credentialEditButton = null;
        String onclick = null;
        for (WebElement element : credentialEditButtons) {
            if (element.getText().equals("Delete")){
                onclick = element.getAttribute("onclick");
                System.out.println(onclick);
                credentialEditButton = element;
            }
        }
        credentialEditButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-delete-credential")));
        WebElement noteDelete = driver.findElement(By.id("btn-delete-credential"));
        noteDelete.click();

        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("Credential was successfully deleted!"));
        Assertions.assertFalse(driver.findElement(By.id("credentialTable")).getText().contains("credential2Url"));
    }


}
