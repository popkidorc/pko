package org.mybatis.extension.auto.dialect;

import java.sql.SQLException;

/**
 * 
 * Automatically create table dialect interface
 * 
 * @author popkidorc
 * @creation 2015年3月28日
 * 
 */
public interface IDatabaseDialect {

	/**
	 * 
	 * Invoke SQL statement
	 * 
	 * @throws Exception
	 */
	public void invoke() throws SQLException;
}
