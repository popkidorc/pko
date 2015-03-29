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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

/**
 * 
 * Automatically create or update table dialect using mysql.
 * 
 * @author popkidorc
 * @creation 2015年3月28日
 * 
 */
public class MysqlDialect extends DatabaseDialect {

	public MysqlDialect(Connection connection, boolean isShowSql,
			List<Class<?>> clazzes) {
		super(connection, isShowSql, clazzes);
	}

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void create() throws SQLException {
		System.out.println("======create====");
		List<String> sqls = new ArrayList<String>();
		List<String> foreigns = new ArrayList<String>();
		// 取消外键约束，方便create或者update
		sqls.add("SET FOREIGN_KEY_CHECKS=0;");
		for (Class<?> clazz : this.clazzes) {
			StringBuffer sql = new StringBuffer("");
			String idField = "";
			if (clazz.isAnnotationPresent(Entity.class)) {
				Entity tableAnnotion = (Entity) clazz
						.getAnnotation(Entity.class);
				String tableName = tableAnnotion.tableName();
				if (tableName.equals("")) {
					tableName = ClassUtils.getShortName(clazz).toUpperCase();
					// throw new Exception("类：[" + clazz.getName() +
					// "]未指定正确的表名!");
				}
				sqls.add("DROP TABLE IF EXISTS " + tableName + ";");
				sql.append("CREATE TABLE " + tableName + "(\n");
				java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
				for (java.lang.reflect.Field field : fields)
					if (field.isAnnotationPresent(Field.class)) {
						Field fieldAnnotion = (Field) field
								.getAnnotation(Field.class);
						String fieldName = fieldAnnotion.fieldName();
						String fieldType = fieldAnnotion.type().toString();
						int fieldLength = fieldAnnotion.length();
						boolean fieldNullable = fieldAnnotion.nullable();
						if (fieldName.equals("")) {
							fieldName = field.getName().toUpperCase();
							// throw new Exception("类：" + clazz.getName() +
							// "的属性["
							// + field.getName() + "]未指定正确的字段名!");
						}
						boolean hasIdAnnotion = field
								.isAnnotationPresent(Id.class);
						if (hasIdAnnotion) {
							sql.append("\t" + fieldName
									+ " INT(11) NOT NULL AUTO_INCREMENT");
						} else {
							if (fieldAnnotion.fKey().length > 0) {
								ForeignKey foreignKey = fieldAnnotion.fKey()[0];
								String foreignKeyTableName = foreignKey
										.tableName();
								String foreignKeyFieldName = foreignKey
										.fieldName();
								if (!foreignKeyTableName.equals("")
										&& !foreignKeyFieldName.equals("")) {
									String foreign_key_name = "FK_" + tableName
											+ "_" + fieldName;
									StringBuffer foreign = new StringBuffer("");
									foreign.append("ALTER TABLE " + tableName
											+ " ");
									foreign.append("ADD CONSTRAINT "
											+ foreign_key_name
											+ " FOREIGN KEY(" + fieldName
											+ ") REFERENCES "
											+ foreignKeyTableName + "("
											+ foreignKeyFieldName + ");\n");
									foreigns.add(foreign.toString());
									sql.append("\t" + fieldName + " INT(11)");
								}
							} else {
								sql.append("\t" + fieldName + " " + fieldType);
								if (fieldType.endsWith("INT"))
									sql.append("("
											+ (fieldLength == 255 ? 11
													: fieldLength) + ")");
								else if (fieldType.equals("VARCHAR")) {
									sql.append("(" + fieldLength + ")");
								}
							}
							sql.append(!fieldNullable ? " NOT NULL" : "");
						}
						if (idField.equals("")) {
							idField = hasIdAnnotion ? fieldName : "";
						}
						sql.append(",\n");
					}
				if (!idField.equals("")) {
					sql.append("\tPRIMARY KEY (" + idField + ")");
				}
				sql.append("\n);");
				sqls.add(sql.toString());
			}
		}
		sqls.addAll(foreigns);
		sqls.add("SET FOREIGN_KEY_CHECKS=1;");
		this.executeSqls(sqls);
	}

