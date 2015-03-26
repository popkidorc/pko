package test.demo.mybatis;

import static org.springframework.util.StringUtils.tokenizeToStringArray;

import java.util.Locale;

import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ConfigurableApplicationContext;

import com.mysql.jdbc.StringUtils;

public class MyBatisSource {

	@Test
	public void MyBatisSourceTest() {
		// String typeAliasesPackage = "demo.mybatis.pojo";
		// String[] typeAliasPackageArray = org.springframework.util.StringUtils
		// .tokenizeToStringArray(
		// typeAliasesPackage,
		// ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
		// for (String packageToScan : typeAliasPackageArray) {
		// System.out.println(packageToScan);
		// // configuration.getTypeAliasRegistry().registerAliases(
		// // packageToScan,
		// // typeAliasesSuperType == null ? Object.class
		// // : typeAliasesSuperType);
		// // if (logger.isDebugEnabled()) {
		// // logger.debug("Scanned package: '" + packageToScan
		// // + "' for aliases");
		// // }
		// }
		System.out.println(MyBatisSource.class.getSimpleName());
		System.out.println(MyBatisSource.class.getSimpleName().toLowerCase(Locale.ENGLISH));
	}
}
