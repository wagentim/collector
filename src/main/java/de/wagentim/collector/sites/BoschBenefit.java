package de.wagentim.collector.sites;

import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.wagentim.collector.db.ObjectDBProductHandler;
import de.wagentim.collector.entity.Price;
import de.wagentim.collector.entity.Product;
import de.wagentim.collector.utils.IConstants;

import com.paulhammant.ngwebdriver.NgWebDriver;

public class BoschBenefit extends ProductSiteHandler
{
	private final String SELECT_LOGIN_USERNAME = "form";
	private final String SELECT_PRODUCTS = ".product-item";
	private final String SELECT_PRODUCT_NAME = ".product-name";
	private final String SELECT_PRODCUT_PRICE = ".product-price";
	private final String SELECT_CATEGORY_PAGE = ".category-page .active";
	private final String SELECT_CATEGORY_PAGE_LIST = ".category-page .page-item";
	private final String ID_PATTERN = "^https://[-a-zA-Z0-9+&@#%?=~_|!:,.;]*/[-a-zA-Z0-9+&@#%?=~_|!:,.;]*/([0-9]*)-";
	private ObjectDBProductHandler dbHandler;
	private Pattern pattern = null;
	private String uuid = IConstants.TXT_EMPTY_STRING;

	private final String USERNAME = "Bin.Huang@etas.com";
	private final String PASSWORD = "!Xiaobao101";
	
	public BoschBenefit(WebDriver webDriver, NgWebDriver ngWebDriver)
	{
		super(webDriver, ngWebDriver);
		pattern = Pattern.compile(ID_PATTERN);
		dbHandler = new ObjectDBProductHandler("D:\\test\\boschbenefit.odb");
	}

	@Override
	protected String getStartLink()
	{
		return "https://bosch.mitarbeiterangebote.de/login";
	}

	@Override
	protected String getSiteName()
	{
		return "Bosch Benefit";
	}

	@Override
	public void handleSite()
	{
		uuid = dbHandler.createEntityManager();
		dbHandler.getAllProductFromDB(uuid);
		// step 1: open the login page
		openPage(getStartLink(), false, false);
		
		// step 2: submit the username and password
		List<WebElement> userName = webDriver.findElements(By.cssSelector(SELECT_LOGIN_USERNAME));
		System.out.println(userName.size());

		// handlePage(page);
		
		// dbHandler.saveBackToDB(uuid);
		// dbHandler.exist();
	}
	
	public List<Product> getPriceDecreaseProducts()
	{
		uuid = dbHandler.createEntityManager();
		dbHandler.getAllProductFromDB(uuid);
		return dbHandler.findProductWithDecreatePrice();
	}

	private void handlePage(String page)
	{
		List<WebElement> list;
		list = getWebElementWithLink(page, SELECT_PRODUCTS, false);
		for(WebElement we : list)
		{
			handleSingleProd(we);
		}
		
		int currPage = getCurrentPageNum();
		logger.info("Page " + currPage);
		String nextPage = findNextPage(currPage);
		
		if(nextPage != null && !nextPage.isEmpty())
		{
			handlePage(nextPage);
		}
	}

	
	private String findNextPage(int currentPage)
	{
		List<WebElement> pages = webDriver.findElements(By.cssSelector(SELECT_CATEGORY_PAGE_LIST));
		if(pages == null || pages.isEmpty())
		{
			return IConstants.TXT_EMPTY_STRING;
		}
		
		currentPage++;
		
		for(WebElement we : pages)
		{
			try
			{
				int pageNum = Integer.parseInt(we.getText());
				
				if(pageNum == currentPage)
				{
					return we.findElement(By.cssSelector("a")).getAttribute("href");
				}
			}
			catch(Exception e)
			{
				continue;
			}
		}
		
		return IConstants.TXT_EMPTY_STRING;
	}

	private int getCurrentPageNum()
	{
		String page = webDriver.findElement(By.cssSelector(SELECT_CATEGORY_PAGE)).getText();
//		System.out.println(page);
		if(page != null && !page.isEmpty())
		{
			return Integer.parseInt(page);
		}
		return -1;
	}

	private void handleSingleProd(WebElement we)
	{
		WebElement pNameBlock = we.findElement(By.cssSelector(SELECT_PRODUCT_NAME));
		String p_name = pNameBlock.getText();
		String p_link = pNameBlock.findElement(By.cssSelector("a")).getAttribute("href");
		String p_price = we.findElement(By.cssSelector(SELECT_PRODCUT_PRICE)).getText();
		
		// assign the values to products
		Product product = new Product();
		product.setProductName(p_name);
		product.setLink(p_link);
		product.setProductID(getProductID(p_link));
		
		Price price = new Price();
		price.setTime(System.currentTimeMillis());
		parserPrice(p_price, price);
		
		dbHandler.addOrUpdateProduct(product, price);
	}
	
	private int getProductID(String link)
	{
		Matcher m = pattern.matcher(link);
		
		if(m.find())
		{
			return Integer.parseInt(m.group(1));
		}
		
		logger.error("Cannot find product ID with: " + link);
		return -1;
	}
	
	private void parserPrice(String priceStr, Price price)
	{
		String tmp = priceStr.trim();
		int index = tmp.lastIndexOf("��");
		if(index == 0)
		{
			price.setPrice(Double.parseDouble(tmp.substring(1)));
		}
		else
		{
			StringTokenizer st = new StringTokenizer(tmp, " ");
			if(st.countTokens() != 2)
			{
				logger.error("Parser Price error: " + priceStr);
				return;
			}
			
			price.setPrice(Double.parseDouble(st.nextToken().trim().substring(1)));
			price.setOrigPrice(Double.parseDouble(st.nextToken().trim().substring(1)));
		}
	}
}
