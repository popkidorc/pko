package org.mybatis.extension.auto.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.extension.auto.annotation.Field;
import org.mybatis.extension.auto.annotation.ForeignKey;
import org.mybatis.extension.auto.annotation.Id;
import org.mybatis.extension.auto.driver.AutoDataSourceParam;
import org.mybatis.extension.auto.type.AutoType;
import org.mybatis.extension.auto.type.FieldType;
import org.mybatis.extension.auto.type.IdType;

/**
 * 
 * Column SQL statement for MySql
 * 
 * @author popkidorc
 * @date 2015年4月1日
 * 
 */
public class MysqlColumnSql extends BaseSql {

	private String tableName;

	private String fieldName;

	private String fieldType;

	private int fieldLength;

	private boolean fieldNullable;

	private boolean fieldIsId;

	private String fieldIdType;

	private ForeignKey[] fieldForeignKeys;

	private List<String> foreignSqls;

	private StringBuffer alterSql;

	private List<String> primaryKeys;

	/**
	 * @param isFormatSql
	 * @param tableName
	 * @param fieldName
	 * @param fieldType
	 * @param fieldLength
	 * @param fieldNullable
	 * @param fieldIsId
	 * @param fieldIdType
	 * @param fieldForeignKeys
	 */
	public MysqlColumnSql(AutoDataSourceParam autoDataSourceParam,
			String tableName, java.lang.reflect.Field field) {
		super(autoDataSourceParam);
		// Prepare parameters
		this.tableName = tableName;
		Field fieldAnnotion = (Field) field.getAnnotation(Field.class);
		this.fieldName = fieldAnnotion.fieldName().equals("") ? field.getName()
				.toUpperCase() : fieldAnnotion.fieldName();
		this.fieldType = fieldAnnotion.type().toString();
		this.fieldLength = this.fieldType.equals(FieldType.INT.toString())
				&& fieldAnnotion.length() > 11 ? 11 : fieldAnnotion.length();
		this.fieldNullable = fieldAnnotion.nullable();
		this.fieldIsId = field.isAnnotationPresent(Id.class);
		this.fieldIdType = this.fieldIsId ? field.getAnnotation(Id.class)
				.idType().toString() : "";
		this.fieldForeignKeys = fieldAnnotion.fKey();
		// handle variable
		if (this.fieldIsId) {
			this.fieldType = FieldType.INT.toString();
			this.fieldLength = 11;
			this.fieldNullable = false;
			if (fieldIdType.equals(IdType.SIMPLE.toString())) {
				this.fieldIdType = "";
			}
		}
	}

	public StringBuffer getColumnSql() {
		this.sql = new StringBuffer();
		if (this.autoDataSourceParam.isFormatSql()) {
			this.sql.append("\t");
		}
		this.sql.append(this.fieldName);
		this.sql.append(" ");
		this.sql.append(this.fieldType + "(" + this.fieldLength + ")");
		this.sql.append(" ");
		this.sql.append(this.fieldNullable ? "" : "NOT NULL");
		this.sql.append(" ");
		this.sql.append(this.fieldIdType);
		this.sql.append(",");
		if (this.autoDataSourceParam.isFormatSql()) {
			this.sql.append("\n");
		}
		return this.sql;
	}

	public List<String> getForeignSqls() throws SQLException {
		this.foreignSqls = new ArrayList<String>();
		if (this.fieldForeignKeys.length <= 0) {
			return foreignSqls;
		}
		String foreignKeyTableName = this.fieldForeignKeys[0].tableName();
		String foreignKeyFieldName = this.fieldForeignKeys[0].fieldName();
		MysqlForeignKeySql mysqlForeignKeySql = new MysqlForeignKeySql(
				this.autoDataSourceParam, this.tableName, this.fieldName,
				foreignKeyTableName, foreignKeyFieldName);
		StringBuffer foreignKeySql = mysqlForeignKeySql.getForeignKeySql();
		if (foreignKeySql != null)
			this.foreignSqls.add(foreignKeySql.toString());
		return this.foreignSqls;
	}

	public StringBuffer getAlterSql() throws SQLException {
		this.alterSql = new StringBuffer();
		if (this.isExistField()) {
			return null;
		}
		if (this.autoDataSourceParam.isFormatSql()) {
			this.alterSql.append("\n");
		}
		this.alterSql.append("ALTER TABLE " + this.tableName);
		this.alterSql.append(" ");
		this.alterSql.append("ADD");
		this.alterSql.append(" ");
		this.alterSql.append("COLUMN ");
		this.alterSql.append(" ");
		this.alterSql.append(this.fieldName);
		this.alterSql.append(" ");
		this.alterSql.append(this.fieldType + "(" + this.fieldLength + ")");
		this.alterSql.append(this.fieldNullable ? "" : " NOT NULL");
		return this.alterSql;
	}

	public List<String> getPrimaryKeys() {
		this.primaryKeys = new ArrayList<String>();
		if (this.fieldIsId)
			this.primaryKeys.add(this.fieldName);
		return this.primaryKeys;
	}

	/**
	 * 
	 * Whether have the foreign key
	 * 
	 * @param foreign_key_name
	 * @return
	 * @throws SQLException
	 */
	public boolean isExistField() throws SQLException {
		if (this.autoDataSourceParam.getAuto().equalsIgnoreCase(
				AutoType.CREATE.toString())) {
			return false;
		}
		// Query the table structure, if the column does not exist,
		// don't deal with; if not, alter columns
		String describeSql = "DESCRIBE " + this.tableName + " "
				+ this.fieldName;
		if (this.autoDataSourceParam.isShowSql()) {
			logger.info("mybatiSql : " + describeSql);
		}
		PreparedStatement preparedStatement = this.autoDataSourceParam
				.getConnection().prepareStatement(describeSql,
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
		ResultSet resultSet;
		try {
			resultSet = preparedStatement.executeQuery();
			resultSet.last();
		} catch (Exception e) {
			logger.warn("alter '" + fieldName + "' column , exception : "
					+ e.getMessage());
			resultSet = null;
		}
		return (resultSet == null || resultSet.getRow() == 0) ? false : true;
	}
}
