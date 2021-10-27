package com.liujie.crm.handller;

import com.liujie.crm.settings.domain.User;
import com.liujie.crm.workbench.dao.ActivityRemarkDao;
import com.liujie.crm.workbench.dao.DicValueDao;
import com.liujie.crm.workbench.domain.DicValue;
import com.liujie.crm.workbench.service.DicService;
import com.liujie.crm.workbench.service.impl.DicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class ContextListener implements ServletContextListener {


//    @Resource
//    DicService service;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {



        ServletContext application = servletContextEvent.getServletContext();
        DicService ds= WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext()).getBean(DicService.class);

        Map<String, List<DicValue>> all = ds.getAll();
        Set<Map.Entry<String, List<DicValue>>> entries = all.entrySet();
        for (Map.Entry<String, List<DicValue>> set : entries) {
            application.setAttribute(set.getKey(), set.getValue());

            System.out.println(set.getKey()+set.getValue());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
