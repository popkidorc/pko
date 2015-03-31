package org.mybatis.extension.auto.dialect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.extension.auto.annotation.Entity;
import org.mybatis.extension.auto.annotation.Field;
import org.mybatis.extension.auto.annotation.ForeignKey;
import org.mybatis.extension.auto.annotation.Id;
import org.mybatis.extension.auto.sql.MysqlForeignKeySql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

/**
 * 
 * Automatically create table dialect using mysql.
 * 
 * @author popkidorc
 * @creation 2015年3月28日
 * 
 */
public class MysqlDialect extends DatabaseDialect {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Constructor
	 * 
	 * @param connection
	 * @param isShowSql
	 * @param isFormatSql
	 * @param clazzes
	 */
	public MysqlDialect(Connection connection, boolean isShowSql,
			boolean isFormatSql, List<Class<?>> clazzes) {
		super(connection, isShowSql, isFormatSql, clazzes);
	}

	@Override
	public void create() throws SQLException {
		System.out.println("======create====");
		List<String> sqls = new ArrayList<String>();
		List<String> foreignSqls = new ArrayList<String>();
		// Cancel the foreign key constraints , for the create or update
		sqls.add("SET FOREIGN_KEY_CHECKS = 0;");
		for (Class<?> clazz : this.clazzes) {
			StringBuffer sql = new StringBuffer("");
			String idField = "";
			if (!clazz.isAnnotationPresent(Entity.class)) {
				continue;
			}
			Entity tableAnnotion = (Entity) clazz.getAnnotation(Entity.class);
			String tableName = tableAnnotion.tableName();
			if (tableName.equals("")) {
				tableName = ClassUtils.getShortName(clazz).toUpperCase();
			}
			sqls.add("DROP TABLE IF EXISTS " + tableName + ";");
			sql.append("CREATE TABLE " + tableName);
			sql.append("(" + "\n");
			java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
			for (java.lang.reflect.Field field : fields) {
				if (!field.isAnnotationPresent(Field.class)) {
					continue;
				}
				Field fieldAnnotion = (Field) field.getAnnotation(Field.class);
				String fieldName = fieldAnnotion.fieldName();
				String fieldType = fieldAnnotion.type().toString();
				int fieldLength = fieldAnnotion.length();
				boolean fieldNullable = fieldAnnotion.nullable();
				boolean fieldIsId = field.isAnnotationPresent(Id.class);
				String fieldIdType = null;
				if (fieldIsId) {
					fieldIdType = field.getAnnotation(Id.class).idType()
							.toString();
				}
				if (fieldName.equals("")) {
					fieldName = field.getName().toUpperCase();
				}
				if (fieldIsId) {
					fieldType = "INT";
					fieldLength = 11;
					sql.append("\t" + fieldName + " " + fieldType + "("
							+ fieldLength + ") NOT NULL AUTO_INCREMENT");
				} else {
					if (fieldAnnotion.fKey().length > 0) {
						ForeignKey foreignKey = fieldAnnotion.fKey()[0];
						String foreignKeyTableName = foreignKey.tableName();
						String foreignKeyFieldName = foreignKey.fieldName();
						if (!foreignKeyTableName.equals("")
								&& !foreignKeyFieldName.equals("")) {
							String foreign_key_name = "FK_" + tableName + "_"
									+ fieldName;
							StringBuffer foreignSql = new StringBuffer("");
							foreignSql.append("ALTER TABLE " + tableName + " ");
							foreignSql.append("ADD CONSTRAINT "
									+ foreign_key_name + " FOREIGN KEY("
									+ fieldName + ") REFERENCES "
									+ foreignKeyTableName + "("
									+ foreignKeyFieldName + ");\n");
							foreignSqls.add(foreignSql.toString());
							fieldType = "INT";
							fieldLength = 11;
						}
					}
					if (fieldType.endsWith("INT")) {
						fieldLength = fieldLength == 255 ? 11 : fieldLength;
					}
					sql.append("\t" + fieldName + " " + fieldType + "("
							+ fieldLength + ")");
					sql.append(fieldNullable ? "" : " NOT NULL");
				}
				if (idField.equals("")) {
					idField = fieldIsId ? fieldName : "";
				}
				sql.append(",\n");
			}
			if (!idField.equals("")) {
				sql.append("\tPRIMARY KEY (" + idField + ")");
			}
			sql.append("\n);");
			sqls.add(sql.toString());
		}
		sqls.addAll(foreignSqls);
		sqls.add("SET FOREIGN_KEY_CHECKS = 1;");
		this.executeSqls(sqls);
	}

