package org.springframework.beans.factory.config.reconstruct;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyPlaceholderConfigurerEncrypt extends
		PropertyPlaceholderConfigurer {

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		String password = props.getProperty("mysql.password");
		if (password != null) {
			// 解密jdbc.password属性值，并重新设置
//			props.setProperty("mysql.password", "258456");
		}
		super.processProperties(beanFactoryToProcess, props);
	}
}
