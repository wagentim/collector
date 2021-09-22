package de.wagentim.collector.sites;

import com.paulhammant.ngwebdriver.NgWebDriver;

import org.openqa.selenium.WebDriver;

public abstract class ImmoSiteHandler extends SiteHandler
{

    public ImmoSiteHandler(WebDriver webDriver, NgWebDriver ngWebDriver) 
    {
        super(webDriver, ngWebDriver);
    }
    
}