	public void createTest() throws SQLException {
		System.out.println("======create====");
		List<String> sqls = new ArrayList<String>();
		List<String> foreignSqls = new ArrayList<String>();
		// Cancel the foreign key constraints , for the create or update
		sqls.add("SET FOREIGN_KEY_CHECKS = 0;");
		for (Class<?> clazz : this.clazzes) {
			StringBuffer sql = new StringBuffer("");
			String idField = "";
			if (!clazz.isAnnotationPresent(Entity.class)) {
				continue;
			}
			Entity tableAnnotion = (Entity) clazz.getAnnotation(Entity.class);
			String tableName = tableAnnotion.tableName();
			if (tableName.equals("")) {
				tableName = ClassUtils.getShortName(clazz).toUpperCase();
			}
			sqls.add("DROP TABLE IF EXISTS " + tableName + ";");
			sql.append("CREATE TABLE " + tableName + "(\n");
			java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
			for (java.lang.reflect.Field field : fields) {
				if (!field.isAnnotationPresent(Field.class)) {
					continue;
				}
				Field fieldAnnotion = (Field) field.getAnnotation(Field.class);
				String fieldName = fieldAnnotion.fieldName();
				String fieldType = fieldAnnotion.type().toString();
				int fieldLength = fieldAnnotion.length();
				boolean fieldNullable = fieldAnnotion.nullable();
				boolean fieldIsId = field.isAnnotationPresent(Id.class);
				String fieldIdType = null;
				if (fieldIsId) {
					fieldIdType = field.getAnnotation(Id.class).idType()
							.toString();
				}
				if (fieldName.equals("")) {
					fieldName = field.getName().toUpperCase();
				}
				if (fieldIsId) {
					fieldType = "INT";
					fieldLength = 11;
					sql.append("\t" + fieldName + " " + fieldType + "("
							+ fieldLength + ") NOT NULL AUTO_INCREMENT");
				} else {
					if (fieldAnnotion.fKey().length > 0) {
						ForeignKey foreignKey = fieldAnnotion.fKey()[0];
						String foreignKeyTableName = foreignKey.tableName();
						String foreignKeyFieldName = foreignKey.fieldName();
						if (!foreignKeyTableName.equals("")
								&& !foreignKeyFieldName.equals("")) {
							MysqlForeignKeySql mysqlForeignKeySql = new MysqlForeignKeySql(
									fieldIsId, tableName, fieldName,
									foreignKeyTableName, foreignKeyFieldName);
							foreignSqls.add(mysqlForeignKeySql
									.getCreateForeignKey().toString());
							fieldType = "INT";
							fieldLength = 11;
						}
					}
					if (fieldType.endsWith("INT")) {
						fieldLength = fieldLength == 255 ? 11 : fieldLength;
					}
					sql.append("\t" + fieldName + " " + fieldType + "("
							+ fieldLength + ")");
					sql.append(fieldNullable ? "" : " NOT NULL");
				}
				if (idField.equals("")) {
					idField = fieldIsId ? fieldName : "";
				}
				sql.append(",\n");
			}
			if (!idField.equals("")) {
				sql.append("\tPRIMARY KEY (" + idField + ")");
			}
			sql.append("\n);");
			sqls.add(sql.toString());
		}
		sqls.addAll(foreignSqls);
		sqls.add("SET FOREIGN_KEY_CHECKS = 1;");
		for (String string : sqls) {
			System.out.println(string);
		}
	}

