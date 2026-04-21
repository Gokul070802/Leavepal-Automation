package com.leavepal.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/createEmployee.feature", glue = {
        "com.leavepal.automation.stepDefinitions",
        "com.leavepal.automation.hooks" }, plugin = {
                "pretty",
                "json:target/cucumber-reports/createEmployee.json" }, monochrome = true)
public class CreateEmployeeFeatureRunner extends AbstractTestNGCucumberTests {
}