package org.mybatis.extension.auto.dialect;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.extension.auto.annotation.Entity;
import org.mybatis.extension.auto.driver.AutoDataSourceParam;
import org.mybatis.extension.auto.sql.MysqlConstraintSql;
import org.mybatis.extension.auto.sql.MysqlTableSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	public MysqlDialect(AutoDataSourceParam autoDataSourceParam) {
		super(autoDataSourceParam);
	}

	@Override
	protected void create() throws SQLException {
		System.out.println("====create====");
		List<String> sqls = new ArrayList<String>();
		// Cancel the foreign key constraints , for the create or update
		MysqlConstraintSql mysqlConstraintSql = new MysqlConstraintSql(
				this.autoDataSourceParam);
		sqls.add(mysqlConstraintSql.getDisableConstraintSqls().toString());
		for (Class<?> clazz : this.autoDataSourceParam.getClazzes()) {
			if (!clazz.isAnnotationPresent(Entity.class)) {
				continue;
			}
			MysqlTableSql mysqlTableSql = new MysqlTableSql(
					this.autoDataSourceParam, clazz);
			sqls.add(mysqlTableSql.getDropTable().toString());
			sqls.add(mysqlTableSql.getCreateTable().toString());
			sqls.addAll(mysqlTableSql.getForeignSqls());
		}
		sqls.add(mysqlConstraintSql.getEnableConstraintSqls().toString());
		this.executeSqls(sqls);
	}

	//
	@Override
	protected void update() throws SQLException {
		System.out.println("====update====");
		List<String> sqls = new ArrayList<String>();
		// Cancel the foreign key constraints , for the create or update
		MysqlConstraintSql mysqlConstraintSql = new MysqlConstraintSql(
				this.autoDataSourceParam);
		sqls.add(mysqlConstraintSql.getDisableConstraintSqls().toString());
		for (Class<?> clazz : this.autoDataSourceParam.getClazzes()) {
			if (!clazz.isAnnotationPresent(Entity.class)) {
				continue;
			}
			MysqlTableSql mysqlTableSql = new MysqlTableSql(
					this.autoDataSourceParam, clazz);
			sqls.add(mysqlTableSql.getCreateTable().toString());
			sqls.addAll(mysqlTableSql.getAlterSqls());
			sqls.addAll(mysqlTableSql.getForeignSqls());
		}
		sqls.add(mysqlConstraintSql.getEnableConstraintSqls().toString());
		this.executeSqls(sqls);
	}

	/**
	 * Execute SQL statements
	 * 
	 * @param sqls
	 * @throws SQLException
	 */
	private void executeSqls(List<String> sqls) throws SQLException {
		Statement statement = this.autoDataSourceParam.getConnection()
				.createStatement();
		for (String sql : sqls) {
			if (this.autoDataSourceParam.isShowSql()) {
				logger.info("mybatiSql : " + sql);
			}
			statement.addBatch(sql);
		}
		statement.executeBatch();
		this.autoDataSourceParam.getConnection().close();
	}

}
