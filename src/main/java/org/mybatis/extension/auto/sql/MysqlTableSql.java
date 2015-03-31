package org.mybatis.extension.auto.sql;

public class MysqlTableSql extends BaseSql {

	public MysqlSql(boolean isFormatSql) {
		super();
		this.isFormatSql = isFormatSql;
	}

	// public String getCreateTable(String tableName, String tableComment) {
	// return "CREATE TABLE " + tableName + "(\n";
	// }

	public StringBuffer getCreateTable() {
		if (this.isFormatSql) {
			this.sql.append("\t");
		}
		this.sql.append("CREATE TABLE " + tableName);
		this.sql.append("(");
		if (this.isFormatSql) {
			this.sql.append("\n");
		}
		this.sql.append("ADD CONSTRAINT " + foreign_key_name);
		this.sql.append(" ");
		this.sql.append("FOREIGN KEY(" + fieldName + ")");
		this.sql.append(" ");
		this.sql.append("REFERENCES " + foreignKeyTableName + "("
				+ foreignKeyFieldName + ");");
		if (this.isFormatSql) {
			this.sql.append("\n");
		}
		return this.sql;
	}
}
