package de.wagentim.collector.controller;

import de.wagentim.collector.crawler.CrawlerFactory;
import de.wagentim.collector.crawler.ICrawler;
import de.wagentim.collector.entity.Task;
import de.wagentim.collector.sites.main.ISite;

import java.lang.reflect.InvocationTargetException;

public class MainController {

    private SettingController settingController = null;
    private TaskController taskController = null;

    private static final MainController mainController = new MainController();
    private MainController(){};

    public static synchronized MainController INSTANCE() {
        return mainController;
    }
    public SettingController getSettingController() {
        if(settingController == null) {
            settingController = new SettingController();
        }
        return settingController;
    }

    public TaskController getTaskController() {
        if(taskController == null) {
            taskController = new TaskController();
        }
        return taskController;
    }

    public void execute(Task t) {
        ICrawler crawler = CrawlerFactory.INSTANCE().getCrawler(t);
        ISite site = getSite(t);
        site.setCrawler(crawler);
        site.execute();
    }

    protected ISite getSite(Task task) {
        String clazzName = task.getClazzName();
        ISite site;

        try {
            Class<?> clazz = Class.forName(clazzName);
            site = (ISite) clazz.getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return site;
    }
}