	@Override
	public void update() throws SQLException {
		System.out.println("======update====");
		List<String> sqls = new ArrayList<String>();
		List<String> foreignSqls = new ArrayList<String>();
		List<String> alterUpdateSqls = new ArrayList<String>();
		sqls.add("SET FOREIGN_KEY_CHECKS = 0;");
		for (Class<?> clazz : this.clazzes) {
			StringBuffer sql = new StringBuffer("");
			String idField = "";
			if (!clazz.isAnnotationPresent(Entity.class)) {
				continue;
			}
			Entity tableAnnotion = (Entity) clazz.getAnnotation(Entity.class);
			String tableName = tableAnnotion.tableName();
			if (tableName.equals("")) {
				tableName = ClassUtils.getShortName(clazz).toUpperCase();
			}
			sql.append("CREATE TABLE IF NOT EXISTS " + tableName + "(\n");

			java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
			for (java.lang.reflect.Field field : fields) {
				if (!field.isAnnotationPresent(Field.class)) {
					continue;
				}
				Field fieldAnnotion = (Field) field.getAnnotation(Field.class);
				String fieldName = fieldAnnotion.fieldName();
				String fieldType = fieldAnnotion.type().toString();
				int fieldLength = fieldAnnotion.length();
				boolean fieldNullable = fieldAnnotion.nullable();
				boolean fieldIsId = field.isAnnotationPresent(Id.class);
				if (fieldName.equals("")) {
					fieldName = field.getName().toUpperCase();
				}
				if (fieldIsId) {
					sql.append("\t" + fieldName
							+ " INT(11) NOT NULL AUTO_INCREMENT");
				} else {
					if (fieldAnnotion.fKey().length > 0) {
						ForeignKey foreignKey = fieldAnnotion.fKey()[0];
						String foreignKeyTableName = foreignKey.tableName();
						String foreignKeyFieldName = foreignKey.fieldName();
						if (!foreignKeyTableName.equals("")
								&& !foreignKeyFieldName.equals("")) {
							String foreign_key_name = "FK_" + tableName + "_"
									+ fieldName;
							PreparedStatement preparedStatement = this.connection
									.prepareStatement(
											"SELECT COLUMN_NAME FROM information_schema.KEY_COLUMN_USAGE where constraint_name='"
													+ foreign_key_name + "'",
											ResultSet.TYPE_SCROLL_INSENSITIVE,
											ResultSet.CONCUR_READ_ONLY);
							ResultSet resultSet = preparedStatement
									.executeQuery();
							resultSet.last();
							if (resultSet.getRow() == 0) {
								StringBuffer foreignSql = new StringBuffer("");
								foreignSql.append("ALTER TABLE " + tableName
										+ " ");
								foreignSql.append("ADD CONSTRAINT "
										+ foreign_key_name + " FOREIGN KEY("
										+ fieldName + ") REFERENCES "
										+ foreignKeyTableName + "("
										+ foreignKeyFieldName + ");\n");
								foreignSqls.add(foreignSql.toString());
							}
							fieldType = "INT";
							fieldLength = 11;
						}
					}
					if (fieldType.endsWith("INT")) {
						fieldLength = fieldLength == 255 ? 11 : fieldLength;
					}
					sql.append("\t" + fieldName + " " + fieldType + "("
							+ fieldLength + ")");
					sql.append(fieldNullable ? "" : " NOT NULL");
				}
				if (idField.equals("")) {
					idField = fieldIsId ? fieldName : "";
				}
				// Query the table structure, if the column does not exist,
				// don't deal with; if not, alter columns
				String assertField = "DESCRIBE " + tableName + " " + fieldName;
				PreparedStatement preparedStatement = this.connection
						.prepareStatement(assertField,
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);
				ResultSet resultSet;
				try {
					resultSet = preparedStatement.executeQuery();
					resultSet.last();
				} catch (Exception e) {
					logger.warn("alter '" + fieldName
							+ "' column , exception : " + e.getMessage());
					resultSet = null;
				}
				if (resultSet == null || resultSet.getRow() == 0) {
					if (fieldType.equalsIgnoreCase("INT")) {
						fieldLength = fieldLength == 255 ? 11 : fieldLength;
					}
					StringBuffer alterUpdateSql = new StringBuffer("");
					alterUpdateSql.append("ALTER TABLE " + tableName
							+ " ADD COLUMN ");
					alterUpdateSql.append(fieldName + " " + fieldType + "("
							+ fieldLength + ")");
					alterUpdateSql.append(fieldNullable ? "" : " NOT NULL");
					alterUpdateSqls.add(alterUpdateSql.toString());
				}
				sql.append(",\n");
			}
			if (!idField.equals("")) {
				sql.append("\tPRIMARY KEY (" + idField + ")");
			}
			sql.append("\n);\n");
			sqls.add(sql.toString());
		}
		sqls.addAll(foreignSqls);
		sqls.addAll(alterUpdateSqls);
		sqls.add("SET FOREIGN_KEY_CHECKS = 1;");
		this.executeSqls(sqls);
	}

	/**
	 * Execute SQL statements
	 * 
	 * @param sqls
	 * @throws SQLException
	 */
	private void executeSqls(List<String> sqls) throws SQLException {
		Statement statement = this.connection.createStatement();
		for (String sql : sqls) {
			if (this.isShowSql) {
				logger.info("mybatiSql : " + sql);
			}
			statement.addBatch(sql);
		}
		statement.executeBatch();
		this.connection.close();
	}

}
