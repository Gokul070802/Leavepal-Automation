package com.leavepal.automation.base;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.leavepal.automation.utils.PageLocators;

public class BaseClass {
    private static final ThreadLocal<ChromeDriver> DRIVER = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> WAIT = new ThreadLocal<>();
    private static final Duration DEFAULT_WAIT_TIMEOUT = Duration.ofSeconds(10);
    public static ChromeOptions options;

    public static void setDriver(ChromeDriver driver) {
        DRIVER.set(driver);
        WAIT.set(new WebDriverWait(driver, DEFAULT_WAIT_TIMEOUT));
    }

    public static ChromeDriver getDriver() {
        ChromeDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized for the current thread.");
        }
        return driver;
    }

    public static boolean hasDriver() {
        return DRIVER.get() != null;
    }

    public static WebDriverWait getWait() {
        WebDriverWait wait = WAIT.get();
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), DEFAULT_WAIT_TIMEOUT);
            WAIT.set(wait);
        }
        return wait;
    }

    public static void removeDriver() {
        WAIT.remove();
        DRIVER.remove();
    }

    /**
     * Waits up to 10 seconds for the app-wide toast notification and returns its
     * text.
     */
    public String getToastMessage() {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(PageLocators.TOAST_MESSAGE))
                .getText().trim();
    }

    /**
     * Returns the toast text if it appears within {@code timeoutSeconds}, or
     * {@code null} if it does not.
     */
    public String getToastMessageIfPresent(int timeoutSeconds) {
        try {
            WebDriverWait shortWait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(PageLocators.TOAST_MESSAGE))
                    .getText().trim();
        } catch (TimeoutException e) {
            return null;
        }
    }
}