	@Override
	public void update() throws SQLException {
		System.out.println("======update====");
		List<String> sqls = new ArrayList<String>();
		List<String> foreigns = new ArrayList<String>();
		List<String> alterUpdates = new ArrayList<String>();
		sqls.add("SET FOREIGN_KEY_CHECKS=0;");
		for (Class<?> clazz : this.clazzes) {
			StringBuffer sql = new StringBuffer("");
			String idField = "";
			if (clazz.isAnnotationPresent(Entity.class)) {
				Entity tableAnnotion = (Entity) clazz
						.getAnnotation(Entity.class);
				String tableName = tableAnnotion.tableName();
				if (tableName.equals("")) {
					tableName = ClassUtils.getShortName(clazz).toUpperCase();
					// throw new DaoException("类:[" + clazz.getName()
					// + "]未指明正确的表名!");
				}
				sql.append("CREATE TABLE IF NOT EXISTS " + tableName + "(\n");
				java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
				for (java.lang.reflect.Field field : fields)
					if (field.isAnnotationPresent(Field.class)) {
						Field fieldAnnotion = (Field) field
								.getAnnotation(Field.class);
						String fieldName = fieldAnnotion.fieldName();
						if (fieldName.equals("")) {
							fieldName = field.getName().toUpperCase();
							// throw new DaoException("类：" + clazz.getName()
							// + "的属性[" + field.getName() + "]未指定正确的字段名!");
						}
						boolean hasIdAnnotion = field
								.isAnnotationPresent(Id.class);
						if (hasIdAnnotion) {
							sql.append("\t" + fieldName
									+ " INT(11) NOT NULL AUTO_INCREMENT");
						} else {
							ForeignKey foreignKey = fieldAnnotion.fKey()[0];
							String foreignKeyTableName = foreignKey.tableName();
							String foreignKeyFieldName = foreignKey.fieldName();
							if (!foreignKeyTableName.equals("")) {
								String foreign_key_name = "FK_" + tableName
										+ "_" + fieldName;
								try {
									PreparedStatement preparedStatement = this.connection
											.prepareStatement(
													"SELECT COLUMN_NAME FROM information_schema.KEY_COLUMN_USAGE where constraint_name='"
															+ foreign_key_name
															+ "'", 1004, 1007);
									ResultSet resultSet = preparedStatement
											.executeQuery();
									resultSet.last();
									if (resultSet.getRow() == 0) {
										StringBuffer foreign = new StringBuffer(
												"");
										foreign.append("ALTER TABLE "
												+ tableName + " ");
										foreign.append("ADD CONSTRAINT "
												+ foreign_key_name
												+ " FOREIGN KEY(" + fieldName
												+ ") REFERENCES "
												+ foreignKeyTableName + "("
												+ foreignKeyFieldName + ");\n");
										foreigns.add(foreign.toString());
									}
									sql.append("\t" + fieldName + " INT(11)");
									sql.append(!fieldAnnotion.nullable() ? " NOT NULL"
											: "");
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								sql.append("\t" + fieldName + " "
										+ fieldAnnotion.type().toString());
								if (fieldAnnotion.type().toString()
										.equals("INT"))
									sql.append("("
											+ (fieldAnnotion.length() == 255 ? 11
													: fieldAnnotion.length())
											+ ")");
								else if (fieldAnnotion.type().toString()
										.equals("VARCHAR")) {
									sql.append("(" + fieldAnnotion.length()
											+ ")");
								}
								sql.append(!fieldAnnotion.nullable() ? " NOT NULL"
										: "");
							}
						}
						if (idField.equals(""))
							idField = hasIdAnnotion ? fieldName : "";
						try {
							String assertField = "DESCRIBE " + tableName + " "
									+ fieldName;
							PreparedStatement preparedStatement = this.connection
									.prepareStatement(assertField, 1004, 1007);
							ResultSet resultSet = preparedStatement
									.executeQuery();
							resultSet.last();
							if (resultSet.getRow() == 0) {
								String type = fieldAnnotion.type().toString();
								int length = fieldAnnotion.length();
								String typeSql = "";
								if (type.equalsIgnoreCase("INT"))
									typeSql = "INT("
											+ (length == 255 ? 11 : length)
											+ ") ";
								else if (type.equalsIgnoreCase("VARCHAR"))
									typeSql = "VARCHAR(" + length + ") ";
								else {
									typeSql = fieldAnnotion.type().toString()
											+ " ";
								}
								alterUpdates.add("ALTER TABLE "
										+ tableName
										+ " ADD COLUMN "
										+ fieldName
										+ " "
										+ typeSql
										+ (fieldAnnotion.nullable() ? ""
												: "NOT NULL"));
							}
						} catch (Exception localException1) {
						}
						sql.append(",\n");
					}
				if (!idField.equals("")) {
					sql.append("\tPRIMARY KEY (" + idField + ")");
				}
				sql.append("\n);\n");
				sqls.add(sql.toString());
			}
		}
		sqls.addAll(foreigns);
		sqls.addAll(alterUpdates);
		sqls.add("SET FOREIGN_KEY_CHECKS=1;");
		this.executeSqls(sqls);
	}

	private void executeSqls(List<String> sqls) throws SQLException {
		Statement statement = this.connection.createStatement();
		for (String sql : sqls) {
			if (this.isShowSql) {
				logger.info("mybatiSql:" + sql);
			}
			statement.addBatch(sql);
		}
		statement.executeBatch();
		this.connection.close();
	}
}
