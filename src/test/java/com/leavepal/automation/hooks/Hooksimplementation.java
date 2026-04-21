package com.leavepal.automation.hooks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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
        Path downloadDir = Paths.get(System.getProperty("user.dir"), "target", "downloads");
        try {
            Files.createDirectories(downloadDir);
        } catch (IOException ex) {
            throw new RuntimeException("Unable to create download directory: " + downloadDir, ex);
        }

        options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-popup-blocking");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadDir.toAbsolutePath().toString());
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true);
        prefs.put("profile.default_content_setting_values.popups", 1);
        options.setExperimentalOption("prefs", prefs);

        setDriver(new ChromeDriver(options));
        Map<String, Object> downloadBehavior = new HashMap<>();
        downloadBehavior.put("behavior", "allow");
        downloadBehavior.put("downloadPath", downloadDir.toAbsolutePath().toString());
        downloadBehavior.put("eventsEnabled", true);
        getDriver().executeCdpCommand("Page.setDownloadBehavior", downloadBehavior);

        getDriver().get(ConfigReader.getRequiredProperty("app.url"));
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
