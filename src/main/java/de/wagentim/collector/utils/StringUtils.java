package de.wagentim.collector.utils;

import de.wagentim.collector.entity.KeyValuePair;

import java.security.Key;
import java.security.KeyException;
import java.security.PublicKey;
import java.util.StringTokenizer;

public class StringUtils
{
    public static final KeyValuePair parserKeyValue(String input, String deli)
    {
        StringTokenizer st = new StringTokenizer(input, deli);
        KeyValuePair kvp = new KeyValuePair();
        int index = 0;
        while(st.hasMoreElements())
        {
            String txt = st.nextToken().trim();
            if(index == 0)
            {
                kvp.setKey(txt);
            }
            else if(index == 1)
            {
                kvp.setValue(txt);
            }
            else
            {
                break;
            }

            index++;
        }

        return kvp;
    }
}
