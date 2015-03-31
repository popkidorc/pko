package org.mybatis.extension.auto.sql;

public abstract class BaseSql {

	protected boolean isFormatSql;

	protected StringBuffer sql;

	public BaseSql(boolean isFormatSql) {
		super();
		this.isFormatSql = isFormatSql;
	}

}
