package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "nav-files-tab")
    private WebElement filesTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "homeNoteTitle")
    private WebElement homeNoteTitle;

    @FindBy(id = "homeNoteDescription")
    private WebElement homeNoteDescription;

    @FindBy(id = "homeCredentialUrl")
    private WebElement homeCredentialUrl;

    @FindBy(id = "homeCredentialUsername")
    private WebElement homeCredentialUsername;

    @FindBy(id = "homeCredentialPassword")
    private WebElement homeCredentialPassword;

    public void logout(){
        logoutButton.click();
    }

    public WebElement getNotesTab(){
        return notesTab;
    }

    public WebElement getLogoutButton() {
        return logoutButton;
    }

    public WebElement getFilesTab() {
        return filesTab;
    }

    public WebElement getCredentialsTab() {
        return credentialsTab;
    }

    public WebElement getHomeNoteTitle() {
        return homeNoteTitle;
    }

    public WebElement getHomeNoteDescription() {
        return homeNoteDescription;
    }

    public WebElement getHomeCredentialUrl() {
        return homeCredentialUrl;
    }

    public WebElement getHomeCredentialUsername() {
        return homeCredentialUsername;
    }

    public WebElement getHomeCredentialPassword() {
        return homeCredentialPassword;
    }
}
