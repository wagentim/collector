package de.wagentim.collector.sites.immobilien;

import com.paulhammant.ngwebdriver.NgWebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import de.wagentim.collector.sites.ImmoSiteHandler;

public class EbayKleinAnzeige extends ImmoSiteHandler
{

    private static final String SELECT_ACCEPT = "div#gdpr-banner button#gdpr-banner-accept";
    private static final String SELECT_CLOSE_LOGIN = "header section#site-header-top section#site-signin div.login-overlay--content a";
    private static final String SELECT_SEARCH_CONTENT = "header section#site-searchbar div#site-search-query-wrp input";
    private static final String SELECT_SEARCH_PLACE = "header section#site-searchbar div#site-search-area-wrp input";

    public EbayKleinAnzeige(WebDriver webDriver, NgWebDriver ngWebDriver) 
    {
        super(webDriver, ngWebDriver);
    }

    @Override
    public void handleSite() 
    {
        // Step 1: open the entry page
        openPage(getStartLink(), false, false);

        // Step 2: login pupup
        WebElement we = webDriver.findElement(By.cssSelector(SELECT_CLOSE_LOGIN));
        if(we != null)
        {
            we.click();
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Step 3: remove term popup
        we = webDriver.findElement(By.cssSelector(SELECT_ACCEPT));
        if(we != null)
        {
            we.click();
        }

        // Step 4: input search content
        we = webDriver.findElement(By.cssSelector(SELECT_SEARCH_CONTENT));
        if(we != null)
        {
            we.sendKeys("gartengrundstück");
        }

        // Step 4: input search place
        we = webDriver.findElement(By.cssSelector(SELECT_SEARCH_PLACE));
        if(we != null)
        {
            we.sendKeys("stuttgart");
        }

    }

    @Override
    protected String getStartLink() 
    {
        return "https://www.ebay-kleinanzeigen.de";
    }

    @Override
    protected String getSiteName() 
    {
        return "Ebay Kleinanzeigen";
    }

    public static void main(String[] args)
    {
        String key = "webdriver.gecko.driver";
		String value = "D:\\libs\\geckodriver.exe";
		System.setProperty(key, value);		
    	FirefoxDriver ffd = new FirefoxDriver();
		NgWebDriver ngwd = new NgWebDriver(ffd);
        
        EbayKleinAnzeige ebay = new EbayKleinAnzeige(ffd, ngwd);
        ebay.handleSite();
    }
}
