package org.mybatis.extension.auto.listener;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import test.demo.logback.LogbackTest;

public class AutoDatabaseListener implements ServletContextListener {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void contextInitialized(ServletContextEvent servletContext) {
		// TODO Auto-generated method stub
		WebApplicationContext springContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext.getServletContext());
		Object object = springContext
				.getBean(org.mybatis.extension.auto.driver.AutoDataSourceDriver.class);
		try {
			object.getClass().getMethod("initialization").invoke(object);
			logger.info("数据库初使化成功....");
		} catch (IllegalAccessException e) {
			logger.error("IllegalAccessException", e.getCause());
		} catch (IllegalArgumentException e) {
			logger.error("IllegalArgumentException", e.getCause());
		} catch (InvocationTargetException e) {
			logger.error("InvocationTargetException", e.getCause());
		} catch (NoSuchMethodException e) {
			logger.error("NoSuchMethodException", e.getCause());
		} catch (SecurityException e) {
			logger.error("SecurityException", e.getCause());
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
