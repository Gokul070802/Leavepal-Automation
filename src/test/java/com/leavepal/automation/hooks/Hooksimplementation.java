package com.leavepal.automation.hooks;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.leavepal.automation.base.BaseClass;
import com.leavepal.automation.utils.ConfigReader;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;

public class Hooksimplementation extends BaseClass {
    private static final long STEP_DELAY_MS = ConfigReader.getLongProperty("execution.step.delay.ms", 1500L);

    @Before
    public void preconditions() {
        options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        setDriver(new ChromeDriver(options));
        getDriver().get("https://leave-management-app-460701269805.asia-south1.run.app/index.html");
        getDriver().manage().window().maximize();
    }

    @AfterStep
    public void slowDownForTracing() {
        if (STEP_DELAY_MS <= 0) {
            return;
        }
        try {
            Thread.sleep(STEP_DELAY_MS);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Step delay interrupted", exception);
        }
    }

    @After
    public void postconditions() {
        if (hasDriver()) {
            getDriver().quit();
            removeDriver();
        }
    }
}
