package org.mybatis.extension.auto.dialect;

import java.sql.SQLException;

import org.mybatis.extension.auto.driver.AutoDataSourceParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Automatically create table dialect using mysql.
 * 
 * @author popkidorc
 * @date 2015年3月30日
 * 
 */
public class OracleDialect extends DatabaseDialect {

	public OracleDialect(AutoDataSourceParam autoDataSourceParam) {
		super(autoDataSourceParam);
	}

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void create() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void update() throws SQLException {
		// TODO Auto-generated method stub

	}

}
