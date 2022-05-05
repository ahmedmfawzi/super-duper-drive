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
class NoteTests {

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
    private void doLogIn(String userName, String password) {
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
    public void testCreateNote() {
        // Create a test account
        doMockSignUp("Create Note","Test","CNOTE","123");
        doLogIn("CNOTE", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement navNoteTab = driver.findElement(By.id("nav-notes-tab"));
        navNoteTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-show-note-modal")));
        WebElement noteModalBtn = driver.findElement(By.id("btn-show-note-modal"));
        noteModalBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitle = driver.findElement(By.id("note-title"));
        noteTitle.click();
        noteTitle.sendKeys("New Note");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescription = driver.findElement(By.id("note-description"));
        noteDescription.click();
        noteDescription.sendKeys("New Note Description");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-submit-note")));
        WebElement noteSubmit = driver.findElement(By.id("btn-submit-note"));
        noteSubmit.click();

        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("Note was successfully added!"));

        Assertions.assertTrue(driver.findElement(By.id("noteTable")).getText().contains("New Note"));

    }

    @Test
    public void testUpdateNote() {
        // Create a test account
        doMockSignUp("Update Note","Test","UNOTE","123");
        doLogIn("UNOTE", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement navNoteTab = driver.findElement(By.id("nav-notes-tab"));
        navNoteTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-show-note-modal")));
        WebElement noteModalBtn = driver.findElement(By.id("btn-show-note-modal"));
        noteModalBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitle = driver.findElement(By.id("note-title"));
        noteTitle.click();
        noteTitle.sendKeys("New Note 2");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescription = driver.findElement(By.id("note-description"));
        noteDescription.click();
        noteDescription.sendKeys("New Note 2 Description");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-submit-note")));
        WebElement noteSubmit = driver.findElement(By.id("btn-submit-note"));
        noteSubmit.click();

        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("Note was successfully added!"));

        List<WebElement> noteEditButtons = driver.findElements(By.cssSelector("button"));
        WebElement noteEditButton = null;
        String onclick;
        for (WebElement element : noteEditButtons) {
            if (element.getText().equals("Edit")){
                onclick = element.getAttribute("onclick");
                System.out.println(onclick);
                if (onclick.contains("New Note 2")) {
                    noteEditButton = element;
                }
            }
        }
        noteEditButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteUpdateTitle = driver.findElement(By.id("note-title"));
        noteUpdateTitle.click();
        noteUpdateTitle.sendKeys(" - Updated");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteUpdateDescription = driver.findElement(By.id("note-description"));
        noteUpdateDescription.click();
        noteUpdateDescription.sendKeys(" - Updated");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-submit-note")));
        WebElement noteUpdateSubmit = driver.findElement(By.id("btn-submit-note"));
        noteUpdateSubmit.click();

        Assertions.assertTrue(driver.findElement(By.id("noteTable")).getText().contains("New Note 2 - Updated"));
        Assertions.assertTrue(driver.findElement(By.id("noteTable")).getText().contains("New Note 2 Description - Updated"));

        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("Note was successfully updated!"));

    }

    @Test
    public void testDeleteNote() {
        // Create a test account
        doMockSignUp("Delete Note","Test","DNOTE","123");
        doLogIn("DNOTE", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement navNoteTab = driver.findElement(By.id("nav-notes-tab"));
        navNoteTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-show-note-modal")));
        WebElement noteModalBtn = driver.findElement(By.id("btn-show-note-modal"));
        noteModalBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitle = driver.findElement(By.id("note-title"));
        noteTitle.click();
        noteTitle.sendKeys("New Note 2");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescription = driver.findElement(By.id("note-description"));
        noteDescription.click();
        noteDescription.sendKeys("New Note 2 Description");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-submit-note")));
        WebElement noteSubmit = driver.findElement(By.id("btn-submit-note"));
        noteSubmit.click();

        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("Note was successfully added!"));

        List<WebElement> noteDeleteButtons = driver.findElements(By.cssSelector("button"));
        WebElement noteDeleteButton = null;
        for (WebElement element : noteDeleteButtons) {
            if (element.getText().equals("Delete")){
                String onclick = element.getAttribute("onclick");
                System.out.println(onclick);
                noteDeleteButton = element;
            }
        }
        if (noteDeleteButton != null) {
            noteDeleteButton.click();
        }

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-delete-note")));
        WebElement noteDelete = driver.findElement(By.id("btn-delete-note"));
        noteDelete.click();

        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("Note was successfully deleted!"));
        Assertions.assertFalse(driver.findElement(By.id("noteTable")).getText().contains("New Note 2"));

    }


}
