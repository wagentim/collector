package de.wagentim.collector.sites;

import org.openqa.selenium.WebDriver;

import com.paulhammant.ngwebdriver.NgWebDriver;

public abstract class ProductSiteHandler extends SiteHandler
{
	
	public ProductSiteHandler(WebDriver webDriver, NgWebDriver ngWebDriver)
	{
		super(webDriver, ngWebDriver);
	}

}
