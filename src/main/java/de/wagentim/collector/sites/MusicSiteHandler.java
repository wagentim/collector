package de.wagentim.collector.sites;

import de.wagentim.collector.entity.Music;
import org.openqa.selenium.WebDriver;

import com.paulhammant.ngwebdriver.NgWebDriver;

import java.util.Iterator;
import java.util.List;

public abstract class MusicSiteHandler extends SiteHandler
{
	public static final String MUSIC_SOURCE_DIR = "F:\\DownloadMusics\\";
	public static final String MUSIC_TARGET_DIR = "F:\\Musics\\updated";
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
