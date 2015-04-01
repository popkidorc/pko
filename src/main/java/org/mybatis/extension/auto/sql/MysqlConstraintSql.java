package org.mybatis.extension.auto.sql;

import org.mybatis.extension.auto.driver.AutoDataSourceParam;

/**
 * 
 * Constraint status SQL statement for MySql
 * 
 * @author popkidorc
 * @date 2015年4月1日
 * 
 */
public class MysqlConstraintSql extends BaseSql {

	public MysqlConstraintSql(AutoDataSourceParam autoDataSourceParam) {
		super(autoDataSourceParam);
	}

	/**
	 * 
	 * enable the constrain
	 * 
	 * @return
	 */
	public StringBuffer getEnableConstraintSqls() {
		this.sql = new StringBuffer();
		if (this.autoDataSourceParam.isFormatSql()) {
			this.sql.append("\n");
		}
		this.sql.append("SET FOREIGN_KEY_CHECKS = ");
		this.sql.append("1;");
		return this.sql;
	}

	/**
	 * 
	 * disable the constraint
	 * 
	 * @return
	 */
	public StringBuffer getDisableConstraintSqls() {
		this.sql = new StringBuffer();
		if (this.autoDataSourceParam.isFormatSql()) {
			this.sql.append("\n");
		}
		this.sql.append("SET FOREIGN_KEY_CHECKS = ");
		this.sql.append("0;");
		return this.sql;
	}
}
