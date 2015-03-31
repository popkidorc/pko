package org.mybatis.extension.auto.sql;

import java.sql.Connection;
import java.util.List;

public class MysqlPrimaryKeySql extends BaseSql {

	private List<String> primaryKeys;

	/**
	 * @param isFormatSql
	 * @param tableName
	 * @param primaryKeys
	 */
	public MysqlPrimaryKeySql(Connection connection, boolean isFormatSql,
			List<String> primaryKeys) {
		super(connection, isFormatSql);
		this.primaryKeys = primaryKeys;
	}

	public StringBuffer getPrimaryKeySqls() {
		this.sql = new StringBuffer();
		if (primaryKeys.size() > 0) {
			if (this.isFormatSql) {
				this.sql.append("\t");
			}
			this.sql.append("PRIMARY KEY (");
			for (String primaryKey : this.primaryKeys) {
				this.sql.append(primaryKey + ",");
			}
			this.sql.deleteCharAt(this.sql.lastIndexOf(","));
			this.sql.append(")");
			this.sql.append(",");
			if (this.isFormatSql) {
				this.sql.append("\n");
			}
		}
		return this.sql;
	}
}
