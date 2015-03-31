package org.mybatis.extension.auto.dialect;

import java.sql.Connection;
import java.util.List;

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

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Constructor
	 * 
	 * @param connection
	 * @param isShowSql
	 * @param isFormatSql
	 * @param clazzes
	 */
	public OracleDialect(Connection connection, boolean isShowSql,
			boolean isFormatSql, List<Class<?>> clazzes) {
		super(connection, isShowSql, isFormatSql, clazzes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() throws Exception {
		// TODO Auto-generated method stub

	}

}
