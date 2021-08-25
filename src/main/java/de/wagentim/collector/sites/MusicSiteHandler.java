package de.wagentim.collector.sites;

import de.wagentim.collector.entity.Music;
import org.openqa.selenium.WebDriver;

import com.paulhammant.ngwebdriver.NgWebDriver;

import de.wagentim.collector.db.ObjectDBProductHandler;

import java.util.Iterator;
import java.util.List;

public abstract class MusicSiteHandler extends SiteHandler
{
	protected static final String MUSIC_SAVE_DIR = "F:\\DownloadMusics\\";
	public MusicSiteHandler(WebDriver webDriver, NgWebDriver ngWebDriver)
	{
		super(webDriver, ngWebDriver);
	}

	protected void printMusics(List<Music> musics)
	{
		Iterator<Music> it = musics.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next().toString());
		}
	}
}
