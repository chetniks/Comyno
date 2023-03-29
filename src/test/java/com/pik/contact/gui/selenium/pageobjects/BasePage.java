package com.pik.contact.gui.selenium.pageobjects;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;

import static com.pik.contact.gui.selenium.setup.SeleniumDriver.getDriver;

public abstract class BasePage<T extends BasePage> {

    private static final String BASE_URL = "http://localhost:";
    private static final int LOAD_TIMEOUT = 30;
    private static final int REFRESH_RATE = 2;
    private int port;

    public BasePage(int port) {
        this.port = port;
    }

    public T openPage() {
        PageFactory.initElements(getDriver(), this);
        getDriver().get(getBaseUrl() + getPageUrl());
        ExpectedCondition pageLoadCondition = getPageLoadCondition();
        waitForPageToLoad(pageLoadCondition);
        return (T) this;
    }

    private String getBaseUrl() {
        return BASE_URL + port;
    }

    protected void waitForPageToLoad(ExpectedCondition pageLoadCondition) {
        Wait wait = new FluentWait(getDriver())
                .withTimeout(LOAD_TIMEOUT, TimeUnit.SECONDS)
                .pollingEvery(REFRESH_RATE, TimeUnit.SECONDS);

        wait.until(pageLoadCondition);
    }

    /**
     * Provides condition when page can be considered as fully loaded.
     *
     */
    protected abstract ExpectedCondition getPageLoadCondition();

    /**
     * Provides page relative URL/
     *
     */
    public abstract String getPageUrl();
}
