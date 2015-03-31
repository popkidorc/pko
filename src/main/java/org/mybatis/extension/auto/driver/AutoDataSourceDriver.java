package org.mybatis.extension.auto.driver;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.mybatis.extension.auto.dialect.IDatabaseDialect;
import org.mybatis.extension.auto.parse.EntityParseScanPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Automatically build table driven class.
 * 
 * @author popkidorc
 * @date 2015年3月30日
 * 
 */
public class AutoDataSourceDriver {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Automatically create table type constant : 'CREATE'. Drop tables and data
	 * , and create tables.
	 */
	private static final String AUTOTYPE_CREATE = "CREATE";

	/**
	 * Automatically create table type constant : 'UPDATE'. Don't drop tables
	 * (including columns) and data , and alter new columns.
	 */
	private static final String AUTOTYPE_UPDATE = "UPDATE";

	/**
	 * Automatically create table type constant : 'NONE'. Don't do any
	 * operation.
	 */
	private static final String AUTOTYPE_NONE = "NONE";

	/**
	 * Automatically create table type, optional values: 'create' or 'update' or
	 * 'none'
	 */
	private String auto;

	/**
	 * Whether to print the SQL statement, optional values: 'true' or 'false'
	 */
	private boolean showSql;
	/**
	 * Whether to format the SQL statement, optional values: 'true' or 'false'
	 * TODO
	 */
	private boolean formatSql;

	/**
	 * Test the SQL statement。
	 */
	private String testSql;

	/**
	 * Automatically create table scan package.<b>required</b>
	 */
	private String[] autoPackages;

	/**
	 * According to the different database dialect, generate relational database
	 * table SQL, and executed。<b>required</b>
	 */
	private String dialectClassName;

	/**
	 * DataSource object.<b>required</b>
	 */
	private DataSource dataSource;

	public String getAuto() {
		return auto;
	}

	public void setAuto(String auto) {
		this.auto = auto;
	}

	public boolean isShowSql() {
		return showSql;
	}

	public void setShowSql(boolean showSql) {
		this.showSql = showSql;
	}

	public boolean isFormatSql() {
		return formatSql;
	}

	public void setFormatSql(boolean formatSql) {
		this.formatSql = formatSql;
	}

	public String getTestSql() {
		return testSql;
	}

	public void setTestSql(String testSql) {
		this.testSql = testSql;
	}

	public String[] getAutoPackages() {
		return autoPackages;
	}

	public void setAutoPackages(String[] autoPackages) {
		this.autoPackages = autoPackages;
	}

	public String getDialectClassName() {
		return dialectClassName;
	}

	public void setDialectClassName(String dialectClassName) {
		this.dialectClassName = dialectClassName;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 
	 * AutoDataSourceDriver initialize function. By listener calls.
	 * 
	 * {@link org.mybatis.extension.auto.listener.AutoDatabaseListener}
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public void initialization() throws SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, Exception {
		this.testSql();
		if (this.getAuto().equalsIgnoreCase(AUTOTYPE_NONE)) {
			return;
		}

		List<Class<?>> clazzes = new ArrayList<Class<?>>();
		for (String autoPackage : this.getAutoPackages()) {
			logger.info("EntityParseScanPackage : " + autoPackage);
			logger.info("EntityParseScanPackage size : "
					+ EntityParseScanPackage.getClassName(autoPackage));
			clazzes.addAll(EntityParseScanPackage.getClassName(autoPackage));
		}
		logger.info("EntityParseScanPackage clazzes size : " + clazzes.size());
		for (Class<?> clazz : clazzes) {
			logger.info("EntityParseScanPackage clazzes : " + clazz.getName());
		}
		Class<?> dialectClass = Class.forName(this.getDialectClassName());
		Constructor<?> constructor = dialectClass.getConstructor(
				Connection.class, boolean.class, boolean.class, List.class);
		IDatabaseDialect databaseDialect = (IDatabaseDialect) constructor
				.newInstance(this.getDataSource().getConnection(),
						this.isShowSql(), this.isFormatSql(), clazzes);
		if (this.getAuto().equalsIgnoreCase(AUTOTYPE_CREATE)) {
			databaseDialect.create();
		} else if (this.getAuto().equalsIgnoreCase(AUTOTYPE_UPDATE)) {
			databaseDialect.update();
		}
	}

	/**
	 * Execute the test SQL statement.
	 * 
	 * @throws SQLException
	 */
	private void testSql() throws SQLException {
		Connection connection = this.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		if (this.getTestSql() == null) {
			return;
		}
		ResultSet resultSet = statement.executeQuery(this.getTestSql());
		resultSet.last();
		if (resultSet.getRow() == 0) {
			return;
		}
		if (this.isShowSql()) {
			logger.info("test database success:" + this.getTestSql());
		} else {
			logger.info("test database success");
		}
	}
}
