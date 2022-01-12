package com.qa.stepdefinitions;

import com.qa.pages.LoginPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LoginStepDefinitions {

    @When("^I enter username as \"([^\"]*)\"$")
    public void iEnterUsernameAs(String username) throws InterruptedException {
        new LoginPage().enterUserName(username);
    }

    @When("^I enter password as \"([^\"]*)\"$")
    public void iEnterPasswordAs(String password) {
        new LoginPage().enterPassword(password);
    }

    @When("^I login$")
    public void iLogin() {
        new LoginPage().pressLoginBtn();
    }

    @Then("^login should fail with an error \"([^\"]*)\"$")
    public void loginShouldFailWithAnError(String err) {
        Assert.assertEquals(new LoginPage().getErrTxt(), err);
    }

    @Then("^I should see Products page with title \"([^\"]*)\"$")
    public void iShouldSeeProductsPageWithTitle(String title) {
        Assert.assertEquals(new LoginPage().getTitle(), title);
    }
}
