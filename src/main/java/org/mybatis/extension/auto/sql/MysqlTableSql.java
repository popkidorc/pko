package org.mybatis.extension.auto.sql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.extension.auto.annotation.Entity;
import org.mybatis.extension.auto.annotation.Field;
import org.springframework.util.ClassUtils;

public class MysqlTableSql extends BaseSql {

	private String tableName;

	private Class<?> clazz;

	private List<String> foreignSqls;

	public List<String> getForeignSqls() {
		return foreignSqls;
	}

	/**
	 * @param isFormatSql
	 * @param tableName
	 * @param clazz
	 */
	public MysqlTableSql(Connection connection, boolean isFormatSql,
			Class<?> clazz) {
		super(connection, isFormatSql);
		// Prepare parameters
		Entity tableAnnotion = (Entity) clazz.getAnnotation(Entity.class);
		this.tableName = tableAnnotion.tableName().equals("") ? ClassUtils
				.getShortName(clazz).toUpperCase() : tableAnnotion.tableName();
		this.clazz = clazz;
		// Prepare variable
		this.foreignSqls = new ArrayList<String>();
	}

	public StringBuffer getCreateTable() {
		this.sql = new StringBuffer();
		if (this.isFormatSql) {
			this.sql.append("\n");
		}
		List<String> primaryKeys = new ArrayList<String>();
		this.sql.append("CREATE TABLE IF NOT EXISTS " + this.tableName);
		this.sql.append("(");
		if (this.isFormatSql) {
			this.sql.append("\n");
		}

		java.lang.reflect.Field[] fields = this.clazz.getDeclaredFields();
		for (java.lang.reflect.Field field : fields) {
			if (!field.isAnnotationPresent(Field.class)) {
				continue;
			}
			// generate column SQL statement
			MysqlColumnSql mysqlColumnSql = new MysqlColumnSql(this.connection,
					this.isFormatSql, this.tableName, field);
			primaryKeys.addAll(mysqlColumnSql.getPrimaryKeys());
			this.foreignSqls.addAll(mysqlColumnSql.getForeignSqls());
			this.sql.append(mysqlColumnSql.getColumnSql());
		}
		MysqlPrimaryKeySql mysqlPrimaryKeySql = new MysqlPrimaryKeySql(
				this.connection, this.isFormatSql, primaryKeys);
		this.sql.append(mysqlPrimaryKeySql.getPrimaryKeySqls());
		this.sql.deleteCharAt(this.sql.lastIndexOf(","));
		this.sql.append(");");
		return this.sql;
	}

	public StringBuffer getDropTable() {
		this.sql = new StringBuffer();
		if (this.isFormatSql) {
			this.sql.append("\n");
		}
		this.sql.append("DROP TABLE IF EXISTS ");
		this.sql.append(this.tableName);
		this.sql.append(";");
		return this.sql;
	}

}
