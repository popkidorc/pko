package org.mybatis.extension.auto.sql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.extension.auto.annotation.Field;
import org.mybatis.extension.auto.annotation.FieldType;
import org.mybatis.extension.auto.annotation.ForeignKey;
import org.mybatis.extension.auto.annotation.Id;
import org.mybatis.extension.auto.annotation.IdType;

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
	public MysqlColumnSql(Connection connection, boolean isFormatSql,
			String tableName, java.lang.reflect.Field field) {
		super(connection, isFormatSql);
		// Prepare parameters
		this.tableName = tableName;
		Field fieldAnnotion = (Field) field.getAnnotation(Field.class);
		this.fieldName = fieldAnnotion.fieldName().equals("") ? field.getName()
				.toUpperCase() : fieldAnnotion.fieldName();
		this.fieldType = fieldAnnotion.type().toString();
		this.fieldLength = fieldAnnotion.length();
		this.fieldNullable = fieldAnnotion.nullable();
		this.fieldIsId = field.isAnnotationPresent(Id.class);
		this.fieldIdType = this.fieldIsId ? field.getAnnotation(Id.class)
				.idType().toString() : "";
		this.fieldForeignKeys = fieldAnnotion.fKey();
		// Prepare variable
		this.foreignSqls = new ArrayList<String>();
		this.primaryKeys = new ArrayList<String>();
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
		if (this.isFormatSql) {
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
		if (this.isFormatSql) {
			this.sql.append("\n");
		}
		return this.sql;
	}

	public List<String> getForeignSqls() {
		if (this.fieldForeignKeys.length <= 0) {
			return this.foreignSqls;
		}
		String foreignKeyTableName = this.fieldForeignKeys[0].tableName();
		String foreignKeyFieldName = this.fieldForeignKeys[0].fieldName();
		if (!foreignKeyTableName.equals("") && !foreignKeyFieldName.equals("")) {
			MysqlForeignKeySql mysqlForeignKeySql = new MysqlForeignKeySql(
					this.connection, this.isFormatSql, this.tableName,
					this.fieldName, foreignKeyTableName, foreignKeyFieldName);
			this.foreignSqls.add(mysqlForeignKeySql.getForeignKeySql()
					.toString());
		}
		return this.foreignSqls;
	}

	public List<String> getPrimaryKeys() {
		if (this.fieldIsId)
			this.primaryKeys.add(this.fieldName);
		return this.primaryKeys;
	}
}
