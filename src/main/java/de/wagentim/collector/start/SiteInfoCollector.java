package de.wagentim.collector.start;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.paulhammant.ngwebdriver.NgWebDriver;

import de.wagentim.collector.entity.Price;
import de.wagentim.collector.entity.Product;
import de.wagentim.collector.sites.DaJiangYou;
import org.openqa.selenium.firefox.FirefoxProfile;

public final class SiteInfoCollector
{
	
	public void run()
	{
		String key = "webdriver.gecko.driver";
		String value = "D:\\libs\\geckodriver.exe";
		System.setProperty(key, value);

		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);

		FirefoxDriver ffd = new FirefoxDriver(options);
//		FirefoxDriver ffd = new FirefoxDriver();
		NgWebDriver ngwd = new NgWebDriver(ffd);
		DaJiangYou djy = new DaJiangYou(ffd, ngwd);
		djy.handleSite();

//		BoschBenefit bb = new BoschBenefit(ffd, ngwd);
//		bb.handleSite();
	}
	
	public void decreasePrice()
	{
		DaJiangYou djy = new DaJiangYou(null, null);
		List<Product> lowProds = djy.getPriceDecreaseProducts();
		
		Iterator<Product> it = lowProds.iterator();
		
		while(it.hasNext())
		{
			Product p = it.next();
			List<Price> ps = p.findLast2Prices();
			double last = ps.get(1).getPrice();
			double before = ps.get(0).getPrice();
			System.out.println(p.getProductName() + "[ " + last + " ]" + " <-> [ " + before + " ]" + " = " + String.format("%.2f", before / last) + "\n" + p.getLink());
		}
	}
	
	public static void main(String[] args)
	{
		SiteInfoCollector sic = new SiteInfoCollector();
		sic.run();
//		sic.decreasePrice();
	}
}
