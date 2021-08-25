package de.wagentim.collector.utils;

import com.google.gson.Gson;

public final class JsonUtils
{
	
	private static Gson gson = new Gson();
	
	public static<T> T readJsonFile(String file, Class<T> jc)
	{
		return gson.fromJson(file, jc);
	}
	
	
}
