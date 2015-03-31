package org.mybatis.extension.auto.dialect;

import java.sql.Connection;
import java.util.List;

/**
 * 
 * Automatically create table dialect interface
 * 
 * @author popkidorc
 * @creation 2015年3月28日
 * 
 */
public abstract class DatabaseDialect implements IDatabaseDialect {

	protected Connection connection;

	protected boolean isShowSql;

	protected boolean isFormatSql;

	protected List<Class<?>> clazzes;

	public DatabaseDialect(Connection connection, boolean isShowSql,
			boolean isFormatSql, List<Class<?>> clazzes) {
		super();
		this.connection = connection;
		this.isShowSql = isShowSql;
		this.isFormatSql = isFormatSql;
		this.clazzes = clazzes;
	}

}
