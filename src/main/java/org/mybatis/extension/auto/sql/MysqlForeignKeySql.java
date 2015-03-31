package org.mybatis.extension.auto.sql;

import java.sql.Connection;

public class MysqlForeignKeySql extends BaseSql {

	private String tableName;

	private String fieldName;

	private String foreignKeyTableName;

	private String foreignKeyFieldName;

	public MysqlForeignKeySql(Connection connection, boolean isFormatSql,
			String tableName, String fieldName, String foreignKeyTableName,
			String foreignKeyFieldName) {
		super(connection, isFormatSql);
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.foreignKeyTableName = foreignKeyTableName;
		this.foreignKeyFieldName = foreignKeyFieldName;
	}

	public StringBuffer getForeignKeySql() {
		this.sql = new StringBuffer();
		if (this.isFormatSql) {
			this.sql.append("\n");
		}
		String foreign_key_name = "FK_" + this.tableName + "_" + fieldName;
		this.sql.append("ALTER TABLE " + this.tableName);
		this.sql.append(" ");
		this.sql.append("ADD CONSTRAINT " + foreign_key_name);
		this.sql.append(" ");
		this.sql.append("FOREIGN KEY(" + this.fieldName + ")");
		this.sql.append(" ");
		this.sql.append("REFERENCES " + this.foreignKeyTableName + "("
				+ this.foreignKeyFieldName + ");");
		return this.sql;
	}
}
