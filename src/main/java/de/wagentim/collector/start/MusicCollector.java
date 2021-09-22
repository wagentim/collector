package de.wagentim.collector.start;

import de.wagentim.collector.sites.musics.Flitting;

public final class MusicCollector extends Collector
{

	public MusicCollector(boolean headless) 
	{
		super(headless);
	}


	public static void main(String[] args)
	{
		MusicCollector sic = new MusicCollector(false);
		sic.run();
	}


	@Override
	void addSites() 
	{
		handlers.add(new Flitting(ffd, ngwd));
	}
	
}
