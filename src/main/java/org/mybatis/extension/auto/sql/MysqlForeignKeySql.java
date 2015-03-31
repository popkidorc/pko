package org.mybatis.extension.auto.sql;

public class MysqlForeignKeySql extends BaseSql {

	private String tableName;

	private String fieldName;

	private String foreignKeyTableName;

	private String foreignKeyFieldName;

	public MysqlForeignKeySql(boolean isFormatSql, String tableName,
			String fieldName, String foreignKeyTableName,
			String foreignKeyFieldName) {
		super(isFormatSql);
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.foreignKeyTableName = foreignKeyTableName;
		this.foreignKeyFieldName = foreignKeyFieldName;
		this.sql = new StringBuffer();
	}

	public StringBuffer getCreateForeignKey() {
		if (this.isFormatSql) {
			this.sql.append("\t");
		}
		String foreign_key_name = "FK_" + tableName + "_" + fieldName;
		this.sql.append("ALTER TABLE " + tableName);
		this.sql.append(" ");
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
