package org.mybatis.extension.auto.sql;

import org.mybatis.extension.auto.driver.AutoDataSourceParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseSql {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected AutoDataSourceParam autoDataSourceParam;

	protected StringBuffer sql;

	/**
	 * @param autoDataSourceParam
	 */
	public BaseSql(AutoDataSourceParam autoDataSourceParam) {
		this.autoDataSourceParam = autoDataSourceParam;
	}

}
