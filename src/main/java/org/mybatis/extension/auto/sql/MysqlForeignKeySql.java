package org.mybatis.extension.auto.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mybatis.extension.auto.driver.AutoDataSourceParam;
import org.mybatis.extension.auto.type.AutoType;

public class MysqlForeignKeySql extends BaseSql {

	private String tableName;

	private String fieldName;

	private String foreignKeyTableName;

	private String foreignKeyFieldName;

	/**
	 * 
	 * Constructor
	 * 
	 * @param autoDataSourceParam
	 * @param tableName
	 * @param fieldName
	 * @param foreignKeyTableName
	 * @param foreignKeyFieldName
	 */
	public MysqlForeignKeySql(AutoDataSourceParam autoDataSourceParam,
			String tableName, String fieldName, String foreignKeyTableName,
			String foreignKeyFieldName) {
		super(autoDataSourceParam);
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.foreignKeyTableName = foreignKeyTableName;
		this.foreignKeyFieldName = foreignKeyFieldName;
	}

	/**
	 * 
	 * Get foreign key SQL statement
	 * 
	 * @return
	 * @throws SQLException
	 */
	public StringBuffer getForeignKeySql() throws SQLException {
		this.sql = new StringBuffer();
		String foreign_key_name = "FK_" + this.tableName + "_" + fieldName;
		if (this.isExistForeignKey(foreign_key_name)) {
			return null;
		}
		if (this.autoDataSourceParam.isFormatSql()) {
			this.sql.append("\n");
		}
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

	/**
	 * 
	 * Whether have the foreign key
	 * 
	 * @param foreign_key_name
	 * @return
	 * @throws SQLException
	 */
	public boolean isExistForeignKey(String foreign_key_name)
			throws SQLException {
		if (this.autoDataSourceParam.getAuto().equalsIgnoreCase(
				AutoType.CREATE.toString())) {
			return false;
		}
		String checkForeignKeySql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE CONSTRAINT_NAME='"
				+ foreign_key_name + "'";
		if (this.autoDataSourceParam.isShowSql()) {
			logger.info("mybatiSql : " + checkForeignKeySql);
		}
		PreparedStatement preparedStatement = this.autoDataSourceParam
				.getConnection().prepareStatement(checkForeignKeySql,
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.last();
		return resultSet.getRow() == 0 ? false : true;
	}
}
