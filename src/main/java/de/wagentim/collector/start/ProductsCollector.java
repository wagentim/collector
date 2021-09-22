package de.wagentim.collector.start;

import java.util.Iterator;
import java.util.List;
import de.wagentim.collector.entity.Price;
import de.wagentim.collector.entity.Product;
import de.wagentim.collector.sites.products.DaJiangYou;

public final class ProductsCollector extends Collector
{
	
	public ProductsCollector(boolean headless) 
	{
		super(headless);
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
		ProductsCollector sic = new ProductsCollector(true);
		sic.run();
//		sic.decreasePrice();
	}

	@Override
	protected void addSites() 
	{
		handlers.add(new DaJiangYou(ffd, ngwd));
	}

}
