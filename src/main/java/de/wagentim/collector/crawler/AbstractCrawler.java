package de.wagentim.collector.crawler;

import de.wagentim.collector.entity.Task;
import de.wagentim.collector.sites.main.AbstractSite;
import de.wagentim.collector.sites.main.ISite;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
@Setter
public abstract class AbstractCrawler implements ICrawler{

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Task task;
}
