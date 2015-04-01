package org.mybatis.extension.auto.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.extension.auto.annotation.Entity;
import org.mybatis.extension.auto.annotation.Field;
import org.mybatis.extension.auto.driver.AutoDataSourceParam;
import org.springframework.util.ClassUtils;

public class MysqlTableSql extends BaseSql {

	private String tableName;

	private Class<?> clazz;

	private List<String> foreignSqls;

	private List<String> alterSqls;

	public List<String> getForeignSqls() {
		return foreignSqls;
	}

	public List<String> getAlterSqls() {
		return alterSqls;
	}

	public MysqlTableSql(AutoDataSourceParam autoDataSourceParam, Class<?> clazz) {
		super(autoDataSourceParam);
		// Prepare parameters
		Entity tableAnnotion = (Entity) clazz.getAnnotation(Entity.class);
		this.tableName = tableAnnotion.tableName().equals("") ? ClassUtils
				.getShortName(clazz).toUpperCase() : tableAnnotion.tableName();
		this.clazz = clazz;
		// Prepare variable
		this.foreignSqls = new ArrayList<String>();
		this.alterSqls = new ArrayList<String>();
	}

	public StringBuffer getCreateTable() throws SQLException {
		this.sql = new StringBuffer();
		if (this.autoDataSourceParam.isFormatSql()) {
			this.sql.append("\n");
		}
		List<String> primaryKeys = new ArrayList<String>();
		this.sql.append("CREATE TABLE IF NOT EXISTS " + this.tableName);
		this.sql.append("(");
		if (this.autoDataSourceParam.isFormatSql()) {
			this.sql.append("\n");
		}

		java.lang.reflect.Field[] fields = this.clazz.getDeclaredFields();
		for (java.lang.reflect.Field field : fields) {
			if (!field.isAnnotationPresent(Field.class)) {
				continue;
			}
			// generate column SQL statement
			MysqlColumnSql mysqlColumnSql = new MysqlColumnSql(
					this.autoDataSourceParam, this.tableName, field);
			primaryKeys.addAll(mysqlColumnSql.getPrimaryKeys());

			this.foreignSqls.addAll(mysqlColumnSql.getForeignSqls());
			StringBuffer alterSql = mysqlColumnSql.getAlterSql();
			if (alterSql != null)
				this.alterSqls.add(alterSql.toString());
			this.sql.append(mysqlColumnSql.getColumnSql());
		}
		MysqlPrimaryKeySql mysqlPrimaryKeySql = new MysqlPrimaryKeySql(
				this.autoDataSourceParam, primaryKeys);
		this.sql.append(mysqlPrimaryKeySql.getPrimaryKeySqls());
		this.sql.deleteCharAt(this.sql.lastIndexOf(","));
		this.sql.append(");");
		return this.sql;
	}

	public StringBuffer getDropTable() {
		this.sql = new StringBuffer();
		if (this.autoDataSourceParam.isFormatSql()) {
			this.sql.append("\n");
		}
		this.sql.append("DROP TABLE IF EXISTS ");
		this.sql.append(this.tableName);
		this.sql.append(";");
		return this.sql;
	}

}
