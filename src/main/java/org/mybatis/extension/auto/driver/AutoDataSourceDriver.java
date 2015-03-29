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
import org.mybatis.extension.auto.parse.ParseScanPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoDataSourceDriver {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Type the name of the Automatically create table.
	 */
	private static final String AUTOTYPE_CREATE = "CREATE";

	/**
	 * Type the name of the Automatically update table.
	 */
	private static final String AUTOTYPE_UPDATE = "UPDATE";

	/**
	 * Type the name of the none.
	 */
	private static final String AUTOTYPE_NONE = "NONE";

	// 自动执行类型(create、update、none)
	private String auto;
	// 是否打印sql(true、false)
	private boolean showSql;
	// 测试sql
	private String testSql;
	// 自动建表遍历包
	private String[] autoPackages;
	// 针对oracle数据库的方言,特定的关系数据库生成优化的SQL
	private String dialectClassName;
	// 数据源
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

	public void initialization() throws SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, Exception {
		this.testSql();
		if (this.getAuto().equalsIgnoreCase(AUTOTYPE_NONE)) {
			return;
		}

		List<Class<?>> clazzes = new ArrayList<Class<?>>();
		for (String autoPackage : this.getAutoPackages()) {
			clazzes.addAll(ParseScanPackage
					.getClassesByPackageName(autoPackage));
		}
		IDatabaseDialect databaseDialect = null;
		Class<?> dialectClass = Class.forName(this.getDialectClassName());
		Constructor<?> constructor = dialectClass.getConstructor(
				Connection.class, boolean.class, List.class);
		databaseDialect = (IDatabaseDialect) constructor.newInstance(this
				.getDataSource().getConnection(), this.isShowSql(), clazzes);
		if (this.getAuto().equalsIgnoreCase(AUTOTYPE_CREATE)) {
			databaseDialect.create();
		} else if (this.getAuto().equalsIgnoreCase(AUTOTYPE_UPDATE)) {
			databaseDialect.update();
		}
	}

	private void testSql() throws SQLException {
		Connection connection = this.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		if (this.getTestSql() != null) {
			ResultSet resultSet = statement.executeQuery(this.getTestSql());
			resultSet.last();
			if (resultSet.getRow() != 0) {
				if (this.isShowSql()) {
					logger.info("test database success:" + this.getTestSql());
				} else {
					logger.info("test database success");
				}
			}
		}
	}
}
