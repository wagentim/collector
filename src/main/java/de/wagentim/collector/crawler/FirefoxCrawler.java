package de.wagentim.collector.crawler;

import com.paulhammant.ngwebdriver.NgWebDriver;
import de.wagentim.collector.entity.Task;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.Select;

public class FirefoxCrawler extends SeleniumCrawler {
    private FirefoxOption currOption;

    public FirefoxCrawler(Task task) {
        setTask(task);
    }

    public void setTask(Task task) {
        this.task = task;
        updateFirefoxDriver();
    }

    private void updateFirefoxDriver() {
        if(currOption == null) {
            currOption = new FirefoxOption();
            updateOption();
        }
        else if(!currOption.isSame(task)) {
            updateOption();
        }
        
        if(webDriver == null) {
            createDriver();
        }
    }

    private void createDriver() {

        FirefoxOptions ops = new FirefoxOptions();
        ops.setHeadless(currOption.isHeadless());

        ProfilesIni profilesIni = new ProfilesIni();
        FirefoxProfile profile = profilesIni.getProfile("crawler");
        ops.setProfile(profile);

        if(currOption.isTrace()) {
            ops.setLogLevel(FirefoxDriverLogLevel.TRACE);
        }
        webDriver = new FirefoxDriver(ops);
        ngWebDriver = new NgWebDriver((JavascriptExecutor) webDriver);

    }

    private void updateOption() {
        currOption.setHeadless(task.isHeadless());
        currOption.setTrace(task.isTrace());
        createDriver();
    }
}
