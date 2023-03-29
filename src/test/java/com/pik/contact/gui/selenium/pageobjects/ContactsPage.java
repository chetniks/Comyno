package com.pik.contact.gui.selenium.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ContactsPage extends BasePage<ContactsPage> {

    @FindBy(id = "filter")
    private WebElement filter;

    @FindBy(css = "table > tbody > tr:nth-child(1) > th > strong > input")
    private WebElement firstContactTitle;

    public ContactsPage(int port) {
        super(port);
    }

    @Override
	protected ExpectedCondition getPageLoadCondition() {
		return ExpectedConditions.titleContains("Contact");
	}

	@Override
	public String getPageUrl() {
		return "/#/view";
	}

	public ContactsPage open() {
		return openPage();
	}

    public void find(String query) throws InterruptedException {
        Thread.sleep(1000);
        filter.sendKeys(query);
    }

    public String firstContactTitle() throws InterruptedException {
        Thread.sleep(1000);
        return firstContactTitle.getAttribute("value");
    }
}
