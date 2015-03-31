package org.mybatis.extension.auto.parse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import org.mybatis.extension.auto.annotation.Entity;

/**
 * 
 * Parsing and scan package class
 * 
 * @see org.mybatis.extension.auto.parse.ParseScanPackage
 * 
 * @author popkidorc
 * @creation 2015年3月30日
 * 
 */
public class EntityParseScanPackage {

	/**
	 * 
	 * Access and specify the package and package all the classes
	 * 
	 * @param packageName
	 * @return classes
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> getClassName(String packageName)
			throws ClassNotFoundException, URISyntaxException, IOException {
		List<Class<?>> clazzes = ParseScanPackage.getClassName(packageName,
				true);
		Iterator<Class<?>> clazzIterable = clazzes.iterator();
		while (clazzIterable.hasNext()) {
			if (!clazzIterable.next().isAnnotationPresent(Entity.class)) {
				clazzIterable.remove();
			}
		}
		return clazzes;
	}
}
