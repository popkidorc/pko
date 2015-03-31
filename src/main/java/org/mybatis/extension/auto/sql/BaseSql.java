package org.mybatis.extension.auto.sql;

import java.sql.Connection;

public abstract class BaseSql {

	protected Connection connection;

	protected boolean isFormatSql;

	protected StringBuffer sql;

	public BaseSql(Connection connection, boolean isFormatSql) {
		super();
		this.connection = connection;
		this.isFormatSql = isFormatSql;
	}

}
