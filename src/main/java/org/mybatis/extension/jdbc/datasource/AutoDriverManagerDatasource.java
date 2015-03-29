package org.mybatis.extension.jdbc.datasource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class AutoDriverManagerDatasource extends DriverManagerDataSource {

	private String driverClassName;

	public String getDriverClassName() {
		return driverClassName;
	}

	@Override
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
		Assert.hasText(driverClassName,
				"Property 'driverClassName' must not be empty");
		String driverClassNameToUse = driverClassName.trim();
		try {
			Class.forName(driverClassNameToUse, true,
					ClassUtils.getDefaultClassLoader());
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException(
					"Could not load JDBC driver class [" + driverClassNameToUse
							+ "]", ex);
		}
		if (logger.isInfoEnabled()) {
			logger.info("Loaded JDBC driver: " + driverClassNameToUse);
		}
	}

}
