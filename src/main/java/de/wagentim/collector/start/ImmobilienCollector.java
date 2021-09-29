package de.wagentim.collector.start;

import de.wagentim.collector.sites.immobilien.EbayKleinAnzeige;

public class ImmobilienCollector extends Collector
{
    public ImmobilienCollector(boolean headless) 
	{
		super(headless);
	}


	public static void main(String[] args)
	{
		ImmobilienCollector sic = new ImmobilienCollector(true);
		sic.run();
	}


	@Override
	void addSites() 
	{
		handlers.add(new EbayKleinAnzeige(ffd, ngwd));
	}
}
