package org.mybatis.extension.auto.listener;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * Automatically build table listener class. Execute
 * {@link org.mybatis.extension.auto.driver.AutoDataSourceDriver} 
 * 'initialization()'.
 * 
 * @author popkidorc
 * @date 2015年3月30日
 * 
 */
public class AutoDatabaseListener implements ServletContextListener {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void contextInitialized(ServletContextEvent servletContext) {
		WebApplicationContext springContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext.getServletContext());
		Object object = springContext
				.getBean(org.mybatis.extension.auto.driver.AutoDataSourceDriver.class);
		try {
			object.getClass().getMethod("initialization").invoke(object);
			logger.info("auto initialize database success");
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
