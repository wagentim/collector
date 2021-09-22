package de.wagentim.collector.start;

import java.util.ArrayList;
import java.util.List;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import de.wagentim.collector.sites.SiteHandler;

public abstract class Collector 
{

    protected FirefoxDriver ffd = null;
    protected NgWebDriver ngwd = null;
    protected List<SiteHandler> handlers = new ArrayList<SiteHandler>();

    public Collector(boolean headless)
    {
        String key = "webdriver.gecko.driver";
		String value = "D:\\libs\\geckodriver.exe";
		System.setProperty(key, value);

		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);
        if(headless)
        {
		    ffd = new FirefoxDriver(options);
        }
        else
        {
    		ffd = new FirefoxDriver();
        }

		ngwd = new NgWebDriver(ffd);
        addSites();
    }

    abstract void addSites();

    protected void run()
	{
		
		for(SiteHandler handler : handlers)
		{
           
            if(handler != null)
            {
			    handler.handleSite();
            }
		
		}
	}
}
