package test.demo.mybatisAuto;

import org.junit.Test;
import org.mybatis.extension.auto.annotation.FieldType;
import org.mybatis.extension.auto.annotation.IdType;
import org.mybatis.extension.auto.sql.MysqlColumnSql;
import org.mybatis.extension.auto.sql.MysqlForeignKeySql;

public class GenerateSql {
	boolean isFormatSql = true;

	@Test
	public void getMysqlForeignKeySql() {
		String tableName = "USER";
		String fieldName = "COMMENT";
		String foreignKeyTableName = "Test_TableName";
		String foreignKeyFieldName = "fieldName";
		MysqlForeignKeySql mysqlForeignKeySql = new MysqlForeignKeySql(
				isFormatSql, tableName, fieldName, foreignKeyTableName,
				foreignKeyFieldName);
		System.out.println(mysqlForeignKeySql.getCreateForeignKey());

	}

	@Test
	public void getMysqlColumnSql() {
		String fieldName = "USERID";
		String fieldType = FieldType.TEXT.toString();
		int fieldLength = 255;
		boolean fieldNullable = false;
		boolean fieldIsId = false;
		String fieldIdType = IdType.AUTO_INCREMENT.toString();

		MysqlColumnSql mysqlColumnSql = new MysqlColumnSql(isFormatSql,
				fieldName, fieldType, fieldLength, fieldNullable, fieldIsId,
				fieldIdType);
		System.out.println(mysqlColumnSql.getCreateColumn());
	}
}
