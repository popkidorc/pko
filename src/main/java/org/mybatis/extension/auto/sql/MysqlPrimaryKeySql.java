package org.mybatis.extension.auto.sql;

import java.util.List;

import org.mybatis.extension.auto.driver.AutoDataSourceParam;

public class MysqlPrimaryKeySql extends BaseSql {

	private List<String> primaryKeys;

	/**
	 * @param isFormatSql
	 * @param tableName
	 * @param primaryKeys
	 */
	public MysqlPrimaryKeySql(AutoDataSourceParam autoDataSourceParam,
			List<String> primaryKeys) {
		super(autoDataSourceParam);
		this.primaryKeys = primaryKeys;
	}

	public StringBuffer getPrimaryKeySqls() {
		this.sql = new StringBuffer();
		if (primaryKeys.size() <= 0) {
			return this.sql;
		}
		if (this.autoDataSourceParam.isFormatSql()) {
			this.sql.append("\t");
		}
		this.sql.append("PRIMARY KEY (");
		for (String primaryKey : this.primaryKeys) {
			this.sql.append(primaryKey + ",");
		}
		this.sql.deleteCharAt(this.sql.lastIndexOf(","));
		this.sql.append(")");
		this.sql.append(",");
		if (this.autoDataSourceParam.isFormatSql()) {
			this.sql.append("\n");
		}
		return this.sql;
	}
}
