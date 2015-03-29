package org.mybatis.extension.auto.parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ParseScanPackage {
	public static List<Class<?>> getClassesByPackageName(String packageName)
			throws IOException, URISyntaxException, ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			String pathUrl = resources.nextElement().toURI().getPath();
			dirs.add(new File(pathUrl));
		}
		if (dirs == null || dirs.size() == 0) {
			throw new FileNotFoundException(path);
		}
		for (File directory : dirs)
			classes.addAll(findClasses(directory, packageName));
		return classes;
	}

	private static List<Class<?>> findClasses(File directory, String packageName)
			throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert (!file.getName().contains("."));
				classes.addAll(findClasses(file,
						packageName + '.' + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName
						+ "."
						+ file.getName().substring(0,
								file.getName().length() - 6)));
			}
		}
		return classes;
	}
}
