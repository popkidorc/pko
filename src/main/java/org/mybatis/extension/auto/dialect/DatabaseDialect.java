package org.mybatis.extension.auto.dialect;

import java.sql.SQLException;

import org.mybatis.extension.auto.driver.AutoDataSourceParam;
import org.mybatis.extension.auto.type.AutoType;

/**
 * 
 * Automatically create table dialect interface
 * 
 * @author popkidorc
 * @creation 2015年3月28日
 * 
 */
public abstract class DatabaseDialect implements IDatabaseDialect {

	protected AutoDataSourceParam autoDataSourceParam;

	/**
	 * 
	 * Constructor
	 * 
	 * @param autoDataSourceParam
	 */
	public DatabaseDialect(AutoDataSourceParam autoDataSourceParam) {
		this.autoDataSourceParam = autoDataSourceParam;
	}

	@Override
	public void invoke() throws SQLException {
		if (this.autoDataSourceParam.getAuto().equalsIgnoreCase(
				AutoType.CREATE.toString()))
			this.create();
		if (this.autoDataSourceParam.getAuto().equalsIgnoreCase(
				AutoType.UPDATE.toString()))
			this.update();
	}

	/**
	 * 
	 * Create the table using the clazzes , will delete existing data and tables
	 * 
	 * @throws SQLException
	 */
	protected abstract void create() throws SQLException;

	/**
	 * 
	 * Update the table using the clazzes, add or update a column does not
	 * delete the existing data and tables, delete a column will delete the data
	 * in the column
	 * 
	 * @throws SQLException
	 */
	protected abstract void update() throws SQLException;
}
