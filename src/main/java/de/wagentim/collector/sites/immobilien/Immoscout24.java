package de.wagentim.collector.sites.immobilien;

import de.wagentim.collector.crawler.ICrawler;
import de.wagentim.collector.persistance.objectdb.ObjectDBImmoHandler;
import de.wagentim.collector.sites.main.SeleniumSite;
import de.wagentim.collector.utils.IConstants;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Immoscout24 extends SeleniumSite
{

    private static final String SELECT_SHADOW_HOST = "div#usercentrics-root";
    private static final String SELECT_SHADOW_BUTTON = "button";
    private static final String SELECT_FORM_INPUT_LOCATION = "div#main-search-tab-content-property input#oss-location";
    private static final String SELECT_SELECTION = "div#main-search-tab-content-property div.oss-main-criterion-container select";
    private static final String SELECT_SELECTION_TYPE = "div#main-search-tab-content-property div.select-input-wrapper select#oss-real-estate-type-rent";
    private static final String SELECT_FOUND_BUTTON = "header section#site-searchbar form#site-search-form button";
    private static final String SELECT_ITEM_LIST = "body div.position-relative li";
    private static final String SELECT_ITEM_LOCATION = "div.aditem-main div.aditem-main--top--left";
    private static final String SELECT_ITEM_RELEASE_TIME = "div.aditem-main div.aditem-main--top--right";
    private static final String SELECT_ITEM_TITLE = "div.aditem-main div.aditem-main--middle a";
    private static final String SELECT_ITEM_PRICE = "div.aditem-main div.aditem-main--middle p.aditem-main--middle--price";
    private static final String SELECT_ITEM_SIZE = "div.aditem-main div.aditem-main--bottom span";

    private final ObjectDBImmoHandler dbHandler;
    private String uuid = IConstants.TXT_EMPTY_STRING;

    public Immoscout24()
    {
        super();
        dbHandler = new ObjectDBImmoHandler("D:\\db\\immoscout24.odb");
    }

    @Override
    public void execute()
    {
        uuid = dbHandler.createEntityManager();
        dbHandler.getAllImmoFromDB(uuid);

        // Step 1: open the entry page
        seleniumCrawler.openPage(getStartLink(), false, false);

        // Step 2: remove popup window
        WebElement we = seleniumCrawler.getWebElement(null, SELECT_SHADOW_HOST, true);
        we = seleniumCrawler.getShadowRoot(we);
        List<WebElement> wes = seleniumCrawler.getWebElements(we, SELECT_SHADOW_BUTTON, false);
        wes.get(wes.size() - 1).click();

        // Step 3: fill in the search content

        seleniumCrawler.fillContent("Ludwigsburg", seleniumCrawler.getWebElement(null, SELECT_FORM_INPUT_LOCATION, true));
        seleniumCrawler.waitfor(3);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /*
        we = seleniumCrawler.getWebElement(null, SELECT_SELECTION, true);
        Select select = new Select(we);
        select.selectByValue("BUY");



        we = seleniumCrawler.getWebElement(null, SELECT_SELECTION_TYPE, true);
        select = new Select(we);
        select.selectByValue("HOUSE_RENT");
        */
        /**
         List<WebElement> wes = webDriver.findElements(By.cssSelector(SELECT_ACCEPT));
         if(wes != null && wes.size() == 2)
         {
         logger.info("file elements");
         WebElement we = wes.get(1);
         we.click();
         }
         /**
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
         we.sendKeys("ludwigsburg");
         }

         handlePage(we);

         dbHandler.saveBackToDB(uuid);
         dbHandler.exit();
         //webDriver.quit();
         **/
    }
    @Override
    protected String getStartLink()
    {
        return "https://www.immobilienscout24.de/";
    }

    @Override
    protected String getSiteName()
    {
        return "Immo Scout24";
    }
}
