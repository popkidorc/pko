package org.mybatis.extension.auto.sql;

import java.sql.Connection;

public class MysqlConstraintSql extends BaseSql {

	/**
	 * @param isFormatSql
	 * @param tableName
	 */
	public MysqlConstraintSql(Connection connection, boolean isFormatSql) {
		super(connection, isFormatSql);
	}

	public StringBuffer getEnableConstraintSqls() {
		this.sql = new StringBuffer();
		if (this.isFormatSql) {
			this.sql.append("\n");
		}
		this.sql.append("SET FOREIGN_KEY_CHECKS = ");
		this.sql.append("0;");
		return this.sql;
	}

	public StringBuffer getDisableConstraintSqls() {
		this.sql = new StringBuffer();
		if (this.isFormatSql) {
			this.sql.append("\n");
		}
		this.sql.append("SET FOREIGN_KEY_CHECKS = ");
		this.sql.append("1;");
		return this.sql;
	}
}
