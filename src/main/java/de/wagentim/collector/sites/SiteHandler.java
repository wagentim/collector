package de.wagentim.collector.sites;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paulhammant.ngwebdriver.NgWebDriver;

public abstract class SiteHandler implements ISiteHandler
{
	protected Logger logger = LoggerFactory.getLogger(SiteHandler.class);
	protected WebDriver webDriver;
	protected NgWebDriver ngWebDriver;
	
	public SiteHandler(WebDriver webDriver, NgWebDriver ngWebDriver)
	{
		this.webDriver = webDriver;
		this.ngWebDriver = ngWebDriver;
	}

	protected void initDBHandler()
	{
		
	}
	
	protected void exit()
	{
		webDriver.quit();
	}

	protected List<WebElement> getWebElementWithLink(String link, String cssSelector, boolean ngWait)
	{
		if(link == null || link.isEmpty())
		{
			logger.error("The input link is empty");
			return null;
		}
		
		webDriver.get(link);
		
		if(ngWait)
		{
			ngWebDriver.waitForAngularRequestsToFinish();
		}
		
		return webDriver.findElements(By.cssSelector(cssSelector));
	}

	protected void openPage(String link, boolean ngWait, boolean newTab)
	{
		if(link == null || link.isEmpty())
		{
			logger.error("The input link is empty");
			return;
		}

		webDriver.get(link);

		if(ngWait)
		{
			ngWebDriver.waitForAngularRequestsToFinish();
		}
	}
	
	public void printElementText(List<WebElement> list)
	{
		for(WebElement we : list)
		{
			System.out.println(we.getText());
		}
	}
	
	public void printElementHTML(List<WebElement> list)
	{
		for(WebElement we : list)
		{
			System.out.println(we.isDisplayed());
		}
	}

	protected void openTab(WebElement webElement, String link)
	{
		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN);
		webElement.sendKeys(selectLinkOpeninNewTab);
		ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
		webDriver.switchTo().window(tabs.get(1));
		webDriver.get(link);
	}

	protected abstract String getStartLink();

	protected abstract String getSiteName();
}
