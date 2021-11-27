package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    private WebDriver driver;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        System.out.println(this.driver);
    }

    @FindBy(id = "inputFirstName")
    private WebElement firstName;

    @FindBy(id = "inputLastName")
    private WebElement lastName;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public void createUser (String firstName, String lastName, String username, String password){
        //clear all textField
        this.firstName.clear();
        this.lastName.clear();
        this.username.clear();
        this.password.clear();

        //populate textField
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.submitButton.click();
    }
}
