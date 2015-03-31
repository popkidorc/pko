package org.mybatis.extension.auto.dialect;

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
	 * Create the table using the clazzes , will delete existing data and tables
	 * 
	 * @throws Exception
	 */
	public void create() throws Exception;

	/**
	 * Update the table using the clazzes, add or update a column does not
	 * delete the existing data and tables, delete a column will delete the data
	 * in the column
	 * 
	 * @throws Exception
	 */
	public void update() throws Exception;
}
